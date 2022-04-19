package com.rarible.tzkt.client

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
        val ownership = ownershipClient.ownershipById(contract, tokenId, owner)
        assertThat(request().path).isEqualTo("/v1/tokens/balances?account=tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88&token.contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&token.tokenId=631268")
        assertThat(ownership.account?.address).isEqualTo(owner)
        assertThat(ownership.token?.tokenId).isEqualTo(tokenId)
        assertThat(ownership.token?.contract?.address).isEqualTo(contract)
        assertThat(ownership.balance).isEqualTo(balance)
    }

    @Test
    fun `should return ownerships by ownership token`() = runBlocking<Unit> {
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
            }, {
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
            }, {
            	"id": 7206553,
            	"account": {
            		"address": "tz2QH8sqmgnFajFb5vN6b9KaDmd4ht2yGv6d"
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
            	"balance": "2",
            	"transfersCount": 2,
            	"firstLevel": 2044600,
            	"firstTime": "2022-01-19T15:43:30Z",
            	"lastLevel": 2044788,
            	"lastTime": "2022-01-19T17:17:30Z"
            }]
        """.trimIndent())

        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "631268"
        val ownerships = ownershipClient.ownershipsByToken(contract, tokenId)
        assertThat(request().path).isEqualTo("/v1/tokens/balances?token.contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&token.tokenId=631268")
        ownerships.forEach {
            assertThat(it.token?.tokenId).isEqualTo(tokenId)
            assertThat(it.token?.contract?.address).isEqualTo(contract)
        }
    }

}
