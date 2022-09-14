package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetOwnershipsAllContinuationAscQuery
import com.rarible.dipdup.client.GetOwnershipsAllContinuationDescQuery
import com.rarible.dipdup.client.GetOwnershipsAllQuery
import com.rarible.dipdup.client.GetOwnershipsByIdsQuery
import com.rarible.dipdup.client.GetOwnershipsByItemContinuationQuery
import com.rarible.dipdup.client.GetOwnershipsByItemQuery
import com.rarible.dipdup.client.core.model.DipDupOwnership
import com.rarible.dipdup.client.fragment.Ownership
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

object OwnershipConverter {

    fun convertByIds(source: List<GetOwnershipsByIdsQuery.Ownership>) = source.map { convert(it.ownership) }

    fun convertAll(source: List<GetOwnershipsAllQuery.Ownership>) = source.map { convert(it.ownership) }
    fun convertAllContinuationAsc(source: List<GetOwnershipsAllContinuationAscQuery.Ownership>) =
        source.map { convert(it.ownership) }

    fun convertAllContinuationDesc(source: List<GetOwnershipsAllContinuationDescQuery.Ownership>) =
        source.map { convert(it.ownership) }

    fun convertByItem(source: List<GetOwnershipsByItemQuery.Ownership>) = source.map { convert(it.ownership) }
    fun convertByItemContinuation(source: List<GetOwnershipsByItemContinuationQuery.Ownership>) =
        source.map { convert(it.ownership) }

    fun convert(source: Ownership) = DipDupOwnership(
        id = source.id,
        tokenId = BigInteger(source.token_id),
        contract = source.contract,
        owner = source.owner,
        updated = OffsetDateTime.parse(source.updated.toString()).toInstant(),

        // TODO: changed to created field
        created = OffsetDateTime.parse(source.updated.toString()).toInstant(),
        balance = BigDecimal(source.balance.toString()).toBigInteger(),
    )
}
