package com.rarible.dipdup.client

import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.OrderStatus
import com.rarible.dipdup.client.exception.DipDupNotFound
import com.rarible.dipdup.client.model.DipDupContinuation
import com.rarible.dipdup.client.model.DipDupOrderSort
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigInteger
import java.util.stream.Stream

class OrderClientFt : BaseClientFt() {

    val orderClient = OrderClient(client)

    companion object {

        @JvmStatic
        fun allOrdersStatuses() = Stream.of(
            Arguments.of(OrderStatus.FILLED, """
                        "__typename": "marketplace_order",
                        "cancelled": false,
                        "created_at": "2022-02-23T15:27:38+00:00",
                        "fill": 1,
                        "ended_at": "2022-02-23T15:29:38+00:00",
                        "id": 16535991,
                        "internal_order_id": "1884785405435349737",
                        "last_updated_at": "2022-02-23T15:29:38+00:00",
                        "make_asset_class": "TEZOS_FT",
                        "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                        "make_token_id": "3",
                        "make_value": 1,
                        "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                        "network": "hangzhou2net",
                        "platform": "Rarible",
                        "start_at": "2022-02-23T15:27:38+00:00",
                        "salt": 17102,
                        "status": "FILLED",
                        "take_asset_class": "XTZ",
                        "take_contract": null,
                        "take_token_id": null,
                        "take_value": 1,
                        "taker": null,
                        "origin_fees": [
                            {
                                "part_account": "tz1Mxsc66En4HsVHr6rppYZW82ZpLhpupToC",
                                "part_value": "100"
                            }
                        ],
                        "payouts": [
                            {
                                "part_account": "tz1Mxsc66En4HsVHr6rppYZW82ZpLhpupToC",
                                "part_value": "200"
                            }
                        ]
                        """),
            Arguments.of(OrderStatus.ACTIVE, """
                        "__typename": "marketplace_order",
                        "cancelled": false,
                        "created_at": "2022-02-23T15:32:24+00:00",
                        "fill": 0,
                        "ended_at": null,
                        "id": 16536566,
                        "internal_order_id": "3354986015405722853",
                        "last_updated_at": "2022-02-23T15:32:24+00:00",
                        "make_asset_class": "TEZOS_FT",
                        "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                        "make_token_id": "1111",
                        "make_value": 1,
                        "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                        "network": "hangzhou2net",
                        "platform": "Rarible",
                        "start_at": "2022-02-23T15:32:24+00:00",
                        "salt": 17108,
                        "status": "ACTIVE",
                        "take_asset_class": "XTZ",
                        "take_contract": null,
                        "take_token_id": null,
                        "take_value": 1,
                        "taker": null,
                        "origin_fees": [
                            {
                                "part_account": "tz1Mxsc66En4HsVHr6rppYZW82ZpLhpupToC",
                                "part_value": "100"
                            }
                        ],
                        "payouts": [
                            {
                                "part_account": "tz1Mxsc66En4HsVHr6rppYZW82ZpLhpupToC",
                                "part_value": "200"
                            }
                        ]
                        """),
            Arguments.of(OrderStatus.CANCELLED, """
                        "__typename": "marketplace_order",
                        "cancelled": true,
                        "created_at": "2022-02-23T15:32:39+00:00",
                        "fill": 0,
                        "ended_at": "2022-02-23T16:18:36+00:00",
                        "id": 16536619,
                        "internal_order_id": "2499317485130079221",
                        "last_updated_at": "2022-02-23T16:18:36+00:00",
                        "make_asset_class": "TEZOS_FT",
                        "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                        "make_token_id": "2222",
                        "make_value": 1,
                        "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                        "network": "hangzhou2net",
                        "platform": "Rarible",
                        "start_at": "2022-02-23T15:32:39+00:00",
                        "salt": 17109,
                        "status": "CANCELLED",
                        "take_asset_class": "XTZ",
                        "take_contract": null,
                        "take_token_id": null,
                        "take_value": 1,
                        "taker": null,
                        "origin_fees": [
                            {
                                "part_account": "tz1Mxsc66En4HsVHr6rppYZW82ZpLhpupToC",
                                "part_value": "100"
                            }
                        ],
                        "payouts": [
                            {
                                "part_account": "tz1Mxsc66En4HsVHr6rppYZW82ZpLhpupToC",
                                "part_value": "200"
                            }
                        ]
                        """)
        )
    }

    @ParameterizedTest
    @MethodSource("allOrdersStatuses")
    fun `should return order`(status: OrderStatus, body: String) = runBlocking<Unit> {
        mock("""{"data": {"marketplace_order_by_pk":{$body}}}""")

        val order = orderClient.getOrderById("5788347d-a3e3-58b9-982c-68149874125b")

        assertThat(order).isNotNull
        assertThat(order.status).isEqualTo(status)
        assertThat(order.originFees.size).isEqualTo(1)
        assertThat(order.payouts.size).isEqualTo(1)
        assertThat(order.originFees.first().account).isEqualTo("tz1Mxsc66En4HsVHr6rppYZW82ZpLhpupToC")
        assertThat(order.originFees.first().value).isEqualTo(100)
        assertThat(order.payouts.first().account).isEqualTo("tz1Mxsc66En4HsVHr6rppYZW82ZpLhpupToC")
        assertThat(order.payouts.first().value).isEqualTo(200)
    }

    @Test
    fun `should return orders`() = runBlocking<Unit> {
        mock("""
            {
              "data": {
                "marketplace_order": [
                  {
                        "__typename": "marketplace_order",
                        "cancelled": false,
                        "created_at": "2022-02-23T15:27:38+00:00",
                        "fill": 1,
                        "ended_at": "2022-02-23T15:29:38+00:00",
                        "id": 16535991,
                        "internal_order_id": "1884785405435349737",
                        "last_updated_at": "2022-02-23T15:29:38+00:00",
                        "make_asset_class": "TEZOS_FT",
                        "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                        "make_token_id": "3",
                        "make_value": 1,
                        "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                        "network": "hangzhou2net",
                        "platform": "Rarible",
                        "start_at": "2022-02-23T15:27:38+00:00",
                        "salt": 17102,
                        "status": "FILLED",
                        "take_asset_class": "XTZ",
                        "take_contract": null,
                        "take_token_id": null,
                        "take_value": 1,
                        "taker": null
                  },
                  {
                        "__typename": "marketplace_order",
                        "cancelled": false,
                        "created_at": "2022-02-23T15:27:38+00:00",
                        "fill": 1,
                        "ended_at": "2022-02-23T15:29:38+00:00",
                        "id": 16535991,
                        "internal_order_id": "1884785405435349737",
                        "last_updated_at": "2022-02-23T15:29:38+00:00",
                        "make_asset_class": "TEZOS_FT",
                        "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                        "make_token_id": "3",
                        "make_value": 1,
                        "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                        "network": "hangzhou2net",
                        "platform": "Rarible",
                        "start_at": "2022-02-23T15:27:38+00:00",
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

        val page = orderClient.getOrdersAll(
            statuses = emptyList(),
            sort = DipDupOrderSort.LAST_UPDATE_DESC,
            size = 10,
            continuation = null
        )
        assertThat(page.orders).hasSize(2)
        assertThat(page.continuation).isNull()
    }

    @Test
    fun `should return orders by ids`() = runBlocking<Unit> {
        mock("""
            {
                "data": {
                    "marketplace_order": [
                        {
                            "__typename": "marketplace_order",
                            "cancelled": false,
                            "created_at": "2022-02-23T15:27:38+00:00",
                            "fill": 1,
                            "ended_at": "2022-02-23T15:29:38+00:00",
                            "id": "83d8414f-ae60-5b91-b270-97ba99964af2",
                            "internal_order_id": "1884785405435349737",
                            "last_updated_at": "2022-02-23T15:29:38+00:00",
                            "make_asset_class": "TEZOS_FT",
                            "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                            "make_token_id": "3",
                            "make_value": 1,
                            "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                            "network": "hangzhou2net",
                            "platform": "Rarible",
                            "start_at": "2022-02-23T15:27:38+00:00",
                            "salt": 17102,
                            "status": "FILLED",
                            "take_asset_class": "XTZ",
                            "take_contract": null,
                            "take_token_id": null,
                            "take_value": 1,
                            "taker": null
                        },
                        {
                            "__typename": "marketplace_order",
                            "cancelled": false,
                            "created_at": "2022-02-23T15:27:38+00:00",
                            "fill": 1,
                            "ended_at": "2022-02-23T15:29:38+00:00",
                            "id": "5788347d-a3e3-58b9-982c-68149874125b",
                            "internal_order_id": "1884785405435349737",
                            "last_updated_at": "2022-02-23T15:29:38+00:00",
                            "make_asset_class": "TEZOS_FT",
                            "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                            "make_token_id": "3",
                            "make_value": 1,
                            "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                            "network": "hangzhou2net",
                            "platform": "Rarible",
                            "start_at": "2022-02-23T15:27:38+00:00",
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

        val orders = orderClient.getOrdersByIds(listOf("83d8414f-ae60-5b91-b270-97ba99964af2", "5788347d-a3e3-58b9-982c-68149874125b"))
        assertThat(orders).hasSize(2)
    }

    @Test
    fun `get orders by item`() = runBlocking<Unit> {
        mock("""
            {
              "data": {
                "marketplace_order": [
                  {
                    "__typename": "marketplace_order",
                    "cancelled": false,
                    "created_at": "2022-02-23T15:27:38+00:00",
                    "fill": 1,
                    "ended_at": "2022-02-23T15:29:38+00:00",
                    "id": 16535991,
                    "internal_order_id": "1884785405435349737",
                    "last_updated_at": "2022-02-23T15:29:38+00:00",
                    "make_asset_class": "TEZOS_FT",
                    "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                    "make_token_id": "3",
                    "make_value": 1,
                    "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                    "network": "hangzhou2net",
                    "platform": "Rarible",
                    "start_at": "2022-02-23T15:27:38+00:00",
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
            contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton",
            tokenId = "691915",
            maker = null,
            currencyId = "XTZ",
            statuses = listOf(),
            size = 2,
            continuation = "1659577833_42737510-3635-53a9-85cc-c37c81c74cf6"
        )
        assertThat(orders.orders).hasSize(1)
    }

    @Test
    fun `get orders by item with empty continuation`() = runBlocking<Unit> {
        mock("""
            {
              "data": {
                "marketplace_order": [
                  {
                    "__typename": "marketplace_order",
                    "cancelled": false,
                    "created_at": "2022-02-23T15:27:38+00:00",
                    "fill": 1,
                    "ended_at": "2022-02-23T15:29:38+00:00",
                    "id": 16535991,
                    "internal_order_id": "1884785405435349737",
                    "last_updated_at": "2022-02-23T15:29:38+00:00",
                    "make_asset_class": "TEZOS_FT",
                    "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                    "make_token_id": "3",
                    "make_value": 1,
                    "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                    "network": "hangzhou2net",
                    "platform": "Rarible",
                    "start_at": "2022-02-23T15:27:38+00:00",
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
            contract = "KT1NWdwVA8zq5DDJTKcMkRqWYJcEcyTTm5WK",
            tokenId = "1168",
            maker = null,
            currencyId = "XTZ",
            statuses = listOf(),
            size = 2,
            continuation = null
        )
        assertThat(orders.orders).hasSize(1)
    }

    @Test
    fun `get orders by item with FT in currency`() = runBlocking<Unit> {
        mock("""
            {
              "data": {
                "marketplace_order": [
                  {
                    "__typename": "marketplace_order",
                    "cancelled": false,
                    "created_at": "2022-02-23T15:27:38+00:00",
                    "fill": 1,
                    "ended_at": "2022-02-23T15:29:38+00:00",
                    "id": 16535991,
                    "internal_order_id": "1884785405435349737",
                    "last_updated_at": "2022-02-23T15:29:38+00:00",
                    "make_asset_class": "TEZOS_FT",
                    "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                    "make_token_id": "3",
                    "make_value": 1,
                    "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                    "network": "hangzhou2net",
                    "platform": "Rarible",
                    "start_at": "2022-02-23T15:27:38+00:00",
                    "salt": 17102,
                    "status": "FILLED",
                    "take_asset_class": "XTZ",
                    "take_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                    "take_token_id": "123",
                    "take_value": 1,
                    "taker": null
                  }]}}
        """.trimIndent())
        val orders = orderClient.getOrdersByItem(
            contract = "KT1NWdwVA8zq5DDJTKcMkRqWYJcEcyTTm5WK",
            tokenId = "1168",
            maker = null,
            currencyId = "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3:123",
            statuses = listOf(),
            size = 2,
            continuation = null
        )
        assertThat(orders.orders).hasSize(1)
    }

    @Test
    fun `should return orders with continuation`() = runBlocking<Unit> {
        mock("""
            {
                "data": {
                    "marketplace_order": [
                        {
                            "__typename": "marketplace_order",
                            "cancelled": false,
                            "created_at": "2022-02-23T15:27:38+00:00",
                            "fill": 1,
                            "ended_at": "2022-02-23T15:29:38+00:00",
                            "id": "83d8414f-ae60-5b91-b270-97ba99964af2",
                            "internal_order_id": "1884785405435349737",
                            "last_updated_at": "2022-02-23T15:29:38+00:00",
                            "make_asset_class": "TEZOS_FT",
                            "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                            "make_token_id": "3",
                            "make_value": 1,
                            "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                            "network": "hangzhou2net",
                            "platform": "Rarible",
                            "start_at": "2022-02-23T15:27:38+00:00",
                            "salt": 17102,
                            "status": "FILLED",
                            "take_asset_class": "XTZ",
                            "take_contract": null,
                            "take_token_id": null,
                            "take_value": 1,
                            "taker": null
                        },
                        {
                            "__typename": "marketplace_order",
                            "cancelled": false,
                            "created_at": "2022-02-23T15:27:38+00:00",
                            "fill": 1,
                            "ended_at": "2022-02-23T15:29:38+00:00",
                            "id": "5788347d-a3e3-58b9-982c-68149874125b",
                            "internal_order_id": "1884785405435349737",
                            "last_updated_at": "2022-02-23T15:29:38+00:00",
                            "make_asset_class": "TEZOS_FT",
                            "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                            "make_token_id": "3",
                            "make_value": 1,
                            "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                            "network": "hangzhou2net",
                            "platform": "Rarible",
                            "start_at": "2022-02-23T15:27:38+00:00",
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
            }""".trimIndent())
        val page = orderClient.getOrdersAll(
            statuses = emptyList(),
            sort = DipDupOrderSort.LAST_UPDATE_DESC,
            size = 2,
            continuation = null
        )
        assertThat(page.orders).hasSize(2)
        assertThat(page.continuation).isNotNull()
        assertThat(DipDupContinuation.isValid(page.continuation!!)).isTrue()
    }

    @Test
    fun `should return assetTypes by item`() = runBlocking<Unit> {
        mock("""
            {
              "data": {
                "marketplace_order": [
                  {
                    "__typename": "marketplace_order",
                    "take_contract": null,
                    "take_token_id": null
                  },
                  {
                    "__typename": "marketplace_order",
                    "take_contract": "KT1FaGrMVr6rvfHsfbPSAPgRabsPMiQeaTin",
                    "take_token_id": "1"
                  }
                ]
              }
            }
        """.trimIndent())
        val types = orderClient.getOrdersCurrenciesByItem("KT1FaGrMVr6rvfHsfbPSAPgRabsPMiQeaTin", "1")

        assertThat(types).hasSize(2)
        assertThat(types).contains(Asset.XTZ())
        assertThat(types).contains(Asset.FT(Asset.FT_NAME, "KT1FaGrMVr6rvfHsfbPSAPgRabsPMiQeaTin", BigInteger("1")))
    }

    @Test
    fun `should return assetTypes by collection`() = runBlocking<Unit> {
        mock("""
            {
              "data": {
                "marketplace_order": [
                  {
                    "__typename": "marketplace_order",
                    "take_contract": null,
                    "take_token_id": null
                  },
                  {
                    "__typename": "marketplace_order",
                    "take_contract": "KT1FaGrMVr6rvfHsfbPSAPgRabsPMiQeaTin",
                    "take_token_id": "1"
                  }
                ]
              }
            }
        """.trimIndent())
        val types = orderClient.getOrdersCurrenciesByCollection("KT1FaGrMVr6rvfHsfbPSAPgRabsPMiQeaTin")

        assertThat(types).hasSize(2)
        assertThat(types).contains(Asset.XTZ())
        assertThat(types).contains(Asset.FT(Asset.FT_NAME, "KT1FaGrMVr6rvfHsfbPSAPgRabsPMiQeaTin", BigInteger("1")))
    }

    @Test
    fun `shouldn't return order by id`() = runBlocking<Unit> {
        mock("""{"data":{"marketplace_order":[]}}""")

        assertThrows<DipDupNotFound> { orderClient.getOrderById("83d8414f-ae60-5b91-b270-97ba99964af3") }
    }

    @Test
    fun `get orders by makers`() = runBlocking<Unit> {
        mock("""
            {
              "data": {
                "marketplace_order": [
                  {
                    "__typename": "marketplace_order",
                    "cancelled": false,
                    "created_at": "2022-02-23T15:27:38+00:00",
                    "fill": 1,
                    "ended_at": "2022-02-23T15:29:38+00:00",
                    "id": 16535991,
                    "internal_order_id": "1884785405435349737",
                    "last_updated_at": "2022-02-23T15:29:38+00:00",
                    "make_asset_class": "TEZOS_FT",
                    "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                    "make_token_id": "3",
                    "make_value": 1,
                    "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                    "network": "hangzhou2net",
                    "platform": "Rarible",
                    "start_at": "2022-02-23T15:27:38+00:00",
                    "salt": 17102,
                    "status": "FILLED",
                    "take_asset_class": "XTZ",
                    "take_contract": null,
                    "take_token_id": null,
                    "take_value": 1,
                    "taker": null
                  }]}}
        """.trimIndent())

        val orders = orderClient.getOrdersByMakers(
            makers = listOf("tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb"),
            statuses = listOf(),
            size = 2,
            continuation = null
        )
        assertThat(orders.orders).hasSize(1)
    }
}
