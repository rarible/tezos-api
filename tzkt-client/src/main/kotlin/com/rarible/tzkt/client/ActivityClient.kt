package com.rarible.tzkt.client

import com.rarible.tzkt.model.ActivityType
import com.rarible.tzkt.model.TokenActivity
import com.rarible.tzkt.model.TypedTokenActivity
import org.springframework.web.reactive.function.client.WebClient

class ActivityClient(
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
        val result = mutableListOf<TypedTokenActivity>()
        activities.forEach {
            val type = if (it.from == null) {
                ActivityType.MINT
            } else if (it.to?.address == BURN_ADDRESS
                || it.to?.address == NULL_ADDRESS
                || it.to == null
            ) {
                ActivityType.BURN
            } else {
                ActivityType.TRANSFER
            }
            result.add(TypedTokenActivity(
                id = it.id,
                type = type,
                level = it.level,
                timestamp = it.timestamp,
                token = it.token,
                from = it.from,
                to = it.to,
                amount = it.amount,
                transactionId = it.transactionId,
                originationId = it.originationId,
                migrationId = it.migrationId
            )
            )
        }
        return result
    }

    suspend fun activityByIds(ids: List<Long>): List<TokenActivity> {
        val activities = invoke<List<TokenActivity>> { builder ->
            builder.path(BASE_PATH).queryParam("id.in", ids.joinToString(","))
        }
        return activities
    }

    // add continuation
    suspend fun activityByItem(contract: String, tokenId: String, size: Int?, continuation: Long?, sortAsc: Boolean = true): List<TokenActivity> {
        val tokens = invoke<List<TokenActivity>> { builder ->
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
        return tokens
    }

    companion object {
        const val BASE_PATH = "v1/tokens/transfers"
        const val BURN_ADDRESS = "tz1burnburnburnburnburnburnburjAYjjX"
        const val NULL_ADDRESS = "tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU"
    }
}
