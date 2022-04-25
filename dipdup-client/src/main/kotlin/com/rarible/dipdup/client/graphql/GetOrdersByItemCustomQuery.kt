package com.rarible.dipdup.client.graphql

import com.rarible.dipdup.client.GetOrdersByItemQuery

data class GetOrdersByItemCustomQuery(
    val contract: String,
    val tokenId: String,
    val maker: String? = null,
    val statuses: List<String> = emptyList(),
    val limit: Int,
    val prevId: String? = null,
    val prevDate: String? = null
) : GetOrdersByItemQuery(limit) {

    // Apollo couldn't generate dynamic query, that's why we do it here
    override fun document(): String {
        val conditions = mutableListOf<String>()
        contract.also { conditions.add("make_contract: {_eq: \"$it\"}") }
        tokenId.also { conditions.add("make_token_id: {_eq: \"$it\"}") }
        maker?.let { conditions.add("maker: {_eq: \"$it\"}") }
        if (statuses.isNotEmpty()) conditions.add("status: {_in: [${statuses.joinToString(",")}]}")
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
        return """
            query GetOrdersByItem(${'$'}limit: Int!) {
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
                id
                internal_order_id
                last_updated_at
                make_asset_class
                make_contract
                make_price
                make_stock
                make_token_id
                make_value
                maker
                network
                platform
                started_at
                salt
                status
                take_asset_class
                take_contract
                take_token_id
                take_value
                taker
            }
        """.trimIndent()
    }
}
