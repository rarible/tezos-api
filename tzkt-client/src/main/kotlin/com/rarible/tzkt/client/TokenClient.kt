package com.rarible.tzkt.client

import com.rarible.tzkt.meta.MetaService
import com.rarible.tzkt.model.ItemId
import com.rarible.tzkt.model.LevelIdContinuation
import com.rarible.tzkt.model.Page
import com.rarible.tzkt.model.Part
import com.rarible.tzkt.model.Token
import com.rarible.tzkt.model.TokenActivity
import com.rarible.tzkt.model.TokenBalance
import com.rarible.tzkt.model.TokenMeta
import com.rarible.tzkt.model.TzktNotFound
import com.rarible.tzkt.royalties.RoyaltiesHandler
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

    suspend fun token(itemId: String): Token {
        val parsed = ItemId.parse(itemId)
        val tokens = invoke<List<Token>> {
            it.path(BASE_PATH)
                .queryParam("contract", parsed.contract)
                .queryParam("tokenId", parsed.tokenId)
                .queryParam("token.standard", "fa2")
        }
        val result = tokens.firstOrNotFound(itemId)

        // enrich with parsed meta
        return result.copy(meta = metaService.meta(result))
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
        sortAsc: Boolean = true
    ): Page<Token> {
        var parsedContinuation = continuation?.let {LevelIdContinuation.parse(it)}

        // First request
        val firstTokens = invoke<List<Token>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.standard", "fa2")
                .apply {
                    queryParam("limit", size)

                    // if we have continuation we need to add additional params
                    parsedContinuation?.let {
                        queryParam("lastLevel", it.level)
                        queryParam("id.${direction(sortAsc)}", it.id)
                    }

                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    queryParam(sorting, "lastLevel")
                    queryParam("metadata.artifactUri.null", "false")
                }
        }

        // Second request
        val secondTokens = if (firstTokens.size < size && parsedContinuation != null) {
            invoke<List<Token>> { builder ->
                builder.path(BASE_PATH)
                    .queryParam("token.standard", "fa2")
                    .apply {
                        queryParam("limit", size)
                        queryParam("lastLevel.${direction(sortAsc)}", parsedContinuation.level)
                        val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                        queryParam(sorting, "lastLevel")
                        queryParam("metadata.artifactUri.null", "false")
                    }

                // enrich with parsed meta
            }
        } else emptyList()

        val tokens = firstTokens + secondTokens
        // enrich with parsed meta
        val slice = tokens.subList(0, min(size, tokens.size)).map { token -> token.copy(meta = metaService.meta(token)) }
        val nextContinuation = if (slice.size >= size) {
            val token = slice.last()
            LevelIdContinuation(token.lastLevel!!, token.id)
        } else null

        return Page(
            items = tokens,
            continuation = nextContinuation?.let { it.toString() }
        )
    }

    suspend fun tokens(ids: List<String>): List<Token> {
        val tokens = coroutineScope {
            ids
                .map {
                    async { token(it) }
                }
                .awaitAll()
        }
        return tokens
    }

    suspend fun tokensByOwner(owner: String, size: Int = DEFAULT_SIZE, continuation: String?): Page<Token> {
        val balances = invoke<List<TokenBalance>> { builder ->
            builder.path(BASE_PATH_BALANCES)
                .apply {
                    queryParam("account", owner)
                    queryParam("token.standard", "fa2")
                    queryParam("limit", size)
                    continuation?.let { queryParam("id.lt", it) }
                    queryParam("sort.desc", "id")
                }
        }

        val nextContinuation = if (balances.size >= size) {
            balances.last().id
        } else null
        val tokensIds = balances.mapNotNull { it.token?.itemId() }
        return toPage(tokensIds, nextContinuation)
    }

    suspend fun tokensByCreator(creator: String, size: Int = DEFAULT_SIZE, continuation: String?): Page<Token> {
        val activities = invoke<List<TokenActivity>> { builder ->
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
        val slice = activities.subList(0, min(size, activities.size))
        val nextContinuation = if (slice.size >= size) {
            slice.last().id
        } else null
        val tokensIds = activities.mapNotNull { it.token?.itemId() }
        return toPage(tokensIds, nextContinuation)
    }

    suspend fun tokensByCollection(collection: String, size: Int = DEFAULT_SIZE, continuation: String?): Page<Token> {
        val activities = invoke<List<Token>> { builder ->
            builder.path(BASE_PATH)
                .apply {
                    queryParam("contract", collection)
                    queryParam("limit", size)
                    continuation?.let { queryParam("id.lt", it) }
                    queryParam("sort.desc", "id")
                    queryParam("select", "id,contract,tokenId")
                }
        }
        val slice = activities.subList(0, min(size, activities.size))
        val nextContinuation = if (slice.size >= size) {
            slice.last().id
        } else null
        val tokensIds = activities.mapNotNull { it.itemId() }
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
                .queryParam("sort.desc","tokenId")
                .queryParam("limit", "1")
                .queryParam("select","tokenId")
        }
        return if(lastTokenId.isNullOrEmpty()){
            BigInteger.ZERO
        } else{
            lastTokenId.first().toBigInteger()
        }
    }

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
}
