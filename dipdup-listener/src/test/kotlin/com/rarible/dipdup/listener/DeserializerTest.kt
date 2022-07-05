package com.rarible.dipdup.listener

import com.fasterxml.jackson.module.kotlin.readValue
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.client.core.model.DipDupTransferActivity
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

    @Test
    fun `should deserialize activity v1`() {
        val txt = """
            {
                "id": "bd38425f-493d-5644-8c5c-eca6731d84a2",
                "network": "ithacanet",
                "fill": "0",
                "platform": "RARIBLE_V1",
                "status": "ACTIVE",
                "startAt": "2022-06-30T14:51:31+00:00",
                "endAt": null,
                "cancelled": false,
                "createdAt": "2022-06-30T14:51:31+00:00",
                "endedAt": null,
                "lastUpdatedAt": "2022-06-30T14:51:31+00:00",
                "maker": "tz1NRh1vTn3b38m7Gg2qP81dqb5Kr2BAjwJV",
                "taker": null,
                "make": {
                    "assetType": {
                        "assetClass": "TEZOS_MT",
                        "contract": "KT19VgWR1FeQYQw3tR3Ch8bEynrjmzHPjGb2",
                        "tokenId": "2"
                    },
                    "assetValue": "2"
                },
                "take": {
                    "assetType": {
                        "assetClass": "XTZ"
                    },
                    "assetValue": "2"
                },
                "originFees": [
                    {
                        "part_account": "tz1gkQ4rNzPTgf1Yn3oBweWHAAvvF9VJv9hh",
                        "part_value": "250"
                    }
                ],
                "payouts": [
                    {
                        "part_account": "tz1NRh1vTn3b38m7Gg2qP81dqb5Kr2BAjwJV",
                        "part_value": "10000"
                    }
                ],
                "salt": "16624826187119183140186178151761430011714134697190895817714779241133841113657211102"
            }
        """.trimIndent()

        val order = DipDupDeserializer.OrderJsonSerializer().createMapper()
            .readValue<DipDupOrder>(txt)

        assertThat(order).isNotNull
    }


    @Test
    fun `should deserialize transfer`() {
        val txt = """
            {
                "id":"64ad4815-2de9-5bba-95b5-5bc77407990e",
                "date":"2022-04-26T07:36:45Z",
                "reverted":"false",
                "transferId":"27148400",
                "contract":"KT1MjD39KZWvX2QF3MJuhG3JGDkUq5LrMygw",
                "tokenId":"0",
                "value":"1",
                "transactionId":"27148396",
                "from":"tz1Ra5aPL6NVBbiN6DqwTHYuk6dHNPtFJiJU",
                "owner":"tz1fNCKWMNdznZv3Gk9MFR2wEQF3VejXZbpw",
                "type": "TRANSFER"
            }
        """.trimIndent()

        val order = DipDupDeserializer.OrderJsonSerializer().createMapper()
            .readValue<DipDupTransferActivity>(txt)

        assertThat(order).isNotNull
    }
}
