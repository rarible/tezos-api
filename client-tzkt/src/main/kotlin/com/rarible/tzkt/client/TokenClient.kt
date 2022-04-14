package com.rarible.tzkt.client

import com.rarible.tzkt.model.Token
import org.springframework.web.reactive.function.client.WebClient

class TokenClient(
    webClient: WebClient
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

    suspend fun tokens(size: Int?, continuation: Long?, sortAsc: Boolean = true): List<Token> {
        val tokens = invoke<List<Token>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.standard", "fa2")
                .apply {
                    size?.let { queryParam("limit", it) }
                    continuation?.let { queryParam("offset.cr", it) }
                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    queryParam(sorting, "id")
                }
        }
        return tokens
    }

    companion object {
        const val BASE_PATH = "v1/tokens"
    }
}
