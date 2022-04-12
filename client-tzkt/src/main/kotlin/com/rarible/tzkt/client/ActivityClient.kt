package com.rarible.tzkt.client

import com.rarible.tzkt.models.Token
import org.springframework.web.reactive.function.client.WebClient

class ActivityClient(
    webClient: WebClient
) : BaseClient(webClient) {

    val basePath = "v1/tokens/transfers"
    suspend fun activities(size: Int?, continuation: Long?, sortAsc: Boolean = true): List<Token> {
        val tokens = invoke<List<Token>> {
            it.path(basePath)
                .queryParam("token.standard", "fa2")
                .apply {
                    if (size != null) {
                        it.queryParam("limit", size)
                    }
                    if(continuation != null){
                        it.queryParam("offset.cr", continuation)
                    }
                    if(!sortAsc){
                        it.queryParam("sort.desc", "id")
                    }
                }
        }
        return tokens
    }
}
