package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.converter.convertTokenActivitiesAllAsc
import com.rarible.dipdup.client.converter.convertTokenActivitiesAllDesc
import com.rarible.dipdup.client.converter.convertTokenActivitiesByItemAsc
import com.rarible.dipdup.client.converter.convertTokenActivitiesByItemDesc
import com.rarible.dipdup.client.converter.convertTokenActivitiesSyncAsc
import com.rarible.dipdup.client.converter.convertTokenActivitiesSyncDesc
import com.rarible.dipdup.client.converter.convertTokensActivitiesByIds
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.model.DipDupActivitiesPage
import com.rarible.dipdup.client.model.DipDupActivityContinuation
import com.rarible.dipdup.client.model.DipDupActivityType
import com.rarible.dipdup.client.model.DipDupSyncSort
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

class TokenActivityClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getActivitiesAll(
        types: List<DipDupActivityType>,
        limit: Int,
        continuation: String? = null,
        sortAsc: Boolean = false
    ): DipDupActivitiesPage {
        var (date, id) = continuation?.let { DipDupActivityContinuation.parse(it) }
            ?.let { Pair(it.date, it.id.toLong()) } ?: mockContinuation(sortAsc)
        val activities = when (sortAsc) {
            false -> convertTokenActivitiesAllDesc(
                safeExecution(
                    GetTokenActivitiesDescQuery(
                        types.map { it.name },
                        limit,
                        date.toString(),
                        id
                    )
                ).token_transfer
            )

            else -> convertTokenActivitiesAllAsc(
                safeExecution(
                    GetTokenActivitiesAscQuery(
                        types.map { it.name },
                        limit,
                        date.toString(),
                        id
                    )
                ).token_transfer
            )
        }
        return page(activities, limit)
    }

    suspend fun getActivitiesSync(
        limit: Int,
        continuation: String? = null,
        sort: DipDupSyncSort? = DipDupSyncSort.DB_UPDATE_DESC
    ): DipDupActivitiesPage {
        val sortInternal = sort ?: DipDupSyncSort.DB_UPDATE_DESC
        var (date, id) = continuation?.let { DipDupActivityContinuation.parse(it) }
            ?.let {
                var id = it.id
                // if we got there continuation from order activity, we have to mock it
                if (!DipDupActivityContinuation.isIdValidLong(id)) {
                    id = when (sortInternal) {
                        DipDupSyncSort.DB_UPDATE_ASC -> 0L
                        DipDupSyncSort.DB_UPDATE_DESC -> Long.MAX_VALUE
                    }.toString()
                }
                Pair(it.date, id.toLong())
            } ?: mockContinuation(sortInternal)
        val activities = when (sortInternal) {
            DipDupSyncSort.DB_UPDATE_DESC -> convertTokenActivitiesSyncDesc(
                safeExecution(
                    GetTokenActivitiesSyncDescQuery(
                        limit,
                        date.toString(),
                        id
                    )
                ).token_transfer
            )
            else -> convertTokenActivitiesSyncAsc(
                safeExecution(
                    GetTokenActivitiesSyncAscQuery(
                        limit,
                        date.toString(),
                        id
                    )
                ).token_transfer
            )
        }
        return syncPage(activities, limit)
    }

    suspend fun getActivitiesByItem(
        types: List<DipDupActivityType>,
        contract: String,
        tokenId: String,
        limit: Int,
        continuation: String? = null,
        sortAsc: Boolean = false
    ): DipDupActivitiesPage {
        var (date, id) = continuation?.let { DipDupActivityContinuation.parse(it) }
            ?.let { Pair(it.date, it.id.toLong()) } ?: mockContinuation(sortAsc)
        val activities = when (sortAsc) {
            false -> convertTokenActivitiesByItemDesc(
                safeExecution(
                    GetTokenActivitiesByItemDescQuery(
                        types.map { it.name },
                        contract,
                        tokenId,
                        limit,
                        date.toString(),
                        id
                    )
                ).token_transfer
            )

            else -> convertTokenActivitiesByItemAsc(
                safeExecution(
                    GetTokenActivitiesByItemAscQuery(
                        types.map { it.name },
                        contract,
                        tokenId,
                        limit,
                        date.toString(),
                        id
                    )
                ).token_transfer
            )
        }
        return page(activities, limit)
    }

    suspend fun getActivitiesByIds(ids: List<String>): List<DipDupActivity> {
        val response = safeExecution(GetTokenActivitiesByIdsQuery(ids))
        return convertTokensActivitiesByIds(response.token_transfer)
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

    private fun syncPage(activities: List<DipDupActivity>, limit: Int): DipDupActivitiesPage {
        val nextContinuation = when (activities.size) {
            limit -> activities[limit - 1].let {
                DipDupActivityContinuation(it.dbUpdatedAt, it.id).toString()
            }

            else -> null
        }
        return DipDupActivitiesPage(
            activities = activities,
            continuation = nextContinuation
        )
    }

    private fun mockContinuation(sortAsc: Boolean): Pair<OffsetDateTime, Long> {
        return when (sortAsc) {
            true -> LocalDateTime.now().minusYears(1000).atOffset(ZoneOffset.UTC) to 0
            else -> LocalDateTime.now().plusYears(1000).atOffset(ZoneOffset.UTC) to Long.MAX_VALUE
        }
    }

    private fun mockContinuation(sortAsc: DipDupSyncSort): Pair<OffsetDateTime, Long> {
        return when (sortAsc) {
            DipDupSyncSort.DB_UPDATE_ASC -> LocalDateTime.now().minusYears(1000).atOffset(ZoneOffset.UTC) to 0
            DipDupSyncSort.DB_UPDATE_DESC -> LocalDateTime.now().plusYears(1000).atOffset(ZoneOffset.UTC) to Long.MAX_VALUE
        }
    }
}
