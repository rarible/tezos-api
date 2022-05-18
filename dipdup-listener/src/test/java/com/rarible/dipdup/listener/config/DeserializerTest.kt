package com.rarible.dipdup.listener.config

import com.fasterxml.jackson.module.kotlin.readValue
import com.rarible.dipdup.client.core.model.DipDupOrder
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class DeserializerTest {

    @Test
    fun `should deserialize order`() {
        val txt = """
            {
                "id": "a563f621-b872-58dd-bfbd-cfa3e68d81ef",
                "network": "private",
                "fill": "1",
                "platform": "Rarible",
                "status": "FILLED",
                "startedAt": "2022-05-18T13:41:29+00:00",
                "endedAt": "2022-05-18T13:41:39+00:00",
                "makeStock": "0",
                "cancelled": false,
                "createdAt": "2022-05-18T13:41:29+00:00",
                "lastUpdatedAt": "2022-05-18T13:41:39+00:00",
                "makePrice": "2",
                "maker": "tz1Mxsc66En4HsVHr6rppYZW82ZpLhpupToC",
                "taker": null,
                "make": {
                    "assetType": {
                        "assetClass": "TEZOS_FT",
                        "contract": "KT1NWdwVA8zq5DDJTKcMkRqWYJcEcyTTm5WK",
                        "tokenId": "1132"
                    },
                    "assetValue": "1"
                },
                "take": {
                    "assetType": {
                        "assetClass": "TEZOS_FT",
                        "contract": "KT1HvTfYG7DgeujAQ1LDvCHiQc29VMycoJh5",
                        "tokenId": "0"
                    },
                    "assetValue": "2"
                },
                "salt": "3218"
            }
        """.trimIndent()

        val order = DipDupDeserializer.OrderJsonSerializer().createMapper()
            .readValue<DipDupOrder>(txt)

        assertThat(order).isNotNull
    }

}
