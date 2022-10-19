package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.rarible.dipdup.client.converter.RoyaltiesConverter.convertAll
import com.rarible.dipdup.client.converter.RoyaltiesConverter.convertAllContinuationAsc
import com.rarible.dipdup.client.converter.RoyaltiesConverter.convertAllContinuationDesc
import com.rarible.dipdup.client.converter.RoyaltiesConverter.convertByIds
import com.rarible.dipdup.client.core.model.DipDupRoyalties
import com.rarible.dipdup.client.core.model.Part
import com.rarible.dipdup.client.core.model.TimestampIdContinuation
import com.rarible.dipdup.client.core.util.uuid5Oid
import com.rarible.dipdup.client.exception.DipDupNotFound
import com.rarible.dipdup.client.model.Page
import com.rarible.dipdup.client.type.Royalties_insert_input
import com.rarible.dipdup.client.type.Royalties_order_by
import com.rarible.dipdup.client.type.order_by
import org.slf4j.LoggerFactory
import java.time.Instant

class RoyaltiesClient(
    client: ApolloClient
) : BaseClient(client) {

    val logger = LoggerFactory.getLogger(javaClass)

    suspend fun getRoyaltiesById(id: String): DipDupRoyalties {
        return getRoyaltiesByIds(listOf(id)).firstOrNull() ?: throw DipDupNotFound(id)
    }

    suspend fun getRoyaltiesByIds(ids: List<String>): List<DipDupRoyalties> {
        val uuids = ids.map(::toUuid5)
        val request = GetRoyaltiesByIdsQuery(uuids)
        val response = safeExecution(request)
        return convertByIds(response.royalties)
    }

    suspend fun insertRoyalty(itemId: String, parts: List<Part>) {
        val id = uuid5Oid(itemId)
        val (contract, tokenId) = itemId.split(":")
        val roInput = Royalties_insert_input(
            Optional.presentIfNotNull(contract),
            Optional.presentIfNotNull("${Instant.now()}"),
            Optional.presentIfNotNull(id.toString()),
            Optional.presentIfNotNull(parts.map { mapOf(
                "part_account" to it.account,
                "part_value" to it.value.toString())
            }),
            Optional.presentIfNotNull(1),
            Optional.presentIfNotNull(true),
            Optional.presentIfNotNull(tokenId)
        )
        val request = InsertRoyaltyMutation(roInput)
        val response = client.mutation(request).execute()
        logger.info("Saved royalty for item $itemId status: ${response.errors}")
    }

    suspend fun getRoyaltiesAll(
        limit: Int = DEFAULT_PAGE,
        continuation: String?,
        sortAsc: Boolean = false
    ): Page<DipDupRoyalties> {
        val tokens = if (continuation == null) {
            val request = GetRoyaltiesAllQuery(
                limit, listOf(
                    orderBy(id = null, updated = sort(sortAsc)),
                    orderBy(id = sort(sortAsc), updated = null)
                )
            )
            val response = safeExecution(request)
            convertAll(response.royalties)
        } else {
            val parsed = TimestampIdContinuation.parse(continuation)
            if (sortAsc) {
                val request = GetRoyaltiesAllContinuationAscQuery(limit, parsed.date.toString(), toUuid5(parsed.id))
                val response = safeExecution(request)
                convertAllContinuationAsc(response.royalties)
            } else {
                val request = GetRoyaltiesAllContinuationDescQuery(limit, parsed.date.toString(), toUuid5(parsed.id))
                val response = safeExecution(request)
                convertAllContinuationDesc(response.royalties)
            }
        }
        return Page.of(tokens, limit) { TimestampIdContinuation(it.updated, it.id) }
    }

    private fun orderBy(id: Optional<order_by>?, updated: Optional<order_by>?) = Royalties_order_by(
        null, updated, id, null, null, null, null
    )

    private fun sort(sortAsc: Boolean) = when (sortAsc) {
        true -> Optional.presentIfNotNull(order_by.asc)
        else -> Optional.presentIfNotNull(order_by.desc)
    }

    companion object {
        const val DEFAULT_PAGE = 1_000
    }
}
