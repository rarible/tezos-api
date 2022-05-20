package com.rarible.tzkt.client

import com.rarible.tzkt.model.ItemId
import com.rarible.tzkt.model.OwnershipId
import com.rarible.tzkt.model.Page
import com.rarible.tzkt.model.TokenBalance
import com.rarible.tzkt.model.TzktNotFound
import org.springframework.web.reactive.function.client.WebClient

class OwnershipClient(
    webClient: WebClient
) : BaseClient(webClient) {

    suspend fun ownershipById(ownershipId: String): TokenBalance {
        val id = OwnershipId.parse(ownershipId)
        val ownership = invoke<List<TokenBalance>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("account", id.owner)
                .queryParam("token.contract", id.contract)
                .queryParam("token.tokenId", id.tokenId)
        }
        return ownership.firstOrNull() ?: throw TzktNotFound("Ownership ${ownershipId} wasn't found")
    }

    suspend fun ownershipsByToken(itemId: String, size: Int = DEFAULT_SIZE, continuation: String?, sortAsc: Boolean = true): Page<TokenBalance> {
        val parsed = ItemId.parse(itemId)
        val ownerships = invoke<List<TokenBalance>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.contract", parsed.contract)
                .queryParam("token.tokenId", parsed.tokenId)
                .apply {
                    queryParam("limit", size)
                    continuation?.let { queryParam("offset.cr", it) }
                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    queryParam(sorting, "id")
                }
        }
        return Page.Get(
            items = ownerships,
            size = size,
            last = { it.id.toString() }
        )
    }

    companion object {
        const val BASE_PATH = "v1/tokens/balances"
        const val DEFAULT_SIZE = 1000
    }
}
