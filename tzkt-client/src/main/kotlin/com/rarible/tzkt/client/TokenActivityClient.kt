package com.rarible.tzkt.client

import com.rarible.tzkt.model.ActivityType
import com.rarible.tzkt.model.Page
import com.rarible.tzkt.model.TokenActivity
import com.rarible.tzkt.model.TypedTokenActivity
import com.rarible.tzkt.model.TzktActivityContinuation
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.web.reactive.function.client.WebClient
import java.time.OffsetDateTime

class TokenActivityClient(
    webClient: WebClient
) : BaseClient(webClient) {

    suspend fun activities(
        size: Int = DEFAULT_SIZE,
        continuation: String?,
        sortAsc: Boolean = true,
        types: List<ActivityType> = emptyList()
    ): Page<TypedTokenActivity> {
        val parsed = continuation?.let { TzktActivityContinuation.parse(it) }

        val prevGroups = coroutineScope {
            types.map {
                async {
                    activities(size, parsed?.date, null, sortAsc, it)
                }
            }
        }

        // If id was parsed we need to send additional requests
        val eqGoups = parsed?.id?.let { id ->
            coroutineScope {
                types.map {
                    async {
                        activities(size, parsed.date, id, sortAsc, it)
                    }
                }
            }
        } ?: emptyList()

        val groups = (prevGroups.awaitAll() + eqGoups.awaitAll()).flatten()
        val activities = when (sortAsc) {
            true -> groups.sortedWith(compareBy({ it.timestamp }, { it.id }))
            else -> groups.sortedWith(compareBy({ it.timestamp }, { it.id })).reversed()
        }
        return Page.Get(
            items = activities,
            size = size,
            last = { TzktActivityContinuation(it.timestamp, it.id.toLong()).toString() }
        )
    }

    suspend fun activities(
        size: Int = DEFAULT_SIZE,
        prevDate: OffsetDateTime?,
        prevId: Long?,
        sortAsc: Boolean = true,
        type: ActivityType? = null
    ): List<TypedTokenActivity> {
        val activities = invoke<List<TokenActivity>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.standard", "fa2")
                .queryParam("metadata.artifactUri.null", "false")
                .apply {
                    size.let { queryParam("limit", it) }
                    when {
                        prevDate != null && prevId != null -> {
                            queryParam("timestamp.eq", prevDate.toString())
                            queryParam("id.${sortPredicate(sortAsc)}", prevId.toString())
                        }
                        prevDate != null -> queryParam("timestamp.${sortPredicate(sortAsc)}", prevDate.toString())
                    }
                    val sorting = if (sortAsc) "timestamp.asc" else "timestamp.desc"
                    queryParam(sorting, "id")
                    type?.let {
                        when(type){
                            ActivityType.MINT -> {
                                queryParam("from.null", "true")
                            }
                            ActivityType.BURN -> {
                                queryParam("to.in", "$BURN_ADDRESS,$NULL_ADDRESS")
                            }
                            ActivityType.TRANSFER -> {
                                queryParam("from.null", "false")
                                queryParam("to.ni", "$BURN_ADDRESS,$NULL_ADDRESS")
                            }
                        }
                    }
                }
        }
        return activities.map(this::mapActivity)
    }

    suspend fun activityByIds(ids: List<String>): List<TokenActivity> {
        val activities = invoke<List<TokenActivity>> { builder ->
            builder.path(BASE_PATH).queryParam("id.in", ids.joinToString(","))
        }
        return activities.map(this::mapActivity)
    }

    suspend fun activityByItem(contract: String, tokenId: String, size: Int?, continuation: Long?, sortAsc: Boolean = true): List<TokenActivity> {
        val activities = invoke<List<TokenActivity>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.contract", contract)
                .queryParam("token.tokenId", tokenId)
                .apply {
                    // TODO: add continuation later
//                    size?.let { queryParam("limit", it) }
                    continuation?.let { queryParam("offset.cr", it) }
                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    queryParam(sorting, "id")
                }
        }
        return activities.map(this::mapActivity)
    }

    private fun mapActivity(tokenActivity: TokenActivity): TypedTokenActivity {
        val type = when {
            tokenActivity.from == null -> ActivityType.MINT
            listOf(BURN_ADDRESS, NULL_ADDRESS, null).contains(tokenActivity.to?.address) -> ActivityType.BURN
            else -> ActivityType.TRANSFER
        }
        return TypedTokenActivity(type = type, tokenActivity)
    }

    private fun sortPredicate(asc: Boolean = false) = when (asc) {
        true -> "gt"
        else -> "lt"
    }

    companion object {
        const val BASE_PATH = "v1/tokens/transfers"
        const val BURN_ADDRESS = "tz1burnburnburnburnburnburnburjAYjjX"
        const val NULL_ADDRESS = "tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU"
        const val DEFAULT_SIZE = 1000
    }
}
