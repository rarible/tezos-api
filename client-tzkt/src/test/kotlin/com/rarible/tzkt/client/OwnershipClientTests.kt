package com.rarible.tzkt.client

import kotlinx.coroutines.runBlocking
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
        val ownership = ownershipClient.ownership(contract, tokenId, owner)
        assert(ownership.account?.address == owner)
        assert(ownership.token?.tokenId == tokenId)
        assert(ownership.token?.contract?.address == contract)
        assert(ownership.balance == balance)
    }

}
