package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.converter.convertAscAll
import com.rarible.dipdup.client.converter.convertByIds
import com.rarible.dipdup.client.converter.convertDescAll
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.model.DipDupActivitiesPage
import com.rarible.dipdup.client.model.DipDupContinuation
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class OrderActivityClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getActivities(limit: Int, continuation: String? = null, sortAsc: Boolean = true): DipDupActivitiesPage {
        val parsedContinuation: DipDupContinuation =
            continuation?.let { DipDupContinuation.parse(it) } ?: mockContinuation(sortAsc)
        val activities = when (sortAsc) {
            true -> convertAscAll(
                safeExecution(
                    GetActivitiesAscQuery(
                        limit,
                        parsedContinuation.date.toString(),
                        parsedContinuation.id.toString()
                    )
                ).marketplace_activity
            )
            else -> convertDescAll(
                safeExecution(
                    GetActivitiesDescQuery(
                        limit,
                        parsedContinuation.date.toString(),
                        parsedContinuation.id.toString()
                    )
                ).marketplace_activity
            )
        }
        val continuation = when (activities.size) {
            limit -> activities[limit - 1].let { DipDupContinuation(it.date, UUID.fromString(it.id)).toString() }
            else -> null
        }
        return DipDupActivitiesPage(
            activities = activities,
            continuation = continuation
        )
    }

    suspend fun getActivities(ids: List<String>): List<DipDupActivity> {
        val response = safeExecution(GetActivitiesByIdsQuery(ids))
        return convertByIds(response.marketplace_activity)
    }

    private fun mockContinuation(sortAsc: Boolean = true): DipDupContinuation {
        val date = when (sortAsc) {
            true -> LocalDateTime.MIN.atOffset(ZoneOffset.UTC)
            else -> LocalDateTime.MAX.atOffset(ZoneOffset.UTC)
        }
        return DipDupContinuation(date, UUID.randomUUID())
    }

}
