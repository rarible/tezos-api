package com.rarible.tzkt.client

import com.rarible.tzkt.models.Contract
import com.rarible.tzkt.models.Token
import org.springframework.web.reactive.function.client.WebClient

class CollectionClient(
    webClient: WebClient
) : BaseClient(webClient) {

    val basePath = "v1/contracts"
    suspend fun collection(contract: String): Contract {
        val collection = invoke<Contract> {
            it.path("$basePath/$contract")
        }
        return collection
    }

    suspend fun collections(size: Int?, continuation: Long?, sortAsc: Boolean = true): List<Contract> {
        val collections = invoke<List<Contract>> {
            it.path(basePath)
                .queryParam("kind", "asset")
                .apply {
                    if (size != null) {
                        it.queryParam("limit", size)
                    }
                    if(continuation != null){
                        it.queryParam("offset.cr", continuation)
                    }
                    if(!sortAsc){
                        it.queryParam("sort.desc", "firstActivity")
                    } else {
                        it.queryParam("sort.asc", "firstActivity")
                    }
                }
        }
        return collections
    }
}
