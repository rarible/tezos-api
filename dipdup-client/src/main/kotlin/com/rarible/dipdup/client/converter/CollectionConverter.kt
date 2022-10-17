package com.rarible.dipdup.client.converter

import com.fasterxml.jackson.module.kotlin.readValue
import com.rarible.dipdup.client.GetCollectionsAllContinuationAscQuery
import com.rarible.dipdup.client.GetCollectionsAllContinuationDescQuery
import com.rarible.dipdup.client.GetCollectionsAllQuery
import com.rarible.dipdup.client.GetCollectionsByIdsQuery
import com.rarible.dipdup.client.core.model.DipDupCollection
import com.rarible.dipdup.client.core.util.MetaUtils
import com.sun.org.slf4j.internal.LoggerFactory

object CollectionConverter {

    val logger = LoggerFactory.getLogger(javaClass)

    fun convertByIds(source: List<GetCollectionsByIdsQuery.Collection_with_metum>) = source.map { convert(it.collection) }

    fun convertAll(source: List<GetCollectionsAllQuery.Collection_with_metum>) = source.map { convert(it.collection) }
    fun convertAllContinuationAsc(source: List<GetCollectionsAllContinuationAscQuery.Collection_with_metum>) =
        source.map { convert(it.collection) }

    fun convertAllContinuationDesc(source: List<GetCollectionsAllContinuationDescQuery.Collection_with_metum>) =
        source.map { convert(it.collection) }

    fun convert(source: com.rarible.dipdup.client.fragment.Collection): DipDupCollection {
        return try {
            val meta = convertMeta(source.metadata)
            DipDupCollection(
                id = source.id,
                owner = source.owner,
                name = meta?.name ?: "Unnamed Collection",
                minters = listOf(),
                standard = null,
                symbol = null
            )
        } catch (ex: Exception) {
            logger.warn("Wrong meta format for collection", ex)
            DipDupCollection(
                id = source.id,
                owner = source.owner,
                name = "Unnamed Collection",
                minters = listOf(),
                standard = null,
                symbol = null
            )
        }
    }

    fun convertMeta(data: String?): Meta? {
        return if (data != null) {
            val map: Map<String, Object> = MetaUtils.mapper().readValue(sanitizeJson(data))
            Meta(
                map["name"]?.let { it.toString() },
                symbol = null
            )
        } else Meta(null ,null)
    }

    fun sanitizeJson(data: String) = data.replace("False", "false").replace("True", "true")

    data class Meta(
        val name: String?,
        val symbol: String?
    )
}
