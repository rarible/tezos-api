package com.rarible.tzkt.client

import com.rarible.tzkt.model.TokenBalance
import org.springframework.web.reactive.function.client.WebClient

class OwnershipClient(
    webClient: WebClient
) : BaseClient(webClient) {

    suspend fun ownershipById(contract: String, tokenId: String, owner: String): TokenBalance {
        val ownership = invoke<List<TokenBalance>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("account", owner)
                .queryParam("token.contract", contract)
                .queryParam("token.tokenId", tokenId)
        }
        return ownership.first()
    }

    suspend fun ownershipsByToken(contract: String, tokenId: String): List<TokenBalance> {
        val ownership = invoke<List<TokenBalance>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.contract", contract)
                .queryParam("token.tokenId", tokenId)
        }
        return ownership
    }

    companion object {
        const val BASE_PATH = "v1/tokens/balances"
    }
}
