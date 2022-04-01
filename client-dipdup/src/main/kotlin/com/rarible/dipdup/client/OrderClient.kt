package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.exception.NotFound

class OrderClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getById(id: String): GetOrderByIdQuery.Marketplace_order_by_pk {
        val request = GetOrderByIdQuery(id = id)
        val response = safeExecution(request)
        checkNotNull(response.marketplace_order_by_pk) { throw NotFound("${request}") }
        return response.marketplace_order_by_pk
    }

    suspend fun getOrders(id: String, limit: Int): List<GetOrdersQuery.Marketplace_order> {
        val response = safeExecution(GetOrdersQuery(id = id, limit = limit))
        return response.marketplace_order
    }

    suspend fun getOrdersByIds(ids: List<String>): List<GetOrdersByIdsQuery.Marketplace_order> {
        val response = safeExecution(GetOrdersByIdsQuery(ids = ids))
        return response.marketplace_order
    }
}
