package com.rarible.tzkt.client

import com.rarible.tzkt.model.ItemId
import com.rarible.tzkt.model.OwnershipId
import com.rarible.tzkt.model.Page
import com.rarible.tzkt.model.TokenBalance
import com.rarible.tzkt.model.TzktNotFound
import com.rarible.tzkt.utils.Tezos
import com.rarible.tzkt.utils.Tezos.NULL_ADDRESSES_STRING
import org.springframework.web.reactive.function.client.WebClient

class OwnershipClient(
    webClient: WebClient
) : BaseClient(webClient) {

    suspend fun ownershipsAll(continuation: String?, size: Int): Page<TokenBalance> {
        val ownerships = invoke<List<TokenBalance>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("sort.desc", "id")
                .queryParam("token.standard", "fa2")
                .queryParam("account.ni", NULL_ADDRESSES_STRING)
                .queryParam("balance.gt", 0)
                .queryParam("limit", size)
                .apply {
                    continuation?.let { queryParam("offset.cr", it) }
                }
        }
        return Page.Get(
            items = ownerships,
            size = size,
            last = { it.id.toString() }
        )
    }

    suspend fun ownershipById(ownershipId: String): TokenBalance {
        val id = OwnershipId.parse(ownershipId)
        // check burn address
        if (Tezos.NULL_ADDRESSES.contains(id.owner)) {
            throw TzktNotFound("Ownership ${ownershipId} wasn't found")
        }
        val ownership = invoke<List<TokenBalance>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("account", id.owner)
                .queryParam("token.contract", id.contract)
                .queryParam("token.tokenId", id.tokenId)
        }
        return ownership.firstOrNull() ?: throw TzktNotFound("Ownership ${ownershipId} wasn't found")
    }

    suspend fun ownershipsByToken(itemId: String, size: Int = DEFAULT_SIZE, continuation: String?, sortAsc: Boolean = true, sortOnFirstLevel: Boolean = false): Page<TokenBalance> {
        val parsed = ItemId.parse(itemId)
        val ownerships = invoke<List<TokenBalance>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.contract", parsed.contract)
                .queryParam("token.tokenId", parsed.tokenId)
                .queryParam("account.ni", NULL_ADDRESSES_STRING)
                .queryParam("balance.gt", 0)
                .apply {
                    queryParam("limit", size)
                    continuation?.let { queryParam("offset.cr", it) }
                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    if(sortOnFirstLevel)
                        queryParam(sorting, "firstLevel")
                    else
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
