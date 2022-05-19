package com.rarible.dipdup.client

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ActivityClientFt : BaseClientFt() {

    val activityClient = OrderActivityClient(client)

    @Test
    fun `should return activities`() = runBlocking<Unit> {
        mock("""
            {
                "data": {
                    "marketplace_activity": [
                        {
                            "type": "LIST",
                            "id": "a95a5fb1-423f-584d-9812-0a332e21507e",
                            "internal_order_id": "2547108250740353062",
                            "make_asset_class": "TEZOS_FT",
                            "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                            "make_token_id": "0",
                            "make_value": 1,
                            "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                            "network": "hangzhou2net",
                            "operation_hash": "oovjuGhB9iyvpP1cSMKYLuxZ7eGfRgea8YvF9V8MMNjsC4sxt72",
                            "operation_level": 569936,
                            "operation_timestamp": "2022-02-23T15:26:38+00:00",
                            "platform": "Rarible",
                            "sell_price": 1,
                            "take_asset_class": "XTZ",
                            "take_contract": null,
                            "take_token_id": null,
                            "take_value": 1,
                            "taker": null
                        },
                        {
                            "type": "LIST",
                            "id": "195a5fb1-423f-584d-9812-0a332e21507e",
                            "internal_order_id": "2547108250740353062",
                            "make_asset_class": "TEZOS_FT",
                            "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                            "make_token_id": "0",
                            "make_value": 1,
                            "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                            "network": "hangzhou2net",
                            "operation_hash": "oovjuGhB9iyvpP1cSMKYLuxZ7eGfRgea8YvF9V8MMNjsC4sxt72",
                            "operation_level": 569936,
                            "operation_timestamp": "2022-02-23T15:26:38+00:00",
                            "platform": "Rarible",
                            "sell_price": 1,
                            "take_asset_class": "XTZ",
                            "take_contract": null,
                            "take_token_id": null,
                            "take_value": 1,
                            "taker": null
                        }
                    ]
                }
            }""")

        val activities = activityClient.getActivities(2, "5788347d-a3e3-58b9-982c-68149874125b")
        assertThat(activities).hasSize(2)
    }

    @Test
    fun `should return activities by id`() = runBlocking<Unit> {
        mock("""
            {
                "data": {
                    "marketplace_activity": [
                        {
                            "type": "LIST",
                            "id": "895a5fb1-423f-584d-9812-0a332e21507e",
                            "internal_order_id": "2547108250740353062",
                            "make_asset_class": "TEZOS_FT",
                            "make_contract": "KT1JwfYcy2uGBg4tS8t8w5CnJotJmF5kN2J3",
                            "make_token_id": "0",
                            "make_value": 1,
                            "maker": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                            "network": "hangzhou2net",
                            "operation_hash": "oovjuGhB9iyvpP1cSMKYLuxZ7eGfRgea8YvF9V8MMNjsC4sxt72",
                            "operation_level": 569936,
                            "operation_timestamp": "2022-02-23T15:26:38+00:00",
                            "platform": "Rarible",
                            "sell_price": 1,
                            "take_asset_class": "XTZ",
                            "take_contract": null,
                            "take_token_id": null,
                            "take_value": 1,
                            "taker": null
                        }
                    ]
                }
            }""".trimIndent())
        val activities = activityClient.getActivities(listOf("895a5fb1-423f-584d-9812-0a332e21507e"))

        assertThat(activities).hasSize(1)
    }
}
