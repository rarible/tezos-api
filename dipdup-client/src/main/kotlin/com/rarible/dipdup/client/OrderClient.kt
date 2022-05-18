package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.converter.convert
import com.rarible.dipdup.client.converter.convertAll
import com.rarible.dipdup.client.converter.convertByIds
import com.rarible.dipdup.client.converter.convertByItem
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.client.core.model.OrderStatus
import com.rarible.dipdup.client.exception.NotFound
import com.rarible.dipdup.client.graphql.GetOrdersByItemCustomQuery
import com.rarible.dipdup.client.graphql.GetOrdersCustomQuery
import com.rarible.dipdup.client.model.DipDupContinuation
import com.rarible.dipdup.client.model.DipDupOrderSort
import com.rarible.dipdup.client.model.DipDupOrdersPage

class OrderClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getOrderById(id: String): DipDupOrder {
        val request = GetOrderByIdQuery(id)
        val response = safeExecution(request)
        checkNotNull(response.marketplace_order_by_pk) { throw NotFound("${request}") }
        return convert(response.marketplace_order_by_pk)
    }

    suspend fun getOrdersAll(
        statuses: List<OrderStatus>,
        sort: DipDupOrderSort? = DipDupOrderSort.LAST_UPDATE_DESC,
        size: Int = DEFAULT_PAGE,
        continuation: String?
    ): DipDupOrdersPage {
        val parsedContinuation = DipDupContinuation.parse(continuation)
        val response = safeExecution(GetOrdersCustomQuery(
            statuses = statuses.map { it.name },
            limit = size,
            sort = sort ?: DipDupOrderSort.LAST_UPDATE_DESC,
            prevId = parsedContinuation?.id.toString(),
            prevDate = parsedContinuation?.date.toString()
        ))
        return convertAll(response.marketplace_order, size)
    }

    suspend fun getOrdersByIds(ids: List<String>): List<DipDupOrder> {
        val response = safeExecution(GetOrdersByIdsQuery(ids))
        return convertByIds(response.marketplace_order)
    }

    suspend fun getOrdersByItem(
        contract: String,
        tokenId:
        String, maker: String?,
        statuses: List<OrderStatus>,
        size: Int = DEFAULT_PAGE,
        continuation: String?
    ): DipDupOrdersPage {
        val parsedContinuation = DipDupContinuation.parse(continuation)
        val response = safeExecution(
            GetOrdersByItemCustomQuery(
                contract = contract,
                tokenId = tokenId,
                maker = maker,
                statuses = statuses.map { it.name },
                limit = size,
                prevId = parsedContinuation?.id.toString(),
                prevDate = parsedContinuation?.date.toString()
            )
        )
        return convertByItem(response.marketplace_order, size)
    }

    companion object {
        const val DEFAULT_PAGE = 1_000
    }
}
