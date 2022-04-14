package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient

class ActivityClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getActivities(id: String, limit: Int): List<GetActivitiesQuery.Marketplace_activity> {
        val response = safeExecution(GetActivitiesQuery(id = id, limit = limit))
        return response.marketplace_activity
    }

}
