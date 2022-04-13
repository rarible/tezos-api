package com.rarible.tzkt.client

import com.rarible.tzkt.model.Token
import org.springframework.web.reactive.function.client.WebClient

class TokenClient(
    webClient: WebClient
) : BaseClient(webClient) {

    val basePath = "v1/tokens"
    suspend fun token(contract: String, id: String): Token {
        val tokens = invoke<List<Token>> {
            it.path(basePath)
                .queryParam("contract", contract)
                .queryParam("tokenId", id)
                .queryParam("token.standard", "fa2")
        }
        return tokens.first()
    }

    suspend fun tokens(size: Int?, continuation: Long?, sortAsc: Boolean = true): List<Token> {
        val tokens = invoke<List<Token>> {
            it.path(basePath)
                .queryParam("token.standard", "fa2")
                .apply {
                    if (size != null) {
                        it.queryParam("limit", size)
                    }
                    if(continuation != null){
                        it.queryParam("offset.cr", continuation)
                    }
                    if(!sortAsc){
                        it.queryParam("sort.desc", "id")
                    }
                }
        }
        return tokens
    }
}
