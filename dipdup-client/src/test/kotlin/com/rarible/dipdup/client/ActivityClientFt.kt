package com.rarible.dipdup.client

import com.rarible.dipdup.client.model.DipDupActivityType
import com.rarible.dipdup.client.model.DipDupContinuation
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
                            "__typename": "marketplace_activity",
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
                            "__typename": "marketplace_activity",
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

        val page = activityClient.getActivitiesAll(listOf(DipDupActivityType.LIST, DipDupActivityType.SELL), 2)
        assertThat(page.activities).hasSize(2)
        assertThat(DipDupContinuation.parse(page.continuation)).isNotNull
    }

    @Test
    fun `should return activities by item`() = runBlocking<Unit> {
        mock("""
            {
                "data": {
                    "marketplace_activity": [
                        {
                                "__typename": "marketplace_activity",
                                "id": "66818640-91e3-5ce2-8322-3ecdcc76fa93",
                                "type": "LIST",
                                "make_contract": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton",
                                "make_token_id": "67798",
                                "internal_order_id": "1135603",
                                "make_asset_class": "TEZOS_FT",
                                "make_value": 1,
                                "maker": "tz1QkhhWe6bwVy5bfYX1RdoTKcarvWoJuWM7",
                                "network": "mainnet",
                                "operation_counter": 13506274,
                                "operation_hash": "op3vnwnjkT31N3RsxgxiWzk9rbwQZUwbW1saZ2hLWEbP3a71hdH",
                                "operation_level": 1723983,
                                "operation_nonce": null,
                                "operation_timestamp": "2021-09-25T17:14:34+00:00",
                                "order_id": "72cc270d-13d8-5684-bf0c-f81bbb33129e",
                                "platform": "Hen",
                                "sell_price": 100,
                                "take_asset_class": "XTZ",
                                "take_contract": null,
                                "take_token_id": null,
                                "take_value": 100,
                                "taker": null
                              },
                              {
                                "__typename": "marketplace_activity",
                                "id": "b824efb6-b57c-5c73-99df-460bc0eaaea0",
                                "type": "CANCEL_LIST",
                                "make_contract": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton",
                                "make_token_id": "67798",
                                "internal_order_id": "0",
                                "make_asset_class": "TEZOS_FT",
                                "make_value": 1,
                                "maker": "tz1QGCWjNpYmcS6T9qFGYSam25e36WeFUCK4",
                                "network": "mainnet",
                                "operation_counter": 11549658,
                                "operation_hash": "oooByyA6uX3NKPw9oMRg9n64oAbMtAz3EJtKTXqKTNNEGtGVhdF",
                                "operation_level": 1632787,
                                "operation_nonce": null,
                                "operation_timestamp": "2021-08-23T19:48:36+00:00",
                                "order_id": "1f4ee13b-7251-5ccb-bfc4-fe356c4ae01a",
                                "platform": "Objkt",
                                "sell_price": 1000,
                                "take_asset_class": "XTZ",
                                "take_contract": null,
                                "take_token_id": null,
                                "take_value": 1000,
                                "taker": null
                              }
                    ]
                }
            }""")

        val page = activityClient.getActivitiesByItem(listOf(DipDupActivityType.LIST, DipDupActivityType.SELL), "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton", "67798", 2)
        assertThat(page.activities).hasSize(2)
        assertThat(DipDupContinuation.parse(page.continuation)).isNotNull
    }

    @Test
    fun `should return activities by item with continuation`() = runBlocking<Unit> {
        mock("""
            {
                "data": {
                    "marketplace_activity": [
                        {
                                "__typename": "marketplace_activity",
                                "id": "ff5d1878-a5bb-5552-83f5-0c8ee02cd5ce",
                                "type": "LIST",
                                "make_contract": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton",
                                "make_token_id": "67798",
                                "internal_order_id": "597665",
                                "make_asset_class": "TEZOS_FT",
                                "make_value": 1,
                                "maker": "tz1R1grtAMfEbgtGQnQBECSwiAp2exucmczS",
                                "network": "mainnet",
                                "operation_counter": 14666679,
                                "operation_hash": "op5mUWyrQvv3eC3CuNS54tAgxNwSpNqU7PLBLsTH9uhTmq5Jdf4",
                                "operation_level": 1568987,
                                "operation_nonce": null,
                                "operation_timestamp": "2021-07-23T01:01:14+00:00",
                                "order_id": "dae5dec7-9d37-5d86-877a-003b5c404283",
                                "platform": "Hen",
                                "sell_price": 10,
                                "take_asset_class": "XTZ",
                                "take_contract": null,
                                "take_token_id": null,
                                "take_value": 10,
                                "taker": null
                              }
                    ]
                }
            }""")

        val page = activityClient.getActivitiesByItem(listOf(DipDupActivityType.LIST, DipDupActivityType.SELL), "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton", "67798", 2, "1626568810_f9ca8eed-5a6b-563b-a01e-177544176c01")
        assertThat(page.activities).hasSize(1)
        assertThat(page.continuation).isNull()
    }

    @Test
    fun `should return activities after continuation`() = runBlocking<Unit> {
        mock("""
            {
                "data": {
                    "marketplace_activity": [
                        {
                            "__typename": "marketplace_activity",
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
                            "__typename": "marketplace_activity",
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

        val page = activityClient.getActivitiesAll(listOf(DipDupActivityType.LIST, DipDupActivityType.SELL), 2, "1652908780_f9de2ba4-38cb-5c42-b7c2-661fddc21693", true)
        assertThat(page.activities).hasSize(2)
    }

    @Test
    fun `should return activities by id`() = runBlocking<Unit> {
        mock("""
            {
                "data": {
                    "marketplace_activity": [
                        {
                            "__typename": "marketplace_activity",
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
        val activities = activityClient.getActivitiesByIds(listOf("895a5fb1-423f-584d-9812-0a332e21507e"))

        assertThat(activities).hasSize(1)
    }
}
