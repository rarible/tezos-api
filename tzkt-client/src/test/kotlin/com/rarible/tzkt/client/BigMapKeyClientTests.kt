package com.rarible.tzkt.client

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BigMapKeyClientTests : BaseClientTests() {

    val bigMapKeyClient = BigMapKeyClient(client)

    @Test
    fun `should return big map key value by ownership big map and key id`() = runBlocking<Unit> {
        mock("""
            {
            	"id": 8933566,
            	"active": true,
            	"hash": "expruNDUh3AyKzrsoNyh7AtFxsM9TPG3jMYAZz9WsEetyMEa1XZiJ2",
            	"key": "53057",
            	"value": {
            		"owner": "tz1gLjae6ukdis29SLf7vgjT9QefVJVg7DdQ",
            		"price": "2500000",
            		"extras": {
            			"name": "S.P. Basic",
            			"symbol": "Panda",
            			"category": "art",
            			"keywords": "",
            			"creator_name": "sleepingpanda",
            			"collection_name": "Sleeping Panda"
            		},
            		"creator": "tz1gSCfWiPL8e6us331gtHCvJr9Cuf3jX8g6",
            		"on_sale": false,
            		"decimals": "0",
            		"editions": "1",
            		"token_id": "53057",
            		"ipfs_hash": "ipfs://Qmchk6TY5V2UctDA3gNfqrGPsPQ4PoiTEGKD7EJ6pAH7RU",
            		"on_auction": false,
            		"collection_id": "0",
            		"edition_number": "1",
            		"creator_royalty": "10"
            	},
            	"firstLevel": 1831090,
            	"lastLevel": 2084297,
            	"updates": 6
            }
        """.trimIndent())

        var bigMapId = "861"
        var keyId = "53057"
        val data = bigMapKeyClient.bigMapKeyWithId(bigMapId, keyId)
        assertThat(request().path).isEqualTo("/v1/bigmaps/$bigMapId/keys/$keyId")
        assertThat(data.id).isEqualTo(8933566)
        assertThat(data.active).isEqualTo(true)
        assertThat(data.key).isEqualTo(keyId)
    }

    @Test
    fun `should return big map key value by contract big map name and key id`() = runBlocking<Unit> {
        mock("""
            {
            	"id": 15543292,
            	"active": true,
            	"hash": "exprtZBwZUeYYYfUs9B9Rg2ywHezVHnCCnmF9WsDQVrs582dSK63dC",
            	"key": "0",
            	"value": {
            		"token_id": "0",
            		"token_info": {
            			"": "697066733a2f2f516d58654b3661595555487164655152396e36437952727464466b546d5966746b504847426d74794762554a314d"
            		}
            	},
            	"firstLevel": 2027201,
            	"lastLevel": 2027201,
            	"updates": 1
            }
        """.trimIndent())

        var bigMapName = "token_metadata"
        var keyId = "0"
        val contract = "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
        val data = bigMapKeyClient.bigMapKeyWithName(contract, bigMapName, keyId)
        assertThat(request().path).isEqualTo("/v1/contracts/$contract/bigmaps/$bigMapName/keys/$keyId")
        assertThat(data.id).isEqualTo(15543292)
        assertThat(data.active).isEqualTo(true)
        assertThat(data.key).isEqualTo(keyId)
    }

}
