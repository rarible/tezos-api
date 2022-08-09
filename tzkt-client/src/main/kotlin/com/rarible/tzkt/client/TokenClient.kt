package com.rarible.tzkt.client

import com.rarible.tzkt.meta.MetaService
import com.rarible.tzkt.model.ItemId
import com.rarible.tzkt.model.Page
import com.rarible.tzkt.model.Part
import com.rarible.tzkt.model.TimestampIdContinuation
import com.rarible.tzkt.model.Token
import com.rarible.tzkt.model.TokenActivity
import com.rarible.tzkt.model.TokenBalanceShort
import com.rarible.tzkt.model.TokenMeta
import com.rarible.tzkt.model.TzktNotFound
import com.rarible.tzkt.royalties.RoyaltiesHandler
import com.rarible.tzkt.utils.Tezos
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.web.reactive.function.client.WebClient
import java.lang.Integer.min
import java.math.BigInteger

class TokenClient(
    webClient: WebClient,
    val metaService: MetaService,
    val royaltyHander: RoyaltiesHandler
) : BaseClient(webClient) {

    suspend fun token(itemId: String, loadMeta: Boolean = false, checkBalance: Boolean = true) =
        tokens(listOf(itemId), loadMeta, checkBalance).firstOrNotFound(itemId)

    suspend fun tokens(ids: List<String>, loadMeta: Boolean = false, checkBalance: Boolean = true) = coroutineScope {
        val groups = groupIds(ids.distinct())
        val tokens = groups
            .map { (contract, ids) -> async { tokens(contract, ids, loadMeta) } }
            .awaitAll()
            .flatten()

        // We need balances to determine which items were deleted
        val balances = if (checkBalance) {
            val tzktIds = tokens.map { it.id }
            burnedByIds(tzktIds)
        } else emptyMap()

        adjustBurnedTokens(tokens, balances)
    }

    suspend fun tokenMeta(itemId: String): TokenMeta {
        val parsed = ItemId.parse(itemId)
        val tokens = invoke<List<Token>> {
            it.path(BASE_PATH)
                .queryParam("contract", parsed.contract)
                .queryParam("tokenId", parsed.tokenId)
                .queryParam("token.standard", "fa2")
        }
        val token = tokens.firstOrNotFound(itemId)
        return metaService.meta(token)
    }

    suspend fun isNft(itemId: String): Boolean? {
        val parsed = ItemId.parse(itemId)
        val token = invoke<List<Token>> {
            it.path(BASE_PATH)
                .queryParam("contract", parsed.contract)
                .queryParam("tokenId", parsed.tokenId)
                .queryParam("token.standard", "fa2")
        }.firstOrNotFound(itemId)
        return token.metadata?.let { it["artifactUri"] != null }
    }

    suspend fun allTokensByLastUpdate(
        size: Int = DEFAULT_SIZE,
        continuation: String?,
        sortAsc: Boolean = false,
        loadMeta: Boolean = false,
        checkBalance: Boolean = true
    ): Page<Token> {
        val parsedContinuation = continuation?.let { TimestampIdContinuation.parse(it) }

        val tokens = parsedContinuation?.let { continuation ->
            // we need to identify internal id for correct continuation
            val prevToken = token(continuation.id, false, false)
            invoke<List<Token>> { builder ->
                builder.path(BASE_PATH)
                    .apply {
                        queryParam("token.standard", "fa2")
                        queryParam("limit", size)
                        queryParam("lastTime.${directionEqual(sortAsc)}", continuation.date)
                        queryParam("id.${direction(sortAsc)}", prevToken.id)
                        val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                        queryParam(sorting, "lastTime,id")
                        queryParam("metadata.artifactUri.null", "false")
                    }
            }
        } ?: invoke { builder ->
            builder.path(BASE_PATH)
                .apply {
                    queryParam("token.standard", "fa2")
                    queryParam("limit", size)
                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    queryParam(sorting, "lastTime,id")
                    queryParam("metadata.artifactUri.null", "false")
                }
        }

        // enrich with parsed meta
        val enrichedSlice = tokens.subList(0, min(size, tokens.size)).map { token ->
            if (loadMeta) {
                token.copy(meta = metaService.meta(token))
            } else token
        }

        // We need balances to determine which items were deleted
        // Run this check only if size < REQUEST_LIMIT
        val balancedSlice = if (checkBalance) {
            val ids = enrichedSlice.map { it.id }
            adjustBurnedTokens(enrichedSlice, burnedByIds(ids))
        } else {
            enrichedSlice
        }

        val nextContinuation = if (balancedSlice.size >= size) {
            val token = balancedSlice.last()
            TimestampIdContinuation(token.lastTime!!.toInstant(), "${token.itemId()}")
        } else null

        return Page(
            items = balancedSlice,
            continuation = nextContinuation?.let { it.toString() }
        )
    }

    suspend fun tokensByOwner(owner: String, size: Int = DEFAULT_SIZE, continuation: String?): Page<Token> {
        val balances = invoke<List<TokenBalanceShort>> { builder ->
            builder.path(BASE_PATH_BALANCES)
                .apply {
                    queryParam("account", owner)
                    queryParam("token.standard", "fa2")
                    queryParam("limit", size)
                    continuation?.let { queryParam("id.lt", it) }
                    queryParam("balance.gt", 0)
                    queryParam("sort.desc", "id")
                    queryParam("select", "id,token.contract.address,token.tokenId")
                }
        }

        val nextContinuation = if (balances.size == size) {
            balances.last().id
        } else null
        val tokensIds = balances.mapNotNull { it.itemId() }
        return toPage(tokensIds, nextContinuation, false)
    }

    suspend fun tokensByCreator(creator: String, size: Int = DEFAULT_SIZE, continuation: String?): Page<Token> {
        val transfers = invoke<List<TokenActivity>> { builder ->
            builder.path(BASE_PATH_TRANSFERS)
                .apply {
                    queryParam("from.null", "true")
                    queryParam("to", creator)
                    queryParam("token.standard", "fa2")
                    queryParam("limit", size)
                    continuation?.let { queryParam("id.lt", it) }
                    queryParam("sort.desc", "id")
                }
        }
        val nextContinuation = if (transfers.size == size) {
            transfers.last().id
        } else null
        val tokensIds = transfers.mapNotNull { it.token?.itemId() }
        return toPage(tokensIds, nextContinuation, true)
    }

    suspend fun tokensByCollection(collection: String, size: Int = DEFAULT_SIZE, continuation: String?): Page<Token> {
        val balances = invoke<List<TokenBalanceShort>> { builder ->
            builder.path(BASE_PATH_BALANCES)
                .apply {
                    queryParam("token.contract", collection)
                    queryParam("limit", size)
                    continuation?.let { queryParam("id.lt", it) }
                    queryParam("balance.gt", 0)
                    queryParam("account.ni", Tezos.NULL_ADDRESSES_STRING)
                    queryParam("sort.desc", "id")
                    queryParam("select", "id,token.contract.address,token.tokenId")
                }
        }
        val nextContinuation = if (balances.size == size) {
            balances.last().id
        } else null
        val tokensIds = balances.mapNotNull { it.itemId() }
        return toPage(tokensIds, nextContinuation, false)
    }

    suspend fun royalty(itemId: String): List<Part> {
        val parsed = ItemId.parse(itemId)
        return royaltyHander.processRoyalties(parsed.contract, parsed.tokenId)
    }

    suspend fun tokenCount(contract: String): BigInteger {
        val lastTokenId = invoke<List<String>> {
            it.path(BASE_PATH)
                .queryParam("contract", contract)
                .queryParam("sort.desc", "tokenId")
                .queryParam("limit", "1")
                .queryParam("select", "tokenId")
        }
        return if (lastTokenId.isNullOrEmpty()) {
            BigInteger.ZERO
        } else {
            lastTokenId.first().toBigInteger()
        }
    }

    suspend fun tokens(contract: String, ids: List<String>, loadMeta: Boolean = false): List<Token> {
        val tokens = invoke<List<Token>> {
            it.path(BASE_PATH)
                .queryParam("contract", contract)
                .queryParam("tokenId.in", ids.joinToString(","))
                .queryParam("token.standard", "fa2")
        }

        // enrich with parsed meta
        return when (loadMeta) {
            true -> tokens.map { it.copy(meta = metaService.meta(it)) }
            else -> tokens
        }
    }

    suspend fun balancesByIds(ids: List<Int>): List<TokenBalanceShort> {
        return invoke<List<TokenBalanceShort>> {
            it.path(BASE_PATH_BALANCES)
                .queryParam("token.id.in", ids.joinToString(","))
                .queryParam("account.in", Tezos.NULL_ADDRESSES_STRING)
                .queryParam("balance.gt", 0)
                .queryParam("select", "token.contract.address,token.tokenId,balance")
        }
    }

    private fun List<Token>.firstOrNotFound(itemId: String): Token {
        return this.firstOrNull() ?: throw TzktNotFound("Token ${itemId} wasn't found")
    }

    private suspend fun burnedByIds(ids: List<Int>) = coroutineScope {
        val groups = ids.chunked(CHUNK_LIMIT)
        val balancesDeferred =
            groups
                .map { async { balancesByIds(it) } }
                .awaitAll()
                .flatten()

        // itemId -> burned count
        balancesDeferred.associateBy({ it.itemId() }, { it.balance })
    }

    private fun adjustBurnedTokens(tokens: List<Token>, burned: Map<String, String?>): List<Token> {
        return if (burned.isNotEmpty()) {
            tokens.map {
                if (burned.containsKey(it.itemId())) {
                    val supply = getBigInt(it.totalSupply)
                    val burned = getBigInt(burned[it.itemId()])
                    it.copy(totalSupply = supply.minus(burned).abs().toString())
                } else {
                    it
                }
            }
        } else {
            tokens
        }
    }

    private suspend fun toPage(tokensIds: List<String>, nextContinuation: Any?, checkBalance: Boolean): Page<Token> {
        val tokens = tokens(tokensIds, false, checkBalance)
        return Page(
            items = tokens,
            continuation = nextContinuation?.let { it.toString() }
        )
    }

    // This is optimization for getting items by id
    // Grouping by contract
    private fun groupIds(ids: List<String>) = ids.map { ItemId.parse(it) }.groupBy({ it.contract }, { it.tokenId })
        .map { (contract, ids) ->

            // chunking to prevent huge number of ids in the GET request
            ids.chunked(CHUNK_LIMIT).map { Pair(contract, it) }
        }.flatten()

    private fun getBigInt(value: String?) = try {
        BigInteger(value)
    } catch (ex: Exception) {
        BigInteger.ZERO
    }

    companion object {
        const val BASE_PATH = "v1/tokens"
        const val BASE_PATH_BALANCES = "v1/tokens/balances"
        const val BASE_PATH_TRANSFERS = "v1/tokens/transfers"
        const val DEFAULT_SIZE = 1000
        const val CHUNK_LIMIT = 100
    }
}
