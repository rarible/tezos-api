package com.rarible.tzkt.client

import com.rarible.tzkt.model.ActivityType
import com.rarible.tzkt.model.Page
import com.rarible.tzkt.model.TokenActivity
import com.rarible.tzkt.model.TypedTokenActivity
import com.rarible.tzkt.model.TzktActivityContinuation
import com.rarible.tzkt.utils.Tezos.NULL_ADDRESSES
import com.rarible.tzkt.utils.Tezos.NULL_ADDRESSES_STRING
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.web.reactive.function.client.WebClient
import java.time.OffsetDateTime

class TokenActivityClient(
    webClient: WebClient
) : BaseClient(webClient) {

    suspend fun getActivitiesAll(
        types: List<ActivityType> = emptyList(),
        size: Int = DEFAULT_SIZE,
        continuation: String?,
        sortAsc: Boolean = true,
        wrapHash: Boolean = false
    ) = activitiesWrapper(null, null, size, continuation, sortAsc, types, wrapHash)

    suspend fun getActivitiesByIds(ids: List<String>): List<TypedTokenActivity> {
        val activities: List<TokenActivity> = withBatch(ids) { batch ->
            invoke { builder ->
                builder.path(BASE_PATH).queryParam("id.in", batch.joinToString(","))
            }
        }
        return wrapWithHashes(activities.map(this::mapActivity))
    }

    suspend fun getActivitiesByItem(
        types: List<ActivityType> = emptyList(),
        contract: String,
        tokenId: String,
        size: Int = DEFAULT_SIZE,
        continuation: String?,
        sortAsc: Boolean = true
    ) = activitiesWrapper(contract, tokenId, size, continuation, sortAsc, types)

    private suspend fun activitiesWrapper(
        contract: String?,
        tokenId: String?,
        size: Int = DEFAULT_SIZE,
        continuation: String?,
        sortAsc: Boolean = true,
        types: List<ActivityType> = emptyList(),
        wrapHash: Boolean = false
    ) = coroutineScope {
        val parsed = continuation?.let { TzktActivityContinuation.parse(it) }

        val prevGroups = types.map {
            async {
                activities(contract, tokenId, size, parsed?.date, null, sortAsc, it)
            }
        }

        // If id was parsed we need to send additional requests
        val eqGoups = parsed?.id?.let { id ->
            types.map {
                async {
                    activities(contract, tokenId, size, parsed.date, id, sortAsc, it)
                }
            }
        } ?: emptyList()

        val groups = (eqGoups.awaitAll() + prevGroups.awaitAll()).flatten().distinctBy { it.id }
        val activities = when (sortAsc) {
            true -> groups.sortedWith(compareBy({ it.timestamp }, { it.id }))
            else -> groups.sortedWith(compareBy({ it.timestamp }, { it.id })).reversed()
        }
        val slice = activities.subList(0, Integer.min(size, activities.size))
        Page.Get(items = if (wrapHash) wrapWithHashes(slice) else slice,
            size = size,
            last = { TzktActivityContinuation(it.timestamp, it.id.toLong()).toString() })
    }

    private suspend fun activities(
        contract: String?,
        tokenId: String?,
        size: Int = DEFAULT_SIZE,
        prevDate: OffsetDateTime?,
        prevId: Long?,
        sortAsc: Boolean = true,
        type: ActivityType? = null
    ): List<TypedTokenActivity> {
        val activities = invoke<List<TokenActivity>> { builder ->
            builder.path(BASE_PATH).queryParam("token.standard", "fa2").queryParam("metadata.artifactUri.null", "false")
                .apply {
                    contract?.let { queryParam("token.contract", it) }
                    tokenId?.let { queryParam("token.tokenId", it) }
                    queryParam("limit", size)
                    when {
                        prevDate != null && prevId != null -> {
                            queryParam("timestamp.eq", prevDate.toString())
                            queryParam("id.${direction(sortAsc)}", prevId.toString())
                        }
                        prevDate != null -> {
                            queryParam("timestamp.${direction(sortAsc)}", prevDate.toString())
                        }
                    }
                    queryParam("sort.${sorting(sortAsc)}", "timestamp, id")
                    type?.let {
                        when (type) {
                            ActivityType.MINT -> {
                                queryParam("from.null", "true")
                                queryParam("to.ni", NULL_ADDRESSES_STRING)
                            }
                            ActivityType.BURN -> {
                                queryParam("from.null", "false")
                                queryParam("to.in", NULL_ADDRESSES_STRING)
                            }
                            ActivityType.TRANSFER -> {
                                queryParam("from.ni", NULL_ADDRESSES_STRING)
                                queryParam("to.ni", NULL_ADDRESSES_STRING)
                            }
                        }
                    }
                }
        }
        return activities.map(this::mapActivity)
    }

    private fun mapActivity(tokenActivity: TokenActivity): TypedTokenActivity {
        val type = when {
            NULL_ADDRESSES.contains(tokenActivity.to?.address) -> ActivityType.BURN
            tokenActivity.from == null -> ActivityType.MINT
            else -> ActivityType.TRANSFER
        }
        return TypedTokenActivity(type = type, tokenActivity)
    }

    private suspend fun wrapWithHashes(activities: List<TypedTokenActivity>): List<TypedTokenActivity> {
        val transactionIds = activities.mapNotNull { it.transactionId }.map { it.toString() }
        val pairs: Map<Long, String> = if (transactionIds.isNotEmpty()) {
            withBatch(transactionIds) {
                invoke<List<TransactionItem>> { builder ->
                    builder.path(BASE_TRANSACTION_PATH).queryParam("id.in", it.joinToString(","))
                        .queryParam("select", "id,hash")
                }.map { it.id to it.hash }
            }.toMap()
        } else {
            emptyMap()
        }
        return activities.map {
            when (it.tokenActivity.transactionId) {
                null -> it
                else -> it.addHash(pairs[it.tokenActivity.transactionId])
            }
        }
        return activities
    }

    companion object {
        const val BASE_PATH = "v1/tokens/transfers"
        const val BASE_TRANSACTION_PATH = "v1/operations/transactions"
        const val DEFAULT_SIZE = 1000
    }

    class TransactionItem(
        val id: Long, val hash: String
    )
}
