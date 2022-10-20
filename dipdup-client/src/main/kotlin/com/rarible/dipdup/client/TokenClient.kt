package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.rarible.dipdup.client.converter.TokenConverter.convertAll
import com.rarible.dipdup.client.converter.TokenConverter.convertAllContinuationAsc
import com.rarible.dipdup.client.converter.TokenConverter.convertAllContinuationDesc
import com.rarible.dipdup.client.converter.TokenConverter.convertByCollection
import com.rarible.dipdup.client.converter.TokenConverter.convertByCollectionContinuation
import com.rarible.dipdup.client.converter.TokenConverter.convertByCreator
import com.rarible.dipdup.client.converter.TokenConverter.convertByCreatorContinuation
import com.rarible.dipdup.client.converter.TokenConverter.convertByIds
import com.rarible.dipdup.client.converter.TokenConverter.convertByOwner
import com.rarible.dipdup.client.converter.TokenConverter.convertByOwnerContinuation
import com.rarible.dipdup.client.converter.TokenConverter.processMetadata
import com.rarible.dipdup.client.core.model.DipDupItem
import com.rarible.dipdup.client.core.model.TimestampIdContinuation
import com.rarible.dipdup.client.core.model.TokenMeta
import com.rarible.dipdup.client.exception.DipDupNotFound
import com.rarible.dipdup.client.model.Page
import com.rarible.dipdup.client.type.Token_order_by
import com.rarible.dipdup.client.type.order_by
import org.slf4j.LoggerFactory

class TokenClient(
    client: ApolloClient
) : BaseClient(client) {

    val logger = LoggerFactory.getLogger(javaClass)

    suspend fun getTokenById(id: String): DipDupItem {
        return getTokensByIds(listOf(id)).firstOrNull() ?: throw DipDupNotFound(id)
    }

    suspend fun getTokenMetaById(id: String): TokenMeta {
        val uuid = toUuid5(id)
        val request = GetTokenMetaByIdQuery(uuid)
        val response = safeExecution(request)
        val data = response.metadata_token.firstOrNull()
        val metadata = data?.metadata.takeUnless { it.isNullOrEmpty() }?.let { processMetadata(it) }
        return if (metadata != null) {
            metadata
        } else {
            logger.warn("DipDup metadata wasn't found for $id")
            throw DipDupNotFound(id)
        }
    }

    suspend fun removeTokenMetaById(id: String) {
        val uuid = toUuid5(id)
        val request = RemoveTokenIdMetaMutation(uuid)
        val response = client.mutation(request).execute()
        logger.info("Saved royalty for item $id status: ${response.errors}")
    }

    suspend fun getTokensByIds(ids: List<String>): List<DipDupItem> {
        val uuids = ids.map(::toUuid5)
        val request = GetTokensByIdsQuery(uuids)
        val response = safeExecution(request)
        return convertByIds(response.token)
    }

    suspend fun getTokensAll(
        limit: Int = DEFAULT_PAGE,
        showDeleted: Boolean = false,
        continuation: String?,
        sortAsc: Boolean = false
    ): Page<DipDupItem> {
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
                val request = GetTokensAllContinuationAscQuery(limit, deleted(showDeleted), parsed.date.toString(),
                    toUuid5(parsed.id))
                val response = safeExecution(request)
                convertAllContinuationAsc(response.token)
            } else {
                val request = GetTokensAllContinuationDescQuery(limit, deleted(showDeleted), parsed.date.toString(),
                    toUuid5(parsed.id))
                val response = safeExecution(request)
                convertAllContinuationDesc(response.token)
            }
        }
        return Page.of(tokens, limit) { TimestampIdContinuation(it.updated, it.id) }
    }

    suspend fun getTokensByOwner(
        owner: String,
        limit: Int = DEFAULT_PAGE,
        continuation: String?
    ): Page<DipDupItem> {
        val tokens = if (continuation == null) {
            val request = GetTokensByOwnerQuery(owner, limit)
            val response = safeExecution(request)
            convertByOwner(response.token_by_owner)
        } else {
            val parsed = TimestampIdContinuation.parse(continuation)
            val request = GetTokensByOwnerContinuationDescQuery(owner, limit, parsed.date.toString(), parsed.id)
            val response = safeExecution(request)
            convertByOwnerContinuation(response.token_by_owner)
        }
        return Page.of(tokens, limit) { TimestampIdContinuation(it.updated, it.id) }
    }

    suspend fun getTokensByCreator(
        creator: String,
        limit: Int = DEFAULT_PAGE,
        continuation: String?
    ): Page<DipDupItem> {
        val tokens = if (continuation == null) {
            val request = GetTokensByCreatorQuery(creator, limit)
            val response = safeExecution(request)
            convertByCreator(response.token_by_owner)
        } else {
            val parsed = TimestampIdContinuation.parse(continuation)
            val request = GetTokensByCreatorContinuationDescQuery(creator, limit, parsed.date.toString(), parsed.id)
            val response = safeExecution(request)
            convertByCreatorContinuation(response.token_by_owner)
        }
        return Page.of(tokens, limit) { TimestampIdContinuation(it.updated, it.id) }
    }

    suspend fun getTokensByCollection(
        creator: String,
        limit: Int = DEFAULT_PAGE,
        continuation: String?
    ): Page<DipDupItem> {
        val tokens = if (continuation == null) {
            val request = GetTokensByCollectionQuery(creator, limit)
            val response = safeExecution(request)
            convertByCollection(response.token_by_owner)
        } else {
            val parsed = TimestampIdContinuation.parse(continuation)
            val request = GetTokensByCollectionContinuationDescQuery(creator, limit, parsed.date.toString(), parsed.id)
            val response = safeExecution(request)
            convertByCollectionContinuation(response.token_by_owner)
        }
        return Page.of(tokens, limit) { TimestampIdContinuation(it.updated, it.id) }
    }

    private fun orderBy(id: Optional<order_by>?, updated: Optional<order_by>?) = Token_order_by(
        null, null, null, null,id, null, null, null, null, null, updated,
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
