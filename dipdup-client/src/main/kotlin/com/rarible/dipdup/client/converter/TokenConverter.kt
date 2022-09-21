package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetTokensAllContinuationAscQuery
import com.rarible.dipdup.client.GetTokensAllContinuationDescQuery
import com.rarible.dipdup.client.GetTokensAllQuery
import com.rarible.dipdup.client.GetTokensByIdsQuery
import com.rarible.dipdup.client.core.model.DipDupItem
import com.rarible.dipdup.client.fragment.Token
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

object TokenConverter {

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
        tzktId = source.tzkt_id.toString().toInt()
    )
}
