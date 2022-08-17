package com.rarible.tzkt.client

import com.rarible.tzkt.config.TzktSettings
import com.rarible.tzkt.model.BatchBody
import com.rarible.tzkt.model.ItemId
import com.rarible.tzkt.model.OwnershipId
import com.rarible.tzkt.model.Page
import com.rarible.tzkt.model.TimestampIdContinuation
import com.rarible.tzkt.model.TokenBalance
import com.rarible.tzkt.model.TzktNotFound
import com.rarible.tzkt.utils.Tezos
import com.rarible.tzkt.utils.Tezos.NULL_ADDRESSES_STRING
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.web.reactive.function.client.WebClient

class OwnershipClient(
    webClient: WebClient,
    val settings: TzktSettings
) : BaseClient(webClient) {

    suspend fun ownershipsAll(continuation: String?, size: Int): Page<TokenBalance> {
        val parsedContinuation = continuation?.let { TimestampIdContinuation.parse(it) }
        val ownerships: List<TokenBalance> = parsedContinuation?.let {
            val prevOwnership = balanceByOwnership(parsedContinuation.id)
            invoke { builder ->
                builder.path(BASE_PATH)
                    .queryParam("token.standard", "fa2")
                    .queryParam("account.ni", NULL_ADDRESSES_STRING)
                    .queryParam("balance.gt", 0)
                    .queryParam("lastTime.le", parsedContinuation.date)
                    .queryParam("id.lt", prevOwnership.id)
                    .queryParam("sort.desc", "lastTime,id")
                    .queryParam("limit", size)
            }
        } ?: invoke { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.standard", "fa2")
                .queryParam("account.ni", NULL_ADDRESSES_STRING)
                .queryParam("balance.gt", 0)
                .queryParam("sort.desc", "lastTime,id")
                .queryParam("limit", size)
        }

        val nextContinuation = if (ownerships.size >= size) {
            val ownership = ownerships.last()
            TimestampIdContinuation(ownership.lastTime!!.toInstant(), "${ownership.ownershipId()}")
        } else null

        return Page(
            items = ownerships,
            continuation = nextContinuation?.let { it.toString() }
        )
    }

    suspend fun ownershipsByIds(ownershipIds: List<String>) = coroutineScope {
        when(settings.useOwnershipsBatch) {
            true -> {
                invokePost({
                    it.path(BASE_PATH)
                }, BatchBody(ownershipIds.distinct()))
            }
            else -> ownershipIds.map { async { ownershipById(it) } }.awaitAll()
        }
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

    suspend fun ownershipsByToken(
        itemId: String,
        size: Int = DEFAULT_SIZE,
        continuation: String?,
        sortAsc: Boolean = false,
        sortOnFirstLevel: Boolean = false,
        removeEmptyBalances: Boolean = true
    ): Page<TokenBalance> {
        val parsed = ItemId.parse(itemId)
        val parsedContinuation = continuation?.let { TimestampIdContinuation.parse(it) }
        val ownerships: List<TokenBalance> = parsedContinuation?.let {
            val prevOwnership = balanceByOwnership(parsedContinuation.id)
            invoke { builder ->
                builder.path(BASE_PATH)
                    .queryParam("token.contract", parsed.contract)
                    .queryParam("token.tokenId", parsed.tokenId)
                    .queryParam("account.ni", NULL_ADDRESSES_STRING)
                    .queryParam("id.${direction(sortAsc)}", prevOwnership.id)
                    .queryParam("lastTime.${directionEqual(sortAsc)}", parsedContinuation.date)
                    .apply {
                        val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                        if (sortOnFirstLevel) {
                            queryParam(sorting, "firstLevel,id")
                        } else {
                            queryParam(sorting, "lastTime,id")
                        }
                        if (removeEmptyBalances)
                            queryParam("balance.gt", 0)
                    }
                    .queryParam("limit", size)
            }
        } ?: invoke { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.contract", parsed.contract)
                .queryParam("token.tokenId", parsed.tokenId)
                .queryParam("account.ni", NULL_ADDRESSES_STRING)
                .apply {
                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    if (sortOnFirstLevel) {
                        queryParam(sorting, "firstLevel,id")
                    } else {
                        queryParam(sorting, "lastTime,id")
                    }
                    if (removeEmptyBalances)
                        queryParam("balance.gt", 0)
                }
                .queryParam("limit", size)
        }

        val nextContinuation = if (ownerships.size >= size) {
            val ownership = ownerships.last()
            TimestampIdContinuation(ownership.lastTime!!.toInstant(), "${ownership.ownershipId()}")
        } else null

        return Page(
            items = ownerships,
            continuation = nextContinuation?.let { it.toString() }
        )
    }

    private suspend fun balanceByOwnership(id: String): TokenBalance {
        val ownershipId = OwnershipId.parse(id)
        return invoke<List<TokenBalance>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.contract", ownershipId.contract)
                .queryParam("token.tokenId", ownershipId.tokenId)
                .queryParam("account", ownershipId.owner)
        }.firstOrNotFound(id)
    }

    private fun List<TokenBalance>.firstOrNotFound(id: String): TokenBalance {
        return this.firstOrNull() ?: throw TzktNotFound("Ownership ${id} wasn't found")
    }

    companion object {
        const val BASE_PATH = "v1/tokens/balances"
        const val DEFAULT_SIZE = 1000
    }
}
