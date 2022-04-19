package com.rarible.tzkt.client

import com.rarible.tzkt.model.TokenTransfer
import org.springframework.web.reactive.function.client.WebClient

class ActivityClient(
    webClient: WebClient
) : BaseClient(webClient) {

    suspend fun activities(size: Int?, continuation: Long?, sortAsc: Boolean = true): List<TokenTransfer> {
        val tokens = invoke<List<TokenTransfer>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.standard", "fa2")
                .apply {
                    size?.let { queryParam("limit", it) }
                    continuation?.let { queryParam("offset.cr", it) }
                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    queryParam(sorting, "id")
                }
        }
        return tokens
    }

    suspend fun activityByIds(ids: List<Long>): List<TokenTransfer> {
        val tokens = invoke<List<TokenTransfer>> { builder ->
            builder.path(BASE_PATH).queryParam("id.in", ids.joinToString(","))
        }
        return tokens
    }

    suspend fun activityByItem(contract: String, tokenId: String): List<TokenTransfer> {
        val tokens = invoke<List<TokenTransfer>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("token.contract", contract)
                .queryParam("token.tokenId", tokenId)
        }
        return tokens
    }

    companion object {
        const val BASE_PATH = "v1/tokens/transfers"
    }
}
