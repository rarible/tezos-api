package com.rarible.tzkt.client

import com.rarible.tzkt.model.Contract
import com.rarible.tzkt.model.Page
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.web.reactive.function.client.WebClient

class CollectionClient(
    webClient: WebClient
) : BaseClient(webClient) {

    suspend fun collection(contract: String): Contract {
        val collection = invoke<Contract> {
            it.path("$BASE_PATH/$contract")
        }
        return collection
    }

    suspend fun collections(size: Int = DEFAULT_SIZE, continuation: String?, sortAsc: Boolean = true): Page<Contract> {
        val collections = invoke<List<Contract>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("kind", "asset")
                .queryParam("tzips.all", "fa2")
                .apply {
                    size?.let { queryParam("limit", it) }
                    continuation?.let { queryParam("offset.cr", it) }
                    val sorting = if (sortAsc) "sort.asc" else "sort.desc"
                    queryParam(sorting, "firstActivity")
                }
        }
        return Page.Get(
            items = collections,
            size = size,
            last = { it.firstActivity!!.toString() }
        )
    }

    suspend fun collections(addresses: List<String>): List<Contract> {
        val collections = coroutineScope {
            addresses
                .map { async { collection(it) } }
                .awaitAll()
        }
        return collections
    }

    companion object {
        const val BASE_PATH = "v1/contracts"
        const val DEFAULT_SIZE = 1000
    }
}
