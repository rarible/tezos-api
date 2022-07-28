package com.rarible.tzkt.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.rarible.tzkt.config.KnownAddresses
import com.rarible.tzkt.meta.MetaService
import com.rarible.tzkt.model.TzktNotFound
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger

class TokenClientTests : BaseClientTests() {


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

    val config = KnownAddresses(
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

    val bigMapKeyClient = BigMapKeyClient(client)
    val ipfsClient = IPFSClient(client, mapper)
    val metaService = MetaService(ObjectMapper().registerKotlinModule(), bigMapKeyClient, ipfsClient, config)
    val tokenClient = TokenClient(client, metaService, mockk())

    @Test
    fun `should return token by contract and token id`() = runBlocking<Unit> {
        mock(
            """
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
        """.trimIndent()
        )
        mock("[]")

        val token = tokenClient.token("KT1NWdwVA8zq5DDJTKcMkRqWYJcEcyTTm5WK:1156")
        assertThat(request().path).isEqualTo("/v1/tokens?contract=KT1NWdwVA8zq5DDJTKcMkRqWYJcEcyTTm5WK&tokenId.in=1156&token.standard=fa2")

        assertThat(token).isNotNull
        assertThat(token.meta).isNull()
        assertThat(token.standard).isEqualTo("fa2")
    }

    @Test
    fun `should return tokens sorted by lastTime with size, continuation and sorted by DESC`() = runBlocking<Unit> {
        mock("""[
    {
        "id": 3489773,
        "contract": {
            "alias": "FXHASH GENTK v2",
            "address": "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
        },
        "tokenId": "1028686",
        "standard": "fa2",
        "firstLevel": 2564517,
        "firstTime": "2022-07-25T13:36:29Z",
        "lastLevel": 2564517,
        "lastTime": "2022-07-25T13:36:29Z",
        "transfersCount": 1,
        "balancesCount": 1,
        "holdersCount": 1,
        "totalMinted": "1",
        "totalBurned": "0",
        "totalSupply": "1",
        "metadata": {
            "ttl": "30",
            "name": "[WAITING TO BE SIGNED]",
            "symbol": "GENTK",
            "decimals": "0",
            "displayUri": "ipfs://QmYwSwa5hP4346GqD7hAjutwJSmeYTdiLQ7Wec2C7Cez1D",
            "artifactUri": "ipfs://QmdGV3UqJqX4v5x9nFcDYeekCEAm3SDXUG5SHdjKQKn4Pe",
            "description": "This Gentk is waiting to be signed by the fxhash signer module",
            "thumbnailUri": "ipfs://QmbvEAn7FLMeYBDroYwBP8qWc3d3VVWbk19tTB83LCMB5S"
        }
    },
    {
        "id": 3489772,
        "contract": {
            "alias": "FXHASH GENTK v2",
            "address": "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
        },
        "tokenId": "1028685",
        "standard": "fa2",
        "firstLevel": 2564516,
        "firstTime": "2022-07-25T13:35:59Z",
        "lastLevel": 2564516,
        "lastTime": "2022-07-25T13:35:59Z",
        "transfersCount": 1,
        "balancesCount": 1,
        "holdersCount": 1,
        "totalMinted": "1",
        "totalBurned": "0",
        "totalSupply": "1",
        "metadata": {
            "ttl": "30",
            "name": "[WAITING TO BE SIGNED]",
            "symbol": "GENTK",
            "decimals": "0",
            "displayUri": "ipfs://QmYwSwa5hP4346GqD7hAjutwJSmeYTdiLQ7Wec2C7Cez1D",
            "artifactUri": "ipfs://QmdGV3UqJqX4v5x9nFcDYeekCEAm3SDXUG5SHdjKQKn4Pe",
            "description": "This Gentk is waiting to be signed by the fxhash signer module",
            "thumbnailUri": "ipfs://QmbvEAn7FLMeYBDroYwBP8qWc3d3VVWbk19tTB83LCMB5S"
        }
    }
]""")
        mock("""[
            {
                "id": 3489772,
                "contract": {
                    "alias": "FXHASH GENTK v2",
                    "address": "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
                },
                "tokenId": "1028685",
                "standard": "fa2",
                "firstLevel": 2564516,
                "firstTime": "2022-07-25T13:35:59Z",
                "lastLevel": 2564516,
                "lastTime": "2022-07-25T13:35:59Z",
                "transfersCount": 1,
                "balancesCount": 1,
                "holdersCount": 1,
                "totalMinted": "1",
                "totalBurned": "0",
                "totalSupply": "1",
                "metadata": {
                    "ttl": "30",
                    "name": "[WAITING TO BE SIGNED]",
                    "symbol": "GENTK",
                    "decimals": "0",
                    "displayUri": "ipfs://QmYwSwa5hP4346GqD7hAjutwJSmeYTdiLQ7Wec2C7Cez1D",
                    "artifactUri": "ipfs://QmdGV3UqJqX4v5x9nFcDYeekCEAm3SDXUG5SHdjKQKn4Pe",
                    "description": "This Gentk is waiting to be signed by the fxhash signer module",
                    "thumbnailUri": "ipfs://QmbvEAn7FLMeYBDroYwBP8qWc3d3VVWbk19tTB83LCMB5S"
                }
            }
        ]""")
        mock("""[
    {
        "id": 3489765,
        "contract": {
            "alias": "FXHASH GENTK v2",
            "address": "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
        },
        "tokenId": "1028682",
        "standard": "fa2",
        "firstLevel": 2564513,
        "firstTime": "2022-07-25T13:34:29Z",
        "lastLevel": 2564513,
        "lastTime": "2022-07-25T13:34:29Z",
        "transfersCount": 1,
        "balancesCount": 1,
        "holdersCount": 1,
        "totalMinted": "1",
        "totalBurned": "0",
        "totalSupply": "1",
        "metadata": {
            "name": "House of Birds #149",
            "tags": [
                "generative art",
                "bird",
                "p5js",
                "origami"
            ],
            "symbol": "GENTK",
            "version": "0.2",
            "decimals": "0",
            "attributes": true,
            "displayUri": "ipfs://Qmaxko28XdawB3Q4jS6CV4wmTfRzDuSfNu244fe7HwZiUj",
            "artifactUri": "ipfs://QmcuM6pt91CwRvqSkzYLnxXBHSETw353qzDGCWCWoEqz7f?fxhash=oocWTh4jyUDBU76zNsfVvAajN79hbsMrmmQFRcBfFQV1SbT7cik",
            "description": "\"House of Birds\" started as a few origami birds inside a frame.\nBut, birds don't need boundaries; birds need trees, hills, and sky.\n\nCreated using p5js;\n\nPress \"s\" to save as png.\n\nTwitter: @SapirCohen__",
            "generatorUri": "ipfs://QmcuM6pt91CwRvqSkzYLnxXBHSETw353qzDGCWCWoEqz7f",
            "thumbnailUri": "ipfs://QmQPLCTBnNY7fsxwJurjaogYtitJNzUTrChyi8FAk3YxvY",
            "iterationHash": "oocWTh4jyUDBU76zNsfVvAajN79hbsMrmmQFRcBfFQV1SbT7cik",
            "authenticityHash": "4fcfb0bd3ec395674c6d55df5abd0a3b7e0d9c9c122963aa462ad1586ebccb0a"
        }
    },
    {
        "id": 3489763,
        "contract": {
            "address": "KT1BLhZmq6wSaNGgeZkT4xYxEfTtQt21awza"
        },
        "tokenId": "34",
        "standard": "fa2",
        "firstLevel": 2564513,
        "firstTime": "2022-07-25T13:34:29Z",
        "lastLevel": 2564513,
        "lastTime": "2022-07-25T13:34:29Z",
        "transfersCount": 1,
        "balancesCount": 1,
        "holdersCount": 1,
        "totalMinted": "1",
        "totalBurned": "0",
        "totalSupply": "1",
        "metadata": {
            "date": "2022-07-25T13:33:06.105Z",
            "name": "ROSE #32",
            "tags": [],
            "image": "ipfs://QmP9Pcik9DpGnVV5MN2x6yFRDE55XpNcVpENNquPU43k3L",
            "minter": "KT1Aq4wWmVanpQhq4TTfjZXB5AjFpx15iQMM",
            "rights": "No License / All Rights Reserved",
            "symbol": "OBJKTCOM",
            "formats": [
                {
                    "uri": "ipfs://QmQ4337n12xf91Cmr9c7Q4md8hxrKNz7GWvjy3xpFvZH4W",
                    "fileName": "Image_20220725_1800.png",
                    "fileSize": "773133",
                    "mimeType": "image/png",
                    "dimensions": {
                        "unit": "px",
                        "value": "720x1280"
                    }
                },
                {
                    "uri": "ipfs://QmP9Pcik9DpGnVV5MN2x6yFRDE55XpNcVpENNquPU43k3L",
                    "fileName": "cover-Image_20220725_1800.jpeg",
                    "fileSize": "159111",
                    "mimeType": "image/jpeg",
                    "dimensions": {
                        "unit": "px",
                        "value": "576x1024"
                    }
                },
                {
                    "uri": "ipfs://QmZcSDKgZPMqfyrfvY82uupwmQa2tbTR1BbigAn3Fho89k",
                    "fileName": "thumbnail-Image_20220725_1800.jpeg",
                    "fileSize": "30613",
                    "mimeType": "image/jpeg",
                    "dimensions": {
                        "unit": "px",
                        "value": "197x350"
                    }
                }
            ],
            "creators": [
                "tz1fZwgLaXzZ4UVBfmQzAfZ6vFPryXARbEuH"
            ],
            "decimals": "0",
            "royalties": {
                "shares": {
                    "tz1fZwgLaXzZ4UVBfmQzAfZ6vFPryXARbEuH": "100"
                },
                "decimals": "3"
            },
            "attributes": [],
            "displayUri": "ipfs://QmP9Pcik9DpGnVV5MN2x6yFRDE55XpNcVpENNquPU43k3L",
            "artifactUri": "ipfs://QmQ4337n12xf91Cmr9c7Q4md8hxrKNz7GWvjy3xpFvZH4W",
            "description": "RoseArt 👩🏼🎨",
            "mintingTool": "https://objkt.com",
            "thumbnailUri": "ipfs://QmZcSDKgZPMqfyrfvY82uupwmQa2tbTR1BbigAn3Fho89k",
            "isBooleanAmount": false,
            "shouldPreferSymbol": false
        }
    }
]""")

        val firstTokenRequest = tokenClient.allTokensByLastUpdate(2, null, false)
        assertThat(firstTokenRequest.items.size).isEqualTo(2)
        assertThat(firstTokenRequest.continuation).isNotNull
        assertThat(firstTokenRequest.continuation).isEqualTo("1658756159000_KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi:1028685")

        val secondTokenRequest = tokenClient.allTokensByLastUpdate(2, firstTokenRequest.continuation, false)
        assertThat(secondTokenRequest.items.size).isEqualTo(2)
        assertThat(secondTokenRequest.continuation).isNotNull
        assertThat(secondTokenRequest.continuation).isEqualTo("1658756069000_KT1BLhZmq6wSaNGgeZkT4xYxEfTtQt21awza:34")
    }

    @Test
    fun `should return tokens by ids`() = runBlocking<Unit> {
        mock(
            """[{
            "id": 1,
            "contract": {
                "alias": "test",
                "address": "KT1S95Dyj2QrJpSnAbHRUSUZr7DhuFqssrog"
            },
            "tokenId": "0",
            "standard": "fa2",
            "firstLevel": 889166,
            "firstTime": "2020-03-31T15:12:51Z",
            "lastLevel": 2280800,
            "lastTime": "2022-04-14T16:51:14Z",
            "transfersCount": 97188,
            "balancesCount": 3029,
            "holdersCount": 1179,
            "totalMinted": "2",
            "totalBurned": "0",
            "totalSupply": "2"
        }]"""
        )
        mock(
            """[]"""
        )
        val tokens = tokenClient.tokens(
            listOf(
                "KT1S95Dyj2QrJpSnAbHRUSUZr7DhuFqssrog:0"
            )
        )

        assertThat(tokens).hasSize(1)
        assertThat(tokens.first().standard).isEqualTo("fa2")
    }

    @Test
    fun `should return true for nft item`() = runBlocking<Unit> {
        mock("""[
                {
        "id": 60,
        "contract": {
            "address": "KT1S95Dyj2QrJpSnAbHRUSUZr7DhuFqssrog"
        },
        "tokenId": "0",
        "standard": "fa2",
        "firstLevel": 1328122,
        "firstTime": "2021-02-02T19:17:52Z",
        "lastLevel": 1390772,
        "lastTime": "2021-03-19T00:29:18Z",
        "transfersCount": 2,
        "balancesCount": 2,
        "holdersCount": 1,
        "totalMinted": "1",
        "totalBurned": "0",
        "totalSupply": "1",
        "metadata": {
            "artifactUri": "https://cloudflare-ipfs.com/ipfs/QmUnPB2pBFHv3MyRDQcmMGkpDNKNqeX57xASa9cx4Xomn1"
        }
    }]""".trimMargin())

        val isNft = tokenClient.isNft("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:157993")

        assertThat(request().path).isEqualTo("/v1/tokens?contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&tokenId=157993&token.standard=fa2")
        assertThat(isNft).isEqualTo(true)
    }

    @Test
    fun `should return false for nft item`() = runBlocking<Unit> {
        mock("""[
                {
        "id": 60,
        "contract": {
            "address": "KT1S95Dyj2QrJpSnAbHRUSUZr7DhuFqssrog"
        },
        "tokenId": "0",
        "standard": "fa2",
        "firstLevel": 1328122,
        "firstTime": "2021-02-02T19:17:52Z",
        "lastLevel": 1390772,
        "lastTime": "2021-03-19T00:29:18Z",
        "transfersCount": 2,
        "balancesCount": 2,
        "holdersCount": 1,
        "totalMinted": "1",
        "totalBurned": "0",
        "totalSupply": "1",
        "metadata": {
            
        }
    }]""".trimMargin())

        val isNft = tokenClient.isNft("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:157993")

        assertThat(request().path).isEqualTo("/v1/tokens?contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&tokenId=157993&token.standard=fa2")
        assertThat(isNft).isEqualTo(false)
    }

    @Test
    fun `should return null for nft item`() = runBlocking<Unit> {
        mock("""[
                {
        "id": 60,
        "contract": {
            "address": "KT1S95Dyj2QrJpSnAbHRUSUZr7DhuFqssrog"
        },
        "tokenId": "0",
        "standard": "fa2",
        "firstLevel": 1328122,
        "firstTime": "2021-02-02T19:17:52Z",
        "lastLevel": 1390772,
        "lastTime": "2021-03-19T00:29:18Z",
        "transfersCount": 2,
        "balancesCount": 2,
        "holdersCount": 1,
        "totalMinted": "1",
        "totalBurned": "0",
        "totalSupply": "1" }]""".trimMargin())

        val isNft = tokenClient.isNft("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:157993")

        assertThat(request().path).isEqualTo("/v1/tokens?contract=KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton&tokenId=157993&token.standard=fa2")
        assertThat(isNft).isNull()
    }

    @Test
    fun `should return meta from escaped string`() = runBlocking<Unit> {
        mock("""
            [
                {
                    "id": 12571,
                    "contract": {
                        "address": "KT1PWG9Xm9vuLGGASfoh7aeCAfPNwuzx1P4J"
                    },
                    "tokenId": "69",
                    "standard": "fa2",
                    "firstLevel": 457281,
                    "firstTime": "2022-04-29T09:13:25Z",
                    "lastLevel": 457283,
                    "lastTime": "2022-04-29T09:13:55Z",
                    "transfersCount": 2,
                    "balancesCount": 2,
                    "holdersCount": 1,
                    "totalMinted": "1",
                    "totalBurned": "0",
                    "totalSupply": "1",
                    "metadata": {
                        "name": "Dogami",
                        "rights": "(c) DOGAMI. All Rights Reserved.",
                        "formats": "[{\"mimeType\": \"video/mp4\", \"uri\": \"ipfs://QmbXbqeV6DTVXYF5UDt15CEv8RMLsuWUEGsjF885QXzn37\"}, {\"mimeType\": \"image/png\", \"uri\": \"ipfs://QmS7QeMdCw39LEf7tEW44owuSuWNRCPjGQKjFq6KuZxCPS\"}, {\"mimeType\": \"image/png\", \"uri\": \"ipfs://QmXJ9faS96kQVDSdu8osz7TJvKg36yWiiRzmkb9X1BGmzp\"}]",
                        "creators": "[\"DOGAMI\"]",
                        "decimals": "0",
                        "royalties": "{\"decimals\": 2, \"shares\": {\"tz1bj9NxKYups7WCFmytkYJTw6rxtizJR79K\": 7}}",
                        "attributes": "",
                        "displayUri": "ipfs://QmS7QeMdCw39LEf7tEW44owuSuWNRCPjGQKjFq6KuZxCPS",
                        "artifactUri": "ipfs://QmbXbqeV6DTVXYF5UDt15CEv8RMLsuWUEGsjF885QXzn37",
                        "description": "Your true virtual companion!",
                        "thumbnailUri": "ipfs://QmXJ9faS96kQVDSdu8osz7TJvKg36yWiiRzmkb9X1BGmzp",
                        "isBooleanAmount": "true"
                    }
                }
            ]
        """.trimIndent())
        mock("[]")
        val nft = tokenClient.token("KT1PWG9Xm9vuLGGASfoh7aeCAfPNwuzx1P4J:69", true)
        assertThat(nft.meta?.content).hasSize(3)
    }

    @Test
    fun `shouldn't return token by contract and token id`() = runBlocking<Unit> {
        mock("[]")
        mock("[]")
        assertThrows<TzktNotFound> { tokenClient.token("KT1NWdwVA8zq5DDJTKcMkRqWYJcEcyTTm5WK:1156") }
    }

    @Test
    fun `should return token count`() = runBlocking<Unit> {
        mock("[\"1977\"]")
        val count = tokenClient.tokenCount("KT1NWdwVA8zq5DDJTKcMkRqWYJcEcyTTm5WK")
        assertThat(count).isEqualTo(BigInteger("1977"))
    }

    @Test
    fun `should return tokens by owner`() = runBlocking<Unit> {
        mock(""" [{
                    "id": 12277692,
                    "token.contract.address": "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS",
                    "token.tokenId": "76999"
                }]""".trimIndent())
        mock("""
            [
                {
                    "id": 3404739,
                    "contract": {
                        "alias": "Rarible",
                        "address": "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS"
                    },
                    "tokenId": "77272",
                    "standard": "fa2",
                    "firstLevel": 2530589,
                    "firstTime": "2022-07-13T13:54:59Z",
                    "lastLevel": 2530589,
                    "lastTime": "2022-07-13T13:54:59Z",
                    "transfersCount": 1,
                    "balancesCount": 1,
                    "holdersCount": 1,
                    "totalMinted": "10",
                    "totalBurned": "0",
                    "totalSupply": "10",
                    "metadata": {}
                }
            ]
        """.trimIndent())
        val page1 = tokenClient.tokensByOwner("tz1gqL7i1s578qj3NzgKmu6C5j3RdSBewGBo", 1, null)
        assertThat(page1.items).hasSize(1)
        assertThat(page1.continuation).isNotEmpty

        mock(""" [{
                    "id": 12277744,
                    "token.contract.address": "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS",
                    "token.tokenId": "77263"
                }]""".trimIndent())
        mock("""
            [
                {
                    "id": 3404739,
                    "contract": {
                        "alias": "Rarible",
                        "address": "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS"
                    },
                    "tokenId": "77272",
                    "standard": "fa2",
                    "firstLevel": 2530589,
                    "firstTime": "2022-07-13T13:54:59Z",
                    "lastLevel": 2530589,
                    "lastTime": "2022-07-13T13:54:59Z",
                    "transfersCount": 1,
                    "balancesCount": 1,
                    "holdersCount": 1,
                    "totalMinted": "10",
                    "totalBurned": "0",
                    "totalSupply": "10",
                    "metadata": {}
                }
            ]
        """.trimIndent())
        val page2 = tokenClient.tokensByOwner("tz1gqL7i1s578qj3NzgKmu6C5j3RdSBewGBo", 2, page1.continuation)
        assertThat(page2.items).hasSize(1)
        assertThat(page2.continuation).isNull()

        assertThat(requests()).isEqualTo(setOf(
            "/v1/tokens/balances?account=tz1gqL7i1s578qj3NzgKmu6C5j3RdSBewGBo&token.standard=fa2&limit=1&balance.gt=0&sort.desc=id&select=id,token.contract.address,token.tokenId",
            "/v1/tokens?contract=KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS&tokenId.in=76999&token.standard=fa2",
            "/v1/tokens/balances?account=tz1gqL7i1s578qj3NzgKmu6C5j3RdSBewGBo&token.standard=fa2&limit=2&id.lt=12277692&balance.gt=0&sort.desc=id&select=id,token.contract.address,token.tokenId",
            "/v1/tokens?contract=KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS&tokenId.in=77263&token.standard=fa2")
        )
    }

    @Test
    fun `should return tokens by creator`() = runBlocking<Unit> {
        mock("""
            [
                {
                    "id": 285714835,
                    "level": 2530589,
                    "timestamp": "2022-07-13T13:54:59Z",
                    "token": {
                        "id": 3404739,
                        "contract": {
                            "alias": "Rarible",
                            "address": "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS"
                        },
                        "tokenId": "77272",
                        "standard": "fa2",
                        "metadata": {
                            "name": "pica",
                            "formats": [
                                {
                                    "uri": "ipfs://QmWGVatVnSXQdwEK43a6Z75jERnSBEBvkYbjTAFCEcjqaa/image.gif",
                                    "fileName": "meme-pikachu.gif",
                                    "fileSize": "2816801",
                                    "mimeType": "image/gif"
                                }
                            ],
                            "decimals": "0",
                            "attributes": [],
                            "displayUri": "ipfs://QmWGVatVnSXQdwEK43a6Z75jERnSBEBvkYbjTAFCEcjqaa/image.gif",
                            "artifactUri": "ipfs://QmWGVatVnSXQdwEK43a6Z75jERnSBEBvkYbjTAFCEcjqaa/image.gif",
                            "description": ""
                        }
                    },
                    "to": {
                        "address": "tz1gqL7i1s578qj3NzgKmu6C5j3RdSBewGBo"
                    },
                    "amount": "10",
                    "transactionId": 285714782
                }
            ]
        """.trimIndent())
        mock("""
            [
                {
                    "id": 3404739,
                    "contract": {
                        "alias": "Rarible",
                        "address": "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS"
                    },
                    "tokenId": "77272",
                    "standard": "fa2",
                    "firstLevel": 2530589,
                    "firstTime": "2022-07-13T13:54:59Z",
                    "lastLevel": 2530589,
                    "lastTime": "2022-07-13T13:54:59Z",
                    "transfersCount": 1,
                    "balancesCount": 1,
                    "holdersCount": 1,
                    "totalMinted": "10",
                    "totalBurned": "0",
                    "totalSupply": "10",
                    "metadata": {}
                }
            ]
        """.trimIndent())
        mock("[]")
        val page1 = tokenClient.tokensByCreator("tz1gqL7i1s578qj3NzgKmu6C5j3RdSBewGBo", 1, null)
        assertThat(page1.items).hasSize(1)
        assertThat(page1.continuation).isNotEmpty
    }

    @Test
    fun `should return tokens by collection`() = runBlocking<Unit> {
        mock("""
            [{
                "id": 3404739,
                "token.contract.address": "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS",
                "token.tokenId": "77272"
            }]
        """.trimIndent())
        mock("""
            [
                {
                    "id": 3404739,
                    "contract": {
                        "alias": "Rarible",
                        "address": "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS"
                    },
                    "tokenId": "77272",
                    "standard": "fa2",
                    "firstLevel": 2530589,
                    "firstTime": "2022-07-13T13:54:59Z",
                    "lastLevel": 2530589,
                    "lastTime": "2022-07-13T13:54:59Z",
                    "transfersCount": 1,
                    "balancesCount": 1,
                    "holdersCount": 1,
                    "totalMinted": "10",
                    "totalBurned": "0",
                    "totalSupply": "10",
                    "metadata": {}
                }
            ]
        """.trimIndent())
        val page1 = tokenClient.tokensByCollection("KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS", 1, null)
        assertThat(page1.items).hasSize(1)
        assertThat(page1.continuation).isNotEmpty()

        assertThat(requests()).isEqualTo(setOf(
            "/v1/tokens/balances?token.contract=KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS&limit=1&balance.gt=0&account.ni=null,tz1burnburnburnburnburnburnburjAYjjX,tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU&sort.desc=id&select=id,token.contract.address,token.tokenId",
            "/v1/tokens?contract=KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS&tokenId.in=77272&token.standard=fa2")
        )
    }

    @Test
    fun `should return total supply = 0`() = runBlocking<Unit> {
        mock("""[{
            "id": 3073945,
            "contract": {
                "alias": "Rarible",
                "address": "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS"
            },
            "tokenId": "73086",
            "standard": "fa2",
            "firstLevel": 2424413,
            "firstTime": "2022-06-04T21:09:29Z",
            "lastLevel": 2569637,
            "lastTime": "2022-07-27T08:53:29Z",
            "transfersCount": 7,
            "balancesCount": 5,
            "holdersCount": 1,
            "totalMinted": "15",
            "totalBurned": "14",
            "totalSupply": "1"
        }]""".trimIndent())
        mock("""[
            {
                "account": {
                    "alias": "Burn Address 🔥",
                    "address": "tz1burnburnburnburnburnburnburjAYjjX"
                },
                "token.contract.address": "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS",
                "token.tokenId": "73086",
                "balance": "1"
            }
        ]""")
        val token = tokenClient.token("KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS:73086")
        assertThat(token.isDeleted()).isTrue()
    }
}
