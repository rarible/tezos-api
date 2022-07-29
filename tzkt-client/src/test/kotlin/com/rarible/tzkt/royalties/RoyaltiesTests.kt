package com.rarible.tzkt.royalties

import com.rarible.tzkt.client.BaseClientTests
import com.rarible.tzkt.client.BigMapKeyClient
import com.rarible.tzkt.client.IPFSClient
import com.rarible.tzkt.client.OwnershipClient
import com.rarible.tzkt.config.KnownAddresses
import com.rarible.tzkt.model.Part
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient

class RoyaltiesTests : BaseClientTests() {

    val bigMapKeyClient = BigMapKeyClient(client)
    val ownershipClient = OwnershipClient(client)
    //    val ipfsWb = WebClient.create("https://ipfs.io/ipfs/")
    val ipfsClient = IPFSClient(client, mapper)
    val logger = LoggerFactory.getLogger(javaClass)

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

    val royaltiesConfig = KnownAddresses(
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

    @Test
    fun `should correctly fetch and parse HEN royalties`() = runBlocking<Unit> {
        mock(
            """
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
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "717867"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz1ZqdrwVRUs8H1Vts2pFvmR1PLikE8eBVZv", 150 * 10)))
    }

    @Test
    fun `should correctly fetch and parse KALAMINT (public collection) royalties`() = runBlocking<Unit> {
        mock(
            """
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
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1EpGgjQs73QfFJs9z7m1Mxm5MTnpC2tqse"
        var tokenId = "53057"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz1gSCfWiPL8e6us331gtHCvJr9Cuf3jX8g6", 10 * 100)))
    }

    @Test
    fun `should correctly fetch and parse KALAMINT (private collection) royalties`() = runBlocking<Unit> {
        mock(
            """
            {
                "id": 19094127,
                "active": true,
                "hash": "expruKjMm7poFyGWD3GFopQEDnGWMY7AtEk1nJ36oBzSquKCMH5yvF",
                "key": "2490",
                "value": {
                    "owner": "tz1MK28ktMVsE1a6aZ56ybB9ESMqc8TKYoPj",
                    "creator": "tz1ZLRT3xiBgGRdNrTYXr8Stz4TppT3hukRi",
                    "ipfs_hash": "ipfs://Qmb2Bk8NUmLpvfPVcDLAnaTkcUEGkky8iTXJG1CghLYZwL",
                    "creator_royalty": "7"
                },
                "firstLevel": 2125817,
                "lastLevel": 2125817,
                "updates": 1
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1EpGgjQs73QfFJs9z7m1Mxm5MTnpC2tqse"
        var tokenId = "53057"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz1ZLRT3xiBgGRdNrTYXr8Stz4TppT3hukRi", 7 * 100)))
    }

    @Test
    fun `should correctly fetch and parse 8Bidou 8x8 royalties`() = runBlocking<Unit> {
        mock(
            """
            {
            	"id": 19575683,
            	"active": true,
            	"hash": "expruDGbwWN7Jqay8SnvBhQQxvbYXtxjNnUXYwm9sExfKo42XqtubG",
            	"key": "69",
            	"value": {
            		"rgb": "989898916d91b37db3000000ce62ced925d9e947e9fd01fd6b956b979b97ffccbcffccbc000000b89286d729d7e74ae779b879679767ffccbcffccbcffccbcffccbcca67cad32bd341bf4177bb77ffccbc000000ffccbc000000ad53adc86ac859d759ffccbcffccbcffccbcffccbcffccbcaa86aaa955a917e917e6ff00ffccbcffccbcffccbcffccbc837d83a989a93af63a13eb13ffccbc423430423430ffccbc8ba68b7f7f7f00ff0039f839ffccbcffccbcffccbc6ec46e59a75989a989",
            		"creater": "tz2QhmKtUWRyArfaqfBedvVdidgKpCcckMXV",
            		"token_id": "69",
            		"token_name": "f09f928038c9aec9a8c8b6d684ca8ad5bcd3bcf09f9280",
            		"creater_name": "67757275677572756879656e61",
            		"token_description": "f09fa498e29aa1efb88f"
            	},
            	"firstLevel": 2139139,
            	"lastLevel": 2139139,
            	"updates": 1
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1MxDwChiDwd6WBVs24g1NjERUoK622ZEFp"
        var tokenId = "69"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz2QhmKtUWRyArfaqfBedvVdidgKpCcckMXV", 1000)))
    }

    @Test
    fun `should correctly fetch and parse 8Bidou 24x24 royalties`() = runBlocking<Unit> {
        mock(
            """
            {
            	"id": 19575683,
            	"active": true,
            	"hash": "expruDGbwWN7Jqay8SnvBhQQxvbYXtxjNnUXYwm9sExfKo42XqtubG",
            	"key": "69",
            	"value": {
            		"rgb": "989898916d91b37db3000000ce62ced925d9e947e9fd01fd6b956b979b97ffccbcffccbc000000b89286d729d7e74ae779b879679767ffccbcffccbcffccbcffccbcca67cad32bd341bf4177bb77ffccbc000000ffccbc000000ad53adc86ac859d759ffccbcffccbcffccbcffccbcffccbcaa86aaa955a917e917e6ff00ffccbcffccbcffccbcffccbc837d83a989a93af63a13eb13ffccbc423430423430ffccbc8ba68b7f7f7f00ff0039f839ffccbcffccbcffccbc6ec46e59a75989a989",
            		"creater": "tz2QhmKtUWRyArfaqfBedvVdidgKpCcckMXV",
            		"token_id": "69",
            		"token_name": "f09f928038c9aec9a8c8b6d684ca8ad5bcd3bcf09f9280",
            		"creater_name": "67757275677572756879656e61",
            		"token_description": "f09fa498e29aa1efb88f"
            	},
            	"firstLevel": 2139139,
            	"lastLevel": 2139139,
            	"updates": 1
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1TR1ErEQPTdtaJ7hbvKTJSa1tsGnHGZTpf"
        var tokenId = "69"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz2QhmKtUWRyArfaqfBedvVdidgKpCcckMXV", 1500)))
    }

    @Test
    fun `should correctly fetch and parse FXHASH_V1 royalties`() = runBlocking<Unit> {
        mock(
            """
            {
            	"id": 20692205,
            	"active": true,
            	"hash": "exprvKZPSRwu9V1N6fgTEUqGXHVH44GkjjDPWTxEbC8z8HsvTNPpcf",
            	"key": "522648",
            	"value": {
            		"assigned": true,
            		"issuer_id": "10525",
            		"iteration": "2",
            		"royalties": "150"
            	},
            	"firstLevel": 2186262,
            	"lastLevel": 2186468,
            	"updates": 2
            }
        """.trimIndent()
        )

        mock(
            """
            {
            	"id": 20682838,
            	"active": true,
            	"hash": "exprv4XTDjLETkCpxXHG6wAhA1Nw7gFVhqCHjqBHq1VwFyGNrDzNGr",
            	"key": "10525",
            	"value": {
            		"price": "250000",
            		"author": "tz1b8GULAVKS1oHpYLJbwuTKvUegXtRbxH82",
            		"supply": "56",
            		"balance": "37",
            		"enabled": true,
            		"metadata": "697066733a2f2f516d646e4d70453274623166707372586a65635073625a6341687663786d454b37666a7a636f6635485677357446",
            		"royalties": "150",
            		"locked_seconds": "10800",
            		"original_supply": "256",
            		"timestamp_minted": "2022-03-11T00:10:24Z"
            	},
            	"firstLevel": 2185871,
            	"lastLevel": 2234035,
            	"updates": 24
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1KEa8z6vWXDJrVqtMrAeDVzsvxat3kHaCE"
        var tokenId = "522648"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz1b8GULAVKS1oHpYLJbwuTKvUegXtRbxH82", 10 * 150)))
    }

    @Test
    fun `should correctly fetch and parse FXHASH_V2 royalties`() = runBlocking<Unit> {
        mock(
            """
              {
              	"id": 24307286,
              	"active": true,
              	"hash": "exprusuWs52aBfDXSpFNiutHZRyj45Lm39vySEiXHyb4UoVAdDbBjQ",
              	"key": "638313",
              	"value": {
              		"minter": "tz1NpUTYsVB2Hdci6ycdWdTz66GvR5oLqQp8",
              		"assigned": false,
              		"issuer_id": "11681",
              		"iteration": "121",
              		"royalties": "110",
              		"royalties_split": [{
              			"pct": "500",
              			"address": "tz1NpUTYsVB2Hdci6ycdWdTz66GvR5oLqQp8"
              		}, {
              			"pct": "500",
              			"address": "tz1g1ZjehDWSJF9aGejq68rph4V24TTqCaRs"
              		}]
              	},
              	"firstLevel": 2302891,
              	"lastLevel": 2302891,
              	"updates": 1
              }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
        var tokenId = "638313"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz1NpUTYsVB2Hdci6ycdWdTz66GvR5oLqQp8", 550), Part("tz1g1ZjehDWSJF9aGejq68rph4V24TTqCaRs", 550)))
    }

    @Test
    fun `should correctly fetch and parse VERSUM royalties`() = runBlocking<Unit> {
        mock(
            """
            {
            	"id": 22118909,
            	"active": true,
            	"hash": "expru9SXah7aKPHQb2ZKRi6jn2oe9kXw6MJMfZFba6A12VNsrWbMP6",
            	"key": "19471",
            	"value": {
            		"minter": "tz1VNAyq17Xpz8QpbxMepbfdrcqNkomeKP35",
            		"splits": [{
            			"pct": "1000",
            			"address": "tz1VNAyq17Xpz8QpbxMepbfdrcqNkomeKP35"
            		}],
            		"royalty": "250",
            		"infusions": [],
            		"extra_fields": {},
            		"max_per_address": "0",
            		"no_transfers_until": null,
            		"req_verified_to_own": false,
            		"contracts_may_hold_token": false
            	},
            	"firstLevel": 2239875,
            	"lastLevel": 2239875,
            	"updates": 1
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1LjmAdYQCLBjwv4S2oFkEzyHVkomAf5MrW"
        var tokenId = "19471"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz1VNAyq17Xpz8QpbxMepbfdrcqNkomeKP35", 250 * 10)))
    }

    @Test
    fun `should correctly fetch and parse RARIBLE royalties`() = runBlocking<Unit> {
        mock(
            """
            {
            	"id": 22192662,
            	"active": true,
            	"hash": "exprv8ngbtovw1efE63nuVUYg8cNm7rTojpJnbzdDKgFpjCv288Ch7",
            	"key": "54686",
            	"value": [{
            		"partValue": "1000",
            		"partAccount": "tz2G4MMCYStTP9eUU35WQCqMSSJGtjJRZx9g"
            	}],
            	"firstLevel": 2241862,
            	"lastLevel": 2241862,
            	"updates": 1
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS"
        var tokenId = "54686"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz2G4MMCYStTP9eUU35WQCqMSSJGtjJRZx9g", 1000)))
    }

    @Test
    fun `should correctly fetch and parse RARIBLE Generated collection royalties`() = runBlocking<Unit> {
        mock(
            """
            {
            	"id": 23011034,
            	"active": true,
            	"hash": "expruDuAZnFKqmLoisJqUGqrNzXTvw7PJM2rYk97JErM5FHCerQqgn",
            	"key": "2",
            	"value": [{
            		"partValue": "1200",
            		"partAccount": "tz1NRh1vTn3b38m7Gg2qP81dqb5Kr2BAjwJV"
            	}],
            	"firstLevel": 2280270,
            	"lastLevel": 2280270,
            	"updates": 1
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT19dDquUBH73ifo1M2jt7vvk8XyirTbUsih"
        var tokenId = "2"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(
            listOf(
                Part("tz1NRh1vTn3b38m7Gg2qP81dqb5Kr2BAjwJV", 1200)
            )
        )
    }

    @Test
    fun `should correctly fetch and parse Royalties Manager royalties for a whole collection`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock404()
        mock(
            """
            {
            	"id": 22207733,
            	"active": true,
            	"hash": "expru8351NLHUryTPgjr7HHsP5P2ZDWrXao1UmYLRM4f7paxTFrY5x",
            	"key": {
            		"nat": null,
            		"address": "KT1KmwGzJvehhfk3pHMD6XBuarqDwiy2TJwu"
            	},
            	"value": [{
            		"partValue": "700",
            		"partAccount": "tz1TS2nBAzAtcnLg45fVZYHhJMdpieKVALwP"
            	}],
            	"firstLevel": 2242276,
            	"lastLevel": 2242276,
            	"updates": 1
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1HNNrmCk1fpqveRDz8Fvww2GM4gPzmA7fo"
        var tokenId = "2"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(
            listOf(
                Part("tz1TS2nBAzAtcnLg45fVZYHhJMdpieKVALwP", 700)
            )
        )
    }

    @Test
    fun `should correctly fetch and parse Royalties Manager royalties for a specific token`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock404()
        mock(
            """
            {
            	"id": 22207733,
            	"active": true,
            	"hash": "expru8351NLHUryTPgjr7HHsP5P2ZDWrXao1UmYLRM4f7paxTFrY5x",
            	"key": {
            		"nat": null,
            		"address": "KT1KmwGzJvehhfk3pHMD6XBuarqDwiy2TJwu"
            	},
            	"value": [{
            		"partValue": "700",
            		"partAccount": "tz1TS2nBAzAtcnLg45fVZYHhJMdpieKVALwP"
            	}],
            	"firstLevel": 2242276,
            	"lastLevel": 2242276,
            	"updates": 1
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1HNNrmCk1fpqveRDz8Fvww2GM4gPzmA7fo"
        var tokenId = "2"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(
            listOf(
                Part("tz1TS2nBAzAtcnLg45fVZYHhJMdpieKVALwP", 700)
            )
        )
    }

    @Test
    fun `should correctly fetch and parse OBJKT royalties`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock(
            """
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
        """.trimIndent()
        )
        mock(
            """
            {
            	"name": "R-Royal #2 OBJKT",
            	"description": "ROYAAAAAAL",
            	"rights": "No License / All Rights Reserved",
            	"minter": "KT1Aq4wWmVanpQhq4TTfjZXB5AjFpx15iQMM",
            	"date": "2022-01-13T10:17:56.746Z",
            	"tags": [],
            	"symbol": "OBJKTCOM",
            	"artifactUri": "ipfs://QmaQ8mHLJAmMLiCTFHk39qV98TnPFADhviPt1dJCQKvZAC",
            	"displayUri": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
            	"thumbnailUri": "ipfs://QmRf5zHZCdiAdUBizryjoiRCVaUy6VvQZQwEWqCsoiLtYc",
            	"image": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
            	"creators": ["tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"],
            	"formats": [{
            		"uri": "ipfs://QmaQ8mHLJAmMLiCTFHk39qV98TnPFADhviPt1dJCQKvZAC",
            		"mimeType": "image/png",
            		"fileSize": 100923,
            		"fileName": "white copy 3.png",
            		"dimensions": {
            			"value": "600x602",
            			"unit": "px"
            		}
            	}, {
            		"uri": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
            		"mimeType": "image/jpeg",
            		"fileName": "cover-white copy 3.jpg",
            		"fileSize": 26702,
            		"dimensions": {
            			"value": "600x602",
            			"unit": "px"
            		}
            	}, {
            		"uri": "ipfs://QmRf5zHZCdiAdUBizryjoiRCVaUy6VvQZQwEWqCsoiLtYc",
            		"mimeType": "image/jpeg",
            		"fileName": "thumbnail-white copy 3.jpg",
            		"fileSize": 14398,
            		"dimensions": {
            			"value": "349x350",
            			"unit": "px"
            		}
            	}],
            	"attributes": [],
            	"decimals": 0,
            	"isBooleanAmount": false,
            	"shouldPreferSymbol": false,
            	"royalties": {
            		"decimals": 3,
            		"shares": {
            			"tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88": 100
            		}
            	}
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
        var tokenId = "0"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(
            listOf(
                Part("tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88", 1000)
            )
        )
    }

    @Test
    fun `should correctly fetch and parse Code crafting royalties`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock(
            """
            {
                "id": 11281265,
                "active": true,
                "hash": "exprtaragTX5o2kb51GppULzdY1KiPcwuELfgLEhV7QoCinYd78ywS",
                "key": "897",
                "value": {
                    "token_id": "897",
                    "token_info": {
                        "": "697066733a2f2f516d5a4b4a423350644e477671447a7653314279516a47317a39645a3439547167797064564578385273697673652f3839372e6a736f6e"
                    }
                },
                "firstLevel": 1910411,
                "lastLevel": 1913412,
                "updates": 2
            }
        """.trimIndent()
        )
        mock(
            """
            {
                "artifactUri": "ipfs://QmRzMEUfnjnrFPWB7J9kaWLQM7PHbKdxibZMcEVoepYF55/shinoda_7370acafa62b699b9961f5fb674d026b0adca84e66e52a28dc6db3e1_897_141810.mp3",
                "attributes": [
                    {
                        "name": "Background",
                        "value": "Yellow"
                    },
                    {
                        "name": "Face",
                        "value": "Big Dog"
                    },
                    {
                        "name": "Foreground",
                        "value": "Toxic Smoke"
                    },
                    {
                        "name": "Head",
                        "value": "Green V"
                    },
                    {
                        "name": "Drums Track",
                        "value": "Drums #14"
                    },
                    {
                        "name": "Melody Track",
                        "value": "Melody #1"
                    },
                    {
                        "name": "Music Track",
                        "value": "Music #8"
                    },
                    {
                        "name": "Percussion Track",
                        "value": "Percussion  #1"
                    }
                ],
                "decimals": 0,
                "description": "ZIGGURATS is the first generative NFT mixtape. It is a collection of 5000 unique audio + visual NFTs on the Tezos chain, generated from new art and music by Mike Shinoda. Visually and musically, no two items are the same, but some are more rare. The initial price of each NFT will be 15 Tezos (XTZ).\n\nIn ancient Mesopotamia, the Ziggurat was a tall, layered, structure. It was believed that the Gods lived in the temple at the top. Great care, effort, and creativity went into the creation of its complex structure. Likewise, this project is built upon layers of effort and devotion, some elements many years in the making.",
                "displayUri": "ipfs://QmfJ85aaYJCGMmPHKfuBCbCeViCnao8M8oWDipEs2X9nBK/897.png",
                "externalUri": "https://ziggurats.xyz/",
                "formats": [
                    {
                        "dataRate": {
                            "unit": "kbps",
                            "value": 64
                        },
                        "duration": "00:06:45",
                        "mimeType": "audio/mpeg",
                        "uri": "ipfs://QmRzMEUfnjnrFPWB7J9kaWLQM7PHbKdxibZMcEVoepYF55/shinoda_7370acafa62b699b9961f5fb674d026b0adca84e66e52a28dc6db3e1_897_141810.mp3"
                    },
                    {
                        "dimensions": {
                            "unit": "px",
                            "value": "1080x1080"
                        },
                        "mimeType": "image/png",
                        "uri": "ipfs://QmfJ85aaYJCGMmPHKfuBCbCeViCnao8M8oWDipEs2X9nBK/897.png"
                    },
                    {
                        "dimensions": {
                            "unit": "px",
                            "value": "350x350"
                        },
                        "mimeType": "image/png",
                        "uri": "ipfs://Qmdt367K8V2GxKaGk6SrQCzqWqaaQMGK6jrLwz45xA4NAd/897.png"
                    }
                ],
                "id": 897,
                "isBooleanAmount": true,
                "name": "ZIGGURATS #897",
                "rightsUri": "https://www.mikeshinoda.com/NFTTerms",
                "royalties": {
                    "decimals": 2,
                    "shares": {
                        "tz1SLgrDBpFWjGCnCwyNpCpQC1v8v2N8M2Ks": "5"
                    }
                },
                "shouldPreferSymbol": false,
                "symbol": "ZIGGS",
                "thumbnailUri": "ipfs://Qmdt367K8V2GxKaGk6SrQCzqWqaaQMGK6jrLwz45xA4NAd/897.png"
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1PNcZQkJXMQ2Mg92HG1kyrcu3auFX5pfd8"
        var tokenId = "897"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(
            listOf(
                Part("tz1SLgrDBpFWjGCnCwyNpCpQC1v8v2N8M2Ks", 500),
            )
        )
    }

    @Test
    fun `should correctly fetch and parse SWEET IO royalties`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock(
            """
            {
            	"id": 22644487,
            	"active": true,
            	"hash": "expruDuAZnFKqmLoisJqUGqrNzXTvw7PJM2rYk97JErM5FHCerQqgn",
            	"key": "2",
            	"value": {
            		"token_id": "2",
            		"token_info": {
            			"": "697066733a2f2f516d504c434d56437a6a4b54555233757a6d78366345686735657577666e4a576870574245414b6b3275655a5052"
            		}
            	},
            	"firstLevel": 2261353,
            	"lastLevel": 2261353,
            	"updates": 1
            }
        """.trimIndent()
        )
        mock(
            """
            {
            	"artifactUri": "ipfs://Qmd6QwCcSFpo8ZM1QKgqtPf9aMkikNLDtRq1j68dEHutuz",
            	"attributes": [{
            		"name": "rarity",
            		"type": "integer",
            		"value": "4"
            	}, {
            		"name": "display_type",
            		"type": "string",
            		"value": "video"
            	}, {
            		"name": "seller_fee_basis_points",
            		"type": "integer",
            		"value": "1000"
            	}, {
            		"name": "fee_recipient",
            		"type": "string",
            		"value": "tz1fRXMLR27hWoD49tdtKunHyfy3CQb5XZst"
            	}],
            	"creators": ["SocialSweet Inc."],
            	"date": "2022-04-07T17:52:37",
            	"decimals": 0,
            	"description": "\u201cRacing in Melbourne is special, you can\u2019t beat that home crowd support especially after a three year absence. I can\u2019t wait to hit the track!\u201d  - Daniel Ricciardo, 2022.  After three years away, F1 finally returns to Australia!  This limited-edition digital collectible celebrates Daniel Ricciardo\u2019s first home race since the last F1 GP held at Melbourne\u2019s Albert Park in 2019.  In addition to being a special race for Daniel, the Australian GP has been a historically successful event for McLaren Racing with the team taking 12 wins there.  The first Australian GP was held in 1928, but the event didn\u2019t become part of the F1 World Championship until 1985.  Beginning in Adelaide, the GP was moved to its current venue of Albert Park in 1996 where it has remained ever since.",
            	"displayUri": "ipfs://Qmd6QwCcSFpo8ZM1QKgqtPf9aMkikNLDtRq1j68dEHutuz",
            	"externalUri": "https://collectible.sweet.io/series/1428/2",
            	"formats": [{
            		"hash": "sha256://5c85b7ca208094bf69152a69a0af017926fe9af90c44d6cb8ebc88ce0667d3c8",
            		"mimeType": "image/png",
            		"uri": "ipfs://Qmd6QwCcSFpo8ZM1QKgqtPf9aMkikNLDtRq1j68dEHutuz"
            	}, {
            		"hash": "sha256://cfe4d52c63a90538e2c66e3b71cc0f778a9cb9ccc3032c13ffb85a26bcceab4d",
            		"mimeType": "application/json",
            		"uri": "ipfs://Qmbv1hWdH1NzUk9Fcjy7rX2ZzWVhoSpTCN7ojxKzEUT54b"
            	}, {
            		"hash": null,
            		"mimeType": "text/html",
            		"uri": "https://collectible.sweet.io/series/1428/2"
            	}],
            	"homepage": "https://collectible.sweet.io/series/1428/2",
            	"interfaces": ["TZIP-012", "TZIP-016", "TZIP-021"],
            	"isBooleanAmount": true,
            	"minter": "SocialSweet Inc.",
            	"name": "Danny Ric Comes Home No. 2",
            	"rightsUri": "https://collectible.sweet.io/static/terms-and-conditions-mar2021.txt",
            	"symbol": "SWEDC",
            	"tags": [],
            	"thumbnailUri": "ipfs://Qmd6QwCcSFpo8ZM1QKgqtPf9aMkikNLDtRq1j68dEHutuz",
            	"version": "1"
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
        var tokenId = "2"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(
            listOf(
                Part("tz1fRXMLR27hWoD49tdtKunHyfy3CQb5XZst", 1000),
            )
        )
    }

    @Test
    fun `should fetch empty royalties for failed request with HEN`() = runBlocking<Unit> {
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "717867"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed request with KALAMINT public collection`() = runBlocking<Unit> {
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1EpGgjQs73QfFJs9z7m1Mxm5MTnpC2tqse"
        var tokenId = "53057"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed request with KALAMINT private collection`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1RCzrLhBpdKXkyasKGpDTCcdbBTipUk77x"
        var tokenId = "2490"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed request with 8Bidou 8x8`() = runBlocking<Unit> {
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1MxDwChiDwd6WBVs24g1NjERUoK622ZEFp"
        var tokenId = "53057"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed request with 8Bidou 24x24`() = runBlocking<Unit> {
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1TR1ErEQPTdtaJ7hbvKTJSa1tsGnHGZTpf"
        var tokenId = "69"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed first request with FXHASH_V1`() = runBlocking<Unit> {
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1KEa8z6vWXDJrVqtMrAeDVzsvxat3kHaCE"
        var tokenId = "522648"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed second request with FXHASH_V1`() = runBlocking<Unit> {
        mock(
            """
            {
            	"id": 20692205,
            	"active": true,
            	"hash": "exprvKZPSRwu9V1N6fgTEUqGXHVH44GkjjDPWTxEbC8z8HsvTNPpcf",
            	"key": "522648",
            	"value": {
            		"assigned": true,
            		"issuer_id": "10525",
            		"iteration": "2",
            		"royalties": "150"
            	},
            	"firstLevel": 2186262,
            	"lastLevel": 2186468,
            	"updates": 2
            }
        """.trimIndent()
        )

        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1KEa8z6vWXDJrVqtMrAeDVzsvxat3kHaCE"
        var tokenId = "522648"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed first request with FXHASH_V2`() = runBlocking<Unit> {
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
        var tokenId = "522648"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed request with VERSUM`() = runBlocking<Unit> {
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1LjmAdYQCLBjwv4S2oFkEzyHVkomAf5MrW"
        var tokenId = "19471"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed first and second request with RARIBLE`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS"
        var tokenId = "54686"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed first and second request with RARIBLE generated collection`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT19dDquUBH73ifo1M2jt7vvk8XyirTbUsih"
        var tokenId = "2"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed first request with OBJKT`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
        var tokenId = "0"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed second request with OBJKT`() = runBlocking<Unit> {
        mock404()
        mock(
            """
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
        """.trimIndent()
        )
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
        var tokenId = "0"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed first request with SWEET IO`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
        var tokenId = "2"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties for failed second request with SWEET IO`() = runBlocking<Unit> {
        mock404()
        mock(
            """
            {
            	"id": 22644487,
            	"active": true,
            	"hash": "expruDuAZnFKqmLoisJqUGqrNzXTvw7PJM2rYk97JErM5FHCerQqgn",
            	"key": "2",
            	"value": {
            		"token_id": "2",
            		"token_info": {
            			"": "697066733a2f2f516d504c434d56437a6a4b54555233757a6d78366345686735657577666e4a576870574245414b6b3275655a5052"
            		}
            	},
            	"firstLevel": 2261353,
            	"lastLevel": 2261353,
            	"updates": 1
            }
        """.trimIndent()
        )
        mock404()
        mock404()
        mock404()
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
        var tokenId = "2"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties with all requests failed`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tX"
        var tokenId = "2"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(emptyList<Part>())
    }

    @Test
    fun `should fetch empty royalties with all requests failed except first owner request`() = runBlocking<Unit> {
        mock404()
        mock404()
        mock404()
        mock404()
        mock404()
        mock(
            """
                [{
                	"id": 6924272,
                	"account": {
                		"address": "tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"
                	},
                	"token": {
                		"id": 1646093,
                		"contract": {
                			"address": "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
                		},
                		"tokenId": "0",
                		"standard": "fa2",
                		"metadata": {
                			"date": "2022-01-13T10:17:56.746Z",
                			"name": "R-Royal #2 OBJKT",
                			"tags": [],
                			"image": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                			"minter": "KT1Aq4wWmVanpQhq4TTfjZXB5AjFpx15iQMM",
                			"rights": "No License / All Rights Reserved",
                			"symbol": "OBJKTCOM",
                			"formats": [{
                				"uri": "ipfs://QmaQ8mHLJAmMLiCTFHk39qV98TnPFADhviPt1dJCQKvZAC",
                				"fileName": "white copy 3.png",
                				"fileSize": "100923",
                				"mimeType": "image/png",
                				"dimensions": {
                					"unit": "px",
                					"value": "600x602"
                				}
                			}, {
                				"uri": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                				"fileName": "cover-white copy 3.jpg",
                				"fileSize": "26702",
                				"mimeType": "image/jpeg",
                				"dimensions": {
                					"unit": "px",
                					"value": "600x602"
                				}
                			}, {
                				"uri": "ipfs://QmRf5zHZCdiAdUBizryjoiRCVaUy6VvQZQwEWqCsoiLtYc",
                				"fileName": "thumbnail-white copy 3.jpg",
                				"fileSize": "14398",
                				"mimeType": "image/jpeg",
                				"dimensions": {
                					"unit": "px",
                					"value": "349x350"
                				}
                			}],
                			"creators": ["tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"],
                			"decimals": "0",
                			"royalties": {
                				"shares": {
                					"tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88": "100"
                				},
                				"decimals": "3"
                			},
                			"attributes": [],
                			"displayUri": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                			"artifactUri": "ipfs://QmaQ8mHLJAmMLiCTFHk39qV98TnPFADhviPt1dJCQKvZAC",
                			"description": "ROYAAAAAAL",
                			"thumbnailUri": "ipfs://QmRf5zHZCdiAdUBizryjoiRCVaUy6VvQZQwEWqCsoiLtYc",
                			"isBooleanAmount": false,
                			"shouldPreferSymbol": false
                		}
                	},
                	"balance": "8",
                	"transfersCount": 3,
                	"firstLevel": 2027201,
                	"firstTime": "2022-01-13T10:18:30Z",
                	"lastLevel": 2030093,
                	"lastTime": "2022-01-14T11:22:50Z"
                }, {
                	"id": 6997071,
                	"account": {
                		"address": "tz1NRh1vTn3b38m7Gg2qP81dqb5Kr2BAjwJV"
                	},
                	"token": {
                		"id": 1646093,
                		"contract": {
                			"address": "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
                		},
                		"tokenId": "0",
                		"standard": "fa2",
                		"metadata": {
                			"date": "2022-01-13T10:17:56.746Z",
                			"name": "R-Royal #2 OBJKT",
                			"tags": [],
                			"image": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                			"minter": "KT1Aq4wWmVanpQhq4TTfjZXB5AjFpx15iQMM",
                			"rights": "No License / All Rights Reserved",
                			"symbol": "OBJKTCOM",
                			"formats": [{
                				"uri": "ipfs://QmaQ8mHLJAmMLiCTFHk39qV98TnPFADhviPt1dJCQKvZAC",
                				"fileName": "white copy 3.png",
                				"fileSize": "100923",
                				"mimeType": "image/png",
                				"dimensions": {
                					"unit": "px",
                					"value": "600x602"
                				}
                			}, {
                				"uri": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                				"fileName": "cover-white copy 3.jpg",
                				"fileSize": "26702",
                				"mimeType": "image/jpeg",
                				"dimensions": {
                					"unit": "px",
                					"value": "600x602"
                				}
                			}, {
                				"uri": "ipfs://QmRf5zHZCdiAdUBizryjoiRCVaUy6VvQZQwEWqCsoiLtYc",
                				"fileName": "thumbnail-white copy 3.jpg",
                				"fileSize": "14398",
                				"mimeType": "image/jpeg",
                				"dimensions": {
                					"unit": "px",
                					"value": "349x350"
                				}
                			}],
                			"creators": ["tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"],
                			"decimals": "0",
                			"royalties": {
                				"shares": {
                					"tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88": "100"
                				},
                				"decimals": "3"
                			},
                			"attributes": [],
                			"displayUri": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                			"artifactUri": "ipfs://QmaQ8mHLJAmMLiCTFHk39qV98TnPFADhviPt1dJCQKvZAC",
                			"description": "ROYAAAAAAL",
                			"thumbnailUri": "ipfs://QmRf5zHZCdiAdUBizryjoiRCVaUy6VvQZQwEWqCsoiLtYc",
                			"isBooleanAmount": false,
                			"shouldPreferSymbol": false
                		}
                	},
                	"balance": "0",
                	"transfersCount": 2,
                	"firstLevel": 2030087,
                	"firstTime": "2022-01-14T11:19:50Z",
                	"lastLevel": 2030103,
                	"lastTime": "2022-01-14T11:27:50Z"
                }, {
                	"id": 6997145,
                	"account": {
                		"address": "tz2NKVi7TWXxU3eFvxkzw7cv3Mybavx1XtMV"
                	},
                	"token": {
                		"id": 1646093,
                		"contract": {
                			"address": "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
                		},
                		"tokenId": "0",
                		"standard": "fa2",
                		"metadata": {
                			"date": "2022-01-13T10:17:56.746Z",
                			"name": "R-Royal #2 OBJKT",
                			"tags": [],
                			"image": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                			"minter": "KT1Aq4wWmVanpQhq4TTfjZXB5AjFpx15iQMM",
                			"rights": "No License / All Rights Reserved",
                			"symbol": "OBJKTCOM",
                			"formats": [{
                				"uri": "ipfs://QmaQ8mHLJAmMLiCTFHk39qV98TnPFADhviPt1dJCQKvZAC",
                				"fileName": "white copy 3.png",
                				"fileSize": "100923",
                				"mimeType": "image/png",
                				"dimensions": {
                					"unit": "px",
                					"value": "600x602"
                				}
                			}, {
                				"uri": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                				"fileName": "cover-white copy 3.jpg",
                				"fileSize": "26702",
                				"mimeType": "image/jpeg",
                				"dimensions": {
                					"unit": "px",
                					"value": "600x602"
                				}
                			}, {
                				"uri": "ipfs://QmRf5zHZCdiAdUBizryjoiRCVaUy6VvQZQwEWqCsoiLtYc",
                				"fileName": "thumbnail-white copy 3.jpg",
                				"fileSize": "14398",
                				"mimeType": "image/jpeg",
                				"dimensions": {
                					"unit": "px",
                					"value": "349x350"
                				}
                			}],
                			"creators": ["tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"],
                			"decimals": "0",
                			"royalties": {
                				"shares": {
                					"tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88": "100"
                				},
                				"decimals": "3"
                			},
                			"attributes": [],
                			"displayUri": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                			"artifactUri": "ipfs://QmaQ8mHLJAmMLiCTFHk39qV98TnPFADhviPt1dJCQKvZAC",
                			"description": "ROYAAAAAAL",
                			"thumbnailUri": "ipfs://QmRf5zHZCdiAdUBizryjoiRCVaUy6VvQZQwEWqCsoiLtYc",
                			"isBooleanAmount": false,
                			"shouldPreferSymbol": false
                		}
                	},
                	"balance": "1",
                	"transfersCount": 1,
                	"firstLevel": 2030093,
                	"firstTime": "2022-01-14T11:22:50Z",
                	"lastLevel": 2030093,
                	"lastTime": "2022-01-14T11:22:50Z"
                }, {
                	"id": 6997273,
                	"account": {
                		"address": "tz1V11fB4EX5VzPKMNQ1CsBKMSFS6fL3Br9W"
                	},
                	"token": {
                		"id": 1646093,
                		"contract": {
                			"address": "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
                		},
                		"tokenId": "0",
                		"standard": "fa2",
                		"metadata": {
                			"date": "2022-01-13T10:17:56.746Z",
                			"name": "R-Royal #2 OBJKT",
                			"tags": [],
                			"image": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                			"minter": "KT1Aq4wWmVanpQhq4TTfjZXB5AjFpx15iQMM",
                			"rights": "No License / All Rights Reserved",
                			"symbol": "OBJKTCOM",
                			"formats": [{
                				"uri": "ipfs://QmaQ8mHLJAmMLiCTFHk39qV98TnPFADhviPt1dJCQKvZAC",
                				"fileName": "white copy 3.png",
                				"fileSize": "100923",
                				"mimeType": "image/png",
                				"dimensions": {
                					"unit": "px",
                					"value": "600x602"
                				}
                			}, {
                				"uri": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                				"fileName": "cover-white copy 3.jpg",
                				"fileSize": "26702",
                				"mimeType": "image/jpeg",
                				"dimensions": {
                					"unit": "px",
                					"value": "600x602"
                				}
                			}, {
                				"uri": "ipfs://QmRf5zHZCdiAdUBizryjoiRCVaUy6VvQZQwEWqCsoiLtYc",
                				"fileName": "thumbnail-white copy 3.jpg",
                				"fileSize": "14398",
                				"mimeType": "image/jpeg",
                				"dimensions": {
                					"unit": "px",
                					"value": "349x350"
                				}
                			}],
                			"creators": ["tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"],
                			"decimals": "0",
                			"royalties": {
                				"shares": {
                					"tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88": "100"
                				},
                				"decimals": "3"
                			},
                			"attributes": [],
                			"displayUri": "ipfs://QmZGqiGBeZyF96KLzEiDTaiAYBZzsgGCfK38JQT2zWGN9q",
                			"artifactUri": "ipfs://QmaQ8mHLJAmMLiCTFHk39qV98TnPFADhviPt1dJCQKvZAC",
                			"description": "ROYAAAAAAL",
                			"thumbnailUri": "ipfs://QmRf5zHZCdiAdUBizryjoiRCVaUy6VvQZQwEWqCsoiLtYc",
                			"isBooleanAmount": false,
                			"shouldPreferSymbol": false
                		}
                	},
                	"balance": "1",
                	"transfersCount": 1,
                	"firstLevel": 2030103,
                	"firstTime": "2022-01-14T11:27:50Z",
                	"lastLevel": 2030103,
                	"lastTime": "2022-01-14T11:27:50Z"
                }]
            """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
        var tokenId = "0"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88", 0)))
    }

    @Test
    @Disabled
    fun `Manual test for royalties`() = runBlocking<Unit> {
        val localTzkt = "https://api.tzkt.io"
        val clientBuilder = WebClient.builder().baseUrl(localTzkt)
            .codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024) }.build()
        val bmClient = BigMapKeyClient(clientBuilder)
        val ownershipClient = OwnershipClient(clientBuilder)
        val ipfs = IPFSClient(WebClient.create("https://ipfs.io/ipfs/"), mapper)
        val handler = RoyaltiesHandler(bmClient, ownershipClient, ipfs, royaltiesConfig)
        val id = "KT1L6BTeGP5NcVmRjys85EaDxBymxMyx5rj8:0"
        val parts = handler.processRoyalties(id)
        println(parts)
    }

    @Test
    @Disabled
    fun `should fetch royalties from meta`() = runBlocking<Unit> {
        val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, royaltiesConfig)
        var contract = "KT1L7GvUxZH5tfa6cgZKnH6vpp2uVxnFVHKu:945"
        var tokenId = "945"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts.first().share).isEqualTo(700)
    }

}
