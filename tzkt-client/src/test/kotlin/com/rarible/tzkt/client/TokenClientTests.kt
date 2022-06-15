package com.rarible.tzkt.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.rarible.tzkt.config.KnownAddresses
import com.rarible.tzkt.meta.MetaService
import com.rarible.tzkt.model.TzktNotFound
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger

class TokenClientTests : BaseClientTests() {


    val HEN = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
    val HEN_ROYALTIES = "KT1Hkg5qeNhfwpKW4fXvq7HGZB9z2EnmCCA9"
    val KALAMINT = "KT1EpGgjQs73QfFJs9z7m1Mxm5MTnpC2tqse"
    val FXHASH_V1 = "KT1KEa8z6vWXDJrVqtMrAeDVzsvxat3kHaCE"
    val FXHASH_MANAGER_LEGACY_V1 = "KT1XCoGnfupWk7Sp8536EfrxcP73LmT68Nyr"
    val FXHASH_V2 = "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
    val VERSUM = "KT1LjmAdYQCLBjwv4S2oFkEzyHVkomAf5MrW"
    val ROYALTIES_MANAGER = "KT1HNNrmCk1fpqveRDz8Fvww2GM4gPzmA7fo"
    val BIDOU_8x8 = "KT1MxDwChiDwd6WBVs24g1NjERUoK622ZEFp"
    val BIDOU_24x24 = "KT1TR1ErEQPTdtaJ7hbvKTJSa1tsGnHGZTpf"

    val config = KnownAddresses(
        hen = HEN,
        henRoyalties = HEN_ROYALTIES,
        kalamint = KALAMINT,
        fxhashV1 = FXHASH_V1,
        fxhashV1Manager = FXHASH_MANAGER_LEGACY_V1,
        fxhashV2 = FXHASH_V2,
        versum = VERSUM,
        royaltiesManager = ROYALTIES_MANAGER,
        bidou8x8 = BIDOU_8x8,
        bidou24x24 = BIDOU_24x24
    )

    val bigMapKeyClient = BigMapKeyClient(client)
    val metaService = MetaService(ObjectMapper().registerKotlinModule(), bigMapKeyClient, config)
    val tokenClient = TokenClient(client, metaService, mockk())

    @Test
    fun `should return token by contract and token id`() = runBlocking<Unit> {
        mock(
            """
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
        """.trimIndent()
        )

        val token = tokenClient.token("KT1NWdwVA8zq5DDJTKcMkRqWYJcEcyTTm5WK:1156")
        assertThat(request().path).isEqualTo("/v1/tokens?contract=KT1NWdwVA8zq5DDJTKcMkRqWYJcEcyTTm5WK&tokenId=1156&token.standard=fa2")

        assertThat(token).isNotNull
        assertThat(token.meta).isNotNull
        assertThat(token.standard).isEqualTo("fa2")
    }

    @Test
    fun `should return tokens with size, continuation and sorted by ASC`() = runBlocking<Unit> {
        mock(
            """
            [{
            	"id": 60,
            	"contract": {
            		"address": "KT1S95Dyj2QrJpSnAbHRUSUZr7DhuFqssrog"
            	},
            	"tokenId": "0",
            	"standard": "fa2",
            	"firstLevel": 1328122,
            	"firstTime": "2021-02-02T19:17:52Z",
            	"lastLevel": 1390772,
            	"lastTime": "2021-03-19T00:29:18Z",
            	"transfersCount": 2,
            	"balancesCount": 2,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1",
            	"metadata": {
            		"Date": "02-02-2021",
            		"name": "Alone Together",
            		"genres": "art",
            		"Creator": "Chris Mischief",
            		"decimals": "0",
            		"displayUri": "https://cloudflare-ipfs.com/ipfs/QmUnPB2pBFHv3MyRDQcmMGkpDNKNqeX57xASa9cx4Xomn1",
            		"artifactUri": "https://cloudflare-ipfs.com/ipfs/QmUnPB2pBFHv3MyRDQcmMGkpDNKNqeX57xASa9cx4Xomn1",
            		"description": "I painted this over the course of the entire first 2020 NYC lockdown. I would leave the canvas only to return to scribble down new observations on the canvas. Days turned into night which turned into weeks which turned into months. We were all alone together.  ",
            		"booleanAmount": "true"
            	}
            }, {
            	"id": 61,
            	"contract": {
            		"address": "KT1S95Dyj2QrJpSnAbHRUSUZr7DhuFqssrog"
            	},
            	"tokenId": "1",
            	"standard": "fa2",
            	"firstLevel": 1330915,
            	"firstTime": "2021-02-04T19:46:23Z",
            	"lastLevel": 1633245,
            	"lastTime": "2021-08-24T00:03:56Z",
            	"transfersCount": 2,
            	"balancesCount": 2,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1",
            	"metadata": {
            		"name": "Mischief Genesis ",
            		"creator": "Chris Mischief",
            		"decimals": "0",
            		"displayUri": "https://cloudflare-ipfs.com/ipfs/QmaTLw6SRSZBgbsfjNhkhkTQmw9XMvt6YiPRHwZ3YN9X4a",
            		"artifactUri": "https://cloudflare-ipfs.com/ipfs/QmaTLw6SRSZBgbsfjNhkhkTQmw9XMvt6YiPRHwZ3YN9X4a",
            		"description": "After many years of hanging out with graffiti artists, assisting in art studios, painting, showing and selling my own works, I picked up an iPad Pro and wondered if I could make art on it. This was my very first attempt and sealed my fate as a digital artist. ",
            		"booleanAmount": "true",
            		"serial number": "AP"
            	}
            }, {
            	"id": 70,
            	"contract": {
            		"address": "KT1UNMbjyPSY8hbHYCxyM1LMbHMzv5EbFFN3"
            	},
            	"tokenId": "0",
            	"standard": "fa2",
            	"firstLevel": 1332416,
            	"firstTime": "2021-02-05T21:55:47Z",
            	"lastLevel": 1332416,
            	"lastTime": "2021-02-05T21:55:47Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1",
            	"metadata": {
            		"name": "Mischief Genesis ",
            		"creator": "Chris Mischief",
            		"decimals": "0",
            		"displayUri": "ipfs://QmaTLw6SRSZBgbsfjNhkhkTQmw9XMvt6YiPRHwZ3YN9X4a",
            		"artifactUri": "ipfs://QmaTLw6SRSZBgbsfjNhkhkTQmw9XMvt6YiPRHwZ3YN9X4a",
            		"description": "After many years of hanging out with graffiti artists, assisting in art studios, painting, showing and selling my own works, I picked up an iPad Pro and wondered if I could make art on it. This was my very first attempt and sealed my fate as a digital artist. ",
            		"thumbnailUri": "ipfs://QmTBAYu12gkhTzZCL7vqN1mr4SjA95UJqDgqxpMCrfjsWP",
            		"booleanAmount": true,
            		"serial number": "AP2"
            	}
            }, {
            	"id": 71,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "7",
            	"standard": "fa2",
            	"firstLevel": 1332523,
            	"firstTime": "2021-02-05T23:48:07Z",
            	"lastLevel": 1332523,
            	"lastTime": "2021-02-05T23:48:07Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "2",
            	"totalBurned": "0",
            	"totalSupply": "2",
            	"metadata": {
            		"name": "ung",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmevGKa3g6hFWNV9wviNb1qWUKFE8mygcrU7hEuDxfP33r",
            			"mimeType": "image/png"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmevGKa3g6hFWNV9wviNb1qWUKFE8mygcrU7hEuDxfP33r",
            		"description": "",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": true
            	}
            }, {
            	"id": 72,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "8",
            	"standard": "fa2",
            	"firstLevel": 1332773,
            	"firstTime": "2021-02-06T04:08:47Z",
            	"lastLevel": 1332773,
            	"lastTime": "2021-02-06T04:08:47Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1000",
            	"totalBurned": "0",
            	"totalSupply": "1000",
            	"metadata": {
            		"name": "la jetee",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmURPaTHnDnA36jeuAVyFYMP8utCciCJY2cB42du1hs9hC",
            			"mimeType": "video/mp4"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmURPaTHnDnA36jeuAVyFYMP8utCciCJY2cB42du1hs9hC",
            		"description": "",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": true
            	}
            }, {
            	"id": 73,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "9",
            	"standard": "fa2",
            	"firstLevel": 1332897,
            	"firstTime": "2021-02-06T06:19:27Z",
            	"lastLevel": 1332897,
            	"lastTime": "2021-02-06T06:19:27Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "10",
            	"totalBurned": "0",
            	"totalSupply": "10",
            	"metadata": {
            		"name": "UNG",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/Qmf6KqanvCYkyJkCLJ3UAei8oavZytWBL9SNEnNTa58M1X",
            			"mimeType": "image/png"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/Qmf6KqanvCYkyJkCLJ3UAei8oavZytWBL9SNEnNTa58M1X",
            		"description": "",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": true
            	}
            }, {
            	"id": 74,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "10",
            	"standard": "fa2",
            	"firstLevel": 1333040,
            	"firstTime": "2021-02-06T08:48:35Z",
            	"lastLevel": 1333040,
            	"lastTime": "2021-02-06T08:48:35Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "10",
            	"totalBurned": "0",
            	"totalSupply": "10",
            	"metadata": {
            		"name": "UNG",
            		"tags": [],
            		"media": [{
            			"hash": "QmZYBf2MbVGzusnq311Jn7WQ6rQqoaxqu6hq6CpDgWGN5A",
            			"path": "QmZYBf2MbVGzusnq311Jn7WQ6rQqoaxqu6hq6CpDgWGN5A",
            			"size": "29368"
            		}],
            		"symbol": "OBJKT",
            		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
            		"formats": [{
            			"uri": "",
            			"mimeType": "image/png"
            		}],
            		"decimals": "0",
            		"artifactUri": "",
            		"description": "",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": true
            	}
            }, {
            	"id": 75,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "11",
            	"standard": "fa2",
            	"firstLevel": 1333049,
            	"firstTime": "2021-02-06T08:58:15Z",
            	"lastLevel": 1333049,
            	"lastTime": "2021-02-06T08:58:15Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "22",
            	"totalBurned": "0",
            	"totalSupply": "22",
            	"metadata": {
            		"name": "fadfa",
            		"tags": [],
            		"media": "https://ipfs.io/ipfs/QmZYBf2MbVGzusnq311Jn7WQ6rQqoaxqu6hq6CpDgWGN5A",
            		"symbol": "OBJKT",
            		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
            		"formats": [{
            			"uri": "",
            			"mimeType": "image/png"
            		}],
            		"decimals": "0",
            		"artifactUri": "",
            		"description": "",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": true
            	}
            }, {
            	"id": 76,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "12",
            	"standard": "fa2",
            	"firstLevel": 1333069,
            	"firstTime": "2021-02-06T09:19:35Z",
            	"lastLevel": 1333761,
            	"lastTime": "2021-02-06T21:14:15Z",
            	"transfersCount": 2,
            	"balancesCount": 2,
            	"holdersCount": 2,
            	"totalMinted": "20",
            	"totalBurned": "0",
            	"totalSupply": "20",
            	"metadata": {
            		"name": "ung",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmZYBf2MbVGzusnq311Jn7WQ6rQqoaxqu6hq6CpDgWGN5A",
            			"mimeType": "image/png"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmZYBf2MbVGzusnq311Jn7WQ6rQqoaxqu6hq6CpDgWGN5A",
            		"description": "",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": true
            	}
            }, {
            	"id": 77,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "13",
            	"standard": "fa2",
            	"firstLevel": 1335564,
            	"firstTime": "2021-02-08T04:51:59Z",
            	"lastLevel": 1335564,
            	"lastTime": "2021-02-08T04:51:59Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "100",
            	"totalBurned": "0",
            	"totalSupply": "100",
            	"metadata": {
            		"name": "hDAO",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmULY1poQwAzgcwoHGBugKdKv1742x6UYDsQ3vBSQkm8LP",
            			"mimeType": "image/png"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmULY1poQwAzgcwoHGBugKdKv1742x6UYDsQ3vBSQkm8LP",
            		"description": "",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": true
            	}
            }]
        """.trimIndent()
        )

        mock(
            """
            [{
            	"id": 78,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "14",
            	"standard": "fa2",
            	"firstLevel": 1335891,
            	"firstTime": "2021-02-08T10:32:19Z",
            	"lastLevel": 1412149,
            	"lastTime": "2021-04-02T23:34:43Z",
            	"transfersCount": 3,
            	"balancesCount": 2,
            	"holdersCount": 1,
            	"totalMinted": "2",
            	"totalBurned": "0",
            	"totalSupply": "2",
            	"metadata": {
            		"name": "Farm house by Qartsi",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1PYqfNnkpZydtSY2Tn3Rv8mjkgUpHJpFXC",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmejBuFzM2YKQnQ2kdfiCwYaHBidMiECZYdzh1hAbqZqWc",
            			"mimeType": "image/jpeg"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmejBuFzM2YKQnQ2kdfiCwYaHBidMiECZYdzh1hAbqZqWc",
            		"description": "Farm house - watercolor with digital enhancement by Qartsi",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": true
            	}
            }, {
            	"id": 79,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "15",
            	"standard": "fa2",
            	"firstLevel": 1336229,
            	"firstTime": "2021-02-08T16:24:35Z",
            	"lastLevel": 1336229,
            	"lastTime": "2021-02-08T16:24:35Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1",
            	"metadata": {
            		"name": "XTZ News",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1Rux3pGrp2KpZUBKvg3VQ4edRYVJsapr4z",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmSrifEeCTpZ5RcSLRdX5wWRbmxjD4LeSpx3ZQi25cXSd4",
            			"mimeType": "image/jpeg"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmSrifEeCTpZ5RcSLRdX5wWRbmxjD4LeSpx3ZQi25cXSd4",
            		"description": "XTZ News Logo",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": false
            	}
            }, {
            	"id": 80,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "16",
            	"standard": "fa2",
            	"firstLevel": 1336349,
            	"firstTime": "2021-02-08T18:30:35Z",
            	"lastLevel": 1336349,
            	"lastTime": "2021-02-08T18:30:35Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "10",
            	"totalBurned": "0",
            	"totalSupply": "10",
            	"metadata": {
            		"name": "Enigma",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1SBxNvpDFC4bjW23CtQfVLqN5cBATVf68B",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmZwx9rB1myiPsonKmhyprZuCLnQmemxgztjGXN5GpMPPg",
            			"mimeType": "image/jpeg"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmZwx9rB1myiPsonKmhyprZuCLnQmemxgztjGXN5GpMPPg",
            		"description": "",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": true
            	}
            }, {
            	"id": 81,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "17",
            	"standard": "fa2",
            	"firstLevel": 1336464,
            	"firstTime": "2021-02-08T20:29:03Z",
            	"lastLevel": 1367712,
            	"lastTime": "2021-03-02T21:07:23Z",
            	"transfersCount": 2,
            	"balancesCount": 2,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1",
            	"metadata": {
            		"name": "Donut",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1PeC7WEA3Mas8UA3sARTW5xkh3K52azqnF",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmdVw9FCBMi2uKxBSLmTkhSWfzojRjw9V2SLcKrCv5PYu3",
            			"mimeType": "image/png"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmdVw9FCBMi2uKxBSLmTkhSWfzojRjw9V2SLcKrCv5PYu3",
            		"description": "Donut",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": false
            	}
            }, {
            	"id": 82,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "18",
            	"standard": "fa2",
            	"firstLevel": 1336550,
            	"firstTime": "2021-02-08T21:57:03Z",
            	"lastLevel": 1336550,
            	"lastTime": "2021-02-08T21:57:03Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1",
            	"metadata": {
            		"name": "TEZCOINART",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1ae2d1BJt7YUqaaec6Xenh3mBqS7VjSZtK",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmZyaqACDN5UpzA835BRXi46VGBnsHS6vWqjNvLsYgmEQL",
            			"mimeType": "image/jpeg"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmZyaqACDN5UpzA835BRXi46VGBnsHS6vWqjNvLsYgmEQL",
            		"description": "TezChainStronk",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": false
            	}
            }, {
            	"id": 83,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "19",
            	"standard": "fa2",
            	"firstLevel": 1336564,
            	"firstTime": "2021-02-08T22:11:03Z",
            	"lastLevel": 1336564,
            	"lastTime": "2021-02-08T22:11:03Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1",
            	"metadata": {
            		"name": "Fluids swimming",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1LMNQj22tZH9q1gZkpJwENi73Np5tns8fn",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmcQymWhiSKuGw9SyELUbFwbxKvQJy7tVcvQkMw2ZNE8ky",
            			"mimeType": "image/jpeg"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmcQymWhiSKuGw9SyELUbFwbxKvQJy7tVcvQkMw2ZNE8ky",
            		"description": "I made this one night hacking around with fluid dynamics simulations. ",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": false
            	}
            }, {
            	"id": 84,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "20",
            	"standard": "fa2",
            	"firstLevel": 1336565,
            	"firstTime": "2021-02-08T22:12:03Z",
            	"lastLevel": 1336565,
            	"lastTime": "2021-02-08T22:12:03Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1",
            	"metadata": {
            		"name": "Fluids swimming",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1LMNQj22tZH9q1gZkpJwENi73Np5tns8fn",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmcQymWhiSKuGw9SyELUbFwbxKvQJy7tVcvQkMw2ZNE8ky",
            			"mimeType": "image/jpeg"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmcQymWhiSKuGw9SyELUbFwbxKvQJy7tVcvQkMw2ZNE8ky",
            		"description": "I made this one night hacking around with fluid dynamics simulations. ",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": false
            	}
            }, {
            	"id": 86,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "21",
            	"standard": "fa2",
            	"firstLevel": 1337626,
            	"firstTime": "2021-02-09T16:48:39Z",
            	"lastLevel": 1337626,
            	"lastTime": "2021-02-09T16:48:39Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1",
            	"metadata": {
            		"name": "TzMoonMan",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1ae2d1BJt7YUqaaec6Xenh3mBqS7VjSZtK",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmT1fKn8Wu9shqWBwAYHK3qQxkspCaUcskjoeNJh8kjpF5",
            			"mimeType": "image/jpeg"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmT1fKn8Wu9shqWBwAYHK3qQxkspCaUcskjoeNJh8kjpF5",
            		"description": "A Tezos Astronaut Riding A Unicycle On The Moon",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": false
            	}
            }, {
            	"id": 87,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "22",
            	"standard": "fa2",
            	"firstLevel": 1337694,
            	"firstTime": "2021-02-09T17:59:59Z",
            	"lastLevel": 1407650,
            	"lastTime": "2021-03-30T19:56:52Z",
            	"transfersCount": 3,
            	"balancesCount": 2,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1",
            	"metadata": {
            		"name": "HalluTzinate",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1ae2d1BJt7YUqaaec6Xenh3mBqS7VjSZtK",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/QmQapWLELmzn3HZupi2NaHBdncb2MJruN8bCuz52GExxAC",
            			"mimeType": "image/jpeg"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/QmQapWLELmzn3HZupi2NaHBdncb2MJruN8bCuz52GExxAC",
            		"description": "Trippy Tezos Design",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": false
            	}
            }, {
            	"id": 88,
            	"contract": {
            		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            	},
            	"tokenId": "23",
            	"standard": "fa2",
            	"firstLevel": 1337802,
            	"firstTime": "2021-02-09T19:50:39Z",
            	"lastLevel": 1337802,
            	"lastTime": "2021-02-09T19:50:39Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1",
            	"metadata": {
            		"name": "RADION FM",
            		"tags": [],
            		"symbol": "OBJKT",
            		"creator": "tz1TAcRvXuUxX9vDvvvkDeoiHxeFjCnFu3Yn",
            		"formats": [{
            			"uri": "https://ipfs.io/ipfs/Qme3gkZSV2rsBWB2eMpVXAQ2AFfxUe8HFvZxw8mMHFobae",
            			"mimeType": "audio/mpeg"
            		}],
            		"decimals": "0",
            		"artifactUri": "https://ipfs.io/ipfs/Qme3gkZSV2rsBWB2eMpVXAQ2AFfxUe8HFvZxw8mMHFobae",
            		"description": "MP3 COMMERCIAL",
            		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            		"booleanAmount": false
            	}
            }]
        """.trimIndent()
        )

        val size = 10
        var tokens = tokenClient.allTokensByLastUpdate(size, null)
        assertThat(request().path).isEqualTo("/v1/tokens?token.standard=fa2&limit=10&sort.asc=lastLevel&metadata.artifactUri.null=false")
        var prevId = 0
        tokens.items.forEach {
            assertThat(it.id).isGreaterThan(prevId)
            assertThat(it.standard).isEqualTo("fa2")
            prevId = it.id!!
        }
        prevId = 0
        val lastId = tokens.items.last().id!!.toLong()
        tokens = tokenClient.allTokensByLastUpdate(size, "123456_123")
        assertThat(request().path).isEqualTo("/v1/tokens?token.standard=fa2&limit=10&lastLevel=123456&id.gt=123&sort.asc=lastLevel&metadata.artifactUri.null=false")
        tokens.items.forEach {
            assertThat(it.id).isGreaterThan(prevId)
            assertThat(it.standard).isEqualTo("fa2")
            prevId = it.id!!
        }
        assertThat(tokens.items.first().id?.toLong()).isGreaterThan(lastId)
    }

    @Test
    fun `should return tokens sorted by lastLevel with size, continuation and sorted by ASC`() = runBlocking<Unit> {
        mock(
            """
                [{
                	"id": 70,
                	"contract": {
                		"address": "KT1UNMbjyPSY8hbHYCxyM1LMbHMzv5EbFFN3"
                	},
                	"tokenId": "0",
                	"standard": "fa2",
                	"firstLevel": 1332416,
                	"firstTime": "2021-02-05T21:55:47Z",
                	"lastLevel": 1332416,
                	"lastTime": "2021-02-05T21:55:47Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "Mischief Genesis ",
                		"creator": "Chris Mischief",
                		"decimals": "0",
                		"displayUri": "ipfs://QmaTLw6SRSZBgbsfjNhkhkTQmw9XMvt6YiPRHwZ3YN9X4a",
                		"artifactUri": "ipfs://QmaTLw6SRSZBgbsfjNhkhkTQmw9XMvt6YiPRHwZ3YN9X4a",
                		"description": "After many years of hanging out with graffiti artists, assisting in art studios, painting, showing and selling my own works, I picked up an iPad Pro and wondered if I could make art on it. This was my very first attempt and sealed my fate as a digital artist. ",
                		"thumbnailUri": "ipfs://QmTBAYu12gkhTzZCL7vqN1mr4SjA95UJqDgqxpMCrfjsWP",
                		"booleanAmount": true,
                		"serial number": "AP2"
                	}
                }, {
                	"id": 71,
                	"contract": {
                		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
                	},
                	"tokenId": "7",
                	"standard": "fa2",
                	"firstLevel": 1332523,
                	"firstTime": "2021-02-05T23:48:07Z",
                	"lastLevel": 1332523,
                	"lastTime": "2021-02-05T23:48:07Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "2",
                	"totalBurned": "0",
                	"totalSupply": "2",
                	"metadata": {
                		"name": "ung",
                		"tags": [],
                		"symbol": "OBJKT",
                		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
                		"formats": [{
                			"uri": "https://ipfs.io/ipfs/QmevGKa3g6hFWNV9wviNb1qWUKFE8mygcrU7hEuDxfP33r",
                			"mimeType": "image/png"
                		}],
                		"decimals": "0",
                		"artifactUri": "https://ipfs.io/ipfs/QmevGKa3g6hFWNV9wviNb1qWUKFE8mygcrU7hEuDxfP33r",
                		"description": "",
                		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"booleanAmount": true
                	}
                }, {
                	"id": 72,
                	"contract": {
                		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
                	},
                	"tokenId": "8",
                	"standard": "fa2",
                	"firstLevel": 1332773,
                	"firstTime": "2021-02-06T04:08:47Z",
                	"lastLevel": 1332773,
                	"lastTime": "2021-02-06T04:08:47Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "1000",
                	"totalBurned": "0",
                	"totalSupply": "1000",
                	"metadata": {
                		"name": "la jetee",
                		"tags": [],
                		"symbol": "OBJKT",
                		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
                		"formats": [{
                			"uri": "https://ipfs.io/ipfs/QmURPaTHnDnA36jeuAVyFYMP8utCciCJY2cB42du1hs9hC",
                			"mimeType": "video/mp4"
                		}],
                		"decimals": "0",
                		"artifactUri": "https://ipfs.io/ipfs/QmURPaTHnDnA36jeuAVyFYMP8utCciCJY2cB42du1hs9hC",
                		"description": "",
                		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"booleanAmount": true
                	}
                }, {
                	"id": 73,
                	"contract": {
                		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
                	},
                	"tokenId": "9",
                	"standard": "fa2",
                	"firstLevel": 1332897,
                	"firstTime": "2021-02-06T06:19:27Z",
                	"lastLevel": 1332897,
                	"lastTime": "2021-02-06T06:19:27Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "10",
                	"totalBurned": "0",
                	"totalSupply": "10",
                	"metadata": {
                		"name": "UNG",
                		"tags": [],
                		"symbol": "OBJKT",
                		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
                		"formats": [{
                			"uri": "https://ipfs.io/ipfs/Qmf6KqanvCYkyJkCLJ3UAei8oavZytWBL9SNEnNTa58M1X",
                			"mimeType": "image/png"
                		}],
                		"decimals": "0",
                		"artifactUri": "https://ipfs.io/ipfs/Qmf6KqanvCYkyJkCLJ3UAei8oavZytWBL9SNEnNTa58M1X",
                		"description": "",
                		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"booleanAmount": true
                	}
                }, {
                	"id": 74,
                	"contract": {
                		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
                	},
                	"tokenId": "10",
                	"standard": "fa2",
                	"firstLevel": 1333040,
                	"firstTime": "2021-02-06T08:48:35Z",
                	"lastLevel": 1333040,
                	"lastTime": "2021-02-06T08:48:35Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "10",
                	"totalBurned": "0",
                	"totalSupply": "10",
                	"metadata": {
                		"name": "UNG",
                		"tags": [],
                		"media": [{
                			"hash": "QmZYBf2MbVGzusnq311Jn7WQ6rQqoaxqu6hq6CpDgWGN5A",
                			"path": "QmZYBf2MbVGzusnq311Jn7WQ6rQqoaxqu6hq6CpDgWGN5A",
                			"size": "29368"
                		}],
                		"symbol": "OBJKT",
                		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
                		"formats": [{
                			"uri": "",
                			"mimeType": "image/png"
                		}],
                		"decimals": "0",
                		"artifactUri": "",
                		"description": "",
                		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"booleanAmount": true
                	}
                }]
            """.trimIndent()
        )
        mock(
            """
                []
            """.trimIndent()
        )
        mock(
            """
                [{
                	"id": 75,
                	"contract": {
                		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
                	},
                	"tokenId": "11",
                	"standard": "fa2",
                	"firstLevel": 1333049,
                	"firstTime": "2021-02-06T08:58:15Z",
                	"lastLevel": 1333049,
                	"lastTime": "2021-02-06T08:58:15Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "22",
                	"totalBurned": "0",
                	"totalSupply": "22",
                	"metadata": {
                		"name": "fadfa",
                		"tags": [],
                		"media": "https://ipfs.io/ipfs/QmZYBf2MbVGzusnq311Jn7WQ6rQqoaxqu6hq6CpDgWGN5A",
                		"symbol": "OBJKT",
                		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
                		"formats": [{
                			"uri": "",
                			"mimeType": "image/png"
                		}],
                		"decimals": "0",
                		"artifactUri": "",
                		"description": "",
                		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"booleanAmount": true
                	}
                }, {
                	"id": 76,
                	"contract": {
                		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
                	},
                	"tokenId": "12",
                	"standard": "fa2",
                	"firstLevel": 1333069,
                	"firstTime": "2021-02-06T09:19:35Z",
                	"lastLevel": 1333761,
                	"lastTime": "2021-02-06T21:14:15Z",
                	"transfersCount": 2,
                	"balancesCount": 2,
                	"holdersCount": 2,
                	"totalMinted": "20",
                	"totalBurned": "0",
                	"totalSupply": "20",
                	"metadata": {
                		"name": "ung",
                		"tags": [],
                		"symbol": "OBJKT",
                		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
                		"formats": [{
                			"uri": "https://ipfs.io/ipfs/QmZYBf2MbVGzusnq311Jn7WQ6rQqoaxqu6hq6CpDgWGN5A",
                			"mimeType": "image/png"
                		}],
                		"decimals": "0",
                		"artifactUri": "https://ipfs.io/ipfs/QmZYBf2MbVGzusnq311Jn7WQ6rQqoaxqu6hq6CpDgWGN5A",
                		"description": "",
                		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"booleanAmount": true
                	}
                }, {
                	"id": 77,
                	"contract": {
                		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
                	},
                	"tokenId": "13",
                	"standard": "fa2",
                	"firstLevel": 1335564,
                	"firstTime": "2021-02-08T04:51:59Z",
                	"lastLevel": 1335564,
                	"lastTime": "2021-02-08T04:51:59Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "100",
                	"totalBurned": "0",
                	"totalSupply": "100",
                	"metadata": {
                		"name": "hDAO",
                		"tags": [],
                		"symbol": "OBJKT",
                		"creator": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC",
                		"formats": [{
                			"uri": "https://ipfs.io/ipfs/QmULY1poQwAzgcwoHGBugKdKv1742x6UYDsQ3vBSQkm8LP",
                			"mimeType": "image/png"
                		}],
                		"decimals": "0",
                		"artifactUri": "https://ipfs.io/ipfs/QmULY1poQwAzgcwoHGBugKdKv1742x6UYDsQ3vBSQkm8LP",
                		"description": "",
                		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"booleanAmount": true
                	}
                }, {
                	"id": 79,
                	"contract": {
                		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
                	},
                	"tokenId": "15",
                	"standard": "fa2",
                	"firstLevel": 1336229,
                	"firstTime": "2021-02-08T16:24:35Z",
                	"lastLevel": 1336229,
                	"lastTime": "2021-02-08T16:24:35Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "XTZ News",
                		"tags": [],
                		"symbol": "OBJKT",
                		"creator": "tz1Rux3pGrp2KpZUBKvg3VQ4edRYVJsapr4z",
                		"formats": [{
                			"uri": "https://ipfs.io/ipfs/QmSrifEeCTpZ5RcSLRdX5wWRbmxjD4LeSpx3ZQi25cXSd4",
                			"mimeType": "image/jpeg"
                		}],
                		"decimals": "0",
                		"artifactUri": "https://ipfs.io/ipfs/QmSrifEeCTpZ5RcSLRdX5wWRbmxjD4LeSpx3ZQi25cXSd4",
                		"description": "XTZ News Logo",
                		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"booleanAmount": false
                	}
                }, {
                	"id": 80,
                	"contract": {
                		"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
                	},
                	"tokenId": "16",
                	"standard": "fa2",
                	"firstLevel": 1336349,
                	"firstTime": "2021-02-08T18:30:35Z",
                	"lastLevel": 1336349,
                	"lastTime": "2021-02-08T18:30:35Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "10",
                	"totalBurned": "0",
                	"totalSupply": "10",
                	"metadata": {
                		"name": "Enigma",
                		"tags": [],
                		"symbol": "OBJKT",
                		"creator": "tz1SBxNvpDFC4bjW23CtQfVLqN5cBATVf68B",
                		"formats": [{
                			"uri": "https://ipfs.io/ipfs/QmZwx9rB1myiPsonKmhyprZuCLnQmemxgztjGXN5GpMPPg",
                			"mimeType": "image/jpeg"
                		}],
                		"decimals": "0",
                		"artifactUri": "https://ipfs.io/ipfs/QmZwx9rB1myiPsonKmhyprZuCLnQmemxgztjGXN5GpMPPg",
                		"description": "",
                		"thumbnailUri": "https://ipfs.io/ipfs/QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"booleanAmount": true
                	}
                }]
            """.trimIndent()
        )
        mock("[]")
        mock("[]")

        val firstTokenRequest = tokenClient.allTokensByLastUpdate(5, null)
        assertThat(firstTokenRequest.items.size).isEqualTo(5)
        assertThat(firstTokenRequest.continuation).isNotNull
        assertThat(firstTokenRequest.continuation).isEqualTo("1333040_74")

        val secondTokenRequest = tokenClient.allTokensByLastUpdate(5, firstTokenRequest.continuation)
        assertThat(secondTokenRequest.items.size).isEqualTo(5)
        assertThat(secondTokenRequest.continuation).isNotNull
        assertThat(secondTokenRequest.continuation).isEqualTo("1336349_80")

        val thirdTokenRequest = tokenClient.allTokensByLastUpdate(6, secondTokenRequest.continuation)
        assertThat(thirdTokenRequest.items.size).isEqualTo(0)
        assertThat(thirdTokenRequest.continuation).isNull()
    }

    @Test
    fun `should return tokens sorted by lastLevel with size, continuation and sorted by DESC`() = runBlocking<Unit> {
        mock(
            """
                [{
                	"id": 2860850,
                	"contract": {
                		"alias": "FXHASH GENTK v2",
                		"address": "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
                	},
                	"tokenId": "686247",
                	"standard": "fa2",
                	"firstLevel": 2352896,
                	"firstTime": "2022-05-10T11:52:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "[WAITING TO BE SIGNED]",
                		"symbol": "GENTK",
                		"decimals": "0",
                		"displayUri": "ipfs://QmYwSwa5hP4346GqD7hAjutwJSmeYTdiLQ7Wec2C7Cez1D",
                		"artifactUri": "ipfs://QmdGV3UqJqX4v5x9nFcDYeekCEAm3SDXUG5SHdjKQKn4Pe",
                		"description": "This Gentk is waiting to be signed by Fxhash Signer module",
                		"thumbnailUri": "ipfs://QmbvEAn7FLMeYBDroYwBP8qWc3d3VVWbk19tTB83LCMB5S"
                	}
                }, {
                	"id": 2860849,
                	"contract": {
                		"alias": "FXHASH GENTK v2",
                		"address": "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
                	},
                	"tokenId": "686246",
                	"standard": "fa2",
                	"firstLevel": 2352896,
                	"firstTime": "2022-05-10T11:52:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "[WAITING TO BE SIGNED]",
                		"symbol": "GENTK",
                		"decimals": "0",
                		"displayUri": "ipfs://QmYwSwa5hP4346GqD7hAjutwJSmeYTdiLQ7Wec2C7Cez1D",
                		"artifactUri": "ipfs://QmdGV3UqJqX4v5x9nFcDYeekCEAm3SDXUG5SHdjKQKn4Pe",
                		"description": "This Gentk is waiting to be signed by Fxhash Signer module",
                		"thumbnailUri": "ipfs://QmbvEAn7FLMeYBDroYwBP8qWc3d3VVWbk19tTB83LCMB5S"
                	}
                }, {
                	"id": 2860800,
                	"contract": {
                		"alias": "FXHASH GENTK v2",
                		"address": "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
                	},
                	"tokenId": "686228",
                	"standard": "fa2",
                	"firstLevel": 2352876,
                	"firstTime": "2022-05-10T11:41:29Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 2,
                	"balancesCount": 2,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "1984 #14",
                		"tags": ["sketch", "eye", "animated", "drawing", "pencil", "1984", "creativecoding", "generativeart", "p5js"],
                		"symbol": "GENTK",
                		"version": "0.2",
                		"decimals": "0",
                		"attributes": [{
                			"name": "Species",
                			"value": "Mammal"
                		}, {
                			"name": "Palette",
                			"value": "Acid"
                		}, {
                			"name": "Format",
                			"value": "Vertical"
                		}, {
                			"name": "Sheet",
                			"value": "Paper"
                		}, {
                			"name": "Torn",
                			"value": "Slightly"
                		}],
                		"displayUri": "ipfs://Qmevvv2qF2SnidEehM4uANUmT3gm1xFiX19cN6GN9qn6Yc",
                		"artifactUri": "ipfs://QmbCv4ktfvEr89kzURRAWbxFQcqzk9wmR5dbtooKWjqscf?fxhash=onify73B3Ziv956VHSPtX3tMhzkJP2xsB9KaETDcBuR3ezv6tXr",
                		"description": "“Always eyes watching you and the voice enveloping you. Asleep or awake, indoors or out of doors, in the bath or bed—no escape. Nothing was your own except the few cubic centimeters in your skull.” George Orwell\n\nPress [s] to save as png.\n\nGenerative artwork by Gandhiavelli, @gandhiavelli. Made with p5js in 2022.",
                		"generatorUri": "ipfs://QmbCv4ktfvEr89kzURRAWbxFQcqzk9wmR5dbtooKWjqscf",
                		"thumbnailUri": "ipfs://QmdP4VKLyu9Y1fy1EDkUyupXG6uUU9sgg5Gy7pbXeT2QBE",
                		"iterationHash": "onify73B3Ziv956VHSPtX3tMhzkJP2xsB9KaETDcBuR3ezv6tXr",
                		"authenticityHash": "5d36062a3a97df430e58c72016366350811d9c6a99e77be813b86cf382e5ee40"
                	}
                }, {
                	"id": 2831713,
                	"contract": {
                		"alias": "hic et nunc NFTs",
                		"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
                	},
                	"tokenId": "729572",
                	"standard": "fa2",
                	"firstLevel": 2341908,
                	"firstTime": "2022-05-06T13:41:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 47,
                	"balancesCount": 31,
                	"holdersCount": 30,
                	"totalMinted": "100",
                	"totalBurned": "0",
                	"totalSupply": "100",
                	"metadata": {
                		"name": "AI Pollock",
                		"tags": ["ai", "artificialintelligence", "generative", "aigenerated", "abstract", "expressionism", "jacksonpollock", "abstractexpressionism", "colors", "colorful", "aigenerated", "60fps", "painting", "aipainting", "generativeart", "creativecoding", "tezos4tezos", "tezos4tezos", "mp4", "video", "fullhd"],
                		"symbol": "OBJKT",
                		"formats": [{
                			"uri": "ipfs://QmX5gdQYN2sP1231ce5hFbPX9GjHtFcpVEScbbJ44faSTq",
                			"mimeType": "video/mp4"
                		}],
                		"creators": ["tz1NZgkr5zUJqso7QXXKt74sX1MJpY4id8MR"],
                		"decimals": "0",
                		"displayUri": "ipfs://QmSmJC95Y9h4VDmtqBxYHAZDppY3jccnsZi23BQcqWBJXB",
                		"artifactUri": "ipfs://QmX5gdQYN2sP1231ce5hFbPX9GjHtFcpVEScbbJ44faSTq",
                		"description": "AI generated abstract expressionistic painting.\nTEZOS4TEZOS edition!\nFormat: MP4\nDuration : 25 s\nFrame rate: 60 FPS \nDimensions: 1920 x 1080",
                		"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"isBooleanAmount": false,
                		"shouldPreferSymbol": false
                	}
                }, {
                	"id": 2831646,
                	"contract": {
                		"alias": "hic et nunc NFTs",
                		"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
                	},
                	"tokenId": "729558",
                	"standard": "fa2",
                	"firstLevel": 2341887,
                	"firstTime": "2022-05-06T13:29:59Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 36,
                	"balancesCount": 26,
                	"holdersCount": 25,
                	"totalMinted": "100",
                	"totalBurned": "0",
                	"totalSupply": "100",
                	"metadata": {
                		"name": "Wet Nights",
                		"tags": ["tez4tez", "tezos4tezos", "naked", "nude", "lesbian", "girl", "woman", "hot", "sexy"],
                		"symbol": "OBJKT",
                		"formats": [{
                			"uri": "ipfs://QmbUsMeGdjdspWGYDoNaM2sjgB9PGJVBb5VCoJecxbreno",
                			"mimeType": "image/jpeg"
                		}],
                		"creators": ["tz1Xv6TCtr9NaP5WLg8BJzkFDbsosVAkep6B"],
                		"decimals": "0",
                		"displayUri": "ipfs://QmYczLzMemt312DpoGPFTsHmhrpZVeXLuJPA9uFNKuQTYJ",
                		"artifactUri": "ipfs://QmbUsMeGdjdspWGYDoNaM2sjgB9PGJVBb5VCoJecxbreno",
                		"description": "Illustration",
                		"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"isBooleanAmount": false,
                		"shouldPreferSymbol": false
                	}
                }]
            """.trimIndent()
        )
        mock(
            """
                [{
                	"id": 2860850,
                	"contract": {
                		"alias": "FXHASH GENTK v2",
                		"address": "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
                	},
                	"tokenId": "686247",
                	"standard": "fa2",
                	"firstLevel": 2352896,
                	"firstTime": "2022-05-10T11:52:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "[WAITING TO BE SIGNED]",
                		"symbol": "GENTK",
                		"decimals": "0",
                		"displayUri": "ipfs://QmYwSwa5hP4346GqD7hAjutwJSmeYTdiLQ7Wec2C7Cez1D",
                		"artifactUri": "ipfs://QmdGV3UqJqX4v5x9nFcDYeekCEAm3SDXUG5SHdjKQKn4Pe",
                		"description": "This Gentk is waiting to be signed by Fxhash Signer module",
                		"thumbnailUri": "ipfs://QmbvEAn7FLMeYBDroYwBP8qWc3d3VVWbk19tTB83LCMB5S"
                	}
                }, {
                	"id": 2860849,
                	"contract": {
                		"alias": "FXHASH GENTK v2",
                		"address": "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
                	},
                	"tokenId": "686246",
                	"standard": "fa2",
                	"firstLevel": 2352896,
                	"firstTime": "2022-05-10T11:52:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "[WAITING TO BE SIGNED]",
                		"symbol": "GENTK",
                		"decimals": "0",
                		"displayUri": "ipfs://QmYwSwa5hP4346GqD7hAjutwJSmeYTdiLQ7Wec2C7Cez1D",
                		"artifactUri": "ipfs://QmdGV3UqJqX4v5x9nFcDYeekCEAm3SDXUG5SHdjKQKn4Pe",
                		"description": "This Gentk is waiting to be signed by Fxhash Signer module",
                		"thumbnailUri": "ipfs://QmbvEAn7FLMeYBDroYwBP8qWc3d3VVWbk19tTB83LCMB5S"
                	}
                }, {
                	"id": 2860848,
                	"contract": {
                		"address": "KT1DyJodC4zgDA8mAekZhH39gEM4BBDdCrTb"
                	},
                	"tokenId": "2",
                	"standard": "fa2",
                	"firstLevel": 2352896,
                	"firstTime": "2022-05-10T11:52:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "2",
                	"totalBurned": "0",
                	"totalSupply": "2",
                	"metadata": {
                		"date": "2022-05-10T11:51:20.391Z",
                		"name": "PB Dailies #2",
                		"tags": ["pbdailies", "photez", "birds", "photography"],
                		"image": "ipfs://QmYDhTmQp4hA6VLHKSvjcPKAoTRDsE7BDEwpNefxtX6tUn",
                		"minter": "KT1Aq4wWmVanpQhq4TTfjZXB5AjFpx15iQMM",
                		"rights": "No License / All Rights Reserved",
                		"symbol": "OBJKTCOM",
                		"formats": [{
                			"uri": "ipfs://QmeDAJsv1xvGHTEGyPCrueTAXAUemBJmkHfoVQjeYqaTdo",
                			"fileName": "PBdailies #2.jpg",
                			"fileSize": "1334382",
                			"mimeType": "image/jpeg",
                			"dimensions": {
                				"unit": "px",
                				"value": "1328x996"
                			}
                		}, {
                			"uri": "ipfs://QmYDhTmQp4hA6VLHKSvjcPKAoTRDsE7BDEwpNefxtX6tUn",
                			"fileName": "cover-PBdailies #2.jpeg",
                			"fileSize": "865853",
                			"mimeType": "image/jpeg",
                			"dimensions": {
                				"unit": "px",
                				"value": "1024x768"
                			}
                		}, {
                			"uri": "ipfs://QmPFiLsFzAjKUwV5Gzj8kGT6oZpLDLmqUcmXi85kkLJiBs",
                			"fileName": "thumbnail-PBdailies #2.jpeg",
                			"fileSize": "127073",
                			"mimeType": "image/jpeg",
                			"dimensions": {
                				"unit": "px",
                				"value": "350x263"
                			}
                		}],
                		"creators": ["tz1QVvTNBH8D5Y3br7a1W9qvLepseCMDa8he"],
                		"decimals": "0",
                		"royalties": {
                			"shares": {
                				"tz1QVvTNBH8D5Y3br7a1W9qvLepseCMDa8he": "150"
                			},
                			"decimals": "3"
                		},
                		"attributes": [],
                		"displayUri": "ipfs://QmYDhTmQp4hA6VLHKSvjcPKAoTRDsE7BDEwpNefxtX6tUn",
                		"artifactUri": "ipfs://QmeDAJsv1xvGHTEGyPCrueTAXAUemBJmkHfoVQjeYqaTdo",
                		"description": "A Red-breasted Merganser swims across a lake\n---\nWeek 1\nBird: Red-breasted Merganser\nEdit: Oil paint exploring empty space\nPhotographed: April 25, 2022\nLocation: Fayetteville, AR",
                		"mintingTool": "https://objkt.com",
                		"thumbnailUri": "ipfs://QmPFiLsFzAjKUwV5Gzj8kGT6oZpLDLmqUcmXi85kkLJiBs",
                		"isBooleanAmount": false,
                		"shouldPreferSymbol": false
                	}
                }, {
                	"id": 2860847,
                	"contract": {
                		"address": "KT1EYJ8dK9MvxK2WxqcTnekXNyMCmHuBjoMz"
                	},
                	"tokenId": "4620",
                	"standard": "fa2",
                	"firstLevel": 2352896,
                	"firstTime": "2022-05-10T11:52:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "Thibaut Hamonou",
                		"tags": ["Top 14", "Section Paloise Béarn Pyrénées", "Rugby", "Fanlive"],
                		"minter": null,
                		"symbol": "FAN",
                		"formats": [{
                			"uri": "ipfs://QmU4tPK78GrFmuqYzp7PAisonRzF1AQwXv1kqUNchAFyRV",
                			"mimeType": "image/png"
                		}],
                		"creators": ["FanLive"],
                		"decimals": "0",
                		"royalties": {
                			"shares": {
                				"tz1gVVoEE5EGokaZXA8NpV63iN7tiE4H42Ae": "10"
                			},
                			"decimals": "2"
                		},
                		"attributes": [{
                			"name": "League",
                			"value": "Top 14"
                		}, {
                			"name": "Nationality",
                			"value": "France"
                		}, {
                			"name": "Position",
                			"value": "Backrow"
                		}, {
                			"name": "Club",
                			"value": "Section Paloise Béarn Pyrénées"
                		}, {
                			"name": "Season",
                			"value": "2021-2022"
                		}, {
                			"name": "Category",
                			"value": "Gold"
                		}, {
                			"name": "Sport",
                			"value": "Rugby"
                		}, {
                			"name": "Serial number",
                			"value": "27/150"
                		}],
                		"displayUri": "ipfs://QmU4tPK78GrFmuqYzp7PAisonRzF1AQwXv1kqUNchAFyRV",
                		"artifactUri": "ipfs://QmU4tPK78GrFmuqYzp7PAisonRzF1AQwXv1kqUNchAFyRV",
                		"thumbnailUri": "ipfs://QmSDeNZwgKUh5DomiCkFP2PGMUE1XC7URjh8w5FqLr5zPN",
                		"isBooleanAmount": true,
                		"shouldPreferSymbol": false
                	}
                }, {
                	"id": 2831713,
                	"contract": {
                		"alias": "hic et nunc NFTs",
                		"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
                	},
                	"tokenId": "729572",
                	"standard": "fa2",
                	"firstLevel": 2341908,
                	"firstTime": "2022-05-06T13:41:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 47,
                	"balancesCount": 31,
                	"holdersCount": 30,
                	"totalMinted": "100",
                	"totalBurned": "0",
                	"totalSupply": "100",
                	"metadata": {
                		"name": "AI Pollock",
                		"tags": ["ai", "artificialintelligence", "generative", "aigenerated", "abstract", "expressionism", "jacksonpollock", "abstractexpressionism", "colors", "colorful", "aigenerated", "60fps", "painting", "aipainting", "generativeart", "creativecoding", "tezos4tezos", "tezos4tezos", "mp4", "video", "fullhd"],
                		"symbol": "OBJKT",
                		"formats": [{
                			"uri": "ipfs://QmX5gdQYN2sP1231ce5hFbPX9GjHtFcpVEScbbJ44faSTq",
                			"mimeType": "video/mp4"
                		}],
                		"creators": ["tz1NZgkr5zUJqso7QXXKt74sX1MJpY4id8MR"],
                		"decimals": "0",
                		"displayUri": "ipfs://QmSmJC95Y9h4VDmtqBxYHAZDppY3jccnsZi23BQcqWBJXB",
                		"artifactUri": "ipfs://QmX5gdQYN2sP1231ce5hFbPX9GjHtFcpVEScbbJ44faSTq",
                		"description": "AI generated abstract expressionistic painting.\nTEZOS4TEZOS edition!\nFormat: MP4\nDuration : 25 s\nFrame rate: 60 FPS \nDimensions: 1920 x 1080",
                		"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                		"isBooleanAmount": false,
                		"shouldPreferSymbol": false
                	}
                }]
            """.trimIndent()
        )
        mock(
            """
                [{
                	"id": 2860850,
                	"contract": {
                		"alias": "FXHASH GENTK v2",
                		"address": "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
                	},
                	"tokenId": "686247",
                	"standard": "fa2",
                	"firstLevel": 2352896,
                	"firstTime": "2022-05-10T11:52:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "[WAITING TO BE SIGNED]",
                		"symbol": "GENTK",
                		"decimals": "0",
                		"displayUri": "ipfs://QmYwSwa5hP4346GqD7hAjutwJSmeYTdiLQ7Wec2C7Cez1D",
                		"artifactUri": "ipfs://QmdGV3UqJqX4v5x9nFcDYeekCEAm3SDXUG5SHdjKQKn4Pe",
                		"description": "This Gentk is waiting to be signed by Fxhash Signer module",
                		"thumbnailUri": "ipfs://QmbvEAn7FLMeYBDroYwBP8qWc3d3VVWbk19tTB83LCMB5S"
                	}
                }, {
                	"id": 2860849,
                	"contract": {
                		"alias": "FXHASH GENTK v2",
                		"address": "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
                	},
                	"tokenId": "686246",
                	"standard": "fa2",
                	"firstLevel": 2352896,
                	"firstTime": "2022-05-10T11:52:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "[WAITING TO BE SIGNED]",
                		"symbol": "GENTK",
                		"decimals": "0",
                		"displayUri": "ipfs://QmYwSwa5hP4346GqD7hAjutwJSmeYTdiLQ7Wec2C7Cez1D",
                		"artifactUri": "ipfs://QmdGV3UqJqX4v5x9nFcDYeekCEAm3SDXUG5SHdjKQKn4Pe",
                		"description": "This Gentk is waiting to be signed by Fxhash Signer module",
                		"thumbnailUri": "ipfs://QmbvEAn7FLMeYBDroYwBP8qWc3d3VVWbk19tTB83LCMB5S"
                	}
                }, {
                	"id": 2860848,
                	"contract": {
                		"address": "KT1DyJodC4zgDA8mAekZhH39gEM4BBDdCrTb"
                	},
                	"tokenId": "2",
                	"standard": "fa2",
                	"firstLevel": 2352896,
                	"firstTime": "2022-05-10T11:52:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "2",
                	"totalBurned": "0",
                	"totalSupply": "2",
                	"metadata": {
                		"date": "2022-05-10T11:51:20.391Z",
                		"name": "PB Dailies #2",
                		"tags": ["pbdailies", "photez", "birds", "photography"],
                		"image": "ipfs://QmYDhTmQp4hA6VLHKSvjcPKAoTRDsE7BDEwpNefxtX6tUn",
                		"minter": "KT1Aq4wWmVanpQhq4TTfjZXB5AjFpx15iQMM",
                		"rights": "No License / All Rights Reserved",
                		"symbol": "OBJKTCOM",
                		"formats": [{
                			"uri": "ipfs://QmeDAJsv1xvGHTEGyPCrueTAXAUemBJmkHfoVQjeYqaTdo",
                			"fileName": "PBdailies #2.jpg",
                			"fileSize": "1334382",
                			"mimeType": "image/jpeg",
                			"dimensions": {
                				"unit": "px",
                				"value": "1328x996"
                			}
                		}, {
                			"uri": "ipfs://QmYDhTmQp4hA6VLHKSvjcPKAoTRDsE7BDEwpNefxtX6tUn",
                			"fileName": "cover-PBdailies #2.jpeg",
                			"fileSize": "865853",
                			"mimeType": "image/jpeg",
                			"dimensions": {
                				"unit": "px",
                				"value": "1024x768"
                			}
                		}, {
                			"uri": "ipfs://QmPFiLsFzAjKUwV5Gzj8kGT6oZpLDLmqUcmXi85kkLJiBs",
                			"fileName": "thumbnail-PBdailies #2.jpeg",
                			"fileSize": "127073",
                			"mimeType": "image/jpeg",
                			"dimensions": {
                				"unit": "px",
                				"value": "350x263"
                			}
                		}],
                		"creators": ["tz1QVvTNBH8D5Y3br7a1W9qvLepseCMDa8he"],
                		"decimals": "0",
                		"royalties": {
                			"shares": {
                				"tz1QVvTNBH8D5Y3br7a1W9qvLepseCMDa8he": "150"
                			},
                			"decimals": "3"
                		},
                		"attributes": [],
                		"displayUri": "ipfs://QmYDhTmQp4hA6VLHKSvjcPKAoTRDsE7BDEwpNefxtX6tUn",
                		"artifactUri": "ipfs://QmeDAJsv1xvGHTEGyPCrueTAXAUemBJmkHfoVQjeYqaTdo",
                		"description": "A Red-breasted Merganser swims across a lake\n---\nWeek 1\nBird: Red-breasted Merganser\nEdit: Oil paint exploring empty space\nPhotographed: April 25, 2022\nLocation: Fayetteville, AR",
                		"mintingTool": "https://objkt.com",
                		"thumbnailUri": "ipfs://QmPFiLsFzAjKUwV5Gzj8kGT6oZpLDLmqUcmXi85kkLJiBs",
                		"isBooleanAmount": false,
                		"shouldPreferSymbol": false
                	}
                }, {
                	"id": 2860847,
                	"contract": {
                		"address": "KT1EYJ8dK9MvxK2WxqcTnekXNyMCmHuBjoMz"
                	},
                	"tokenId": "4620",
                	"standard": "fa2",
                	"firstLevel": 2352896,
                	"firstTime": "2022-05-10T11:52:14Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 1,
                	"balancesCount": 1,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "Thibaut Hamonou",
                		"tags": ["Top 14", "Section Paloise Béarn Pyrénées", "Rugby", "Fanlive"],
                		"minter": null,
                		"symbol": "FAN",
                		"formats": [{
                			"uri": "ipfs://QmU4tPK78GrFmuqYzp7PAisonRzF1AQwXv1kqUNchAFyRV",
                			"mimeType": "image/png"
                		}],
                		"creators": ["FanLive"],
                		"decimals": "0",
                		"royalties": {
                			"shares": {
                				"tz1gVVoEE5EGokaZXA8NpV63iN7tiE4H42Ae": "10"
                			},
                			"decimals": "2"
                		},
                		"attributes": [{
                			"name": "League",
                			"value": "Top 14"
                		}, {
                			"name": "Nationality",
                			"value": "France"
                		}, {
                			"name": "Position",
                			"value": "Backrow"
                		}, {
                			"name": "Club",
                			"value": "Section Paloise Béarn Pyrénées"
                		}, {
                			"name": "Season",
                			"value": "2021-2022"
                		}, {
                			"name": "Category",
                			"value": "Gold"
                		}, {
                			"name": "Sport",
                			"value": "Rugby"
                		}, {
                			"name": "Serial number",
                			"value": "27/150"
                		}],
                		"displayUri": "ipfs://QmU4tPK78GrFmuqYzp7PAisonRzF1AQwXv1kqUNchAFyRV",
                		"artifactUri": "ipfs://QmU4tPK78GrFmuqYzp7PAisonRzF1AQwXv1kqUNchAFyRV",
                		"thumbnailUri": "ipfs://QmSDeNZwgKUh5DomiCkFP2PGMUE1XC7URjh8w5FqLr5zPN",
                		"isBooleanAmount": true,
                		"shouldPreferSymbol": false
                	}
                }, {
                	"id": 2763372,
                	"contract": {
                		"alias": "Tezotopia NFT Registry",
                		"address": "KT1ViVwoVfGSCsDaxjwoovejm1aYSGz7s2TZ"
                	},
                	"tokenId": "44349",
                	"standard": "fa2",
                	"firstLevel": 2319002,
                	"firstTime": "2022-04-28T09:35:29Z",
                	"lastLevel": 2352896,
                	"lastTime": "2022-05-10T11:52:14Z",
                	"transfersCount": 7,
                	"balancesCount": 2,
                	"holdersCount": 1,
                	"totalMinted": "1",
                	"totalBurned": "0",
                	"totalSupply": "1",
                	"metadata": {
                		"name": "Tz T.Top 1.11",
                		"tags": ["unit", "tezotopia", "gaming", "gifdotgames"],
                		"genres": ["gaming"],
                		"symbol": "TZTOP",
                		"creators": ["gifdotgames"],
                		"decimals": "0",
                		"language": "en",
                		"displayUri": "ipfs://QmeJqTpYs66Qkqo9NWyp4tH8LieL4tBQwbkxwSutTEsjSL",
                		"publishers": ["gifdotgames"],
                		"artifactUri": "ipfs://QmeJqTpYs66Qkqo9NWyp4tH8LieL4tBQwbkxwSutTEsjSL",
                		"description": "One of the original clone droids to envelope all of Tezotopia's mercenary markets. The T.Top 1.11 model is a light,affordable, standard war machine that is produced in massive quantities.",
                		"thumbnailUri": "ipfs://QmVzWLwwbqKeLELLS2rGurGZpg42AC4oJ77wFt15a79eJG",
                		"collectionName": "unit",
                		"isTransferable": true,
                		"isBooleanAmount": true,
                		"shouldPreferSymbol": false
                	}
                }]
            """.trimIndent()
        )
        mock("[]")

        val firstTokenRequest = tokenClient.allTokensByLastUpdate(5, null, false)
        assertThat(firstTokenRequest.items.size).isEqualTo(5)
        assertThat(firstTokenRequest.continuation).isNotNull
        assertThat(firstTokenRequest.continuation).isEqualTo("2352896_2831646")

        val secondTokenRequest = tokenClient.allTokensByLastUpdate(5, firstTokenRequest.continuation, false)
        assertThat(secondTokenRequest.items.size).isEqualTo(5)
        assertThat(secondTokenRequest.continuation).isNotNull
        assertThat(secondTokenRequest.continuation).isEqualTo("2352896_2831713")

        val thirdTokenRequest = tokenClient.allTokensByLastUpdate(6, secondTokenRequest.continuation, false)
        assertThat(thirdTokenRequest.items.size).isEqualTo(5)
        assertThat(thirdTokenRequest.continuation).isNull()
    }

    @Test
    fun `should return tokens by ids`() = runBlocking<Unit> {
        mock(
            """[{
            "id": 1,
            "contract": {
                "alias": "tzBTC",
                "address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            },
            "tokenId": "0",
            "standard": "fa1.2",
            "firstLevel": 889166,
            "firstTime": "2020-03-31T15:12:51Z",
            "lastLevel": 2280800,
            "lastTime": "2022-04-14T16:51:14Z",
            "transfersCount": 97188,
            "balancesCount": 3029,
            "holdersCount": 1179,
            "totalMinted": "107615636205",
            "totalBurned": "0",
            "totalSupply": "107615636205",
            "metadata": {
                "name": "tzBTC",
                "symbol": "tzBTC",
                "decimals": "8"
            }
        }]"""
        )
        mock(
            """[{
            "id": 2,
            "contract": {
                "alias": "StakerDAO",
                "address": "KT1EctCuorV2NfVb1XTQgvzJ88MQtWP8cMMv"
            },
            "tokenId": "0",
            "standard": "fa1.2",
            "firstLevel": 767840,
            "firstTime": "2020-01-06T03:46:32Z",
            "lastLevel": 1098868,
            "lastTime": "2020-08-25T00:50:26Z",
            "transfersCount": 25,
            "balancesCount": 22,
            "holdersCount": 20,
            "totalMinted": "1500000",
            "totalBurned": "0",
            "totalSupply": "1500000"
        }]"""
        )
        val tokens = tokenClient.tokens(
            listOf(
                "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn:0",
                "KT1EctCuorV2NfVb1XTQgvzJ88MQtWP8cMMv:0"
            )
        )

        assertThat(tokens).hasSize(2)
        assertThat(tokens.first().standard).isEqualTo("fa1.2")
    }

    @Test
    fun `should return true for nft item`() = runBlocking<Unit> {
        mock("""[
                {
        "id": 60,
        "contract": {
            "address": "KT1S95Dyj2QrJpSnAbHRUSUZr7DhuFqssrog"
        },
        "tokenId": "0",
        "standard": "fa2",
        "firstLevel": 1328122,
        "firstTime": "2021-02-02T19:17:52Z",
        "lastLevel": 1390772,
        "lastTime": "2021-03-19T00:29:18Z",
        "transfersCount": 2,
        "balancesCount": 2,
        "holdersCount": 1,
        "totalMinted": "1",
        "totalBurned": "0",
        "totalSupply": "1",
        "metadata": {
            "artifactUri": "https://cloudflare-ipfs.com/ipfs/QmUnPB2pBFHv3MyRDQcmMGkpDNKNqeX57xASa9cx4Xomn1"
        }
    }]""".trimMargin())

        val isNft = tokenClient.isNft("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:157993")

        assertThat(request().path).isEqualTo("/v1/tokens?contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&tokenId=157993&token.standard=fa2")
        assertThat(isNft).isEqualTo(true)
    }

    @Test
    fun `should return false for nft item`() = runBlocking<Unit> {
        mock("""[
                {
        "id": 60,
        "contract": {
            "address": "KT1S95Dyj2QrJpSnAbHRUSUZr7DhuFqssrog"
        },
        "tokenId": "0",
        "standard": "fa2",
        "firstLevel": 1328122,
        "firstTime": "2021-02-02T19:17:52Z",
        "lastLevel": 1390772,
        "lastTime": "2021-03-19T00:29:18Z",
        "transfersCount": 2,
        "balancesCount": 2,
        "holdersCount": 1,
        "totalMinted": "1",
        "totalBurned": "0",
        "totalSupply": "1",
        "metadata": {
            
        }
    }]""".trimMargin())

        val isNft = tokenClient.isNft("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:157993")

        assertThat(request().path).isEqualTo("/v1/tokens?contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&tokenId=157993&token.standard=fa2")
        assertThat(isNft).isEqualTo(false)
    }

    @Test
    fun `should return null for nft item`() = runBlocking<Unit> {
        mock("""[
                {
        "id": 60,
        "contract": {
            "address": "KT1S95Dyj2QrJpSnAbHRUSUZr7DhuFqssrog"
        },
        "tokenId": "0",
        "standard": "fa2",
        "firstLevel": 1328122,
        "firstTime": "2021-02-02T19:17:52Z",
        "lastLevel": 1390772,
        "lastTime": "2021-03-19T00:29:18Z",
        "transfersCount": 2,
        "balancesCount": 2,
        "holdersCount": 1,
        "totalMinted": "1",
        "totalBurned": "0",
        "totalSupply": "1" }]""".trimMargin())

        val isNft = tokenClient.isNft("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:157993")

        assertThat(request().path).isEqualTo("/v1/tokens?contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&tokenId=157993&token.standard=fa2")
        assertThat(isNft).isNull()
    }

    @Test
    fun `shouldn't return token by contract and token id`() = runBlocking<Unit> {
        mock("[]")
        assertThrows<TzktNotFound> { tokenClient.token("KT1NWdwVA8zq5DDJTKcMkRqWYJcEcyTTm5WK:1156") }
    }

    @Test
    fun `should return token count`() = runBlocking<Unit> {
        mock("[\"1977\"]")
        val count = tokenClient.tokenCount("KT1NWdwVA8zq5DDJTKcMkRqWYJcEcyTTm5WK")
        assertThat(count).isEqualTo(BigInteger("1977"))
    }
}
