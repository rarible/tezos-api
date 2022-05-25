package com.rarible.tzkt.client

import com.rarible.tzkt.model.ActivityType
import com.rarible.tzkt.model.TzktActivityContinuation
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ActivityClientTests : BaseClientTests() {

    val activityClient = TokenActivityClient(client)

    @Test
    fun `should return NFT activities with size, continuation, and sorting ASC`() = runBlocking<Unit> {
        mock("""
            [{
            	"id": 23818305,
            	"level": 889166,
            	"timestamp": "2020-03-31T15:12:51Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"to": {
            		"address": "tz1ZAwyfujwED4yUhQAtc1eqm4gW5u2Xiw77"
            	},
            	"amount": "100000000",
            	"transactionId": 23818302
            }, {
            	"id": 23820166,
            	"level": 889188,
            	"timestamp": "2020-03-31T15:34:51Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1ZAwyfujwED4yUhQAtc1eqm4gW5u2Xiw77"
            	},
            	"to": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"amount": "10000",
            	"transactionId": 23820160
            }]
        """.trimIndent())

        mock("""
            [{
            	"id": 24209728,
            	"level": 901489,
            	"timestamp": "2020-04-09T06:47:20Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"to": {
            		"address": "tz1fNwSagKoNLDM7YFDDRCKg6CosghKtLFp8"
            	},
            	"amount": "1",
            	"transactionId": 24209723
            }, {
            	"id": 24276908,
            	"level": 903632,
            	"timestamp": "2020-04-10T18:47:02Z",
            	"token": {
            		"id": 2,
            		"contract": {
            			"alias": "StakerDAO",
            			"address": "KT1EctCuorV2NfVb1XTQgvzJ88MQtWP8cMMv"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2"
            	},
            	"from": {
            		"address": "tz1WAVpSaCFtLQKSJkrdVApCQC1TNK8iNxq9"
            	},
            	"to": {
            		"address": "tz1dcWXLS1UBeGc7EazGvoNE6D8YSzVkAsSa"
            	},
            	"amount": "1",
            	"transactionId": 24276901
            }]
        """.trimIndent())

        var activities = activityClient.activities(2, null, true, listOf(ActivityType.MINT, ActivityType.TRANSFER))
        assertThat(TzktActivityContinuation.isValid(activities.continuation!!)).isTrue
        assertThat(activities.items).hasSize(2)
    }

    @Test
    fun `should return NFT activities by id`() = runBlocking<Unit> {
        mock("""[
            {
                "id": 23818305,
                "level": 889166,
                "timestamp": "2020-03-31T15:12:51Z",
                "token": {
                    "id": 1,
                    "contract": {
                        "alias": "tzBTC",
                        "address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
                    },
                    "tokenId": "0",
                    "standard": "fa1.2",
                    "metadata": {
                        "name": "tzBTC",
                        "symbol": "tzBTC",
                        "decimals": "8"
                    }
                },
                "to": {
                    "address": "tz1ZAwyfujwED4yUhQAtc1eqm4gW5u2Xiw77"
                },
                "amount": "100000000",
                "transactionId": 23818302
            },
            {
                "id": 23820166,
                "level": 889188,
                "timestamp": "2020-03-31T15:34:51Z",
                "token": {
                    "id": 1,
                    "contract": {
                        "alias": "tzBTC",
                        "address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
                    },
                    "tokenId": "0",
                    "standard": "fa1.2",
                    "metadata": {
                        "name": "tzBTC",
                        "symbol": "tzBTC",
                        "decimals": "8"
                    }
                },
                "from": {
                    "address": "tz1ZAwyfujwED4yUhQAtc1eqm4gW5u2Xiw77"
                },
                "to": {
                    "address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
                },
                "amount": "10000",
                "transactionId": 23820160
            }
        ]""".trimIndent())

        var activities = activityClient.activityByIds(listOf(23818305, 23820166).map { it.toString() })

        assertThat(activities).hasSize(2)
        assertThat(request().path).isEqualTo("/v1/tokens/transfers?id.in=23818305,23820166")
    }

    @Test
    fun `should return NFT activities by item`() = runBlocking<Unit> {
        mock("""[
            {
                "id": 23818305,
                "level": 889166,
                "timestamp": "2020-03-31T15:12:51Z",
                "token": {
                    "id": 1,
                    "contract": {
                        "alias": "tzBTC",
                        "address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
                    },
                    "tokenId": "0",
                    "standard": "fa1.2",
                    "metadata": {
                        "name": "tzBTC",
                        "symbol": "tzBTC",
                        "decimals": "8"
                    }
                },
                "to": {
                    "address": "tz1ZAwyfujwED4yUhQAtc1eqm4gW5u2Xiw77"
                },
                "amount": "100000000",
                "transactionId": 23818302
            },
            {
                "id": 23820166,
                "level": 889188,
                "timestamp": "2020-03-31T15:34:51Z",
                "token": {
                    "id": 1,
                    "contract": {
                        "alias": "tzBTC",
                        "address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
                    },
                    "tokenId": "0",
                    "standard": "fa1.2",
                    "metadata": {
                        "name": "tzBTC",
                        "symbol": "tzBTC",
                        "decimals": "8"
                    }
                },
                "from": {
                    "address": "tz1ZAwyfujwED4yUhQAtc1eqm4gW5u2Xiw77"
                },
                "to": {
                    "address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
                },
                "amount": "10000",
                "transactionId": 23820160
            }]
        """.trimIndent())

        val size = 10
        var continuation = 0L
        var activities = activityClient.activityByItem("KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn", "0", size, continuation)

        assertThat(activities).hasSize(2)
        assertThat(request().path).isEqualTo("/v1/tokens/transfers?token.contract=KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn&token.tokenId=0&offset.cr=0&sort.asc=id")
    }

    @Test
    fun `should return MINT only NFT activities`() = runBlocking<Unit> {
        mock("""
            [{
            	"id": 35474276,
            	"level": 1212043,
            	"timestamp": "2020-11-12T06:41:22Z",
            	"token": {
            		"id": 11,
            		"contract": {
            			"address": "KT1FaKvzjgVGZtiA7yyx97txY8J5cE5qpjQ1"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"to": {
            		"address": "tz1LVkMDYV4AqLEgPDjd2c9zwZ2Wq76dqRYC"
            	},
            	"amount": "5000000",
            	"transactionId": 35474275
            }, {
            	"id": 35514416,
            	"level": 1212700,
            	"timestamp": "2020-11-12T18:10:46Z",
            	"token": {
            		"id": 12,
            		"contract": {
            			"address": "KT1Ex8LrDbCrZuTgmWin8eEo7HFw74jAqTvz"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"to": {
            		"address": "tz1MGG4inpLtsuWe2WtK1G89bpeSM36t2YP2"
            	},
            	"amount": "2000000",
            	"transactionId": 35514414
            }, {
            	"id": 35715584,
            	"level": 1218174,
            	"timestamp": "2020-11-16T16:18:10Z",
            	"token": {
            		"id": 13,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"to": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"amount": "10",
            	"transactionId": 35715575
            }, {
            	"id": 35742555,
            	"level": 1219058,
            	"timestamp": "2020-11-17T07:18:50Z",
            	"token": {
            		"id": 14,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "2",
            		"standard": "fa2"
            	},
            	"to": {
            		"alias": "Django Bits",
            		"address": "tz1YRG68NdqtAcsFEwTUw6FsSsiBb5kagEDo"
            	},
            	"amount": "10",
            	"transactionId": 35742552
            }, {
            	"id": 35742660,
            	"level": 1219062,
            	"timestamp": "2020-11-17T07:22:50Z",
            	"token": {
            		"id": 15,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "3",
            		"standard": "fa2"
            	},
            	"to": {
            		"alias": "Django Bits",
            		"address": "tz1YRG68NdqtAcsFEwTUw6FsSsiBb5kagEDo"
            	},
            	"amount": "1",
            	"transactionId": 35742657
            }, {
            	"id": 35743044,
            	"level": 1219077,
            	"timestamp": "2020-11-17T07:37:50Z",
            	"token": {
            		"id": 16,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "4",
            		"standard": "fa2"
            	},
            	"to": {
            		"alias": "Django Bits",
            		"address": "tz1YRG68NdqtAcsFEwTUw6FsSsiBb5kagEDo"
            	},
            	"amount": "1",
            	"transactionId": 35743043
            }, {
            	"id": 35745141,
            	"level": 1219151,
            	"timestamp": "2020-11-17T08:53:50Z",
            	"token": {
            		"id": 18,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "5",
            		"standard": "fa2"
            	},
            	"to": {
            		"alias": "Django Bits",
            		"address": "tz1YRG68NdqtAcsFEwTUw6FsSsiBb5kagEDo"
            	},
            	"amount": "5",
            	"transactionId": 35745137
            }, {
            	"id": 35747031,
            	"level": 1219218,
            	"timestamp": "2020-11-17T10:01:30Z",
            	"token": {
            		"id": 19,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "6",
            		"standard": "fa2"
            	},
            	"to": {
            		"address": "tz1WW1xr4mdTif7jorgVr7aqY3YnnJPSDXj7"
            	},
            	"amount": "5",
            	"transactionId": 35747029
            }, {
            	"id": 35874700,
            	"level": 1222149,
            	"timestamp": "2020-11-19T11:56:50Z",
            	"token": {
            		"id": 20,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "7",
            		"standard": "fa2"
            	},
            	"to": {
            		"alias": "Django Bits",
            		"address": "tz1YRG68NdqtAcsFEwTUw6FsSsiBb5kagEDo"
            	},
            	"amount": "1",
            	"transactionId": 35874696
            }, {
            	"id": 36027492,
            	"level": 1225935,
            	"timestamp": "2020-11-22T04:49:24Z",
            	"token": {
            		"id": 21,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "8",
            		"standard": "fa2"
            	},
            	"to": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"amount": "73000000",
            	"transactionId": 36027488
            }]
        """.trimIndent())

        var activities = activityClient.activities(10,null,null,true,ActivityType.MINT)
        assertThat(activities).hasSize(10)
        assertThat(request().path).isEqualTo("/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&limit=10&timestamp.asc=id&from.null=true")
        activities.forEach{
            assertThat(it.from).isNull()
            assertThat(it.type).isEqualTo(ActivityType.MINT)
        }
    }

    @Test
    fun `should return BURN only NFT activities`() = runBlocking<Unit> {
        mock("""
            [{
            	"id": 41904714,
            	"level": 1361444,
            	"timestamp": "2021-02-26T11:49:23Z",
            	"token": {
            		"id": 267,
            		"contract": {
            			"address": "KT1M2JnD1wsg7w2B4UXJXtKQPuDUpU2L7cJH"
            		},
            		"tokenId": "125",
            		"standard": "fa2",
            		"metadata": {
            			"name": "img000000005.jpg",
            			"tags": [],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "https://ipfs.io/ipfs/Qmf1J978CQA9dDSDBZjHAxScnb4W3i26Cbmv1cvkfrvkLu",
            				"mimeType": "image/jpeg"
            			}],
            			"creators": ["tz1hkNPg5jQ66pJpZH2boiF5AnbAcd1dt5KS"],
            			"decimals": "0",
            			"artifactUri": "https://ipfs.io/ipfs/Qmf1J978CQA9dDSDBZjHAxScnb4W3i26Cbmv1cvkfrvkLu",
            			"description": "GRADDIIIII",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"booleanAmount": false,
            			"symbolPreference": true
            		}
            	},
            	"from": {
            		"address": "tz1hkNPg5jQ66pJpZH2boiF5AnbAcd1dt5KS"
            	},
            	"to": {
            		"alias": "Burn Address \uD83D\uDD25",
            		"address": "tz1burnburnburnburnburnburnburjAYjjX"
            	},
            	"amount": "1",
            	"transactionId": 41904711
            }, {
            	"id": 42708413,
            	"level": 1377020,
            	"timestamp": "2021-03-09T09:33:32Z",
            	"token": {
            		"id": 5127,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "2503",
            		"standard": "fa2",
            		"metadata": {
            			"name": "Wish a dove of Peace in #Belarus",
            			"tags": ["Belarus", "Freedom", "Feminism", "Represion", "Dictatorship", "Peace"],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmPQHreYSdF1AHNk4iZuTuLjGMKixgh21MHgPn74Rdr1bf",
            				"mimeType": "image/jpeg"
            			}],
            			"creators": ["tz1LqzGX5ELdvfkTdt6Y9FsmVmRu6LfkN9gu"],
            			"decimals": "0",
            			"artifactUri": "ipfs://QmPQHreYSdF1AHNk4iZuTuLjGMKixgh21MHgPn74Rdr1bf",
            			"description": "We, belarussian people showed that we are pacific and we only want freedom of this 26yr of dictatorship. We want Progressism",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"from": {
            		"alias": "Django Bits",
            		"address": "tz1YRG68NdqtAcsFEwTUw6FsSsiBb5kagEDo"
            	},
            	"to": {
            		"alias": "Burn Address \uD83D\uDD25",
            		"address": "tz1burnburnburnburnburnburnburjAYjjX"
            	},
            	"amount": "1",
            	"transactionId": 42708396
            }, {
            	"id": 42708448,
            	"level": 1377021,
            	"timestamp": "2021-03-09T09:34:32Z",
            	"token": {
            		"id": 5100,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "2478",
            		"standard": "fa2",
            		"metadata": {
            			"name": "Pray for Belarus",
            			"tags": ["artfeminismfreedombelaruswomen"],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmRwJEQkJ8M4DiMq4izECJYd5atMC7WkNRJLoAB3Y4UtTy",
            				"mimeType": "image/jpeg"
            			}],
            			"creators": ["tz1LqzGX5ELdvfkTdt6Y9FsmVmRu6LfkN9gu"],
            			"decimals": "0",
            			"artifactUri": "ipfs://QmRwJEQkJ8M4DiMq4izECJYd5atMC7WkNRJLoAB3Y4UtTy",
            			"description": "Our women's fight agains the Dictatorship patriarchy",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"from": {
            		"alias": "Django Bits",
            		"address": "tz1YRG68NdqtAcsFEwTUw6FsSsiBb5kagEDo"
            	},
            	"to": {
            		"alias": "Burn Address \uD83D\uDD25",
            		"address": "tz1burnburnburnburnburnburnburjAYjjX"
            	},
            	"amount": "1",
            	"transactionId": 42708445
            }, {
            	"id": 42854244,
            	"level": 1379938,
            	"timestamp": "2021-03-11T10:22:29Z",
            	"token": {
            		"id": 5182,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "2556",
            		"standard": "fa2",
            		"metadata": {
            			"name": "Lucky OWL",
            			"tags": ["owl", "luck", "money", "cubism"],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmaCj825dF5Fqc1u1TvzeHu4QqfCpkBk8bgbLF4aQkU9HS",
            				"mimeType": "image/png"
            			}],
            			"creators": ["tz1fMia93yL7vndY2fZ5rGAQPgex7RQHXV1m"],
            			"decimals": "0",
            			"artifactUri": "ipfs://QmaCj825dF5Fqc1u1TvzeHu4QqfCpkBk8bgbLF4aQkU9HS",
            			"description": "OWL is a sign of luck",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"from": {
            		"address": "tz1NdS3X9adSTkn2HMEuGa4b1oHHrnHTMBCn"
            	},
            	"to": {
            		"alias": "Burn Address \uD83D\uDD25",
            		"address": "tz1burnburnburnburnburnburnburjAYjjX"
            	},
            	"amount": "1",
            	"transactionId": 42854230
            }, {
            	"id": 42872839,
            	"level": 1380336,
            	"timestamp": "2021-03-11T17:02:45Z",
            	"token": {
            		"id": 6913,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "4248",
            		"standard": "fa2",
            		"metadata": {
            			"name": "What is even real anymore?",
            			"tags": ["million", "dollar", "tweet"],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmW7JP7V7YkXkppohRP64uJrEDvzLtc6DrfDZHyLp9nkay",
            				"mimeType": "image/jpeg"
            			}],
            			"creators": ["tz1XX6QTcq9WkUAjC11UujjsoRoWqgdJU9LR"],
            			"decimals": "0",
            			"artifactUri": "ipfs://QmW7JP7V7YkXkppohRP64uJrEDvzLtc6DrfDZHyLp9nkay",
            			"description": "https://twitter.com/der_flow_/status/1368916996910424066 - The Million Dollar Tweet",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"from": {
            		"alias": "der_flow",
            		"address": "tz1XX6QTcq9WkUAjC11UujjsoRoWqgdJU9LR"
            	},
            	"to": {
            		"alias": "Burn Address \uD83D\uDD25",
            		"address": "tz1burnburnburnburnburnburnburjAYjjX"
            	},
            	"amount": "1",
            	"transactionId": 42872834
            }, {
            	"id": 42872935,
            	"level": 1380338,
            	"timestamp": "2021-03-11T17:04:45Z",
            	"token": {
            		"id": 6909,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "4244",
            		"standard": "fa2",
            		"metadata": {
            			"name": "Pickover attractor",
            			"tags": ["attractor", "AttractArt", "mathart"],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmZaWQeFSq8C7x5DbPAs5BKh6zTiuoscNdx8z88xPZxHCW",
            				"mimeType": "image/png"
            			}],
            			"creators": ["tz1VLDF7y9LWSseHmxixCq31QhHH1TFRffLd"],
            			"decimals": "0",
            			"artifactUri": "ipfs://QmZaWQeFSq8C7x5DbPAs5BKh6zTiuoscNdx8z88xPZxHCW",
            			"description": "AttractArt project. Manually adjusted and colorized Pickover attractor.",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"from": {
            		"address": "tz1VLDF7y9LWSseHmxixCq31QhHH1TFRffLd"
            	},
            	"to": {
            		"alias": "Burn Address \uD83D\uDD25",
            		"address": "tz1burnburnburnburnburnburnburjAYjjX"
            	},
            	"amount": "1",
            	"transactionId": 42872927
            }, {
            	"id": 42872995,
            	"level": 1380339,
            	"timestamp": "2021-03-11T17:05:45Z",
            	"token": {
            		"id": 4273,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "1682",
            		"standard": "fa2",
            		"metadata": {
            			"name": "Light clock",
            			"tags": [],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmSxERwoYfT9GvcyviPjoKdimfh9Ssjj7vkDXFRBP19gYS",
            				"mimeType": "video/quicktime"
            			}],
            			"creators": ["tz1Tk1MJDZ96bK85RLAqtKfnptZZyXsJ2jaq"],
            			"decimals": "0",
            			"artifactUri": "ipfs://QmSxERwoYfT9GvcyviPjoKdimfh9Ssjj7vkDXFRBP19gYS",
            			"description": "Seamless abstract animation (1280x1280 60fps)",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"from": {
            		"alias": "exsstas",
            		"address": "tz1Tk1MJDZ96bK85RLAqtKfnptZZyXsJ2jaq"
            	},
            	"to": {
            		"alias": "Burn Address \uD83D\uDD25",
            		"address": "tz1burnburnburnburnburnburnburjAYjjX"
            	},
            	"amount": "1",
            	"transactionId": 42872990
            }, {
            	"id": 42873536,
            	"level": 1380351,
            	"timestamp": "2021-03-11T17:18:25Z",
            	"token": {
            		"id": 5514,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "2881",
            		"standard": "fa2",
            		"metadata": {
            			"name": "jpiringer",
            			"tags": ["jpiringer", "joergpiringer", "jörgpiringer", "piringer"],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmYkLFn8nWYWLCtQ922nSWARnDraao6nW4WvJMvKuHmKYG",
            				"mimeType": "video/quicktime"
            			}],
            			"creators": ["tz2BBUUpcf3NGoZSBZp1yhutdcuwren6fiWt"],
            			"decimals": "0",
            			"artifactUri": "ipfs://QmYkLFn8nWYWLCtQ922nSWARnDraao6nW4WvJMvKuHmKYG",
            			"description": "it's me who made the things in this collection",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"from": {
            		"alias": "jörg piringer",
            		"address": "tz2BBUUpcf3NGoZSBZp1yhutdcuwren6fiWt"
            	},
            	"to": {
            		"alias": "Burn Address \uD83D\uDD25",
            		"address": "tz1burnburnburnburnburnburnburjAYjjX"
            	},
            	"amount": "1",
            	"transactionId": 42873521
            }, {
            	"id": 42878008,
            	"level": 1380363,
            	"timestamp": "2021-03-11T17:31:45Z",
            	"token": {
            		"id": 6912,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "4247",
            		"standard": "fa2",
            		"metadata": {
            			"name": "What is even real anymore?",
            			"tags": ["million", "dollar", "tweet", ""],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmW7JP7V7YkXkppohRP64uJrEDvzLtc6DrfDZHyLp9nkay",
            				"mimeType": "image/jpeg"
            			}],
            			"creators": ["tz1XX6QTcq9WkUAjC11UujjsoRoWqgdJU9LR"],
            			"decimals": "0",
            			"artifactUri": "ipfs://QmW7JP7V7YkXkppohRP64uJrEDvzLtc6DrfDZHyLp9nkay",
            			"description": "https://twitter.com/der_flow_/status/1368916996910424066 - The million dollar tweet",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"from": {
            		"alias": "der_flow",
            		"address": "tz1XX6QTcq9WkUAjC11UujjsoRoWqgdJU9LR"
            	},
            	"to": {
            		"alias": "Burn Address \uD83D\uDD25",
            		"address": "tz1burnburnburnburnburnburnburjAYjjX"
            	},
            	"amount": "1",
            	"transactionId": 42878000
            }, {
            	"id": 42910747,
            	"level": 1380480,
            	"timestamp": "2021-03-11T19:30:10Z",
            	"token": {
            		"id": 316,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "170",
            		"standard": "fa2",
            		"metadata": {
            			"name": "The Kiss No. 1",
            			"tags": [],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://Qme7mCgT9X1RFrvN8xh1eT8JvSpbUM3jx4M1snjKxvS9QY",
            				"mimeType": "image/png"
            			}],
            			"creators": ["tz2G5SBeAJVbiE1icji3P59Lx8EvcwMLEHp3"],
            			"decimals": "0",
            			"artifactUri": "ipfs://Qme7mCgT9X1RFrvN8xh1eT8JvSpbUM3jx4M1snjKxvS9QY",
            			"description": "Image generated by GAN trained on Philippine cinema sex scenes ",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"from": {
            		"alias": "sintang_ligalig",
            		"address": "tz2G5SBeAJVbiE1icji3P59Lx8EvcwMLEHp3"
            	},
            	"to": {
            		"alias": "Burn Address \uD83D\uDD25",
            		"address": "tz1burnburnburnburnburnburnburjAYjjX"
            	},
            	"amount": "5",
            	"transactionId": 42910736
            }]
        """.trimIndent())

        var activities = activityClient.activities(10,null,null,true,ActivityType.BURN)
        assertThat(activities).hasSize(10)
        assertThat(request().path).isEqualTo("/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&limit=10&timestamp.asc=id&to.in=tz1burnburnburnburnburnburnburjAYjjX,tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU")
        activities.forEach{
            assertThat(it.to?.address).isIn("tz1burnburnburnburnburnburnburjAYjjX", "tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU")
            assertThat(it.type).isEqualTo(ActivityType.BURN)
        }
    }

    @Test
    fun `should return TRANSFER only NFT activities`() = runBlocking<Unit> {
        mock("""
            [{
            	"id": 35715967,
            	"level": 1218188,
            	"timestamp": "2020-11-16T16:32:10Z",
            	"token": {
            		"id": 13,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"from": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"to": {
            		"address": "KT1Wk94dW9z6Do1fw8AqJVjAx5Z5NYKwygTT"
            	},
            	"amount": "1",
            	"transactionId": 35715962
            }, {
            	"id": 35716562,
            	"level": 1218203,
            	"timestamp": "2020-11-16T16:47:10Z",
            	"token": {
            		"id": 13,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"from": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"to": {
            		"address": "KT1SRTR7jVzrBgwzbvbhp7SQ2WFomfqgsYAP"
            	},
            	"amount": "1",
            	"transactionId": 35716559
            }, {
            	"id": 35716594,
            	"level": 1218204,
            	"timestamp": "2020-11-16T16:48:10Z",
            	"token": {
            		"id": 13,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"from": {
            		"address": "KT1SRTR7jVzrBgwzbvbhp7SQ2WFomfqgsYAP"
            	},
            	"to": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"amount": "1",
            	"transactionId": 35716592
            }, {
            	"id": 35717001,
            	"level": 1218219,
            	"timestamp": "2020-11-16T17:03:10Z",
            	"token": {
            		"id": 13,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"from": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"to": {
            		"address": "KT1SRTR7jVzrBgwzbvbhp7SQ2WFomfqgsYAP"
            	},
            	"amount": "1",
            	"transactionId": 35716996
            }, {
            	"id": 35717029,
            	"level": 1218220,
            	"timestamp": "2020-11-16T17:04:10Z",
            	"token": {
            		"id": 13,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"from": {
            		"address": "KT1SRTR7jVzrBgwzbvbhp7SQ2WFomfqgsYAP"
            	},
            	"to": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"amount": "1",
            	"transactionId": 35717026
            }, {
            	"id": 35717135,
            	"level": 1218222,
            	"timestamp": "2020-11-16T17:06:10Z",
            	"token": {
            		"id": 13,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"from": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"to": {
            		"address": "KT1SRTR7jVzrBgwzbvbhp7SQ2WFomfqgsYAP"
            	},
            	"amount": "5",
            	"transactionId": 35717130
            }, {
            	"id": 35726363,
            	"level": 1218467,
            	"timestamp": "2020-11-16T21:15:10Z",
            	"token": {
            		"id": 13,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"from": {
            		"address": "KT1SRTR7jVzrBgwzbvbhp7SQ2WFomfqgsYAP"
            	},
            	"to": {
            		"address": "tz1MGG4inpLtsuWe2WtK1G89bpeSM36t2YP2"
            	},
            	"amount": "1",
            	"transactionId": 35726358
            }, {
            	"id": 35727755,
            	"level": 1218519,
            	"timestamp": "2020-11-16T22:09:10Z",
            	"token": {
            		"id": 13,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"from": {
            		"address": "KT1SRTR7jVzrBgwzbvbhp7SQ2WFomfqgsYAP"
            	},
            	"to": {
            		"alias": "Tezos Hunter",
            		"address": "tz1UXAsfFeufr6p2PafNr2oXNrTDSdtwwdgm"
            	},
            	"amount": "1",
            	"transactionId": 35727753
            }, {
            	"id": 35728183,
            	"level": 1218535,
            	"timestamp": "2020-11-16T22:25:50Z",
            	"token": {
            		"id": 13,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"from": {
            		"address": "KT1SRTR7jVzrBgwzbvbhp7SQ2WFomfqgsYAP"
            	},
            	"to": {
            		"address": "tz1drEV5soQL85qJMNts3JfkgZxD1oL88gsh"
            	},
            	"amount": "1",
            	"transactionId": 35728182
            }, {
            	"id": 35729136,
            	"level": 1218570,
            	"timestamp": "2020-11-16T23:00:50Z",
            	"token": {
            		"id": 13,
            		"contract": {
            			"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            		},
            		"tokenId": "1",
            		"standard": "fa2"
            	},
            	"from": {
            		"address": "KT1SRTR7jVzrBgwzbvbhp7SQ2WFomfqgsYAP"
            	},
            	"to": {
            		"address": "tz1dnbCkPtdCvSUTXE6MLZxzLTXTovsHdqH4"
            	},
            	"amount": "1",
            	"transactionId": 35729133
            }]
        """.trimIndent())

        var activities = activityClient.activities(10,null,null,true,ActivityType.TRANSFER)
        assertThat(activities).hasSize(10)
        assertThat(request().path).isEqualTo("/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&limit=10&timestamp.asc=id&from.null=false&to.ni=tz1burnburnburnburnburnburnburjAYjjX,tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU")
        activities.forEach{
            assertThat(it.from).isNotNull
            assertThat(it.to).isNotNull
            assertThat(it.to?.address).isNotIn("tz1burnburnburnburnburnburnburjAYjjX", "tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU")
            assertThat(it.type).isEqualTo(ActivityType.TRANSFER)
        }
    }

    @Test
    fun `should check continuation`() = runBlocking<Unit> {
        mock("""
            [{
            	"id": 24209728,
            	"level": 901489,
            	"timestamp": "2020-04-09T06:47:20Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"to": {
            		"address": "tz1fNwSagKoNLDM7YFDDRCKg6CosghKtLFp8"
            	},
            	"amount": "1",
            	"transactionId": 24209723
            }]
        """.trimIndent())
        mock("""
            [{
            	"id": 124209728,
            	"level": 901489,
            	"timestamp": "2020-04-19T06:47:20Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"to": {
            		"address": "tz1fNwSagKoNLDM7YFDDRCKg6CosghKtLFp8"
            	},
            	"amount": "1",
            	"transactionId": 24209723
            }]
        """.trimIndent())

        var activities = activityClient.activities(2, TzktActivityContinuation(OffsetDateTime.now(), Long.MAX_VALUE).toString(), false, listOf(ActivityType.MINT))
        assertThat(TzktActivityContinuation.isValid(activities.continuation!!)).isTrue
        assertThat(activities.items).hasSize(2)
    }
}
