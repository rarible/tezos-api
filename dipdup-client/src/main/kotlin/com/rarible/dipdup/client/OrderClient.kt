package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.converter.convert
import com.rarible.dipdup.client.converter.convertAll
import com.rarible.dipdup.client.converter.convertByIds
import com.rarible.dipdup.client.converter.convertByItem
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.client.exception.NotFound
import java.math.BigInteger

class OrderClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getById(id: String): DipDupOrder {
        val request = GetOrderByIdQuery(id = id)
        val response = safeExecution(request)
        checkNotNull(response.marketplace_order_by_pk) { throw NotFound("${request}") }
        return convert(response.marketplace_order_by_pk)
    }

    suspend fun getOrders(limit: Int = DEFAULT_PAGE, prevId: String = BigInteger.ZERO.toString()): List<DipDupOrder> {
        val response = safeExecution(GetOrdersQuery(limit = limit, prevId = prevId))
        return convertAll(response.marketplace_order)
    }

    suspend fun getOrdersByIds(ids: List<String>): List<DipDupOrder> {
        val response = safeExecution(GetOrdersByIdsQuery(ids = ids))
        return convertByIds(response.marketplace_order)
    }

    suspend fun getOrdersByItem(contract: String, id: String, limit: Int = DEFAULT_PAGE, prevId: String = BigInteger.ZERO.toString()): List<DipDupOrder> {
        val response = safeExecution(GetOrdersByItemQuery(
            makeContract = contract,
            makeTokenId = id,
            limit = limit,
            prevId = prevId
        ))
        return convertByItem(response.marketplace_order)
    }

    companion object {
        const val DEFAULT_PAGE = 1_000
    }
}
