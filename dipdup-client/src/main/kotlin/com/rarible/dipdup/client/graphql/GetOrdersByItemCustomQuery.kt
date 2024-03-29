package com.rarible.dipdup.client.graphql

import com.rarible.dipdup.client.GetOrdersByItemQuery
import com.rarible.dipdup.client.core.model.TezosPlatform

data class GetOrdersByItemCustomQuery(
    val contract: String,
    val tokenId: String,
    val maker: String? = null,
    val currencyId: String,
    val statuses: List<String> = emptyList(),
    val platforms: List<TezosPlatform>,
    val limit: Int,
    val prevId: String? = null,
    val prevValue: String? = null,
    val isBid: Boolean = false
) : GetOrdersByItemQuery(limit) {

    // Apollo couldn't generate dynamic query, that's why we do it here
    override fun document(): String {
        val conditions = mutableListOf<String>()
        val left = side(isBid)
        val right = side(!isBid)

        contract.also { conditions.add("${left}_contract: {_eq: \"$it\"}") }
        tokenId.also { conditions.add("${left}_token_id: {_eq: \"$it\"}") }
        maker?.let { conditions.add("maker: {_eq: \"$it\"}") }
        if (currencyId == "XTZ") {
            conditions.add("${right}_contract: {_is_null: true}")
        } else {
            val raw = currencyId.split(":")
            conditions.add("${right}_contract: {_eq: \"${raw[0]}\"}")
            if (raw.size == 2) {
                conditions.add("${right}_token_id: {_eq: \"${raw[1]}\"}")
            }
        }
        conditions.add("is_bid: {_eq: $isBid}")
        if (statuses.isNotEmpty()) conditions.add("status: {_in: [${statuses.joinToString(",")}]}")
        conditions.add("platform: {_in: [${platforms.joinToString(",")}]}")
        prevValue?.let { conditions.add("""
            _or: [
                {
                    make_price: {_gt: "$prevValue"}
                },
                {
                    make_price: {_eq: "$prevValue"}
                    id: {_gt: "$prevId"}
                }
            ]
        """.trimIndent()) }
        return """
            query GetOrdersByItem(${'$'}limit: Int!) {
                marketplace_order(
                    where: {${conditions.joinToString(",\n")}}
                    limit: ${'$'}limit
                    order_by: [{make_price: asc},{id: asc}]
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
                make_stock
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

    private fun side(isBid: Boolean) = if (isBid) "take" else "make"
}
