package com.rarible.tzkt.client

import com.rarible.tzkt.model.TokenBalance
import org.springframework.web.reactive.function.client.WebClient

class OwnershipClient(
    webClient: WebClient
) : BaseClient(webClient) {

    val basePath = "v1/tokens/balances"
    suspend fun ownership(contract: String, tokenId: String, owner: String): TokenBalance {
        val ownership = invoke<List<TokenBalance>> {
            it.path(basePath)
                .queryParam("account", owner)
                .queryParam("token.contract", contract)
                .queryParam("token.tokenId", tokenId)
        }
        return ownership.first()
    }
}
