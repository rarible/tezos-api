package com.rarible.tzkt.client

import com.rarible.tzkt.model.TokenBalance
import org.springframework.web.reactive.function.client.WebClient

class OwnershipClient(
    webClient: WebClient
) : BaseClient(webClient) {

    suspend fun ownership(contract: String, tokenId: String, owner: String): TokenBalance {
        val ownership = invoke<List<TokenBalance>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("account", owner)
                .queryParam("token.contract", contract)
                .queryParam("token.tokenId", tokenId)
        }
        return ownership.first()
    }

    companion object {
        const val BASE_PATH = "v1/tokens/balances"
    }
}
