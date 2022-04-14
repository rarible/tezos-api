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

        val handler = RoyaltiesHandler(bigMapKeyClient, ipfsClient)
        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "717867"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz1ZqdrwVRUs8H1Vts2pFvmR1PLikE8eBVZv", 150 * 10)))
    }

    @Test
    fun `should correctly fetch and parse KALAMINT royalties`() = runBlocking<Unit> {
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

        val handler = RoyaltiesHandler(bigMapKeyClient, ipfsClient)
        var contract = "KT1EpGgjQs73QfFJs9z7m1Mxm5MTnpC2tqse"
        var tokenId = "53057"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz1gSCfWiPL8e6us331gtHCvJr9Cuf3jX8g6", 10 * 100)))
    }

    @Test
    fun `should correctly fetch and parse FXHASH royalties`() = runBlocking<Unit> {
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

        val handler = RoyaltiesHandler(bigMapKeyClient, ipfsClient)
        var contract = "KT1KEa8z6vWXDJrVqtMrAeDVzsvxat3kHaCE"
        var tokenId = "522648"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz1b8GULAVKS1oHpYLJbwuTKvUegXtRbxH82", 10 * 150)))
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

        val handler = RoyaltiesHandler(bigMapKeyClient, ipfsClient)
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

        val handler = RoyaltiesHandler(bigMapKeyClient, ipfsClient)
        var contract = "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS"
        var tokenId = "54686"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(listOf(Part("tz2G4MMCYStTP9eUU35WQCqMSSJGtjJRZx9g", 1000)))
    }

    @Test
    fun `should correctly fetch and parse OBJKT royalties`() = runBlocking<Unit> {
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
            			"tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88": 100,
                        "tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP89": 100

            		}
            	}
            }
        """.trimIndent()
        )

        val handler = RoyaltiesHandler(bigMapKeyClient, ipfsClient)
        var contract = "KT1EffErZNVCPXW2trCMD5gGkACdAbAzj4tT"
        var tokenId = "0"
        val id = "$contract:$tokenId"
        val parts = handler.processRoyalties(id)
        assertThat(parts).isEqualTo(
            listOf(
                Part("tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88", 1000),
                Part("tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP89", 1000)
            )
        )
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

        val handler = RoyaltiesHandler(bigMapKeyClient, ipfsClient)
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
    fun `should correctly fetch and parse SWEET IO royalties`() = runBlocking<Unit> {
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

        val handler = RoyaltiesHandler(bigMapKeyClient, ipfsClient)
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
}
