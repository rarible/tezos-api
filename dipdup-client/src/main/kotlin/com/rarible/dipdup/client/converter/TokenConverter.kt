package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetTokensAllContinuationAscQuery
import com.rarible.dipdup.client.GetTokensAllContinuationDescQuery
import com.rarible.dipdup.client.GetTokensAllQuery
import com.rarible.dipdup.client.GetTokensByIdsQuery
import com.rarible.dipdup.client.core.model.DipDupItem
import com.rarible.dipdup.client.fragment.Token
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
        id = source.id,
        metadataSynced = source.metadata_synced,
        minted = BigInteger(source.minted.toString()),
        mintedAt = OffsetDateTime.parse(source.minted_at.toString()).toInstant(),
        supply = BigInteger(source.supply.toString()),
        tokenId = BigInteger(source.token_id),
        updated = OffsetDateTime.parse(source.updated.toString()).toInstant(),
        contract = source.contract,
        deleted = source.deleted,
        metadataRetries = source.metadata_retries,
        tzktId = source.tzkt_id
    )
}
