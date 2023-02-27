package com.rarible.dipdup.client.converter

import com.fasterxml.jackson.module.kotlin.readValue
import com.rarible.dipdup.client.GetCollectionsAllContinuationAscQuery
import com.rarible.dipdup.client.GetCollectionsAllContinuationDescQuery
import com.rarible.dipdup.client.GetCollectionsAllQuery
import com.rarible.dipdup.client.GetCollectionsByIdsQuery
import com.rarible.dipdup.client.core.model.DipDupCollection
import com.rarible.dipdup.client.core.util.MetaUtils
import com.rarible.dipdup.client.fragment.Collection
import org.slf4j.LoggerFactory

object CollectionConverter {

    val logger = LoggerFactory.getLogger(javaClass)

    fun convertByIds(source: List<GetCollectionsByIdsQuery.Collection_with_metum>) = source.map { convert(it.collection) }

    fun convertAll(source: List<GetCollectionsAllQuery.Collection_with_metum>) = source.map { convert(it.collection) }
    fun convertAllContinuationAsc(source: List<GetCollectionsAllContinuationAscQuery.Collection_with_metum>) =
        source.map { convert(it.collection) }

    fun convertAllContinuationDesc(source: List<GetCollectionsAllContinuationDescQuery.Collection_with_metum>) =
        source.map { convert(it.collection) }

    fun convert(source: Collection): DipDupCollection {
        val minters = minters(source)
        return try {
            val meta = convertMeta(source.metadata)
            DipDupCollection(
                id = source.id,
                owner = source.owner,
                name = meta?.name ?: "Unnamed Collection",
                minters = minters,
                standard = null,
                symbol = null,
                meta = meta
            )
        } catch (ex: Exception) {
            logger.warn("Wrong meta format for collection", ex)
            DipDupCollection(
                id = source.id,
                owner = source.owner,
                name = "Unnamed Collection",
                minters = minters,
                standard = null,
                symbol = null
            )
        }
    }

    private fun minters(source: Collection): List<String> {
        return try {
            source.minters as List<String>
        } catch (ex: Exception) {
            emptyList()
        }
    }

    private fun convertMeta(data: String?): DipDupCollection.Meta? {
        return if (data != null) {
            val map: Map<String, Object> = MetaUtils.mapper().readValue(sanitizeJson(data))
            DipDupCollection.Meta(
                name = map["name"]?.let { it.toString() },
                description = map["description"]?.let { it.toString() },
                homepage = map["homepage"]?.let { it.toString() },
                symbol = null,
                content = map["imageUri"]?.let { listOf(DipDupCollection.Content(
                    uri = it.toString(),
                    representation = DipDupCollection.Representation.ORIGINAL
                )) } ?: emptyList()
            )
        } else null
    }

    private fun sanitizeJson(data: String) = data.replace("False", "false").replace("True", "true")

}
