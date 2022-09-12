package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.rarible.dipdup.client.converter.OwnershipConverter.convertAll
import com.rarible.dipdup.client.converter.OwnershipConverter.convertAllContinuationAsc
import com.rarible.dipdup.client.converter.OwnershipConverter.convertAllContinuationDesc
import com.rarible.dipdup.client.converter.OwnershipConverter.convertByIds
import com.rarible.dipdup.client.converter.OwnershipConverter.convertByItem
import com.rarible.dipdup.client.converter.OwnershipConverter.convertByItemContinuation
import com.rarible.dipdup.client.core.model.DipDupOwnership
import com.rarible.dipdup.client.core.model.TimestampIdContinuation
import com.rarible.dipdup.client.exception.DipDupNotFound
import com.rarible.dipdup.client.model.Page
import com.rarible.dipdup.client.type.Ownership_order_by
import com.rarible.dipdup.client.type.order_by

class OwneshipClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getOwnershipById(id: String): DipDupOwnership {
        return getOwnershipsByIds(listOf(id)).firstOrNull() ?: throw DipDupNotFound(id)
    }

    suspend fun getOwnershipsByIds(ids: List<String>): List<DipDupOwnership> {
        val request = GetOwnershipsByIdsQuery(ids)
        val response = safeExecution(request)
        return convertByIds(response.ownership)
    }

    suspend fun getOwnershipsAll(
        limit: Int = DEFAULT_PAGE,
        continuation: String?,
        sortAsc: Boolean = false
    ): Page<DipDupOwnership> {
        val ownerships = if (continuation == null) {
            val request = GetOwnershipsAllQuery(
                limit, listOf(
                    orderBy(id = null, updated = sort(sortAsc)),
                    orderBy(id = sort(sortAsc), updated = null)
                )
            )
            val response = safeExecution(request)
            convertAll(response.ownership)
        } else {
            val parsed = TimestampIdContinuation.parse(continuation)
            if (sortAsc) {
                val request = GetOwnershipsAllContinuationAscQuery(limit, parsed.date.toString(), parsed.id)
                val response = safeExecution(request)
                convertAllContinuationAsc(response.ownership)
            } else {
                val request = GetOwnershipsAllContinuationDescQuery(limit, parsed.date.toString(), parsed.id)
                val response = safeExecution(request)
                convertAllContinuationDesc(response.ownership)
            }
        }
        return Page.of(ownerships, limit)
    }

    suspend fun getOwnershipsByItem(
        limit: Int = DEFAULT_PAGE,
        itemId: String,
        continuation: String?
    ): Page<DipDupOwnership> {
        val (contract, token_id) = itemId.split(":")
        val ownerships = if (continuation == null) {
            val request = GetOwnershipsByItemQuery(limit, contract, token_id)
            val response = safeExecution(request)
            convertByItem(response.ownership)
        } else {
            val parsed = TimestampIdContinuation.parse(continuation)
            val request = GetOwnershipsByItemContinuationQuery(limit, contract, token_id, parsed.id)
            val response = safeExecution(request)
            convertByItemContinuation(response.ownership)
        }
        return Page.of(ownerships, limit)
    }

    private fun orderBy(id: Optional<order_by>?, updated: Optional<order_by>?) = Ownership_order_by(
        null, null, id, null, null, updated
    )

    private fun sort(sortAsc: Boolean) = when (sortAsc) {
        true -> Optional.presentIfNotNull(order_by.asc)
        else -> Optional.presentIfNotNull(order_by.desc)
    }

    companion object {
        const val DEFAULT_PAGE = 1_000
    }
}
