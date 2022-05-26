package com.rarible.tzkt.client

import com.rarible.tzkt.meta.MetaService
import com.rarible.tzkt.model.ItemId
import com.rarible.tzkt.model.LevelIdContinuation
import com.rarible.tzkt.model.Page
import com.rarible.tzkt.model.Part
import com.rarible.tzkt.model.Token
import com.rarible.tzkt.model.TokenMeta
import com.rarible.tzkt.model.TzktNotFound
import com.rarible.tzkt.royalties.RoyaltiesHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.web.reactive.function.client.WebClient
import java.lang.Integer.min

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

    suspend fun royalty(itemId: String): List<Part> {
        val parsed = ItemId.parse(itemId)
        return royaltyHander.processRoyalties(parsed.contract, parsed.tokenId)
    }

    fun direction(asc: Boolean) = if (asc) "gt" else "lt"

    fun List<Token>.firstOrNotFound(itemId: String): Token {
        return this.firstOrNull() ?: throw TzktNotFound("Token ${itemId} wasn't found")
    }

    companion object {
        const val BASE_PATH = "v1/tokens"
        const val DEFAULT_SIZE = 1000
    }
}
