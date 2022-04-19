package com.rarible.tzkt.client

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TokenClientTests : BaseClientTests() {

    val tokenClient = TokenClient(client)

    @Test
    fun `should return token by contract and token id`() = runBlocking<Unit> {
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
        assertThat(request().path).isEqualTo("/v1/tokens?contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&tokenId=157993&token.standard=fa2")

        assertThat(token).isNotNull
        assertThat(token.standard).isEqualTo("fa2")
    }

    @Test
    fun `should return tokens by with size, continuation and sorted by ASC`() = runBlocking<Unit> {
        mock("""
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
        """.trimIndent())

        mock("""
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
        """.trimIndent())

        val size = 10
        var continuation = 0L
        var tokens = tokenClient.tokens(size, continuation)
        assertThat(request().path).isEqualTo("/v1/tokens?token.standard=fa2&limit=10&offset.cr=0&sort.asc=id&metadata.artifactUri.null=false")
        var prevId = 0
        tokens.forEach{
            assertThat(it.id).isGreaterThan(prevId)
            assertThat(it.standard).isEqualTo("fa2")
            prevId = it.id!!
        }
        prevId = 0
        val lastId = tokens.last().id!!.toLong()
        continuation = lastId
        tokens = tokenClient.tokens(size, continuation)
        assertThat(request().path).isEqualTo("/v1/tokens?token.standard=fa2&limit=10&offset.cr=77&sort.asc=id&metadata.artifactUri.null=false")
        tokens.forEach{
            assertThat(it.id).isGreaterThan(prevId)
            assertThat(it.standard).isEqualTo("fa2")
            prevId = it.id!!
        }
        assertThat(tokens.first().id?.toLong()).isGreaterThan(lastId)
    }

    @Test
    fun `should return tokens by with size, continuation and sorted by DESC`() = runBlocking<Unit> {
        mock("""
            [{
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
        """.trimIndent())

        mock("""
            [{
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
            }]
        """.trimIndent())

        val size = 10
        var continuation = 88L
        var tokens = tokenClient.tokens(size, continuation, false)
        assertThat(request().path).isEqualTo("/v1/tokens?token.standard=fa2&limit=10&offset.cr=88&sort.desc=id&metadata.artifactUri.null=false")
        var prevId = 88L
        tokens.forEach{
            assertThat(it.id?.toLong()).isLessThan(prevId)
            assertThat(it.standard).isEqualTo("fa2")
            prevId = it.id!!.toLong()
        }
        val lastId = tokens.last().id!!.toLong()
        continuation = lastId
        prevId = lastId
        tokens = tokenClient.tokens(size, continuation, false)
        assertThat(request().path).isEqualTo("/v1/tokens?token.standard=fa2&limit=10&offset.cr=77&sort.desc=id&metadata.artifactUri.null=false")
        tokens.forEach{
            assertThat(it.id?.toLong()).isLessThan(prevId)
            assertThat(it.standard).isEqualTo("fa2")
            prevId = it.id!!.toLong()
        }
        assertThat(tokens.first().id?.toLong()).isLessThan(lastId)
    }

    @Test
    fun `should return tokens by ids`() = runBlocking<Unit> {
        mock("""[{
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
        }]""")
        mock("""[{
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
        }]""")
        val tokens = tokenClient.tokens(listOf("KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn:0", "KT1EctCuorV2NfVb1XTQgvzJ88MQtWP8cMMv:0"))

        assertThat(tokens).hasSize(2)
        assertThat(tokens.first().standard).isEqualTo("fa1.2")
    }
}
