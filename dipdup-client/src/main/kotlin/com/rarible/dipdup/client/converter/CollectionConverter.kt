package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetCollectionsAllContinuationAscQuery
import com.rarible.dipdup.client.GetCollectionsAllContinuationDescQuery
import com.rarible.dipdup.client.GetCollectionsAllQuery
import com.rarible.dipdup.client.GetCollectionsByIdsQuery
import com.rarible.dipdup.client.core.model.DipDupCollection
import java.time.OffsetDateTime

object CollectionConverter {

    fun convertByIds(source: List<GetCollectionsByIdsQuery.Collection_with_metum>) = source.map { convert(it.collection) }

    fun convertAll(source: List<GetCollectionsAllQuery.Collection_with_metum>) = source.map { convert(it.collection) }
    fun convertAllContinuationAsc(source: List<GetCollectionsAllContinuationAscQuery.Collection_with_metum>) =
        source.map { convert(it.collection) }

    fun convertAllContinuationDesc(source: List<GetCollectionsAllContinuationDescQuery.Collection_with_metum>) =
        source.map { convert(it.collection) }

    fun convert(source: com.rarible.dipdup.client.fragment.Collection): DipDupCollection {
        val meta = convertMeta(source.metadata)
        return DipDupCollection(
            id = source.contract,
            owner = source.owner,
            name = meta?.name ?: "Unnamed Collection",
            minters = listOf(),
            standard = null,
            symbol = null,
            updated = OffsetDateTime.parse(source.db_updated_at.toString()).toInstant()
        )
    }

    fun convertMeta(data: Any?): Meta? {
        return if (data is Map<*, *>) {
            val name = data.get("name").toString()
            Meta(
                name = name,
                symbol = null
            )
        } else null
    }

    data class Meta(
        val name: String?,
        val symbol: String?
    )
}
