package com.rarible.tzkt.client

import com.rarible.tzkt.model.ActivityType
import com.rarible.tzkt.model.TokenActivity
import com.rarible.tzkt.model.TypedTokenActivity
import org.springframework.web.reactive.function.client.WebClient

class TokenActivityClient(
    webClient: WebClient
) : BaseClient(webClient) {

    suspend fun activities(
        size: Int?,
        continuation: Long?,
        sortAsc: Boolean = true,
        type: ActivityType? = null
    ): List<TypedTokenActivity> {
        val activities = invoke<List<TokenActivity>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.standard", "fa2")
                .queryParam("metadata.artifactUri.null", "false")
                .apply {
                    size?.let { queryParam("limit", it) }
                    continuation?.let { queryParam("offset.cr", it) }
                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
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

    suspend fun activityByIds(ids: List<Long>): List<TokenActivity> {
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
                    size?.let { queryParam("limit", it) }
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

    companion object {
        const val BASE_PATH = "v1/tokens/transfers"
        const val BURN_ADDRESS = "tz1burnburnburnburnburnburnburjAYjjX"
        const val NULL_ADDRESS = "tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU"
    }
}
