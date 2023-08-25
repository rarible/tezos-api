package com.rarible.dipdup.client.converter

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.readValue
import com.rarible.dipdup.client.GetTokensAllContinuationAscQuery
import com.rarible.dipdup.client.GetTokensAllContinuationDescQuery
import com.rarible.dipdup.client.GetTokensAllQuery
import com.rarible.dipdup.client.GetTokensByCollectionContinuationDescQuery
import com.rarible.dipdup.client.GetTokensByCollectionQuery
import com.rarible.dipdup.client.GetTokensByCreatorContinuationDescQuery
import com.rarible.dipdup.client.GetTokensByCreatorQuery
import com.rarible.dipdup.client.GetTokensByIdsQuery
import com.rarible.dipdup.client.GetTokensByOwnerContinuationDescQuery
import com.rarible.dipdup.client.GetTokensByOwnerQuery
import com.rarible.dipdup.client.core.model.DipDupItem
import com.rarible.dipdup.client.core.model.Part
import com.rarible.dipdup.client.core.model.TokenMeta
import com.rarible.dipdup.client.core.util.MetaUtils
import com.rarible.dipdup.client.fragment.Token
import com.rarible.dipdup.client.fragment.Token_by_owner
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

object TokenConverter {
    val mapper = MetaUtils.mapper()
    val logger = LoggerFactory.getLogger(javaClass)

    fun convertByIds(source: List<GetTokensByIdsQuery.Token>) = source.map { convert(it.token) }

    fun convertAll(source: List<GetTokensAllQuery.Token>) = source.map { convert(it.token) }
    fun convertAllContinuationAsc(source: List<GetTokensAllContinuationAscQuery.Token>) =
        source.map { convert(it.token) }

    fun convertAllContinuationDesc(source: List<GetTokensAllContinuationDescQuery.Token>) =
        source.map { convert(it.token) }

    fun convert(source: Token) = DipDupItem(
        id = DipDupItem.itemId(source.contract, BigInteger(source.token_id)),
        minted = BigDecimal(source.minted.toString()).toBigInteger(),
        mintedAt = OffsetDateTime.parse(source.minted_at.toString()).toInstant(),
        supply = BigDecimal(source.supply.toString()).toBigInteger(),
        tokenId = BigInteger(source.token_id),
        updated = OffsetDateTime.parse(source.updated.toString()).toInstant(),
        creators = creators(source.creator),
        contract = source.contract,
        deleted = source.deleted,
        tzktId = source.tzkt_id.toString().toBigInteger()
    )

    fun convertByOwner(source: List<GetTokensByOwnerQuery.Token_by_owner>) = source.map { convert(it.token_by_owner) }
    fun convertByOwnerContinuation(source: List<GetTokensByOwnerContinuationDescQuery.Token_by_owner>) =
        source.map { convert(it.token_by_owner) }

    fun convertByCreator(source: List<GetTokensByCreatorQuery.Token_by_owner>) = source.map { convert(it.token_by_owner) }
    fun convertByCreatorContinuation(source: List<GetTokensByCreatorContinuationDescQuery.Token_by_owner>) =
        source.map { convert(it.token_by_owner) }

    fun convertByCollection(source: List<GetTokensByCollectionQuery.Token_by_owner>) = source.map { convert(it.token_by_owner) }
    fun convertByCollectionContinuation(source: List<GetTokensByCollectionContinuationDescQuery.Token_by_owner>) =
        source.map { convert(it.token_by_owner) }

    fun convert(source: Token_by_owner) = DipDupItem(
        id = DipDupItem.itemId(source.contract, BigInteger(source.token_id)),
        minted = BigDecimal(source.minted.toString()).toBigInteger(),
        mintedAt = OffsetDateTime.parse(source.minted_at.toString()).toInstant(),
        supply = BigDecimal(source.supply.toString()).toBigInteger(),
        tokenId = BigInteger(source.token_id),
        updated = OffsetDateTime.parse(source.updated.toString()).toInstant(),
        creators = creators(source.creator),
        contract = source.contract,
        deleted = source.deleted,
        tzktId = source.tzkt_id.toString().toBigInteger()
    )

    fun creators(source: String?): List<Part> { // for now, we have only one creator
        return when(source) {
            null -> emptyList()
            else -> listOf(Part(source, 10000))
        }
    }

    fun processMetadata(metadata: String): TokenMeta? {
        try {
            val map: Map<String, Any> = mapper.readValue(fixBoolean(metadata))
            val meta: TokenMeta.TzktMeta = mapper.convertValue(adjustMeta(map))
            var tokenAttributes = meta.attributes
            return TokenMeta(
                name = meta.name ?: TokenMeta.UNTITLED,
                description = meta.description,
                content = meta.contents(),
                attributes = tokenAttributes ?: emptyList(),
                tags = meta.tags ?: emptyList()
            )
        } catch (ex: Exception) {
            logger.error("Failed during parsing meta: $metadata", ex)
            return null
        }
    }

    // Mailformed json: KT1XKUyUtRqobh5CqZzXFJW6UT5t55Sn3iT6:7
    private fun fixBoolean(metadata: String): String {
        return metadata.replace("False", "false")
    }

    private fun adjustMeta(source: Map<String, *>): Map<String, *> {
        val mutable = source.toMutableMap()
        mutable["formats"] = adjustListMap(mutable["formats"])
        mutable["creators"] = adjustList(mutable["creators"])
        mutable["tags"] = adjustList(mutable["tags"])
        if (mutable["attributes"] != null && mutable["attributes"] is List<*>) {
            mutable["attributes"] = (mutable["attributes"] as List<Map<String, *>>)
                .map { it.toMutableMap() }
                .filter {
                    it["key"] != null || it["name"] != null
                }
                .map {
                    it["key"] = it["name"]

                    // fill according to Attribute
                    mapOf(
                        "key" to it["key"],
                        "value" to it["value"],
                        "type" to it["type"],
                        "format" to it["format"]
                    )
                }
        } else {
            mutable.remove("attributes")
        }
        return mutable.toMap()
    }

    private fun adjustListMap(source: Any?): List<Map<String, Object>> {
        return when (source) {
            is String -> mapper.readValue(source)
            is List<*> -> source as List<Map<String, Object>>
            else -> emptyList()
        }
    }

    private fun adjustList(source: Any?): List<String> {
        return when (source) {
            is String -> listOf(source)
            is List<*> -> source as List<String>
            else -> emptyList()
        }
    }
}
