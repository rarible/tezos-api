package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.converter.convertAllAsc
import com.rarible.dipdup.client.converter.convertByIds
import com.rarible.dipdup.client.converter.convertAllDesc
import com.rarible.dipdup.client.converter.convertByItemAsc
import com.rarible.dipdup.client.converter.convertByItemDesc
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.core.model.WithOperationCounter
import com.rarible.dipdup.client.model.DipDupActivitiesPage
import com.rarible.dipdup.client.model.DipDupActivityContinuation
import com.rarible.dipdup.client.model.DipDupActivityType
import java.time.LocalDateTime
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
        val parsedContinuation: DipDupActivityContinuation =
            continuation?.let { DipDupActivityContinuation.parse(it) } ?: mockContinuation(sortAsc)
        val activities = when (sortAsc) {
            true -> convertAllAsc(
                safeExecution(
                    GetActivitiesAscQuery(
                        types.map { it.name },
                        limit,
                        parsedContinuation.date.toString(),
                        parsedContinuation.id
                    )
                ).marketplace_activity
            )
            else -> convertAllDesc(
                safeExecution(
                    GetActivitiesDescQuery(
                        types.map { it.name },
                        limit,
                        parsedContinuation.date.toString(),
                        parsedContinuation.id
                    )
                ).marketplace_activity
            )
        }
        val nextContinuation = when (activities.size) {
            limit -> activities[limit - 1].let {
                val withOperationCounter = it as WithOperationCounter
                DipDupActivityContinuation(it.date, withOperationCounter.operationCounter).toString()
            }
            else -> null
        }
        return DipDupActivitiesPage(
            activities = activities,
            continuation = nextContinuation
        )
    }

    suspend fun getActivitiesByItem(
        types: List<DipDupActivityType>,
        contract: String,
        tokenId: String,
        limit: Int,
        continuation: String? = null,
        sortAsc: Boolean = false
    ): DipDupActivitiesPage {
        val parsedContinuation: DipDupActivityContinuation =
            continuation?.let { DipDupActivityContinuation.parse(it) } ?: mockContinuation(sortAsc)
        val activities = when (sortAsc) {
            true -> convertByItemAsc(
                safeExecution(
                    GetActivitiesByItemAscQuery(
                        types.map { it.name },
                        contract,
                        tokenId,
                        limit,
                        parsedContinuation.date.toString(),
                        parsedContinuation.id
                    )
                ).marketplace_activity
            )
            else -> convertByItemDesc(
                safeExecution(
                    GetActivitiesByItemDescQuery(
                        types.map { it.name },
                        contract,
                        tokenId,
                        limit,
                        parsedContinuation.date.toString(),
                        parsedContinuation.id
                    )
                ).marketplace_activity
            )
        }
        val nextContinuation = when (activities.size) {
            limit -> activities[limit - 1].let {
                val withOperationCounter = it as WithOperationCounter
                DipDupActivityContinuation(it.date, withOperationCounter.operationCounter).toString()
            }
            else -> null
        }
        return DipDupActivitiesPage(
            activities = activities,
            continuation = nextContinuation
        )
    }

    suspend fun getActivitiesByIds(ids: List<String>): List<DipDupActivity> {
        val response = safeExecution(GetActivitiesByIdsQuery(ids))
        return convertByIds(response.marketplace_activity)
    }

    private fun mockContinuation(sortAsc: Boolean = true): DipDupActivityContinuation {
        val date = when (sortAsc) {
            true -> LocalDateTime.now().minusYears(1000).atOffset(ZoneOffset.UTC)
            else -> LocalDateTime.now().plusYears(1000).atOffset(ZoneOffset.UTC)
        }
        val id: Int = when (sortAsc) {
            true -> 0
            else -> Int.MAX_VALUE
        }
        return DipDupActivityContinuation(date, id)
    }

}
