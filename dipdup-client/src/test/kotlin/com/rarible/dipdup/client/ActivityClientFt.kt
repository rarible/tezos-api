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
                            "id": 16535888,
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
                            "id": 16535888,
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

        val activities = activityClient.getActivities(10, "1")
        assertThat(activities).hasSize(2)
    }
}
