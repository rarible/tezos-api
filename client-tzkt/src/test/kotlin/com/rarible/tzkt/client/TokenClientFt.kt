package org.rarible.tezos.client.tzkt.client

import com.rarible.tzkt.client.TokenClient
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TokenClientFt : BaseClientFt() {

    val tokenClient = TokenClient(client)

    @Test
    fun `should return item by contract and token id`() = runBlocking<Unit> {
        mock("""
            [
                {
                    "id": 238284,
                    "contract": {
                        "alias": "hic et nunc NFTs",
                        "address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
                    },
                    "tokenId": "157993",
                    "standard": "fa2",
                    "firstLevel": 1540555,
                    "firstTime": "2021-07-02T21:16:34Z",
                    "lastLevel": 2269497,
                    "lastTime": "2022-04-10T16:41:44Z",
                    "transfersCount": 1484,
                    "balancesCount": 523,
                    "holdersCount": 213,
                    "totalMinted": "2500",
                    "totalBurned": "0",
                    "totalSupply": "2500",
                    "metadata": {
                        "name": "Monster Cake",
                        "tags": [
                            "Deslucréce",
                            "animation",
                            "illustration",
                            "2d",
                            "Lucrece",
                            "gif",
                            "celebration",
                            "specialevent",
                            "cake"
                        ],
                        "symbol": "OBJKT",
                        "formats": [
                            {
                                "uri": "ipfs://Qmb2tb63QwXHMQs1Zup6X6xRykACdNzE9HHwoz6b2E1GPA",
                                "mimeType": "image/gif"
                            }
                        ],
                        "creators": [
                            "tz1RNDoqLh5VCqBUXrgmzhwxqcR7ptAfvfNh"
                        ],
                        "decimals": "0",
                        "displayUri": "ipfs://Qmb2tb63QwXHMQs1Zup6X6xRykACdNzE9HHwoz6b2E1GPA",
                        "artifactUri": "ipfs://Qmb2tb63QwXHMQs1Zup6X6xRykACdNzE9HHwoz6b2E1GPA",
                        "description": "Hooray! Cake! Why does it smell like meat?... 2021-2022 birthday event.",
                        "thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                        "isBooleanAmount": false,
                        "shouldPreferSymbol": false
                    }
                }
            ]
        """.trimIndent())

        val token = tokenClient.token("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton", "157993")
        
        assertThat(token).isNotNull
        assertThat(token.standard).isEqualTo("fa2")
    }

}
