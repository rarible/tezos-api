package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Query
import com.rarible.dipdup.client.exception.DipDupNotFound
import com.rarible.dipdup.client.exception.ResponseError

abstract class BaseClient(
    val client: ApolloClient
) {

    suspend fun <D : Query.Data> safeExecution(request: Query<D>): D {
        val response = client.query(request).execute()
        response.errors?.let { throw ResponseError("${it} for ${request}") }
        checkNotNull(response.data) { throw DipDupNotFound("${request}") }
        return response.data!!
    }
}
