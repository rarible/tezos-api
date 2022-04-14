package com.rarible.dipdup.client

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OrderClientFt : BaseClientFt() {

    val orderClient = OrderClient(client)

    @Test
    fun `should return order`() = runBlocking<Unit> {
        mock("""
            {
                "data": {
                    "marketplace_order_by_pk": {
                        "taker": null,
                        "status": "ACTIVE",
                        "started_at": "2021-12-17T22:13:36+00:00",
                        "salt": 32348802,
                        "network": "mainnet",
                        "maker": "tz1NwgYECLW7MXgkkg2u5q1XmgY6gB6javcs",
                        "make_token_id": "31990332720004186509664948820058199551131943283279046269956010934973196929702",
                        "make_price": 1000,
                        "make_value": 1,
                        "platform": "Objkt",
                        "make_contract": "KT1HZVd9Cjc2CMe3sQvXgbxhpJkdena21pih",
                        "make_stock": 1,
                        "last_updated_at": "2021-12-17T22:13:36+00:00",
                        "internal_order_id": "267708",
                        "id": 138995105,
                        "fill": 0,
                        "ended_at": null,
                        "created_at": "2021-12-17T22:13:36+00:00",
                        "cancelled": false
                    }
                }
            }""")

        val order = orderClient.getById("1")

        assertThat(order).isNotNull
    }

    @Test
    fun `should return orders`() = runBlocking<Unit> {
        mock("""
            {
              "data": {
                "marketplace_order": [
                  {
                        "taker": null,
                        "status": "ACTIVE",
                        "started_at": "2021-12-17T22:13:36+00:00",
                        "salt": 32348802,
                        "network": "mainnet",
                        "maker": "tz1NwgYECLW7MXgkkg2u5q1XmgY6gB6javcs",
                        "make_token_id": "31990332720004186509664948820058199551131943283279046269956010934973196929702",
                        "make_price": 1000,
                        "make_value": 1,
                        "platform": "Objkt",
                        "make_contract": "KT1HZVd9Cjc2CMe3sQvXgbxhpJkdena21pih",
                        "make_stock": 1,
                        "last_updated_at": "2021-12-17T22:13:36+00:00",
                        "internal_order_id": "267708",
                        "id": 138995105,
                        "fill": 0,
                        "ended_at": null,
                        "created_at": "2021-12-17T22:13:36+00:00",
                        "cancelled": false
                  },
                  {
                        "taker": null,
                        "status": "ACTIVE",
                        "started_at": "2021-12-17T22:13:36+00:00",
                        "salt": 32348802,
                        "network": "mainnet",
                        "maker": "tz1NwgYECLW7MXgkkg2u5q1XmgY6gB6javcs",
                        "make_token_id": "31990332720004186509664948820058199551131943283279046269956010934973196929702",
                        "make_price": 1000,
                        "make_value": 1,
                        "platform": "Objkt",
                        "make_contract": "KT1HZVd9Cjc2CMe3sQvXgbxhpJkdena21pih",
                        "make_stock": 1,
                        "last_updated_at": "2021-12-17T22:13:36+00:00",
                        "internal_order_id": "267708",
                        "id": 138995105,
                        "fill": 0,
                        "ended_at": null,
                        "created_at": "2021-12-17T22:13:36+00:00",
                        "cancelled": false
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
                            "taker": null,
                            "status": "ACTIVE",
                            "started_at": "2021-12-17T22:13:36+00:00",
                            "salt": 32348802,
                            "network": "mainnet",
                            "maker": "tz1NwgYECLW7MXgkkg2u5q1XmgY6gB6javcs",
                            "make_token_id": "31990332720004186509664948820058199551131943283279046269956010934973196929702",
                            "make_price": 1000,
                            "make_value": 1,
                            "platform": "Objkt",
                            "make_contract": "KT1HZVd9Cjc2CMe3sQvXgbxhpJkdena21pih",
                            "make_stock": 1,
                            "last_updated_at": "2021-12-17T22:13:36+00:00",
                            "internal_order_id": "267708",
                            "id": 138995105,
                            "fill": 0,
                            "ended_at": null,
                            "created_at": "2021-12-17T22:13:36+00:00",
                            "cancelled": false
                        },
                        {
                            "taker": null,
                            "status": "ACTIVE",
                            "started_at": "2021-12-17T22:13:36+00:00",
                            "salt": 32348802,
                            "network": "mainnet",
                            "maker": "tz1NwgYECLW7MXgkkg2u5q1XmgY6gB6javcs",
                            "make_token_id": "31990332720004186509664948820058199551131943283279046269956010934973196929702",
                            "make_price": 1000,
                            "make_value": 1,
                            "platform": "Objkt",
                            "make_contract": "KT1HZVd9Cjc2CMe3sQvXgbxhpJkdena21pih",
                            "make_stock": 1,
                            "last_updated_at": "2021-12-17T22:13:36+00:00",
                            "internal_order_id": "267708",
                            "id": 138995105,
                            "fill": 0,
                            "ended_at": null,
                            "created_at": "2021-12-17T22:13:36+00:00",
                            "cancelled": false
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
                    "taker": null,
                    "status": "ACTIVE",
                    "started_at": "2021-12-17T22:13:36+00:00",
                    "salt": 32348802,
                    "network": "mainnet",
                    "maker": "tz1NwgYECLW7MXgkkg2u5q1XmgY6gB6javcs",
                    "make_token_id": "31990332720004186509664948820058199551131943283279046269956010934973196929702",
                    "make_price": 1000,
                    "make_value": 1,
                    "platform": "Objkt",
                    "make_contract": "KT1HZVd9Cjc2CMe3sQvXgbxhpJkdena21pih",
                    "make_stock": 1,
                    "last_updated_at": "2021-12-17T22:13:36+00:00",
                    "internal_order_id": "267708",
                    "id": 138995105,
                    "fill": 0,
                    "ended_at": null,
                    "created_at": "2021-12-17T22:13:36+00:00",
                    "cancelled": false
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
