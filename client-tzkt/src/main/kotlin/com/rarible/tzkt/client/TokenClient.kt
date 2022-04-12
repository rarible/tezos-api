package com.rarible.tzkt.client

import com.rarible.tzkt.models.Token
import org.springframework.web.reactive.function.client.WebClient

class TokenClient(
    webClient: WebClient
) : BaseClient(webClient) {

    suspend fun token(contract: String, id: String): Token {
        val tokens = invoke<List<Token>> {
            it.path("v1/tokens")
                .queryParam("contract", contract)
                .queryParam("tokenId", id)
        }
        return tokens.first()
    }
}
