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
import java.time.Instant

class TokenClient(
    webClient: WebClient,
    val metaService: MetaService,
    val royaltyHander: RoyaltiesHandler
) : BaseClient(webClient) {

    suspend fun token(itemId: String, loadMeta: Boolean = false): Token {
        val parsed = ItemId.parse(itemId)
        val tokens = invoke<List<Token>> {
            it.path(BASE_PATH)
                .queryParam("contract", parsed.contract)
                .queryParam("tokenId", parsed.tokenId)
                .queryParam("token.standard", "fa2")
        }
        val result = tokens.firstOrNotFound(itemId)

        // enrich with parsed meta
        return when (loadMeta) {
            true -> result.copy(meta = metaService.meta(result))
            else -> result
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

    suspend fun tokens(ids: List<String>, loadMeta: Boolean = false): List<Token> {
        // This is optimization for getting items by id
        // Grouping by contract
        val groups = ids.map { ItemId.parse(it) }.groupBy({ it.contract }, { it.tokenId })
            .map { (contract, ids) ->

                // chunking to prevent huge number of ids in the GET request
                ids.chunked(100).map { Pair(contract, it) }
            }.flatten()
        val tokens = coroutineScope {
            groups
                .map { (contract, ids) -> async { tokens(contract, ids, loadMeta) } }
                .awaitAll()
                .flatten()
        }
        return tokens
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

        val firstTokens = invoke<List<Token>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.standard", "fa2")
                .apply {
                    queryParam("limit", size)

                    // if we have continuation we need to add additional params
                    parsedContinuation?.let {
                        queryParam("lastTime.${directionEqual(sortAsc)}", it.date)
                    }

                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    queryParam(sorting, "lastTime,id")
                    queryParam("metadata.artifactUri.null", "false")
                }
        }

        // In case of continuation we need to find internal id of token by contract:tokenId
        val tokens: List<Token> = parsedContinuation?.let { continuation ->
            var continuationItem = firstTokens.byItemId(continuation.id)
            if (continuationItem == null) {
                continuationItem = findTzktToken(continuation.id, continuation.date, size, sortAsc)
            }
            tokensByLastUpdateAndId(continuation.date, continuationItem.id, size, sortAsc)
        } ?: firstTokens

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

    suspend fun findTzktToken(itemId: String, lastDate: Instant, size: Int, sortAsc: Boolean = true): Token {
        var tzktToken: Token? = null
        var lastId: Int? = null
        while (tzktToken == null) {
            val tokens = tokensByLastUpdateAndId(lastDate, lastId, size, sortAsc)
            tzktToken = tokens.byItemId(itemId)
            lastId = tokens.last().id
        }
        return tzktToken
    }

    suspend fun tokensByLastUpdateAndId(lastDate: Instant, lastId: Int?, size: Int, sortAsc: Boolean = true) =
        invoke<List<Token>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.standard", "fa2")
                .apply {
                    queryParam("limit", size)
                    queryParam("lastTime.${directionEqual(sortAsc)}", lastDate)
                    lastId?.let { queryParam("id.${direction(sortAsc)}", lastId) }
                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    queryParam(sorting, "lastTime,id")
                    queryParam("metadata.artifactUri.null", "false")
                }
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
        return toPage(tokensIds, nextContinuation)
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
        return toPage(tokensIds, nextContinuation)
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
        return toPage(tokensIds, nextContinuation)
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

    fun directionEqual(asc: Boolean) = if (asc) "gte" else "lte"
    fun direction(asc: Boolean) = if (asc) "gt" else "lt"

    fun List<Token>.firstOrNotFound(itemId: String): Token {
        return this.firstOrNull() ?: throw TzktNotFound("Token ${itemId} wasn't found")
    }

    private suspend fun toPage(tokensIds: List<String>, nextContinuation: Any?): Page<Token> {
        val tokens = tokens(tokensIds)
        return Page(
            items = tokens,
            continuation = nextContinuation?.let { it.toString() }
        )
    }

    companion object {
        const val BASE_PATH = "v1/tokens"
        const val BASE_PATH_BALANCES = "v1/tokens/balances"
        const val BASE_PATH_TRANSFERS = "v1/tokens/transfers"
        const val DEFAULT_SIZE = 1000
    }

    fun List<Token>.byItemId(itemId: String) = this.firstOrNull { item -> item.itemId() == itemId }
}
