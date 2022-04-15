package com.rarible.dipdup.client

import com.rarible.dipdup.client.core.model.OrderStatus
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class OrderClientFt : BaseClientFt() {

    val orderClient = OrderClient(client)

    companion object {

        @JvmStatic
        fun allOrdersStatuses() = Stream.of(
            Arguments.of(OrderStatus.FILLED, """
                        "cancelled": false,
                        "created_at": "2022-02-23T15:27:38+00:00",
                        "fill": 1,
                        "ended_at": "2022-02-23T15:29:38+00:00",
                        "id": 16535991,
                        "internal_order_id": "1884785405435349737",
                        "last_updated_at": "2022-02-23T15:29:38+00:00",
                        "make_asset_class": "TEZOS_FT",
                        "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                        "make_price": 1,
                        "make_stock": 0,
                        "make_token_id": "3",
                        "make_value": 1,
                        "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                        "network": "hangzhou2net",
                        "platform": "Rarible",
                        "started_at": "2022-02-23T15:27:38+00:00",
                        "salt": 17102,
                        "status": "FILLED",
                        "take_asset_class": "XTZ",
                        "take_contract": null,
                        "take_token_id": null,
                        "take_value": 1,
                        "taker": null"""),
            Arguments.of(OrderStatus.ACTIVE, """
                        "cancelled": false,
                        "created_at": "2022-02-23T15:32:24+00:00",
                        "fill": 0,
                        "ended_at": null,
                        "id": 16536566,
                        "internal_order_id": "3354986015405722853",
                        "last_updated_at": "2022-02-23T15:32:24+00:00",
                        "make_asset_class": "TEZOS_FT",
                        "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                        "make_price": 1,
                        "make_stock": 1,
                        "make_token_id": "1111",
                        "make_value": 1,
                        "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                        "network": "hangzhou2net",
                        "platform": "Rarible",
                        "started_at": "2022-02-23T15:32:24+00:00",
                        "salt": 17108,
                        "status": "ACTIVE",
                        "take_asset_class": "XTZ",
                        "take_contract": null,
                        "take_token_id": null,
                        "take_value": 1,
                        "taker": null"""),
            Arguments.of(OrderStatus.CANCELLED, """
                        "cancelled": true,
                        "created_at": "2022-02-23T15:32:39+00:00",
                        "fill": 0,
                        "ended_at": "2022-02-23T16:18:36+00:00",
                        "id": 16536619,
                        "internal_order_id": "2499317485130079221",
                        "last_updated_at": "2022-02-23T16:18:36+00:00",
                        "make_asset_class": "TEZOS_FT",
                        "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                        "make_price": 1,
                        "make_stock": 1,
                        "make_token_id": "2222",
                        "make_value": 1,
                        "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                        "network": "hangzhou2net",
                        "platform": "Rarible",
                        "started_at": "2022-02-23T15:32:39+00:00",
                        "salt": 17109,
                        "status": "CANCELLED",
                        "take_asset_class": "XTZ",
                        "take_contract": null,
                        "take_token_id": null,
                        "take_value": 1,
                        "taker": null""")
        )
    }

    @ParameterizedTest
    @MethodSource("allOrdersStatuses")
    fun `should return order`(status: OrderStatus, body: String) = runBlocking<Unit> {
        mock("""{"data": {"marketplace_order_by_pk":{$body}}}""")

        val order = orderClient.getById("1")

        assertThat(order).isNotNull
        assertThat(order.status).isEqualTo(status)
    }

    @Test
    fun `should return orders`() = runBlocking<Unit> {
        mock("""
            {
              "data": {
                "marketplace_order": [
                  {
                        "cancelled": false,
                        "created_at": "2022-02-23T15:27:38+00:00",
                        "fill": 1,
                        "ended_at": "2022-02-23T15:29:38+00:00",
                        "id": 16535991,
                        "internal_order_id": "1884785405435349737",
                        "last_updated_at": "2022-02-23T15:29:38+00:00",
                        "make_asset_class": "TEZOS_FT",
                        "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                        "make_price": 1,
                        "make_stock": 0,
                        "make_token_id": "3",
                        "make_value": 1,
                        "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                        "network": "hangzhou2net",
                        "platform": "Rarible",
                        "started_at": "2022-02-23T15:27:38+00:00",
                        "salt": 17102,
                        "status": "FILLED",
                        "take_asset_class": "XTZ",
                        "take_contract": null,
                        "take_token_id": null,
                        "take_value": 1,
                        "taker": null
                  },
                  {
                        "cancelled": false,
                        "created_at": "2022-02-23T15:27:38+00:00",
                        "fill": 1,
                        "ended_at": "2022-02-23T15:29:38+00:00",
                        "id": 16535991,
                        "internal_order_id": "1884785405435349737",
                        "last_updated_at": "2022-02-23T15:29:38+00:00",
                        "make_asset_class": "TEZOS_FT",
                        "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                        "make_price": 1,
                        "make_stock": 0,
                        "make_token_id": "3",
                        "make_value": 1,
                        "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                        "network": "hangzhou2net",
                        "platform": "Rarible",
                        "started_at": "2022-02-23T15:27:38+00:00",
                        "salt": 17102,
                        "status": "FILLED",
                        "take_asset_class": "XTZ",
                        "take_contract": null,
                        "take_token_id": null,
                        "take_value": 1,
                        "taker": null
                  }
                ]
              }
            }""")

        val orders = orderClient.getOrders(10, "1")
        assertThat(orders).hasSize(2)
    }


    @Test
    fun `should return orders by ids`() = runBlocking<Unit> {
        mock("""
            {
                "data": {
                    "marketplace_order": [
                        {
                            "cancelled": false,
                            "created_at": "2022-02-23T15:27:38+00:00",
                            "fill": 1,
                            "ended_at": "2022-02-23T15:29:38+00:00",
                            "id": 16535991,
                            "internal_order_id": "1884785405435349737",
                            "last_updated_at": "2022-02-23T15:29:38+00:00",
                            "make_asset_class": "TEZOS_FT",
                            "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                            "make_price": 1,
                            "make_stock": 0,
                            "make_token_id": "3",
                            "make_value": 1,
                            "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                            "network": "hangzhou2net",
                            "platform": "Rarible",
                            "started_at": "2022-02-23T15:27:38+00:00",
                            "salt": 17102,
                            "status": "FILLED",
                            "take_asset_class": "XTZ",
                            "take_contract": null,
                            "take_token_id": null,
                            "take_value": 1,
                            "taker": null
                        },
                        {
                            "cancelled": false,
                            "created_at": "2022-02-23T15:27:38+00:00",
                            "fill": 1,
                            "ended_at": "2022-02-23T15:29:38+00:00",
                            "id": 16535991,
                            "internal_order_id": "1884785405435349737",
                            "last_updated_at": "2022-02-23T15:29:38+00:00",
                            "make_asset_class": "TEZOS_FT",
                            "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                            "make_price": 1,
                            "make_stock": 0,
                            "make_token_id": "3",
                            "make_value": 1,
                            "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                            "network": "hangzhou2net",
                            "platform": "Rarible",
                            "started_at": "2022-02-23T15:27:38+00:00",
                            "salt": 17102,
                            "status": "FILLED",
                            "take_asset_class": "XTZ",
                            "take_contract": null,
                            "take_token_id": null,
                            "take_value": 1,
                            "taker": null
                        }
                    ]
                }
            }""")

        val orders = orderClient.getOrdersByIds(listOf("1", "10"))
        assertThat(orders).hasSize(2)
    }

    @Test
    fun `get orders by item`() = runBlocking<Unit> {
        mock("""
            {
              "data": {
                "marketplace_order": [
                  {
                    "cancelled": false,
                    "created_at": "2022-02-23T15:27:38+00:00",
                    "fill": 1,
                    "ended_at": "2022-02-23T15:29:38+00:00",
                    "id": 16535991,
                    "internal_order_id": "1884785405435349737",
                    "last_updated_at": "2022-02-23T15:29:38+00:00",
                    "make_asset_class": "TEZOS_FT",
                    "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                    "make_price": 1,
                    "make_stock": 0,
                    "make_token_id": "3",
                    "make_value": 1,
                    "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                    "network": "hangzhou2net",
                    "platform": "Rarible",
                    "started_at": "2022-02-23T15:27:38+00:00",
                    "salt": 17102,
                    "status": "FILLED",
                    "take_asset_class": "XTZ",
                    "take_contract": null,
                    "take_token_id": null,
                    "take_value": 1,
                    "taker": null
                  }]}}
        """.trimIndent())
        val orders = orderClient.getOrdersByItem(
            contract = "KT1H8sxNSgnkCeZsij4z76pkXu8BCZNvPZEx",
            id = "3",
            limit = 10,
            prevId = "1"
        )
        assertThat(orders).hasSize(1)
    }
}
