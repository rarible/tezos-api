package com.rarible.dipdup.listener

import com.fasterxml.jackson.module.kotlin.readValue
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.listener.config.DipDupDeserializer
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class DeserializerTest {

    @Test
    fun `should deserialize order`() {
        val txt = """
            {
                "id": "386ee88f-62e3-50db-9ad2-ef5846583715",
                "network": "ithacanet",
                "fill": "0",
                "platform": "RARIBLE_V2",
                "status": "ACTIVE",
                "startAt": "2022-06-23T10:51:20+00:00",
                "endAt": null,
                "cancelled": false,
                "createdAt": "2022-06-23T10:51:20+00:00",
                "endedAt": null,
                "lastUpdatedAt": "2022-07-01T08:29:30+00:00",
                "maker": "tz1NRh1vTn3b38m7Gg2qP81dqb5Kr2BAjwJV",
                "taker": null,
                "make": {
                    "assetType": {
                        "assetClass": "TEZOS_MT",
                        "contract": "KT1RuoaCbnZpMgdRpSoLfJUzSkGz1ZSiaYwj",
                        "tokenId": "213"
                    },
                    "assetValue": "48"
                },
                "take": {
                    "assetType": {
                        "assetClass": "XTZ"
                    },
                    "assetValue": "1"
                },
                "originFees": [
                    {
                        "part_account": "tz1gkQ4rNzPTgf1Yn3oBweWHAAvvF9VJv9hh",
                        "part_value": "250"
                    }
                ],
                "payouts": [],
                "salt": "6990552"
            }
        """.trimIndent()

        val order = DipDupDeserializer.OrderJsonSerializer().createMapper()
            .readValue<DipDupOrder>(txt)

        assertThat(order).isNotNull
    }

}
