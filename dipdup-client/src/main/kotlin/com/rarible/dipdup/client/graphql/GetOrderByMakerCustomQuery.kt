package com.rarible.dipdup.client.graphql

import com.rarible.dipdup.client.GetOrdersByMakerQuery
import com.rarible.dipdup.client.core.model.TezosPlatform

data class GetOrderByMakerCustomQuery(
    val makers: List<String>,
    val statuses: List<String> = emptyList(),
    val platforms: List<TezosPlatform>,
    val limit: Int,
    val prevId: String? = null,
    val prevDate: String? = null,
    val isBid: Boolean = false
) : GetOrdersByMakerQuery(limit) {
    // Apollo couldn't generate dynamic query, that's why we do it here
    override fun document(): String {
        val conditions = mutableListOf<String>()
        conditions.add("maker: {_in: [\"${makers.joinToString ( "\",\"" )}\"]}")
        if (statuses.isNotEmpty()) conditions.add("status: {_in: [${statuses.joinToString(",")}]}")
        conditions.add("platform: {_in: [${platforms.joinToString(",")}]}")
        prevDate?.let { conditions.add("""
            _or: [
                {
                    last_updated_at: {_lt: "$prevDate"}
                },
                {
                    last_updated_at: {_eq: "$prevDate"}
                    id: {_lt: "$prevId"}
                }
            ]
        """.trimIndent()) }
        conditions.add("is_bid: {_eq: $isBid}")
        return """
            query GetOrdersByMaker(${'$'}limit: Int!) {
                marketplace_order(
                    where: {${conditions.joinToString(",\n")}}
                    limit: ${'$'}limit
                    order_by: {last_updated_at: desc, id: desc}
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
    }
}
