package com.rarible.tzkt.client

import com.rarible.tzkt.models.Token
import org.springframework.web.reactive.function.client.WebClient

class OwnershipClient(
    webClient: WebClient
) : BaseClient(webClient) {

    val basePath = "v1/tokens/balances"
    suspend fun ownership(contract: String, tokenId: String, owner: String): Token {
        val tokens = invoke<List<Token>> {
            it.path(basePath)
                .queryParam("account", owner)
                .queryParam("token.contract", contract)
                .queryParam("token.tokenId", tokenId)
        }
        return tokens.first()
    }
}
