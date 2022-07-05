package com.rarible.tzkt.client

import com.rarible.tzkt.model.ItemId
import com.rarible.tzkt.model.OwnershipId
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OwnershipClientTests : BaseClientTests() {

    val ownershipClient = OwnershipClient(client)

    @Test
    fun `should return ownership by ownership id`() = runBlocking<Unit> {
        mock("""
            [{
            	"id": 6923827,
            	"account": {
            		"address": "tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"
            	},
            	"token": {
            		"id": 1645905,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "631268",
            		"standard": "fa2",
            		"metadata": {
            			"name": "R-Royal #1 H=N",
            			"tags": [""],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmNMPwuzRHm4QGcYUKoQoDZnuufKvDNGtN9dZ4NiUc67Wg",
            				"mimeType": "image/png"
            			}],
            			"creators": ["tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"],
            			"decimals": "0",
            			"displayUri": "ipfs://QmWZKRPsSsEnoA1gaQ7axAX3EPqepGjLv2GCGp7bXVrWwR",
            			"artifactUri": "ipfs://QmNMPwuzRHm4QGcYUKoQoDZnuufKvDNGtN9dZ4NiUc67Wg",
            			"description": "1 H-N",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"balance": "7",
            	"transfersCount": 3,
            	"firstLevel": 2027158,
            	"firstTime": "2022-01-13T09:57:00Z",
            	"lastLevel": 2028045,
            	"lastTime": "2022-01-13T17:28:40Z"
            }]
        """.trimIndent())

        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "631268"
        var owner = "tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"
        val balance = "7"
        val ownership = ownershipClient.ownershipById(OwnershipId(contract, tokenId, owner).toString())
        assertThat(request().path).isEqualTo("/v1/tokens/balances?account=tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88&token.contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&token.tokenId=631268")
        assertThat(ownership.account?.address).isEqualTo(owner)
        assertThat(ownership.token?.tokenId).isEqualTo(tokenId)
        assertThat(ownership.token?.contract?.address).isEqualTo(contract)
        assertThat(ownership.balance).isEqualTo(balance)
    }

    @Test
    fun `should return ownerships by token`() = runBlocking<Unit> {
        mock("""
            [{
            	"id": 6923827,
            	"account": {
            		"address": "tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"
            	},
            	"token": {
            		"id": 1645905,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "631268",
            		"standard": "fa2",
            		"metadata": {
            			"name": "R-Royal #1 H=N",
            			"tags": [""],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmNMPwuzRHm4QGcYUKoQoDZnuufKvDNGtN9dZ4NiUc67Wg",
            				"mimeType": "image/png"
            			}],
            			"creators": ["tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"],
            			"decimals": "0",
            			"displayUri": "ipfs://QmWZKRPsSsEnoA1gaQ7axAX3EPqepGjLv2GCGp7bXVrWwR",
            			"artifactUri": "ipfs://QmNMPwuzRHm4QGcYUKoQoDZnuufKvDNGtN9dZ4NiUc67Wg",
            			"description": "1 H-N",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"balance": "7",
            	"transfersCount": 3,
            	"firstLevel": 2027158,
            	"firstTime": "2022-01-13T09:57:00Z",
            	"lastLevel": 2028045,
            	"lastTime": "2022-01-13T17:28:40Z"
            }, {
            	"id": 6924058,
            	"account": {
            		"address": "tz1NRh1vTn3b38m7Gg2qP81dqb5Kr2BAjwJV"
            	},
            	"token": {
            		"id": 1645905,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "631268",
            		"standard": "fa2",
            		"metadata": {
            			"name": "R-Royal #1 H=N",
            			"tags": [""],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmNMPwuzRHm4QGcYUKoQoDZnuufKvDNGtN9dZ4NiUc67Wg",
            				"mimeType": "image/png"
            			}],
            			"creators": ["tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"],
            			"decimals": "0",
            			"displayUri": "ipfs://QmWZKRPsSsEnoA1gaQ7axAX3EPqepGjLv2GCGp7bXVrWwR",
            			"artifactUri": "ipfs://QmNMPwuzRHm4QGcYUKoQoDZnuufKvDNGtN9dZ4NiUc67Wg",
            			"description": "1 H-N",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"balance": "1",
            	"transfersCount": 6,
            	"firstLevel": 2027180,
            	"firstTime": "2022-01-13T10:08:00Z",
            	"lastLevel": 2044788,
            	"lastTime": "2022-01-19T17:17:30Z"
            }]
        """.trimIndent())

        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "631268"
        val ownerships = ownershipClient.ownershipsByToken(ItemId(contract, tokenId).toString(), 2, null, true)
        assertThat(request().path).isEqualTo("/v1/tokens/balances?token.contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&token.tokenId=631268&limit=2&sort.asc=id")
        ownerships.items.forEach {
            assertThat(it.token?.tokenId).isEqualTo(tokenId)
            assertThat(it.token?.contract?.address).isEqualTo(contract)
        }
        assertThat(ownerships.continuation).isEqualTo("6924058")
    }

    @Test
    fun `should return ownerships by token with continuation`() = runBlocking<Unit> {
        mock("""
            [{
            	"id": 6941500,
            	"account": {
            		"address": "tz2NKVi7TWXxU3eFvxkzw7cv3Mybavx1XtMV"
            	},
            	"token": {
            		"id": 1645905,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "631268",
            		"standard": "fa2",
            		"metadata": {
            			"name": "R-Royal #1 H=N",
            			"tags": [""],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmNMPwuzRHm4QGcYUKoQoDZnuufKvDNGtN9dZ4NiUc67Wg",
            				"mimeType": "image/png"
            			}],
            			"creators": ["tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"],
            			"decimals": "0",
            			"displayUri": "ipfs://QmWZKRPsSsEnoA1gaQ7axAX3EPqepGjLv2GCGp7bXVrWwR",
            			"artifactUri": "ipfs://QmNMPwuzRHm4QGcYUKoQoDZnuufKvDNGtN9dZ4NiUc67Wg",
            			"description": "1 H-N",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"balance": "0",
            	"transfersCount": 2,
            	"firstLevel": 2028045,
            	"firstTime": "2022-01-13T17:28:40Z",
            	"lastLevel": 2028076,
            	"lastTime": "2022-01-13T17:45:20Z"
            }, {
            	"id": 7146690,
            	"account": {
            		"address": "tz1ciFAFNfaPY4562dHy7BbCqf4vG39kLJa1"
            	},
            	"token": {
            		"id": 1645905,
            		"contract": {
            			"alias": "hic et nunc NFTs",
            			"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            		},
            		"tokenId": "631268",
            		"standard": "fa2",
            		"metadata": {
            			"name": "R-Royal #1 H=N",
            			"tags": [""],
            			"symbol": "OBJKT",
            			"formats": [{
            				"uri": "ipfs://QmNMPwuzRHm4QGcYUKoQoDZnuufKvDNGtN9dZ4NiUc67Wg",
            				"mimeType": "image/png"
            			}],
            			"creators": ["tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"],
            			"decimals": "0",
            			"displayUri": "ipfs://QmWZKRPsSsEnoA1gaQ7axAX3EPqepGjLv2GCGp7bXVrWwR",
            			"artifactUri": "ipfs://QmNMPwuzRHm4QGcYUKoQoDZnuufKvDNGtN9dZ4NiUc67Wg",
            			"description": "1 H-N",
            			"thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
            			"isBooleanAmount": false,
            			"shouldPreferSymbol": false
            		}
            	},
            	"balance": "0",
            	"transfersCount": 4,
            	"firstLevel": 2041352,
            	"firstTime": "2022-01-18T12:13:50Z",
            	"lastLevel": 2044600,
            	"lastTime": "2022-01-19T15:43:30Z"
            }]
        """.trimIndent())

        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "631268"
        val ownerships = ownershipClient.ownershipsByToken(ItemId(contract, tokenId).toString(), 2, "6924058", true)
        assertThat(request().path).isEqualTo("/v1/tokens/balances?token.contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&token.tokenId=631268&limit=2&offset.cr=6924058&sort.asc=id")
        ownerships.items.forEach {
            assertThat(it.token?.tokenId).isEqualTo(tokenId)
            assertThat(it.token?.contract?.address).isEqualTo(contract)
        }
        assertThat(ownerships.continuation).isEqualTo("7146690")
    }

    @Test
    fun `should return all ownerships`() = runBlocking<Unit> {
        mock("""
            [
                {
                    "id": 121041,
                    "account": {
                        "address": "tz2GaDTjRHyEbpoHwtzvGWJRRHhaQbsy9nxi"
                    },
                    "token": {
                        "id": 75279,
                        "contract": {
                            "address": "KT1Pm2H31abC73TfgiJTJrKfzozobFxfFy5a"
                        },
                        "tokenId": "51",
                        "standard": "fa2",
                        "metadata": {
                            "date": "2022-07-01T09:49:07.000Z",
                            "name": "Ranunculus albusmachina",
                            "tags": [
                                "AI Herbarium"
                            ],
                            "minter": "tz1QCMomemFVu1GZtP7zRGPXwNCw3jcAQ3Dp",
                            "creators": [],
                            "decimals": "0",
                            "signature": "YRyAOxnxgQwPwun5Pit56XzKKE5Q7OarHmh1jbDL9vDv88nFHPmgM4jBlWMV",
                            "displayUri": "ipfs://QmeTQv4jLUnP4Vf6PNAEv6ZnaZASxYjYFvWd1d2x8efiZN",
                            "publishers": [
                                "Tezos"
                            ],
                            "artifactUri": "ipfs://QmeTQv4jLUnP4Vf6PNAEv6ZnaZASxYjYFvWd1d2x8efiZN",
                            "description": "Marco Violet-Vianello, Metavert AI Herbarium, 2022, Digital image, 1024x1024 pixels, edition 1 of 1.  MET.HER.011.",
                            "contributors": [],
                            "thumbnailUri": "ipfs://QmeTQv4jLUnP4Vf6PNAEv6ZnaZASxYjYFvWd1d2x8efiZN",
                            "isBooleanAmount": false
                        }
                    },
                    "balance": "1",
                    "transfersCount": 1,
                    "firstLevel": 798297,
                    "firstTime": "2022-07-05T07:29:10Z",
                    "lastLevel": 798297,
                    "lastTime": "2022-07-05T07:29:10Z"
                },
                {
                    "id": 121040,
                    "account": {
                        "address": "tz1QCMomemFVu1GZtP7zRGPXwNCw3jcAQ3Dp"
                    },
                    "token": {
                        "id": 75279,
                        "contract": {
                            "address": "KT1Pm2H31abC73TfgiJTJrKfzozobFxfFy5a"
                        },
                        "tokenId": "51",
                        "standard": "fa2",
                        "metadata": {
                            "date": "2022-07-01T09:49:07.000Z",
                            "name": "Ranunculus albusmachina",
                            "tags": [
                                "AI Herbarium"
                            ],
                            "minter": "tz1QCMomemFVu1GZtP7zRGPXwNCw3jcAQ3Dp",
                            "creators": [],
                            "decimals": "0",
                            "signature": "YRyAOxnxgQwPwun5Pit56XzKKE5Q7OarHmh1jbDL9vDv88nFHPmgM4jBlWMV",
                            "displayUri": "ipfs://QmeTQv4jLUnP4Vf6PNAEv6ZnaZASxYjYFvWd1d2x8efiZN",
                            "publishers": [
                                "Tezos"
                            ],
                            "artifactUri": "ipfs://QmeTQv4jLUnP4Vf6PNAEv6ZnaZASxYjYFvWd1d2x8efiZN",
                            "description": "Marco Violet-Vianello, Metavert AI Herbarium, 2022, Digital image, 1024x1024 pixels, edition 1 of 1.  MET.HER.011.",
                            "contributors": [],
                            "thumbnailUri": "ipfs://QmeTQv4jLUnP4Vf6PNAEv6ZnaZASxYjYFvWd1d2x8efiZN",
                            "isBooleanAmount": false
                        }
                    },
                    "balance": "9",
                    "transfersCount": 2,
                    "firstLevel": 798297,
                    "firstTime": "2022-07-05T07:29:10Z",
                    "lastLevel": 798297,
                    "lastTime": "2022-07-05T07:29:10Z"
                }
            ]
        """.trimIndent())

        val ownerships = ownershipClient.ownershipsAll(null, 2)
        assertThat(request().path).isEqualTo("/v1/tokens/balances?sort.desc=id&token.standard=fa2&limit=2")
        assertThat(ownerships.items).hasSize(2)
        assertThat(ownerships.continuation).isEqualTo("121040")
    }

    @Test
    fun `should return all ownerships with continuation`() = runBlocking<Unit> {
        mock("""
            [
                {
                    "id": 121039,
                    "account": {
                        "address": "tz2GaDTjRHyEbpoHwtzvGWJRRHhaQbsy9nxi"
                    },
                    "token": {
                        "id": 75278,
                        "contract": {
                            "address": "KT1Pm2H31abC73TfgiJTJrKfzozobFxfFy5a"
                        },
                        "tokenId": "49",
                        "standard": "fa2",
                        "metadata": {
                            "date": "2022-07-01T09:49:01.000Z",
                            "name": "Alcea striata artificiens",
                            "tags": [
                                "AI Herbarium"
                            ],
                            "minter": "tz1QCMomemFVu1GZtP7zRGPXwNCw3jcAQ3Dp",
                            "creators": [],
                            "decimals": "0",
                            "signature": "B51uf6kxJhL8aTYS9MipyCf9Lsp5RMkvTmyGF7chtDL4aDja5NpNNYa6dMve",
                            "displayUri": "ipfs://QmYEFu1AQ3pe7c8Bjn3D6Ca6mPPcVge7YQ16WTh8dcUnoz",
                            "publishers": [
                                "Tezos"
                            ],
                            "artifactUri": "ipfs://QmYEFu1AQ3pe7c8Bjn3D6Ca6mPPcVge7YQ16WTh8dcUnoz",
                            "description": "Marco Violet-Vianello, Metavert AI Herbarium, 2022, Digital image, 1024x1024 pixels, edition 1 of 1.  MET.HER.009.",
                            "contributors": [],
                            "thumbnailUri": "ipfs://QmYEFu1AQ3pe7c8Bjn3D6Ca6mPPcVge7YQ16WTh8dcUnoz",
                            "isBooleanAmount": false
                        }
                    },
                    "balance": "1",
                    "transfersCount": 1,
                    "firstLevel": 798297,
                    "firstTime": "2022-07-05T07:29:10Z",
                    "lastLevel": 798297,
                    "lastTime": "2022-07-05T07:29:10Z"
                },
                {
                    "id": 121038,
                    "account": {
                        "address": "tz1QCMomemFVu1GZtP7zRGPXwNCw3jcAQ3Dp"
                    },
                    "token": {
                        "id": 75278,
                        "contract": {
                            "address": "KT1Pm2H31abC73TfgiJTJrKfzozobFxfFy5a"
                        },
                        "tokenId": "49",
                        "standard": "fa2",
                        "metadata": {
                            "date": "2022-07-01T09:49:01.000Z",
                            "name": "Alcea striata artificiens",
                            "tags": [
                                "AI Herbarium"
                            ],
                            "minter": "tz1QCMomemFVu1GZtP7zRGPXwNCw3jcAQ3Dp",
                            "creators": [],
                            "decimals": "0",
                            "signature": "B51uf6kxJhL8aTYS9MipyCf9Lsp5RMkvTmyGF7chtDL4aDja5NpNNYa6dMve",
                            "displayUri": "ipfs://QmYEFu1AQ3pe7c8Bjn3D6Ca6mPPcVge7YQ16WTh8dcUnoz",
                            "publishers": [
                                "Tezos"
                            ],
                            "artifactUri": "ipfs://QmYEFu1AQ3pe7c8Bjn3D6Ca6mPPcVge7YQ16WTh8dcUnoz",
                            "description": "Marco Violet-Vianello, Metavert AI Herbarium, 2022, Digital image, 1024x1024 pixels, edition 1 of 1.  MET.HER.009.",
                            "contributors": [],
                            "thumbnailUri": "ipfs://QmYEFu1AQ3pe7c8Bjn3D6Ca6mPPcVge7YQ16WTh8dcUnoz",
                            "isBooleanAmount": false
                        }
                    },
                    "balance": "9",
                    "transfersCount": 2,
                    "firstLevel": 798297,
                    "firstTime": "2022-07-05T07:29:10Z",
                    "lastLevel": 798297,
                    "lastTime": "2022-07-05T07:29:10Z"
                }
            ]
        """.trimIndent())

        val ownerships = ownershipClient.ownershipsAll("121040", 2)
        assertThat(request().path).isEqualTo("/v1/tokens/balances?sort.desc=id&token.standard=fa2&limit=2&offset.cr=121040")
        assertThat(ownerships.items).hasSize(2)
        assertThat(ownerships.continuation).isEqualTo("121038")
    }

}
