package com.rarible.tzkt.client

import com.rarible.tzkt.model.ActivityType
import com.rarible.tzkt.model.TokenTransfer
import org.springframework.web.reactive.function.client.WebClient

class ActivityClient(
    webClient: WebClient
) : BaseClient(webClient) {

    suspend fun activities(
        size: Int?,
        continuation: Long?,
        sortAsc: Boolean = true,
        type: ActivityType? = null
    ): List<TokenTransfer> {
        val activities = invoke<List<TokenTransfer>> { builder ->
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
                                queryParam("to.in", "tz1burnburnburnburnburnburnburjAYjjX,tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU")
                            }
                            ActivityType.TRANSFER -> {
                                queryParam("from.null", "false")
                                queryParam("to.ni", "tz1burnburnburnburnburnburnburjAYjjX,tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU")
                            }
                        }
                    }
                }
        }
        activities.forEach {
            if (it.from == null) {
                it.type = ActivityType.MINT
            } else if (it.to?.address == "tz1burnburnburnburnburnburnburjAYjjX"
                || it.to?.address == "tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU"
                || it.to == null
            ) {
                it.type = ActivityType.BURN
            } else {
                it.type = ActivityType.TRANSFER
            }
        }
        return activities
    }

    suspend fun activityByIds(ids: List<Long>): List<TokenTransfer> {
        val activities = invoke<List<TokenTransfer>> { builder ->
            builder.path(BASE_PATH).queryParam("id.in", ids.joinToString(","))
        }
        return activities
    }

    companion object {
        const val BASE_PATH = "v1/tokens/transfers"
    }
}
