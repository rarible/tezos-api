package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetTokensAllContinuationAscQuery
import com.rarible.dipdup.client.GetTokensAllContinuationDescQuery
import com.rarible.dipdup.client.GetTokensAllQuery
import com.rarible.dipdup.client.GetTokensByIdsQuery
import com.rarible.dipdup.client.core.model.DipDupToken
import com.rarible.dipdup.client.fragment.Token
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

fun convertByIds(source: List<GetTokensByIdsQuery.Token>) = source.map { convert(it.token) }

fun convertAll(source: List<GetTokensAllQuery.Token>) = source.map { convert(it.token) }
fun convertAllContinuationAsc(source: List<GetTokensAllContinuationAscQuery.Token>) = source.map { convert(it.token) }
fun convertAllContinuationDesc(source: List<GetTokensAllContinuationDescQuery.Token>) = source.map { convert(it.token) }

fun convert(source: Token) = DipDupToken(
    id = source.id,
    metadata_synced = source.metadata_synced,
    minted = BigDecimal(source.minted.toString()),
    minted_at = OffsetDateTime.parse(source.minted_at.toString()).toInstant(),
    supply = BigDecimal(source.supply.toString()),
    token_id = BigInteger(source.token_id),
    updated = OffsetDateTime.parse(source.updated.toString()).toInstant(),
    contract = source.contract,
    deleted = source.deleted,
    metadata_retries = source.metadata_retries,
    tzkt_id = source.tzkt_id
)
