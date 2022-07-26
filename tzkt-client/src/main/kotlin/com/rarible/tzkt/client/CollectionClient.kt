package com.rarible.tzkt.client

import com.rarible.tzkt.meta.MetaCollectionService
import com.rarible.tzkt.model.CollectionMeta
import com.rarible.tzkt.model.CollectionType
import com.rarible.tzkt.model.Contract
import com.rarible.tzkt.model.Page
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.web.reactive.function.client.WebClient

class CollectionClient(
    webClient: WebClient,
    val metaCollectionService: MetaCollectionService
) : BaseClient(webClient) {

    suspend fun collection(contract: String): Contract = coroutineScope {
        val collection = async {
            invoke<Contract> {
                it.path("$BASE_PATH/$contract")
            }
        }
        val meta = async {
            collectionMeta(contract)
        }
        collection.await().meta(meta.await())
    }

    suspend fun collectionsAll(size: Int = DEFAULT_SIZE, continuation: String?, sortAsc: Boolean = true): Page<Contract> {
        val collections = invoke<List<Contract>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("kind", "asset")
                .queryParam("tzips.all", "fa2")
                .apply {
                    size?.let { queryParam("limit", it) }
                    continuation?.let { queryParam("offset", it) }
                    queryParam("sort.${sorting(sortAsc)}", "firstActivity")
                }
        }
        return Page(collections, offset(continuation, collections))
    }

    fun <T> offset(prevOffset: String?, items: List<T>): String? {
        return when {
            prevOffset == null && items.isNotEmpty() -> items.size.toString()
            prevOffset != null && items.isNotEmpty() -> (prevOffset.toInt() + items.size).toString()
            prevOffset != null && items.isEmpty() -> prevOffset
            else -> null
        }
    }

    suspend fun collectionsByOwner(owner: String, size: Int = DEFAULT_SIZE, continuation: String?, sortAsc: Boolean = true): Page<Contract> {
        val collections = invoke<List<Contract>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("kind", "asset")
                .queryParam("tzips.all", "fa2")
                .apply {
                    size?.let { queryParam("limit", it) }
                    queryParam("creator.eq", owner)
                    continuation?.let { queryParam("offset", it) }
                    queryParam("sort.${sorting(sortAsc)}", "firstActivity")
                }
        }
        return Page(collections, offset(continuation, collections))
    }

    suspend fun collectionType(contract: String): CollectionType {
        val schema = invoke<Map<String, Any>> { builder ->
            builder.path("$BASE_PATH/$contract/storage/schema")
        }.getMap("schema:object")
        val type: CollectionType = when {
            schema?.get("ledger:big_map:object:nat") != null -> CollectionType.MT
            schema?.get("ledger:big_map_flat:nat:address") != null -> CollectionType.NFT
            else -> {
                logger.warn("Wrong collection type for $contract $schema, set MT")
                CollectionType.MT
            }
        }
        return type
    }

    suspend fun collectionsByIds(addresses: List<String>): List<Contract> {
        val collections = coroutineScope {
            addresses
                .map { async { collection(it) } }
                .awaitAll()
        }
        return collections
    }

    suspend fun collectionMeta(contract: String): CollectionMeta {
        return metaCollectionService.get(contract)
    }

    private fun Map<String, Any>?.getMap(key: String): Map<String, Any>? {
        return this?.get(key) as Map<String, Any>?
    }

    companion object {
        const val BASE_PATH = "v1/contracts"
        const val DEFAULT_SIZE = 1000
    }
}
