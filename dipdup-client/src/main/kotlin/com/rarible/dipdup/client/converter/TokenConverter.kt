package com.rarible.dipdup.client.converter

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.readValue
import com.rarible.dipdup.client.GetTokensAllContinuationAscQuery
import com.rarible.dipdup.client.GetTokensAllContinuationDescQuery
import com.rarible.dipdup.client.GetTokensAllQuery
import com.rarible.dipdup.client.GetTokensByIdsQuery
import com.rarible.dipdup.client.core.model.DipDupItem
import com.rarible.dipdup.client.core.model.TokenMeta
import com.rarible.dipdup.client.core.util.MetaUtils
import com.rarible.dipdup.client.fragment.Token
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

object TokenConverter {
    val mapper = MetaUtils.mapper()

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
        contract = source.contract,
        deleted = source.deleted,
        tzktId = source.tzkt_id.toString().toBigInteger()
    )

    fun processMetadata(metadata: String?): TokenMeta {
        return if (!metadata.isNullOrEmpty()){
            val map: Map<String, Any> = mapper.readValue(metadata)
            val meta: TokenMeta.TzktMeta = mapper.convertValue(adjustMeta(map))
            var tokenAttributes = meta.attributes
            TokenMeta(
                name = meta.name ?: TokenMeta.UNTITLED,
                description = meta.description,
                content = meta.contents(),
                attributes = tokenAttributes ?: emptyList(),
                tags = meta.tags ?: emptyList()
            )
        } else {
            TokenMeta(
                name = TokenMeta.UNTITLED,
                description = null,
                content = emptyList(),
                attributes = emptyList(),
                tags = emptyList()
            )
        }

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
