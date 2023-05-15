package com.rarible.dipdup.client.graphql

import com.rarible.dipdup.client.GetOrdersQuery
import com.rarible.dipdup.client.core.model.TezosPlatform
import com.rarible.dipdup.client.model.DipDupOrderSort
import java.util.*

data class GetOrdersCustomQuery(
    val statuses: List<String> = emptyList(),
    val platforms: List<TezosPlatform>,
    val limit: Int,
    val sort: DipDupOrderSort,
    val prevId: UUID? = null,
    val prevDate: String? = null,
    val isBid: Boolean? = null
) : GetOrdersQuery(limit) {

    // Apollo couldn't generate dynamic query, that's why we do it here
    override fun document(): String {
        val conditions = mutableListOf<String>()
        if (statuses.isNotEmpty()) conditions.add("status: {_in: [${statuses.joinToString(",")}]}")
        conditions.add("platform: {_in: [${platforms.joinToString(",")}]}")
        prevDate?.let { conditions.add(continuation(sort)) }
        isBid?.let { conditions.add("is_bid: {_eq: $isBid}") }
        val request = """
            query GetOrders(${'$'}limit: Int!) {
                marketplace_order(
                    where: {${conditions.joinToString(",\n")}}
                    limit: ${'$'}limit
                    order_by: ${orderBy(sort)}
                ) { __typename ...order } }  
            fragment order on marketplace_order {
                cancelled
                created_at
                fill
                ended_at
                end_at
                id
                internal_order_id
                last_updated_at
                make_asset_class
                make_contract
                make_token_id
                make_value
                make_price
                maker
                network
                platform
                start_at
                is_bid
                salt
                status
                take_asset_class
                take_contract
                take_token_id
                take_value
                take_price
                taker
                origin_fees
                payouts
            }
        """.trimIndent()
        return request
    }

    fun continuation(sort: DipDupOrderSort) = when (sort) {
        DipDupOrderSort.LAST_UPDATE_DESC -> """
            _or: [
                {
                    last_updated_at: {_lt: "$prevDate"}
                },
                {
                    last_updated_at: {_eq: "$prevDate"}
                    id: {_lt: "$prevId"}
                }
            ]
        """.trimIndent()
        else -> """
            _or: [
                {
                    last_updated_at: {_gt: "$prevDate"}
                },
                {
                    last_updated_at: {_eq: "$prevDate"}
                    id: {_gt: "$prevId"}
                }
            ]
        """.trimIndent()

    }

    fun orderBy(sort: DipDupOrderSort) = when (sort) {
        DipDupOrderSort.LAST_UPDATE_DESC -> "[{last_updated_at: desc}, {id: desc}]"
        else -> "[{last_updated_at: asc}, {id: asc}]"
    }
}
