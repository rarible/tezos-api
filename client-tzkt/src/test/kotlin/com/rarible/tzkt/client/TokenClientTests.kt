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
            	"id": 11,
            	"contract": {
            		"address": "KT1FaKvzjgVGZtiA7yyx97txY8J5cE5qpjQ1"
            	},
            	"tokenId": "1",
            	"standard": "fa2",
            	"firstLevel": 1212043,
            	"firstTime": "2020-11-12T06:41:22Z",
            	"lastLevel": 1212043,
            	"lastTime": "2020-11-12T06:41:22Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "5000000",
            	"totalBurned": "0",
            	"totalSupply": "5000000"
            }, {
            	"id": 12,
            	"contract": {
            		"address": "KT1Ex8LrDbCrZuTgmWin8eEo7HFw74jAqTvz"
            	},
            	"tokenId": "1",
            	"standard": "fa2",
            	"firstLevel": 1212700,
            	"firstTime": "2020-11-12T18:10:46Z",
            	"lastLevel": 1321635,
            	"lastTime": "2021-01-29T02:17:40Z",
            	"transfersCount": 4,
            	"balancesCount": 3,
            	"holdersCount": 3,
            	"totalMinted": "22000000",
            	"totalBurned": "0",
            	"totalSupply": "22000000"
            }, {
            	"id": 13,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "1",
            	"standard": "fa2",
            	"firstLevel": 1218174,
            	"firstTime": "2020-11-16T16:18:10Z",
            	"lastLevel": 1218665,
            	"lastTime": "2020-11-17T00:39:10Z",
            	"transfersCount": 14,
            	"balancesCount": 9,
            	"holdersCount": 8,
            	"totalMinted": "10",
            	"totalBurned": "0",
            	"totalSupply": "10"
            }, {
            	"id": 14,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "2",
            	"standard": "fa2",
            	"firstLevel": 1219058,
            	"firstTime": "2020-11-17T07:18:50Z",
            	"lastLevel": 1219085,
            	"lastTime": "2020-11-17T07:45:50Z",
            	"transfersCount": 2,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "10",
            	"totalBurned": "0",
            	"totalSupply": "10"
            }, {
            	"id": 15,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "3",
            	"standard": "fa2",
            	"firstLevel": 1219062,
            	"firstTime": "2020-11-17T07:22:50Z",
            	"lastLevel": 1219062,
            	"lastTime": "2020-11-17T07:22:50Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 16,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "4",
            	"standard": "fa2",
            	"firstLevel": 1219077,
            	"firstTime": "2020-11-17T07:37:50Z",
            	"lastLevel": 1219086,
            	"lastTime": "2020-11-17T07:46:50Z",
            	"transfersCount": 3,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 18,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "5",
            	"standard": "fa2",
            	"firstLevel": 1219151,
            	"firstTime": "2020-11-17T08:53:50Z",
            	"lastLevel": 1219151,
            	"lastTime": "2020-11-17T08:53:50Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "5",
            	"totalBurned": "0",
            	"totalSupply": "5"
            }, {
            	"id": 19,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "6",
            	"standard": "fa2",
            	"firstLevel": 1219218,
            	"firstTime": "2020-11-17T10:01:30Z",
            	"lastLevel": 1219218,
            	"lastTime": "2020-11-17T10:01:30Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "5",
            	"totalBurned": "0",
            	"totalSupply": "5"
            }, {
            	"id": 20,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "7",
            	"standard": "fa2",
            	"firstLevel": 1222149,
            	"firstTime": "2020-11-19T11:56:50Z",
            	"lastLevel": 1222149,
            	"lastTime": "2020-11-19T11:56:50Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 21,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "8",
            	"standard": "fa2",
            	"firstLevel": 1225935,
            	"firstTime": "2020-11-22T04:49:24Z",
            	"lastLevel": 1225935,
            	"lastTime": "2020-11-22T04:49:24Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "73000000",
            	"totalBurned": "0",
            	"totalSupply": "73000000"
            }]
        """.trimIndent())

        mock("""
            [{
            	"id": 22,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "9",
            	"standard": "fa2",
            	"firstLevel": 1225970,
            	"firstTime": "2020-11-22T05:26:24Z",
            	"lastLevel": 1225970,
            	"lastTime": "2020-11-22T05:26:24Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1000000",
            	"totalBurned": "0",
            	"totalSupply": "1000000"
            }, {
            	"id": 23,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "10",
            	"standard": "fa2",
            	"firstLevel": 1226003,
            	"firstTime": "2020-11-22T06:00:44Z",
            	"lastLevel": 1226003,
            	"lastTime": "2020-11-22T06:00:44Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1000000",
            	"totalBurned": "0",
            	"totalSupply": "1000000"
            }, {
            	"id": 24,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "11",
            	"standard": "fa2",
            	"firstLevel": 1237504,
            	"firstTime": "2020-11-30T07:37:32Z",
            	"lastLevel": 1237504,
            	"lastTime": "2020-11-30T07:37:32Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 25,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "12",
            	"standard": "fa2",
            	"firstLevel": 1237516,
            	"firstTime": "2020-11-30T07:49:32Z",
            	"lastLevel": 1237516,
            	"lastTime": "2020-11-30T07:49:32Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 26,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "13",
            	"standard": "fa2",
            	"firstLevel": 1238169,
            	"firstTime": "2020-11-30T18:47:20Z",
            	"lastLevel": 1238821,
            	"lastTime": "2020-12-01T05:44:05Z",
            	"transfersCount": 5,
            	"balancesCount": 4,
            	"holdersCount": 3,
            	"totalMinted": "73000000",
            	"totalBurned": "0",
            	"totalSupply": "73000000"
            }, {
            	"id": 27,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "14",
            	"standard": "fa2",
            	"firstLevel": 1238743,
            	"firstTime": "2020-12-01T04:25:25Z",
            	"lastLevel": 1238743,
            	"lastTime": "2020-12-01T04:25:25Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 28,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "15",
            	"standard": "fa2",
            	"firstLevel": 1243119,
            	"firstTime": "2020-12-04T05:46:07Z",
            	"lastLevel": 1243119,
            	"lastTime": "2020-12-04T05:46:07Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 29,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "16",
            	"standard": "fa2",
            	"firstLevel": 1243271,
            	"firstTime": "2020-12-04T08:18:47Z",
            	"lastLevel": 1243271,
            	"lastTime": "2020-12-04T08:18:47Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 30,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "17",
            	"standard": "fa2",
            	"firstLevel": 1243295,
            	"firstTime": "2020-12-04T08:42:47Z",
            	"lastLevel": 1243295,
            	"lastTime": "2020-12-04T08:42:47Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 31,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "18",
            	"standard": "fa2",
            	"firstLevel": 1243303,
            	"firstTime": "2020-12-04T08:50:47Z",
            	"lastLevel": 1243303,
            	"lastTime": "2020-12-04T08:50:47Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }]
        """.trimIndent())

        val size = 10
        var continuation = 0L
        var tokens = tokenClient.tokens(size, continuation)
        assertThat(request().path).isEqualTo("/v1/tokens?token.standard=fa2&limit=10&offset.cr=0&sort.asc=id")
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
        assertThat(request().path).isEqualTo("/v1/tokens?token.standard=fa2&limit=10&offset.cr=21&sort.asc=id")
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
            	"id": 45,
            	"contract": {
            		"address": "KT197cMAmydiH3QH7Xjqqrf8PgX7Xq5FyDat"
            	},
            	"tokenId": "1",
            	"standard": "fa2",
            	"firstLevel": 1292852,
            	"firstTime": "2021-01-08T06:03:42Z",
            	"lastLevel": 1292852,
            	"lastTime": "2021-01-08T06:03:42Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "2",
            	"totalBurned": "0",
            	"totalSupply": "2"
            }, {
            	"id": 40,
            	"contract": {
            		"address": "KT1KTPdVqmnU1F6v2Q14nmKqyd37t4zsWFUa"
            	},
            	"tokenId": "1",
            	"standard": "fa2",
            	"firstLevel": 1262533,
            	"firstTime": "2020-12-17T19:14:50Z",
            	"lastLevel": 1262541,
            	"lastTime": "2020-12-17T19:22:50Z",
            	"transfersCount": 2,
            	"balancesCount": 2,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 39,
            	"contract": {
            		"address": "KT1KTPdVqmnU1F6v2Q14nmKqyd37t4zsWFUa"
            	},
            	"tokenId": "0",
            	"standard": "fa2",
            	"firstLevel": 1261053,
            	"firstTime": "2020-12-16T18:26:40Z",
            	"lastLevel": 1261241,
            	"lastTime": "2020-12-16T21:35:20Z",
            	"transfersCount": 2,
            	"balancesCount": 2,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 38,
            	"contract": {
            		"address": "KT19949GQgb9e11oXGrC2iMjABnUzRRP6UfZ"
            	},
            	"tokenId": "0",
            	"standard": "fa2",
            	"firstLevel": 1259709,
            	"firstTime": "2020-12-15T19:48:32Z",
            	"lastLevel": 1259709,
            	"lastTime": "2020-12-15T19:48:32Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 37,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "21",
            	"standard": "fa2",
            	"firstLevel": 1258796,
            	"firstTime": "2020-12-15T04:30:04Z",
            	"lastLevel": 1259278,
            	"lastTime": "2020-12-15T12:34:52Z",
            	"transfersCount": 2,
            	"balancesCount": 2,
            	"holdersCount": 2,
            	"totalMinted": "10",
            	"totalBurned": "0",
            	"totalSupply": "10"
            }, {
            	"id": 35,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "20",
            	"standard": "fa2",
            	"firstLevel": 1255382,
            	"firstTime": "2020-12-12T19:10:33Z",
            	"lastLevel": 1255382,
            	"lastTime": "2020-12-12T19:10:33Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 34,
            	"contract": {
            		"address": "KT1Gx5FUi9yxjhivFEYaYd2QyWhTQnXPcwCv"
            	},
            	"tokenId": "0",
            	"standard": "fa2",
            	"firstLevel": 1249682,
            	"firstTime": "2020-12-08T19:43:28Z",
            	"lastLevel": 1270427,
            	"lastTime": "2020-12-23T07:38:01Z",
            	"transfersCount": 13,
            	"balancesCount": 13,
            	"holdersCount": 13,
            	"totalMinted": "5000",
            	"totalBurned": "0",
            	"totalSupply": "5000"
            }, {
            	"id": 33,
            	"contract": {
            		"address": "KT1EhTDnyFoRs3J6giL9gWJJEsPKY6uJ6asZ"
            	},
            	"tokenId": "0",
            	"standard": "fa2",
            	"firstLevel": 1249673,
            	"firstTime": "2020-12-08T19:34:28Z",
            	"lastLevel": 1249673,
            	"lastTime": "2020-12-08T19:34:28Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "5000",
            	"totalBurned": "0",
            	"totalSupply": "5000"
            }, {
            	"id": 32,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "19",
            	"standard": "fa2",
            	"firstLevel": 1243320,
            	"firstTime": "2020-12-04T09:07:47Z",
            	"lastLevel": 1243320,
            	"lastTime": "2020-12-04T09:07:47Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 31,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "18",
            	"standard": "fa2",
            	"firstLevel": 1243303,
            	"firstTime": "2020-12-04T08:50:47Z",
            	"lastLevel": 1243303,
            	"lastTime": "2020-12-04T08:50:47Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }]
        """.trimIndent())

        mock("""
            [{
            	"id": 30,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "17",
            	"standard": "fa2",
            	"firstLevel": 1243295,
            	"firstTime": "2020-12-04T08:42:47Z",
            	"lastLevel": 1243295,
            	"lastTime": "2020-12-04T08:42:47Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 29,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "16",
            	"standard": "fa2",
            	"firstLevel": 1243271,
            	"firstTime": "2020-12-04T08:18:47Z",
            	"lastLevel": 1243271,
            	"lastTime": "2020-12-04T08:18:47Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 28,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "15",
            	"standard": "fa2",
            	"firstLevel": 1243119,
            	"firstTime": "2020-12-04T05:46:07Z",
            	"lastLevel": 1243119,
            	"lastTime": "2020-12-04T05:46:07Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 27,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "14",
            	"standard": "fa2",
            	"firstLevel": 1238743,
            	"firstTime": "2020-12-01T04:25:25Z",
            	"lastLevel": 1238743,
            	"lastTime": "2020-12-01T04:25:25Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 26,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "13",
            	"standard": "fa2",
            	"firstLevel": 1238169,
            	"firstTime": "2020-11-30T18:47:20Z",
            	"lastLevel": 1238821,
            	"lastTime": "2020-12-01T05:44:05Z",
            	"transfersCount": 5,
            	"balancesCount": 4,
            	"holdersCount": 3,
            	"totalMinted": "73000000",
            	"totalBurned": "0",
            	"totalSupply": "73000000"
            }, {
            	"id": 25,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "12",
            	"standard": "fa2",
            	"firstLevel": 1237516,
            	"firstTime": "2020-11-30T07:49:32Z",
            	"lastLevel": 1237516,
            	"lastTime": "2020-11-30T07:49:32Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 24,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "11",
            	"standard": "fa2",
            	"firstLevel": 1237504,
            	"firstTime": "2020-11-30T07:37:32Z",
            	"lastLevel": 1237504,
            	"lastTime": "2020-11-30T07:37:32Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1",
            	"totalBurned": "0",
            	"totalSupply": "1"
            }, {
            	"id": 23,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "10",
            	"standard": "fa2",
            	"firstLevel": 1226003,
            	"firstTime": "2020-11-22T06:00:44Z",
            	"lastLevel": 1226003,
            	"lastTime": "2020-11-22T06:00:44Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1000000",
            	"totalBurned": "0",
            	"totalSupply": "1000000"
            }, {
            	"id": 22,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "9",
            	"standard": "fa2",
            	"firstLevel": 1225970,
            	"firstTime": "2020-11-22T05:26:24Z",
            	"lastLevel": 1225970,
            	"lastTime": "2020-11-22T05:26:24Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "1000000",
            	"totalBurned": "0",
            	"totalSupply": "1000000"
            }, {
            	"id": 21,
            	"contract": {
            		"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9"
            	},
            	"tokenId": "8",
            	"standard": "fa2",
            	"firstLevel": 1225935,
            	"firstTime": "2020-11-22T04:49:24Z",
            	"lastLevel": 1225935,
            	"lastTime": "2020-11-22T04:49:24Z",
            	"transfersCount": 1,
            	"balancesCount": 1,
            	"holdersCount": 1,
            	"totalMinted": "73000000",
            	"totalBurned": "0",
            	"totalSupply": "73000000"
            }]
        """.trimIndent())

        val size = 10
        var continuation = 46L
        var tokens = tokenClient.tokens(size, continuation, false)
        assertThat(request().path).isEqualTo("/v1/tokens?token.standard=fa2&limit=10&offset.cr=46&sort.desc=id")
        var prevId = 46L
        tokens.forEach{
            assertThat(it.id?.toLong()).isLessThan(prevId)
            assertThat(it.standard).isEqualTo("fa2")
            prevId = it.id!!.toLong()
        }
        val lastId = tokens.last().id!!.toLong()
        continuation = lastId
        prevId = lastId
        tokens = tokenClient.tokens(size, continuation, false)
        assertThat(request().path).isEqualTo("/v1/tokens?token.standard=fa2&limit=10&offset.cr=31&sort.desc=id")
        tokens.forEach{
            assertThat(it.id?.toLong()).isLessThan(prevId)
            assertThat(it.standard).isEqualTo("fa2")
            prevId = it.id!!.toLong()
        }
        assertThat(tokens.first().id?.toLong()).isLessThan(lastId)
    }
}
