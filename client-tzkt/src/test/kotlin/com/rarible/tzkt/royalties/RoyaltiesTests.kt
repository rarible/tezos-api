package com.rarible.tzkt.royalties

import com.rarible.tzkt.client.BaseClientTests
import com.rarible.tzkt.client.BigMapKeyClient
import com.rarible.tzkt.client.IPFSClient
import com.rarible.tzkt.model.Part
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RoyaltiesTests : BaseClientTests() {

    val bigMapKeyClient = BigMapKeyClient(client)
    val ipfsClient = IPFSClient(client)

    @Test
    fun `should correctly fetch and parse HEN royalties`() = runBlocking<Unit> {
        mock("""
            {
            	"id": 23007619,
            	"active": true,
            	"hash": "expruxWM8vEiY8js5egJjQMWkq7GUEJ4BgQaXo828G3Wuy7uZbQzEM",
            	"key": "717867",
            	"value": {
            		"issuer": "tz1ZqdrwVRUs8H1Vts2pFvmR1PLikE8eBVZv",
            		"royalties": "150"
            	},
            	"firstLevel": 2279944,
            	"lastLevel": 2279944,
            	"updates": 1
            }
        """.trimIndent())

        val handler = RoyaltiesHandler(bigMapKeyClient, ipfsClient)
        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "717867"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(listOf(id))
        assertThat(parts).isEqualTo(mapOf(Pair(id, listOf(Part("tz1ZqdrwVRUs8H1Vts2pFvmR1PLikE8eBVZv", 150*10)))))
    }

}
