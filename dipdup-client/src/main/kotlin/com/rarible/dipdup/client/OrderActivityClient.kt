package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.converter.convertAll
import com.rarible.dipdup.client.converter.convertByIds
import com.rarible.dipdup.client.core.model.DipDupActivity

class OrderActivityClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getActivities(limit: Int, prevId: String): List<DipDupActivity> {
        val response = safeExecution(GetActivitiesQuery(limit, prevId))
        return convertAll(response.marketplace_activity)
    }

    suspend fun getActivities(ids: List<String>): List<DipDupActivity> {
        val response = safeExecution(GetActivitiesByIdsQuery(ids))
        return convertByIds(response.marketplace_activity)
    }

}
