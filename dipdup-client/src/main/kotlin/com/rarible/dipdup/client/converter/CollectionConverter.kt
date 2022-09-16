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

    fun convert(source: com.rarible.dipdup.client.fragment.Collection) = DipDupCollection(
        id = source.contract,
        owner = source.owner,
        name = "Unnamed",
        minters = listOf(),
        standard = "",
        symbol = null,
        updated = OffsetDateTime.parse(source.db_updated_at.toString()).toInstant()
    )
}
