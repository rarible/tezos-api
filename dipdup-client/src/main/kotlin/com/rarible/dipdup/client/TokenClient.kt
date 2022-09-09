package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.rarible.dipdup.client.converter.convert
import com.rarible.dipdup.client.converter.convertAll
import com.rarible.dipdup.client.converter.convertAllContinuationAsc
import com.rarible.dipdup.client.converter.convertAllContinuationDesc
import com.rarible.dipdup.client.converter.convertByIds
import com.rarible.dipdup.client.core.model.DipDupToken
import com.rarible.dipdup.client.core.model.TimestampIdContinuation
import com.rarible.dipdup.client.exception.DipDupNotFound
import com.rarible.dipdup.client.model.Page
import com.rarible.dipdup.client.type.Token_order_by
import com.rarible.dipdup.client.type.order_by

class TokenClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getTokenById(id: String): DipDupToken {
        return getTokensByIds(listOf(id)).firstOrNull() ?: throw DipDupNotFound(id)
    }

    suspend fun getTokensByIds(ids: List<String>): List<DipDupToken> {
        val request = GetTokensByIdsQuery(ids)
        val response = safeExecution(request)
        return convertByIds(response.token)
    }

    suspend fun getTokensAll(
        limit: Int = DEFAULT_PAGE,
        showDeleted: Boolean = false,
        continuation: String?,
        sortAsc: Boolean = false
    ): Page<DipDupToken> {
        val tokens = if (continuation == null) {
            val request = GetTokensAllQuery(
                limit, deleted(showDeleted), listOf(
                    orderBy(id = null, updated = sort(sortAsc)),
                    orderBy(id = sort(sortAsc), updated = null)
                )
            )
            val response = safeExecution(request)
            convertAll(response.token)
        } else {
            val parsed = TimestampIdContinuation.parse(continuation)
            if (sortAsc) {
                val request = GetTokensAllContinuationAscQuery(limit, deleted(showDeleted), parsed.date.toString(), parsed.id)
                val response = safeExecution(request)
                convertAllContinuationAsc(response.token)
            } else {
                val request = GetTokensAllContinuationDescQuery(limit, deleted(showDeleted), parsed.date.toString(), parsed.id)
                val response = safeExecution(request)
                convertAllContinuationDesc(response.token)
            }
        }
        return Page.of(tokens, limit)
    }

    private fun orderBy(id: Optional<order_by>?, updated: Optional<order_by>?) = Token_order_by(
        null, null, id, null, null, null, null, null, null, null, updated
    )

    private fun sort(sortAsc: Boolean) = when (sortAsc) {
        true -> Optional.presentIfNotNull(order_by.asc)
        else -> Optional.presentIfNotNull(order_by.desc)
    }

    private fun deleted(showDeleted: Boolean) = when (showDeleted) {
        true -> listOf(true, false)
        else -> listOf(false)
    }

    companion object {
        const val DEFAULT_PAGE = 1_000
    }
}
