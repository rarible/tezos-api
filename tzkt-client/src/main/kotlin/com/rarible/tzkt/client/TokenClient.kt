package com.rarible.tzkt.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.rarible.tzkt.meta.MetaService
import com.rarible.tzkt.model.Part
import com.rarible.tzkt.model.Token
import com.rarible.tzkt.model.TokenMeta
import com.rarible.tzkt.royalties.RoyaltiesHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.web.reactive.function.client.WebClient

class TokenClient(
    webClient: WebClient,
    val metaService: MetaService,
    val royaltyHander: RoyaltiesHandler
) : BaseClient(webClient) {

    suspend fun token(contract: String, id: String): Token {
        val tokens = invoke<List<Token>> {
            it.path(BASE_PATH)
                .queryParam("contract", contract)
                .queryParam("tokenId", id)
                .queryParam("token.standard", "fa2")
        }
        return tokens.first()
    }

    suspend fun tokenMeta(contract: String, id: String): TokenMeta {
        val tokens = invoke<List<Token>> {
            it.path(BASE_PATH)
                .queryParam("contract", contract)
                .queryParam("tokenId", id)
        }
        val token = tokens.first()
        return metaService.meta(token)
    }

    suspend fun tokens(size: Int?, continuation: Long?, sortAsc: Boolean = true): List<Token> {
        val tokens = invoke<List<Token>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.standard", "fa2")
                .apply {
                    size?.let { queryParam("limit", it) }
                    continuation?.let { queryParam("offset.cr", it) }
                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    queryParam(sorting, "id")
                    queryParam("metadata.artifactUri.null", "false")
                }
        }
        return tokens
    }

    suspend fun tokens(ids: List<String>): List<Token> {
        val tokens = coroutineScope {
            ids
                .map {
                    val raw = it.split(":")
                    async { token(raw[0], raw[1]) }
                }
                .awaitAll()
        }
        return tokens
    }

    suspend fun royalty(contract: String, id: String): List<Part> {
        return royaltyHander.processRoyalties(contract, id)
    }

    companion object {
        const val BASE_PATH = "v1/tokens"
    }
}
