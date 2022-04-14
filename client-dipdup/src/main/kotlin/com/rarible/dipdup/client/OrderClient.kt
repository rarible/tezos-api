package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.exception.NotFound
import java.math.BigInteger

class OrderClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getById(id: String): GetOrderByIdQuery.Marketplace_order_by_pk {
        val request = GetOrderByIdQuery(id = id)
        val response = safeExecution(request)
        checkNotNull(response.marketplace_order_by_pk) { throw NotFound("${request}") }
        return response.marketplace_order_by_pk
    }

    suspend fun getOrders(limit: Int = DEFAULT_PAGE, prevId: String = BigInteger.ZERO.toString()): List<GetOrdersQuery.Marketplace_order> {
        val response = safeExecution(GetOrdersQuery(limit = limit, prevId = prevId))
        return response.marketplace_order
    }

    suspend fun getOrdersByIds(ids: List<String>): List<GetOrdersByIdsQuery.Marketplace_order> {
        val response = safeExecution(GetOrdersByIdsQuery(ids = ids))
        return response.marketplace_order
    }

    suspend fun getOrdersByItem(contract: String, id: String, limit: Int = DEFAULT_PAGE, prevId: String = BigInteger.ZERO.toString()): List<GetOrdersByItemQuery.Marketplace_order> {
        val response = safeExecution(GetOrdersByItemQuery(
            makeContract = contract,
            makeTokenId = id,
            limit = limit,
            prevId = prevId
        ))
        return response.marketplace_order
    }

    companion object {
        const val DEFAULT_PAGE = 1_000
    }
}
