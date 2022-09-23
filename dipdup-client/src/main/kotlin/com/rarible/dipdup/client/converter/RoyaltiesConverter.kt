package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetRoyaltiesAllContinuationAscQuery
import com.rarible.dipdup.client.GetRoyaltiesAllContinuationDescQuery
import com.rarible.dipdup.client.GetRoyaltiesAllQuery
import com.rarible.dipdup.client.GetRoyaltiesByIdsQuery
import com.rarible.dipdup.client.GetTokensAllContinuationAscQuery
import com.rarible.dipdup.client.GetTokensAllContinuationDescQuery
import com.rarible.dipdup.client.GetTokensAllQuery
import com.rarible.dipdup.client.GetTokensByIdsQuery
import com.rarible.dipdup.client.core.model.DipDupItem
import com.rarible.dipdup.client.core.model.DipDupRoyalties
import com.rarible.dipdup.client.core.model.Part
import com.rarible.dipdup.client.fragment.Royalties
import com.rarible.dipdup.client.fragment.Token
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

object RoyaltiesConverter {

    fun convertByIds(source: List<GetRoyaltiesByIdsQuery.Royalty>) = source.map { convert(it.royalties) }

    fun convertAll(source: List<GetRoyaltiesAllQuery.Royalty>) = source.map { convert(it.royalties) }
    fun convertAllContinuationAsc(source: List<GetRoyaltiesAllContinuationAscQuery.Royalty>) =
        source.map { convert(it.royalties) }

    fun convertAllContinuationDesc(source: List<GetRoyaltiesAllContinuationDescQuery.Royalty>) =
        source.map { convert(it.royalties) }

    fun convert(source: Royalties) = DipDupRoyalties(
        id = DipDupItem.itemId(source.contract, BigInteger(source.token_id)),
        tokenId = BigInteger(source.token_id),
        contract = source.contract,
        parts = source.parts as List<Part>,
        updated = OffsetDateTime.parse(source.db_updated_at.toString()).toInstant()
    )
}
