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
                        "id": 1,
                        "cancelled": true,
                        "created_at": "2022-02-10T08:35:04+00:00",
                        "ended_at": "2022-02-10T13:01:54+00:00",
                        "fill": 0.000000000000000000000000000000000000,
                        "internal_order_id": "1000000",
                        "last_updated_at": "2022-02-10T13:01:54+00:00",
                        "make_contract": "KT1Q8JB2bdphCHhEBKc1PMsjArLPcAezGBVK",
                        "make_price": 0.010000000000000000000000000000000000,
                        "make_stock": 10.000000000000000000000000000000000000,
                        "make_token_id": "2",
                        "make_value": 10.000000000000000000000000000000000000,
                        "maker": "tz1XHhjLXQuG9rf9n7o1VbgegMkiggy1oktu",
                        "network": "mainnet",
                        "platform": "Objkt_v2",
                        "started_at": "2022-02-10T08:35:04+00:00",
                        "status": "CANCELLED"
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
                    "cancelled": false,
                    "created_at": "2022-02-11T01:16:24+00:00",
                    "ended_at": null,
                    "fill": 0,
                    "id": 2109,
                    "internal_order_id": "1001663",
                    "last_updated_at": "2022-02-11T01:16:24+00:00",
                    "make_contract": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton",
                    "make_price": 2,
                    "make_stock": 1,
                    "make_token_id": "492517",
                    "make_value": 1,
                    "maker": "tz1cwWy1DPNcd5imGficjrMQv184rHeW1Qh5",
                    "network": "mainnet",
                    "platform": "Objkt_v2",
                    "started_at": "2022-02-11T01:16:24+00:00",
                    "status": "ACTIVE"
                  },
                  {
                    "cancelled": true,
                    "created_at": "2022-02-10T08:35:04+00:00",
                    "ended_at": "2022-02-10T13:01:54+00:00",
                    "fill": 0,
                    "id": 1,
                    "internal_order_id": "1000000",
                    "last_updated_at": "2022-02-10T13:01:54+00:00",
                    "make_contract": "KT1Q8JB2bdphCHhEBKc1PMsjArLPcAezGBVK",
                    "make_price": 0.01,
                    "make_stock": 10,
                    "make_token_id": "2",
                    "make_value": 10,
                    "maker": "tz1XHhjLXQuG9rf9n7o1VbgegMkiggy1oktu",
                    "network": "mainnet",
                    "platform": "Objkt_v2",
                    "started_at": "2022-02-10T08:35:04+00:00",
                    "status": "CANCELLED"
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
                            "cancelled": true,
                            "created_at": "2022-02-10T08:35:04+00:00",
                            "ended_at": "2022-02-10T13:01:54+00:00",
                            "fill": 0.000000000000000000000000000000000000,
                            "id": 1,
                            "internal_order_id": "1000000",
                            "last_updated_at": "2022-02-10T13:01:54+00:00",
                            "make_contract": "KT1Q8JB2bdphCHhEBKc1PMsjArLPcAezGBVK",
                            "make_price": 0.010000000000000000000000000000000000,
                            "make_stock": 10.000000000000000000000000000000000000,
                            "make_token_id": "2",
                            "make_value": 10.000000000000000000000000000000000000,
                            "maker": "tz1XHhjLXQuG9rf9n7o1VbgegMkiggy1oktu",
                            "network": "mainnet",
                            "platform": "Objkt_v2",
                            "started_at": "2022-02-10T08:35:04+00:00",
                            "status": "CANCELLED"
                        },
                        {
                            "cancelled": true,
                            "created_at": "2022-02-10T13:01:54+00:00",
                            "ended_at": "2022-02-10T13:13:24+00:00",
                            "fill": 0.000000000000000000000000000000000000,
                            "id": 10,
                            "internal_order_id": "1000001",
                            "last_updated_at": "2022-02-10T13:13:24+00:00",
                            "make_contract": "KT1Q8JB2bdphCHhEBKc1PMsjArLPcAezGBVK",
                            "make_price": 0.020000000000000000000000000000000000,
                            "make_stock": 10.000000000000000000000000000000000000,
                            "make_token_id": "2",
                            "make_value": 10.000000000000000000000000000000000000,
                            "maker": "tz1XHhjLXQuG9rf9n7o1VbgegMkiggy1oktu",
                            "network": "mainnet",
                            "platform": "Objkt_v2",
                            "started_at": "2022-02-10T13:01:54+00:00",
                            "status": "CANCELLED"
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
                    "id": 92597459,
                    "fill": 0,
                    "internal_order_id": "32186",
                    "last_updated_at": "2021-09-22T14:01:54+00:00",
                    "make_contract": "KT1H8sxNSgnkCeZsij4z76pkXu8BCZNvPZEx",
                    "make_price": 30,
                    "make_stock": 1,
                    "make_token_id": "3",
                    "make_value": 1,
                    "maker": "tz1XudBX7sBhkMxqGay7zdRLaeWn9rRxzVFD",
                    "network": "mainnet",
                    "platform": "Objkt",
                    "started_at": "2021-09-22T13:31:54+00:00",
                    "status": "CANCELLED",
                    "ended_at": "2021-09-22T14:01:54+00:00",
                    "created_at": "2021-09-22T13:31:54+00:00",
                    "cancelled": true
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
