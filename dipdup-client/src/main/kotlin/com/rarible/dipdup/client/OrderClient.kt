package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.converter.convert
import com.rarible.dipdup.client.converter.convertAll
import com.rarible.dipdup.client.converter.convertByIds
import com.rarible.dipdup.client.converter.convertByItem
import com.rarible.dipdup.client.converter.convertByMaker
import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.client.core.model.OrderStatus
import com.rarible.dipdup.client.exception.DipDupNotFound
import com.rarible.dipdup.client.graphql.GetOrderByMakerCustomQuery
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
        checkNotNull(response.marketplace_order_by_pk) { throw DipDupNotFound("${request}") }
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
            prevId = parsedContinuation?.let { it.toString() },
            prevDate = parsedContinuation?.let { it.toString() }
        ))
        return convertAll(response.marketplace_order, size)
    }

    suspend fun getOrdersByIds(ids: List<String>): List<DipDupOrder> {
        val response = safeExecution(GetOrdersByIdsQuery(ids))
        return convertByIds(response.marketplace_order)
    }

    suspend fun getOrdersByItem(
        contract: String,
        tokenId: String,
        maker: String?,
        currencyId: String,
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
                currencyId = currencyId,
                statuses = statuses.map { it.name },
                limit = size,
                prevId = parsedContinuation?.let { it.toString() },
                prevDate = parsedContinuation?.let { it.toString() }
            )
        )
        return convertByItem(response.marketplace_order, size)
    }

    suspend fun getOrdersByMakers(
        makers: List<String>,
        statuses: List<OrderStatus>,
        size: Int = DEFAULT_PAGE,
        continuation: String?
    ): DipDupOrdersPage {
        val parsedContinuation = DipDupContinuation.parse(continuation)
        val response = safeExecution(
            GetOrderByMakerCustomQuery(
                makers = makers,
                statuses = statuses.map { it.name },
                limit = size,
                prevId = parsedContinuation?.let { it.toString() },
                prevDate = parsedContinuation?.let { it.toString() }
            )
        )
        return convertByMaker(response.marketplace_order, size)
    }

    suspend fun getOrdersCurrenciesByItem(
        contract: String,
        tokenId: String
    ): List<Asset.AssetType> {
        val response = safeExecution(GetOrdersTakeContractsByMakeItemQuery(contract, tokenId))
        return convert(response)
    }

    suspend fun getOrdersCurrenciesByCollection(
        contract: String
    ): List<Asset.AssetType> {
        val response = safeExecution(GetOrdersTakeContractsByMakeCollectionQuery(contract))
        return convert(response)
    }

    companion object {
        const val DEFAULT_PAGE = 1_000
    }
}
