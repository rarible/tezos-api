package com.rarible.tzkt.client

import com.rarible.tzkt.meta.MetaService
import com.rarible.tzkt.model.ItemId
import com.rarible.tzkt.model.Page
import com.rarible.tzkt.model.Part
import com.rarible.tzkt.model.TimestampItemIdContinuation
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
        // This is optimization for getting items by id
        // Grouping by contract
        val groups = ids.map { ItemId.parse(it) }.groupBy({ it.contract }, { it.tokenId })
            .map { (contract, ids) ->

                // chunking to prevent huge number of ids in the GET request
                ids.chunked(100).map { Pair(contract, it) }
            }.flatten()

        val tokensDeferred = async {
            groups
                .map { (contract, ids) -> async { tokens(contract, ids, loadMeta) } }
                .awaitAll()
                .flatten()
        }

        // We need balances to determine which items were deleted
        val balancesDeferred = async {
            if (checkBalance) {
                groups
                    .map { (contract, ids) -> async { balances(contract, ids) } }
                    .awaitAll()
                    .flatten()
            } else emptyList()
        }
        val tokens = tokensDeferred.await()

        // If balance exists -> item wasn't burned
        val burned = balancesDeferred.await().associateBy({ it.itemId() }, { it.balance })
        if (burned.isNotEmpty()) {
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
        loadMeta: Boolean = false
    ): Page<Token> {
        var parsedContinuation = continuation?.let { TimestampItemIdContinuation.parse(it) }

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
        val slice = tokens.subList(0, min(size, tokens.size)).map { token ->
            if (loadMeta) {
                token.copy(meta = metaService.meta(token))
            } else token
        }

        val nextContinuation = if (slice.size >= size) {
            val token = slice.last()
            TimestampItemIdContinuation(token.lastTime!!.toInstant(), "${token.itemId()}")
        } else null

        return Page(
            items = tokens,
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

    suspend fun balances(contract: String, ids: List<String>, loadMeta: Boolean = false): List<TokenBalanceShort> {
        return invoke<List<TokenBalanceShort>> {
            it.path(BASE_PATH_BALANCES)
                .queryParam("token.contract", contract)
                .queryParam("token.tokenId.in", ids.joinToString(","))
                .queryParam("account.in", Tezos.NULL_ADDRESSES_STRING)
                .queryParam("balance.gt", 0)
                .queryParam("select", "token.contract.address,token.tokenId,balance")
        }
    }

    fun directionEqual(asc: Boolean) = if (asc) "gte" else "lte"
    fun direction(asc: Boolean) = if (asc) "gt" else "lt"

    fun List<Token>.firstOrNotFound(itemId: String): Token {
        return this.firstOrNull() ?: throw TzktNotFound("Token ${itemId} wasn't found")
    }

    private suspend fun toPage(tokensIds: List<String>, nextContinuation: Any?, checkBalance: Boolean): Page<Token> {
        val tokens = tokens(tokensIds, false, checkBalance)
        return Page(
            items = tokens,
            continuation = nextContinuation?.let { it.toString() }
        )
    }

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
    }
}
