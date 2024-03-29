package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.converter.convert
import com.rarible.dipdup.client.converter.convertAll
import com.rarible.dipdup.client.converter.convertByIds
import com.rarible.dipdup.client.converter.convertByItem
import com.rarible.dipdup.client.converter.convertByMaker
import com.rarible.dipdup.client.converter.convertOrdersContinuationSyncAsc
import com.rarible.dipdup.client.converter.convertOrdersContinuationSyncDesc
import com.rarible.dipdup.client.converter.convertOrdersSyncAsc
import com.rarible.dipdup.client.converter.convertOrdersSyncDesc
import com.rarible.dipdup.client.converter.toPage
import com.rarible.dipdup.client.converter.toPageCurrency
import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.client.core.model.OrderStatus
import com.rarible.dipdup.client.core.model.TezosPlatform
import com.rarible.dipdup.client.exception.DipDupNotFound
import com.rarible.dipdup.client.graphql.GetOrderByMakerCustomQuery
import com.rarible.dipdup.client.graphql.GetOrdersByCollectionCustomQuery
import com.rarible.dipdup.client.graphql.GetOrdersByItemCustomQuery
import com.rarible.dipdup.client.graphql.GetOrdersCustomQuery
import com.rarible.dipdup.client.model.DipDupActivityContinuation
import com.rarible.dipdup.client.model.DipDupContinuation
import com.rarible.dipdup.client.model.DipDupCurrencyContinuation
import com.rarible.dipdup.client.model.DipDupOrderSort
import com.rarible.dipdup.client.model.DipDupOrdersPage
import com.rarible.dipdup.client.model.DipDupSyncSort

class OrderClient(
    client: ApolloClient
) : BaseClient(client) {

    suspend fun getOrderById(id: String): DipDupOrder {
        val request = GetOrderByIdQuery(id)
        val response = safeExecution(request)
        checkNotNull(response.marketplace_order_by_pk) { throw DipDupNotFound("${request}") }
        val order = convert(response.marketplace_order_by_pk)
        return wrapWithLegacy(order)
    }

    suspend fun getOrdersAll(
        statuses: List<OrderStatus>,
        platforms: List<TezosPlatform> = listOf(TezosPlatform.RARIBLE_V1, TezosPlatform.RARIBLE_V2),
        sort: DipDupOrderSort? = DipDupOrderSort.LAST_UPDATE_DESC,
        isBid: Boolean? = null,
        size: Int = DEFAULT_PAGE,
        continuation: String?
    ): DipDupOrdersPage {
        val parsedContinuation = DipDupContinuation.parse(continuation)
        val response = safeExecution(GetOrdersCustomQuery(
            statuses = statuses.map { it.name },
            platforms = platforms,
            limit = size,
            sort = sort ?: DipDupOrderSort.LAST_UPDATE_DESC,
            prevId = parsedContinuation?.let { it.id },
            prevDate = parsedContinuation?.let { it.date.toString() },
            isBid = isBid
        ))
        return convertAll(response.marketplace_order, size)
    }

    suspend fun getOrdersSync(
        limit: Int,
        continuation: String? = null,
        sort: DipDupSyncSort? = DipDupSyncSort.DB_UPDATE_DESC
    ): DipDupOrdersPage {
        val sortInternal = sort ?: DipDupSyncSort.DB_UPDATE_DESC
        val activities = if (continuation == null) {
            when (sortInternal) {
                DipDupSyncSort.DB_UPDATE_ASC -> convertOrdersSyncAsc(safeExecution(GetOrdersSyncAscQuery(limit)).marketplace_order)
                DipDupSyncSort.DB_UPDATE_DESC -> convertOrdersSyncDesc(safeExecution(GetOrdersSyncDescQuery(limit)).marketplace_order)
            }
        } else {
            val parsed = DipDupActivityContinuation.parse(continuation)!!
            var (date, id) = parsed.date to parsed.id
            when (sortInternal) {
                DipDupSyncSort.DB_UPDATE_ASC -> convertOrdersContinuationSyncAsc(
                    safeExecution(
                        GetOrdersSyncContinuationAscQuery(
                            limit,
                            date.toString(),
                            id
                        )
                    ).marketplace_order
                )
                DipDupSyncSort.DB_UPDATE_DESC -> convertOrdersContinuationSyncDesc(
                    safeExecution(
                        GetOrdersSyncContinuationDescQuery(
                            limit,
                            date.toString(),
                            id
                        )
                    ).marketplace_order
                )
            }
        }
        return page(activities, limit)
    }

    suspend fun getOrdersByIds(ids: List<String>): List<DipDupOrder> {
        val response = safeExecution(GetOrdersByIdsQuery(ids))
        val orders = convertByIds(response.marketplace_order)
        return wrapWithLegacy(orders)
    }

    suspend fun wrapWithLegacy(orders: List<DipDupOrder>): List<DipDupOrder> {
        val data = if (orders.isNotEmpty()) {
            val legacyIds = orders
                .filter { it.platform == TezosPlatform.RARIBLE_V1 }
                .map { it.id }
            if (legacyIds.isNotEmpty()) {
                getLegacyData(legacyIds)
            } else {
                emptyMap()
            }
        } else {
            emptyMap()
        }
        return if (data.isNotEmpty()) {
            orders.map {
                if (data.containsKey(it.id)) {
                    it.copy(legacyData = data[it.id])
                } else {
                    it
                }
            }
        } else {
            orders
        }
    }

    suspend fun wrapWithLegacy(order: DipDupOrder): DipDupOrder {
        return if (order.platform == TezosPlatform.RARIBLE_V1) {
            val data = getLegacyData(listOf(order.id))
            if (data.containsKey(order.id)) {
                order.copy(legacyData = data[order.id])
            } else {
                order
            }
        } else {
            order
        }
    }

    suspend fun getOrdersByItem(
        contract: String,
        tokenId: String,
        maker: String?,
        currencyId: String,
        statuses: List<OrderStatus>,
        platforms: List<TezosPlatform> = listOf(TezosPlatform.RARIBLE_V1, TezosPlatform.RARIBLE_V2),
        isBid: Boolean = false,
        size: Int = DEFAULT_PAGE,
        continuation: String?
    ): DipDupOrdersPage {
        val parsedContinuation = DipDupCurrencyContinuation.parse(continuation)
        val response = safeExecution(
            GetOrdersByItemCustomQuery(
                contract = contract,
                tokenId = tokenId,
                maker = maker,
                currencyId = currencyId,
                statuses = statuses.map { it.name },
                platforms = platforms,
                limit = size,
                prevId = parsedContinuation?.let { it.id.toString() },
                prevValue = parsedContinuation?.let { it.value.toString() },
                isBid = isBid
            )
        )
        val items = convertByItem(response.marketplace_order)
        return toPageCurrency(wrapWithLegacy(items), size)
    }

    suspend fun getOrdersByCollection(
        contract: String,
        statuses: List<OrderStatus>,
        platforms: List<TezosPlatform> = listOf(TezosPlatform.RARIBLE_V1, TezosPlatform.RARIBLE_V2),
        isBid: Boolean = false,
        size: Int = DEFAULT_PAGE,
        continuation: String?
    ): DipDupOrdersPage {
        val parsedContinuation = DipDupContinuation.parse(continuation)
        val response = safeExecution(
            GetOrdersByCollectionCustomQuery(
                contract = contract,
                statuses = statuses.map { it.name },
                platforms = platforms,
                limit = size,
                prevId = parsedContinuation?.let { it.id.toString() },
                prevDate = parsedContinuation?.let { it.date.toString() },
                isBid = isBid
            )
        )
        val items = convertByItem(response.marketplace_order)
        return toPage(wrapWithLegacy(items), size)
    }

    suspend fun getOrdersByMakers(
        makers: List<String>,
        statuses: List<OrderStatus>,
        platforms: List<TezosPlatform> = listOf(TezosPlatform.RARIBLE_V1, TezosPlatform.RARIBLE_V2),
        isBid: Boolean = false,
        size: Int = DEFAULT_PAGE,
        continuation: String?
    ): DipDupOrdersPage {
        val parsedContinuation = DipDupContinuation.parse(continuation)
        val response = safeExecution(
            GetOrderByMakerCustomQuery(
                makers = makers,
                statuses = statuses.map { it.name },
                platforms = platforms,
                limit = size,
                prevId = parsedContinuation?.let { it.id.toString() },
                prevDate = parsedContinuation?.let { it.date.toString() },
                isBid = isBid
            )
        )
        return convertByMaker(response.marketplace_order, size)
    }

    suspend fun getSellOrdersCurrenciesByItem(
        contract: String,
        tokenId: String
    ): List<Asset.AssetType> {
        val response = safeExecution(GetOrdersTakeContractsByMakeItemQuery(contract, tokenId))
        return convert(response)
    }

    suspend fun getSellOrdersCurrenciesByCollection(
        contract: String
    ): List<Asset.AssetType> {
        val response = safeExecution(GetOrdersTakeContractsByMakeCollectionQuery(contract))
        return convert(response)
    }

    suspend fun getBidOrdersCurrenciesByItem(
        contract: String,
        tokenId: String
    ): List<Asset.AssetType> {
        val response = safeExecution(GetOrdersMakeContractsByTakeItemQuery(contract, tokenId))
        return convert(response)
    }

    suspend fun getBidOrdersCurrenciesByCollection(
        contract: String
    ): List<Asset.AssetType> {
        val response = safeExecution(GetOrdersMakeContractsByTakeCollectionQuery(contract))
        return convert(response)
    }

    suspend fun getLegacyData(ids: List<String>): Map<String, Any> {
        val response = safeExecution(GetLegacyDataQuery(ids))
        return convert(response)
    }

    private fun page(orders: List<DipDupOrder>, limit: Int): DipDupOrdersPage {
        val nextContinuation = when (orders.size) {
            limit -> orders[limit - 1].let {
                DipDupActivityContinuation(it.lastUpdatedAt, it.id).toString()
            }
            else -> null
        }
        return DipDupOrdersPage(
            orders = orders,
            continuation = nextContinuation
        )
    }

    companion object {
        const val DEFAULT_PAGE = 1_000
    }
}
