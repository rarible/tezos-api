package com.rarible.tzkt.client

import com.rarible.tzkt.model.ActivityType
import com.rarible.tzkt.model.TzktActivityContinuation
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ActivityClientTests : BaseClientTests() {

    val activityClient = TokenActivityClient(client)

    @Test
    fun `should return All NFT activities with size, continuation, and sorting ASC`() = runBlocking<Unit> {
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
        mock("""
            [
                {
                    "id": 23818302,
                    "hash": "ooqmgMXibYMtdzcNZ4mfV15hyWwxY1MHjj44U8nLMvYvsAywAAx"
                },
                {
                    "id": 23820160,
                    "hash": "ooFxmRy5Gk8aj7CTG54BtZVmcaEedtzXHA5NAv59LkBV2ikmmPL"
                },
                {
                    "id": 24209723,
                    "hash": "onkrZdHaH3Duk7kVvqsQxQtRgvCeYFxbCSxj1Ri6UWqZTAGyaDw"
                },
                {
                    "id": 24276901,
                    "hash": "ooQnGgktNiVhjKtDT8zeD9H2oCY4eAwj7b8MHxP8wWjf7gHnpYw"
                }
            ]
        """.trimIndent())

        var activities = activityClient.getActivitiesAll(listOf(ActivityType.MINT, ActivityType.TRANSFER), 2, null, true)
        assertThat(TzktActivityContinuation.isValid(activities.continuation!!)).isTrue
        assertThat(activities.items).hasSize(2)
        assertThat(activities.items.first().tokenActivity.transactionHash).isNotEmpty
        assertThat(mockServer.requestCount).isEqualTo(3)
        assertThat(requests()).isEqualTo(setOf(
            "/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&limit=2&sort.asc=timestamp&from.null=false&to.null=false",
            "/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&limit=2&sort.asc=timestamp&from.null=true",
            "/v1/operations/transactions?id.in=23818302,23820160,24209723,24276901&select=id,hash")
        )
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
        mock("""
            [
                {
                    "id": 23818302,
                    "hash": "ooqmgMXibYMtdzcNZ4mfV15hyWwxY1MHjj44U8nLMvYvsAywAAx"
                },
                {
                    "id": 23820160,
                    "hash": "ooFxmRy5Gk8aj7CTG54BtZVmcaEedtzXHA5NAv59LkBV2ikmmPL"
                }
            ]
        """.trimIndent())

        var activities = activityClient.getActivitiesByIds(listOf(23818305, 23820166).map { it.toString() })

        assertThat(activities).hasSize(2)
        assertThat(activities.first().tokenActivity.transactionHash).isNotEmpty
        assertThat(request().path).isEqualTo("/v1/tokens/transfers?id.in=23818305,23820166")
    }

    @Test
    fun `should return NFT activities by item`() = runBlocking<Unit> {
        mock("""[{
                    "id": 225174251,
                    "level": 2342063,
                    "timestamp": "2022-05-06T15:00:59Z",
                    "token": {
                        "id": 2832236,
                        "contract": {
                            "address": "KT1TkjKV9mNCyYJdzjZ39XdGP6YMwB5ipYGQ"
                        },
                        "tokenId": "4021",
                        "standard": "fa2",
                        "metadata": {
                            "x": "01114f54b36e89a8ab"
                        }
                    },
                    "to": {
                        "address": "tz1WHUWCMqVqq5AagGN5DrytqWz88tYgedUr"
                    },
                    "amount": "1",
                    "transactionId": 225174019
                }]
        """.trimIndent())
        mock("""[{
                "id": 225200387,
                "level": 2342110,
                "timestamp": "2022-05-06T15:27:29Z",
                "token": {
                    "id": 2833399,
                    "contract": {
                        "address": "KT1TkjKV9mNCyYJdzjZ39XdGP6YMwB5ipYGQ"
                    },
                    "tokenId": "2256",
                    "standard": "fa2",
                    "metadata": {
                        "x": "0e0f285874ab"
                    }
                },
                "from": {
                    "address": "tz1USC7gqCPfB3UzyQxMacgBpgJSgt6NRNnF"
                },
                "to": {
                    "address": "tz1ciDXpDiX7UK5zEcqvtcUSf5SLoNV5BWob"
                },
                "amount": "1",
                "transactionId": 225200218
            }]
        """.trimIndent())
        mock("""[{
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
                        "formats": [
                            {
                                "uri": "https://ipfs.io/ipfs/Qmf1J978CQA9dDSDBZjHAxScnb4W3i26Cbmv1cvkfrvkLu",
                                "mimeType": "image/jpeg"
                            }
                        ],
                        "creators": [
                            "tz1hkNPg5jQ66pJpZH2boiF5AnbAcd1dt5KS"
                        ],
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
                    "alias": "Burn Address 🔥",
                    "address": "tz1burnburnburnburnburnburnburjAYjjX"
                },
                "amount": "1",
                "transactionId": 41904711
            }]
        """.trimIndent())
        mock("""
            [
                {
                    "id": 225174019,
                    "hash": "ooqmgMXibYMtdzcNZ4mfV15hyWwxY1MHjj44U8nLMvYvsAywAAx"
                },
                {
                    "id": 225200218,
                    "hash": "ooqmgMXibYMtdzcNZ4mfV15hyWwxY1MHjj44U8nLMvYvsAywAAx"
                },
                {
                    "id": 41904711,
                    "hash": "ooqmgMXibYMtdzcNZ4mfV15hyWwxY1MHjj44U8nLMvYvsAywAAx"
                }
            ]
        """.trimIndent())

        var activities = activityClient.getActivitiesByItem(listOf(ActivityType.MINT, ActivityType.TRANSFER, ActivityType.BURN), "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn", "0", 3, null, false)

        assertThat(activities.items).hasSize(3)
        assertThat(activities.items.first().tokenActivity.transactionHash).isNotEmpty
        assertThat(activities.continuation.toString()).isEqualTo("1614340163000_41904714")
        assertThat(mockServer.requestCount).isEqualTo(4)
        assertThat(requests()).isEqualTo(setOf(
            "/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&token.contract=KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn&token.tokenId=0&limit=3&sort.desc=timestamp&from.null=true",
            "/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&token.contract=KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn&token.tokenId=0&limit=3&sort.desc=timestamp&from.null=false&to.null=false",
            "/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&token.contract=KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn&token.tokenId=0&limit=3&sort.desc=timestamp&to.null=true",
            "/v1/operations/transactions?id.in=225200218,225174019,41904711&select=id,hash")
        )
    }

    @Test
    fun `should return NFT activities by item asc with continuation`() = runBlocking<Unit> {
        mock("""[{
                    "id": 225174251,
                    "level": 2342063,
                    "timestamp": "2022-05-06T15:00:59Z",
                    "token": {
                        "id": 2832236,
                        "contract": {
                            "address": "KT1TkjKV9mNCyYJdzjZ39XdGP6YMwB5ipYGQ"
                        },
                        "tokenId": "4021",
                        "standard": "fa2",
                        "metadata": {
                            "x": "01114f54b36e89a8ab"
                        }
                    },
                    "to": {
                        "address": "tz1WHUWCMqVqq5AagGN5DrytqWz88tYgedUr"
                    },
                    "amount": "1",
                    "transactionId": 225174019
                }]
        """.trimIndent())
        mock("""[{
                "id": 225200387,
                "level": 2342110,
                "timestamp": "2022-05-06T15:27:29Z",
                "token": {
                    "id": 2833399,
                    "contract": {
                        "address": "KT1TkjKV9mNCyYJdzjZ39XdGP6YMwB5ipYGQ"
                    },
                    "tokenId": "2256",
                    "standard": "fa2",
                    "metadata": {
                        "x": "0e0f285874ab"
                    }
                },
                "from": {
                    "address": "tz1USC7gqCPfB3UzyQxMacgBpgJSgt6NRNnF"
                },
                "to": {
                    "address": "tz1ciDXpDiX7UK5zEcqvtcUSf5SLoNV5BWob"
                },
                "amount": "1",
                "transactionId": 225200218
            }]
        """.trimIndent())
        mock("""[{
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
                        "formats": [
                            {
                                "uri": "https://ipfs.io/ipfs/Qmf1J978CQA9dDSDBZjHAxScnb4W3i26Cbmv1cvkfrvkLu",
                                "mimeType": "image/jpeg"
                            }
                        ],
                        "creators": [
                            "tz1hkNPg5jQ66pJpZH2boiF5AnbAcd1dt5KS"
                        ],
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
                    "alias": "Burn Address 🔥",
                    "address": "tz1burnburnburnburnburnburnburjAYjjX"
                },
                "amount": "1",
                "transactionId": 41904711
            }]
        """.trimIndent())
        mock("[]")
        mock("[]")
        mock("[]")
        mock("""
            [
                {
                    "id": 225174019,
                    "hash": "ooqmgMXibYMtdzcNZ4mfV15hyWwxY1MHjj44U8nLMvYvsAywAAx"
                },
                {
                    "id": 225200218,
                    "hash": "ooqmgMXibYMtdzcNZ4mfV15hyWwxY1MHjj44U8nLMvYvsAywAAx"
                },
                {
                    "id": 41904711,
                    "hash": "ooqmgMXibYMtdzcNZ4mfV15hyWwxY1MHjj44U8nLMvYvsAywAAx"
                }
            ]
        """.trimIndent())

        var activities = activityClient.getActivitiesByItem(listOf(ActivityType.MINT, ActivityType.TRANSFER, ActivityType.BURN), "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn", "0", 3, "1614340163000_41904714", true)

        assertThat(activities.items).hasSize(3)
        assertThat(activities.items.first().tokenActivity.transactionHash).isNotEmpty
        assertThat(activities.continuation.toString()).isEqualTo("1651850849000_225200387")
        assertThat(mockServer.requestCount).isEqualTo(7)
        assertThat(requests()).isEqualTo(setOf(
            "/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&token.contract=KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn&token.tokenId=0&limit=3&timestamp.gt=2021-02-26T11:49:23Z&sort.asc=timestamp&from.null=true",
            "/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&token.contract=KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn&token.tokenId=0&limit=3&timestamp.gt=2021-02-26T11:49:23Z&sort.asc=timestamp&from.null=false&to.null=false",
            "/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&token.contract=KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn&token.tokenId=0&limit=3&timestamp.eq=2021-02-26T11:49:23Z&id.gt=41904714&sort.asc=timestamp&to.null=true",
            "/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&token.contract=KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn&token.tokenId=0&limit=3&timestamp.gt=2021-02-26T11:49:23Z&sort.asc=timestamp&to.null=true",
            "/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&token.contract=KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn&token.tokenId=0&limit=3&timestamp.eq=2021-02-26T11:49:23Z&id.gt=41904714&sort.asc=timestamp&from.null=true",
            "/v1/tokens/transfers?token.standard=fa2&metadata.artifactUri.null=false&token.contract=KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn&token.tokenId=0&limit=3&timestamp.eq=2021-02-26T11:49:23Z&id.gt=41904714&sort.asc=timestamp&from.null=false&to.null=false",
            "/v1/operations/transactions?id.in=41904711,225174019,225200218&select=id,hash")
        )
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
        mock("""
            [
                {
                    "id": 24209723,
                    "hash": "ooqmgMXibYMtdzcNZ4mfV15hyWwxY1MHjj44U8nLMvYvsAywAAx"
                },
                {
                    "id": 24209723,
                    "hash": "ooqmgMXibYMtdzcNZ4mfV15hyWwxY1MHjj44U8nLMvYvsAywAAx"
                }
            ]
        """.trimIndent())


        var activities = activityClient.getActivitiesAll(listOf(ActivityType.MINT), 2, TzktActivityContinuation(OffsetDateTime.now(), Long.MAX_VALUE).toString(), false)
        assertThat(TzktActivityContinuation.isValid(activities.continuation!!)).isTrue
        assertThat(activities.items).hasSize(2)
        assertThat(activities.items.first().tokenActivity.transactionHash).isNotEmpty
    }

    // This's for testnet real indexer
    @Disabled
    @Test
    fun `should return 1000 activities`() = runBlocking<Unit> {
        val fileContent = ActivityClientTests::class.java.getResource("/testnet_transactions.txt").readText()
        val ids = fileContent.split(",").map { it.trim() }
        var activities = activityClient.getActivitiesByIds(ids)
        assertThat(activities).hasSize(1000)
        val hashes = activities.mapNotNull { it.transactionHash }
        assertThat(hashes).hasSize(879) //maybe flaky
    }
}
