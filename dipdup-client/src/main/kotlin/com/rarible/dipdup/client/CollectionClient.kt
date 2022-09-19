package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.rarible.dipdup.client.converter.CollectionConverter.convertAll
import com.rarible.dipdup.client.converter.CollectionConverter.convertAllContinuationAsc
import com.rarible.dipdup.client.converter.CollectionConverter.convertAllContinuationDesc
import com.rarible.dipdup.client.converter.CollectionConverter.convertByIds
import com.rarible.dipdup.client.core.model.DipDupCollection
import com.rarible.dipdup.client.core.model.TimestampIdContinuation
import com.rarible.dipdup.client.exception.DipDupNotFound
import com.rarible.dipdup.client.model.Page
import com.rarible.dipdup.client.type.Collection_with_meta_order_by
import com.rarible.dipdup.client.type.order_by

class CollectionClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getCollectionById(id: String): DipDupCollection {
        return getCollectionsByIds(listOf(id)).firstOrNull() ?: throw DipDupNotFound(id)
    }

    suspend fun getCollectionsByIds(ids: List<String>): List<DipDupCollection> {
        val request = GetCollectionsByIdsQuery(ids)
        val response = safeExecution(request)
        return convertByIds(response.collection_with_meta)
    }

    suspend fun getCollectionsAll(
        limit: Int = DEFAULT_PAGE,
        continuation: String?,
        sortAsc: Boolean = false
    ): Page<DipDupCollection> {
        val collections = if (continuation == null) {
            val request = GetCollectionsAllQuery(
                limit, listOf(
                    orderBy(id = null, updated = sort(sortAsc)),
                    orderBy(id = sort(sortAsc), updated = null)
                )
            )
            val response = safeExecution(request)
            convertAll(response.collection_with_meta)
        } else {
            val parsed = TimestampIdContinuation.parse(continuation)
            if (sortAsc) {
                val request = GetCollectionsAllContinuationAscQuery(limit, parsed.date.toString(), parsed.id)
                val response = safeExecution(request)
                convertAllContinuationAsc(response.collection_with_meta)
            } else {
                val request = GetCollectionsAllContinuationDescQuery(limit, parsed.date.toString(), parsed.id)
                val response = safeExecution(request)
                convertAllContinuationDesc(response.collection_with_meta)
            }
        }
        return Page.of(collections, limit)
    }

    private fun orderBy(id: Optional<order_by>?, updated: Optional<order_by>?) = Collection_with_meta_order_by(
        id, updated, null, null
    )

    private fun sort(sortAsc: Boolean) = when (sortAsc) {
        true -> Optional.presentIfNotNull(order_by.asc)
        else -> Optional.presentIfNotNull(order_by.desc)
    }

    companion object {
        const val DEFAULT_PAGE = 1_000
    }
}
