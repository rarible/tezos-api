package com.rarible.tzkt.client

import com.rarible.tzkt.config.TzktSettings
import com.rarible.tzkt.meta.MetaCollectionService
import com.rarible.tzkt.model.BatchBody
import com.rarible.tzkt.model.CollectionMeta
import com.rarible.tzkt.model.CollectionType
import com.rarible.tzkt.model.Contract
import com.rarible.tzkt.model.Page
import com.rarible.tzkt.model.TokenBalance
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.web.reactive.function.client.WebClient

class CollectionClient(
    webClient: WebClient,
    val metaCollectionService: MetaCollectionService,
    val settings: TzktSettings
) : BaseClient(webClient) {

    suspend fun collection(contract: String, meta: Boolean = false): Contract = coroutineScope {
        val collection = async {
            invoke<Contract> {
                it.path("$BASE_PATH/$contract")
            }
        }
        if (meta) {
            val meta = async {
                collectionMeta(contract)
            }
            collection.await().meta(meta.await())
        } else {
            collection.await()
        }
    }

    suspend fun collectionsAll(size: Int = DEFAULT_SIZE, continuation: String?, sortAsc: Boolean = true): Page<Contract> {
        val firstActivity = continuation?.let { collection(it, false).firstActivity }
        val collections = invoke<List<Contract>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("kind", "asset")
                .queryParam("tzips.all", "fa2")
                .apply {
                    size?.let { queryParam("limit", it) }
                    firstActivity?.let { queryParam("offset.cr", it) }
                    queryParam("sort.${sorting(sortAsc)}", "firstActivity")
                }
        }
        return Page(collections, offset(size, collections))
    }

    fun offset(size: Int, items: List<Contract>): String? {
        return when {
            size == items.size -> items.last().address
            else -> null
        }
    }

    suspend fun collectionsByOwner(owner: String, size: Int = DEFAULT_SIZE, continuation: String?, sortAsc: Boolean = true): Page<Contract> {
        val firstActivity = continuation?.let { collection(it, false).firstActivity }
        val collections = invoke<List<Contract>> { builder ->
            builder.path(BASE_PATH)
                .queryParam("kind", "asset")
                .queryParam("tzips.all", "fa2")
                .apply {
                    size?.let { queryParam("limit", it) }
                    queryParam("creator.eq", owner)
                    firstActivity?.let { queryParam("offset.cr", it) }
                    queryParam("sort.${sorting(sortAsc)}", "firstActivity")
                }
        }
        return Page(collections, offset(size, collections))
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

    suspend fun collectionsByIds(addresses: List<String>) = coroutineScope {
        val distinctIds = addresses.distinct()
        if (distinctIds.isEmpty()) emptyList<Contract>()
        when(settings.useCollectionBatch) {
            true -> {
                invokePost({
                    it.path(BASE_PATH)
                }, BatchBody(distinctIds))
            }
            else -> distinctIds.map { async { collection(it) } }.awaitAll()
        }
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
