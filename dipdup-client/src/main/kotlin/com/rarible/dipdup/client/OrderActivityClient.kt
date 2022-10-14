package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.converter.convertAllAsc
import com.rarible.dipdup.client.converter.convertByIds
import com.rarible.dipdup.client.converter.convertAllDesc
import com.rarible.dipdup.client.converter.convertByItemAsc
import com.rarible.dipdup.client.converter.convertByItemDesc
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.core.util.isValidUUID
import com.rarible.dipdup.client.model.DipDupActivitiesPage
import com.rarible.dipdup.client.model.DipDupActivityContinuation
import com.rarible.dipdup.client.model.DipDupActivityType
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

class OrderActivityClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getActivitiesAll(
        types: List<DipDupActivityType>,
        limit: Int,
        continuation: String? = null,
        sortAsc: Boolean = false
    ): DipDupActivitiesPage {
        val (date, id) = activityOperation(continuation, sortAsc)
        val activities = when (sortAsc) {
            true -> convertAllAsc(
                safeExecution(
                    GetOrderActivitiesAscQuery(
                        types.map { it.name },
                        limit,
                        date.toString(),
                        id
                    )
                ).marketplace_activity
            )
            else -> convertAllDesc(
                safeExecution(
                    GetOrderActivitiesDescQuery(
                        types.map { it.name },
                        limit,
                        date.toString(),
                        id
                    )
                ).marketplace_activity
            )
        }
        return page(activities, limit)
    }

    suspend fun getActivitiesByItem(
        types: List<DipDupActivityType>,
        contract: String,
        tokenId: String,
        limit: Int,
        continuation: String? = null,
        sortAsc: Boolean = false
    ): DipDupActivitiesPage {
        val (date, id) = activityOperation(continuation, sortAsc)
        val activities = when (sortAsc) {
            true -> convertByItemAsc(
                safeExecution(
                    GetOrderActivitiesByItemAscQuery(
                        types.map { it.name },
                        contract,
                        tokenId,
                        limit,
                        date.toString(),
                        id
                    )
                ).marketplace_activity
            )
            else -> convertByItemDesc(
                safeExecution(
                    GetOrderActivitiesByItemDescQuery(
                        types.map { it.name },
                        contract,
                        tokenId,
                        limit,
                        date.toString(),
                        id
                    )
                ).marketplace_activity
            )
        }
        return page(activities, limit)
    }

    suspend fun getActivitiesByIds(ids: List<String>): List<DipDupActivity> {
        val response = safeExecution(GetOrderActivitiesByIdsQuery(ids))
        return convertByIds(response.marketplace_activity)
    }

    suspend fun activityOperation(continuation: String?, sortAsc: Boolean): Pair<OffsetDateTime, Int> {
        var (date, id) = mockContinuation(sortAsc)
        continuation?.let {
            val parsed = DipDupActivityContinuation.parse(it)!!
            date = parsed.date
            if (isValidUUID(parsed.id)) {
                val response = safeExecution(GetOrderActivitiesByIdsQuery(listOf(parsed.id)))
                if (response.marketplace_activity.size > 0) {
                    id = response.marketplace_activity.first().order_activity.operation_counter
                }
            }
        }
        return date to id
    }

    private fun page(activities: List<DipDupActivity>, limit: Int): DipDupActivitiesPage {
        val nextContinuation = when (activities.size) {
            limit -> activities[limit - 1].let {
                DipDupActivityContinuation(it.date, it.id).toString()
            }
            else -> null
        }
        return DipDupActivitiesPage(
            activities = activities,
            continuation = nextContinuation
        )
    }

    private fun mockContinuation(sortAsc: Boolean): Pair<OffsetDateTime, Int> {
        return when (sortAsc) {
            true -> LocalDateTime.now().minusYears(1000).atOffset(ZoneOffset.UTC) to 0
            else -> LocalDateTime.now().plusYears(1000).atOffset(ZoneOffset.UTC) to Int.MAX_VALUE
        }
    }

}
