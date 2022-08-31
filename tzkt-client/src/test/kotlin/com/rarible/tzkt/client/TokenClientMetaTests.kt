package com.rarible.tzkt.client

import com.rarible.tzkt.config.KnownAddresses
import com.rarible.tzkt.config.TzktSettings
import com.rarible.tzkt.meta.MetaService
import com.rarible.tzkt.model.TokenMeta
import com.rarible.tzkt.model.TokenMeta.Representation
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TokenClientMetaTests : BaseClientTests() {

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
    val DOGAMI = "KT1NVvPsNDChrLRH5K2cy6Sc9r1uuUwdiZQd"
    val DOGAMI_GAP = "KT1CAbPGHUWvkSA9bxMPkqSgabgsjtmRYEda"

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
        bidou24x24 = BIDOU_24x24,
        dogami = DOGAMI,
        dogamiGap = DOGAMI_GAP
    )

    val bigMapKeyClient = BigMapKeyClient(client)
    val ipfsClient = IPFSClient(client, mapper)
    val metaService = MetaService(mapper, bigMapKeyClient, ipfsClient, config)
    val tokenClient = TokenClient(client, metaService, mockk(), TzktSettings())

    @Test
    fun `should return meta from hen`() = runBlocking<Unit> {
        mock(
            """[{
            "id": 2453767,
            "contract": {
                "alias": "hic et nunc NFTs",
                "address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
            },
            "tokenId": "703680",
            "standard": "fa2",
            "firstLevel": 2221324,
            "firstTime": "2022-03-23T21:47:14Z",
            "lastLevel": 2296981,
            "lastTime": "2022-04-20T11:58:29Z",
            "transfersCount": 36,
            "balancesCount": 20,
            "holdersCount": 14,
            "totalMinted": "30",
            "totalBurned": "0",
            "totalSupply": "30",
            "metadata": {
                "name": "Smoking area 07",
                "tags": [
                    "pixelart",
                    "gif",
                    "goodvibe"
                ],
                "symbol": "OBJKT",
                "formats": [
                    {
                        "uri": "ipfs://QmUfR1S71rA7krc8YZTZbSwHEToJ2ZjNsbcQuTLfLyfLH2",
                        "mimeType": "image/gif"
                    }
                ],
                "creators": [
                    "tz1iMpEwmHDMCAydU3xTuzCNo6KV8RwdUNur"
                ],
                "decimals": "0",
                "displayUri": "ipfs://QmUfR1S71rA7krc8YZTZbSwHEToJ2ZjNsbcQuTLfLyfLH2",
                "artifactUri": "ipfs://QmUfR1S71rA7krc8YZTZbSwHEToJ2ZjNsbcQuTLfLyfLH2",
                "description": "Cappadocia, I miss you! What a magical place. Let's have some cay, Turkish delight, baklava and enjoy the balloon ride. ",
                "thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                "isBooleanAmount": false,
                "shouldPreferSymbol": false
            }
        }]"""
        )

        val meta = tokenClient.tokenMeta("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:2453767")

        assertThat(meta.name).isEqualTo("Smoking area 07")
        assertThat(meta.tags).hasSize(3)
        assertThat(meta.content.first()).isEqualTo(
            TokenMeta.Content(
                uri = "ipfs://QmUfR1S71rA7krc8YZTZbSwHEToJ2ZjNsbcQuTLfLyfLH2",
                mimeType = "image/gif",
                representation = Representation.ORIGINAL
            )
        )
        assertThat(meta.tags).containsAll(listOf("pixelart", "gif", "goodvibe"))
        assertThat(meta.description).isEqualTo("Cappadocia, I miss you! What a magical place. Let's have some cay, Turkish delight, baklava and enjoy the balloon ride. ")
    }

    @Test
    fun `should return meta from rarible`() = runBlocking<Unit> {
        mock(
            """[{
                "id": 122453767,
                "metadata": {
                    "name": "hDAO",
                    "description": "",
                    "tags": [],
                    "symbol": "OBJKT",
                    "artifactUri": "ipfs://QmSxUBJtaDRcFW8sWgGuGNpL8TY8W3HyVRLx97w8nSS3oN",
                    "creators": [
                        "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
                    ],
                    "formats": [
                        {
                            "uri": "ipfs://QmSxUBJtaDRcFW8sWgGuGNpL8TY8W3HyVRLx97w8nSS3oN",
                            "mimeType": "image/gif"
                        }
                    ],
                    "thumbnailUri": "ipfs://QmNrhZHUaEqxhyLfqoq1mtHSipkWHeT31LNHb1QEbDHgnc",
                    "decimals": 0
                }
            }]"""
        )

        val meta = tokenClient.tokenMeta("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:2453767")

        assertThat(meta.name).isEqualTo("hDAO")
        assertThat(meta.attributes).hasSize(0)
        assertThat(meta.content.first()).isEqualTo(
            TokenMeta.Content(
                uri = "ipfs://QmSxUBJtaDRcFW8sWgGuGNpL8TY8W3HyVRLx97w8nSS3oN",
                mimeType = "image/gif",
                representation = Representation.ORIGINAL
            )
        )
        assertThat(meta.description).isEqualTo("")
    }

    @Test
    fun `should return meta from objkt`() = runBlocking<Unit> {
        mock(
            """[{
            "id": 2575734,
            "contract": {
                "address": "KT1QfjybjyD9dbWgHhFMSm2x1J3s9ZookcER"
            },
            "tokenId": "1",
            "standard": "fa2",
            "firstLevel": 2260823,
            "firstTime": "2022-04-07T14:18:14Z",
            "lastLevel": 2299586,
            "lastTime": "2022-04-21T10:39:59Z",
            "transfersCount": 23,
            "balancesCount": 19,
            "holdersCount": 17,
            "totalMinted": "20",
            "totalBurned": "0",
            "totalSupply": "20",
            "metadata": {
                "date": "2022-04-07T14:17:00.811Z",
                "name": "Thinker 02: To share",
                "tags": [
                    "thinker",
                    "share",
                    "fantasy"
                ],
                "image": "ipfs://QmQM2QCBPguVc6i9TmDarRf6FW2jSKyhvKUz568qLmpJaX",
                "minter": "KT1Aq4wWmVanpQhq4TTfjZXB5AjFpx15iQMM",
                "rights": "No License / All Rights Reserved",
                "symbol": "OBJKTCOM",
                "formats": [
                    {
                        "uri": "ipfs://QmTnMV5yQiSYAxWcFMyYZR5oZj84vuhVFU2Co7i1StVUpW",
                        "fileName": "Pensador 02.mp4",
                        "fileSize": "11066237",
                        "mimeType": "video/mp4"
                    },
                    {
                        "uri": "ipfs://QmQM2QCBPguVc6i9TmDarRf6FW2jSKyhvKUz568qLmpJaX",
                        "fileName": "cover-Pensador 02.jpeg",
                        "fileSize": "530557",
                        "mimeType": "image/jpeg",
                        "dimensions": {
                            "unit": "px",
                            "value": "768x768"
                        }
                    },
                    {
                        "uri": "ipfs://QmSbHHMk8dEEjFGYLrsZCiLxnvAjWUAPc61vWHxxZfB5eE",
                        "fileName": "thumbnail-Pensador 02.jpeg",
                        "fileSize": "142799",
                        "mimeType": "image/jpeg",
                        "dimensions": {
                            "unit": "px",
                            "value": "350x350"
                        }
                    }
                ],
                "creators": [
                    "tz1WSnvqNRrK8HJeK2bCp5uxUgLnNjzbHZd7"
                ],
                "decimals": "0",
                "royalties": {
                    "shares": {
                        "tz1WSnvqNRrK8HJeK2bCp5uxUgLnNjzbHZd7": "250"
                    },
                    "decimals": "3"
                },
                "attributes": [],
                "displayUri": "ipfs://QmQM2QCBPguVc6i9TmDarRf6FW2jSKyhvKUz568qLmpJaX",
                "artifactUri": "ipfs://QmTnMV5yQiSYAxWcFMyYZR5oZj84vuhVFU2Co7i1StVUpW",
                "description": "Sharing is growing",
                "thumbnailUri": "ipfs://QmSbHHMk8dEEjFGYLrsZCiLxnvAjWUAPc61vWHxxZfB5eE",
                "isBooleanAmount": false,
                "shouldPreferSymbol": false
            }
        }]"""
        )

        val meta = tokenClient.tokenMeta("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:2453767")

        assertThat(meta.name).isEqualTo("Thinker 02: To share")
        assertThat(meta.tags).hasSize(3)
        assertThat(meta.content).contains(
            TokenMeta.Content(
                uri = "ipfs://QmTnMV5yQiSYAxWcFMyYZR5oZj84vuhVFU2Co7i1StVUpW",
                mimeType = "video/mp4",
                representation = Representation.ORIGINAL
            )
        )
        assertThat(meta.content).contains(
            TokenMeta.Content(
                uri = "ipfs://QmSbHHMk8dEEjFGYLrsZCiLxnvAjWUAPc61vWHxxZfB5eE",
                mimeType = "image/jpeg",
                representation = Representation.PREVIEW
            )
        )
        assertThat(meta.content).contains(
            TokenMeta.Content(
                uri = "ipfs://QmQM2QCBPguVc6i9TmDarRf6FW2jSKyhvKUz568qLmpJaX",
                mimeType = "image/jpeg",
                representation = Representation.BIG
            )
        )
        assertThat(meta.tags).containsAll(listOf("thinker","share","fantasy"))
        assertThat(meta.description).isEqualTo("Sharing is growing")
    }

    @Test
    fun `should return meta from 8Bidou 8x8`() = runBlocking<Unit> {
        mock(
            """
                [
                    {
                        "id": 2234888,
                        "contract": {
                            "alias": "8bidou 8x8",
                            "address": "KT1MxDwChiDwd6WBVs24g1NjERUoK622ZEFp"
                        },
                        "tokenId": "69",
                        "standard": "fa2",
                        "firstLevel": 2139139,
                        "firstTime": "2022-02-22T08:30:10Z",
                        "lastLevel": 2228095,
                        "lastTime": "2022-03-26T09:23:54Z",
                        "transfersCount": 14,
                        "balancesCount": 10,
                        "holdersCount": 9,
                        "totalMinted": "10",
                        "totalBurned": "0",
                        "totalSupply": "10"
                    }
                ]
            """.trimIndent()
        )
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

        val meta = tokenClient.tokenMeta("KT1MxDwChiDwd6WBVs24g1NjERUoK622ZEFp:69")

        assertThat(meta.name).isEqualTo("f09f928038c9aec9a8c8b6d684ca8ad5bcd3bcf09f9280")
        assertThat(meta.description).isEqualTo("f09fa498e29aa1efb88f")
        assertThat(meta.attributes).hasSize(2)
        assertThat(meta.content.first()).isEqualTo(
            TokenMeta.Content(
                //TODO: to update once image generation is done
                uri = "",
                mimeType = "image/jpeg",
                representation = Representation.PREVIEW
            )
        )
        assertThat(meta.attributes.map { it.key }).containsAll(listOf("creator", "creator_name"))
    }

    @Test
    fun `should return meta from 8Bidou 24x24`() = runBlocking<Unit> {
        mock(
            """
                [
                    {
                        "id": 2234888,
                        "contract": {
                            "alias": "8bidou 8x8",
                            "address": "KT1TR1ErEQPTdtaJ7hbvKTJSa1tsGnHGZTpf"
                        },
                        "tokenId": "69",
                        "standard": "fa2",
                        "firstLevel": 2139139,
                        "firstTime": "2022-02-22T08:30:10Z",
                        "lastLevel": 2228095,
                        "lastTime": "2022-03-26T09:23:54Z",
                        "transfersCount": 14,
                        "balancesCount": 10,
                        "holdersCount": 9,
                        "totalMinted": "10",
                        "totalBurned": "0",
                        "totalSupply": "10"
                    }
                ]
            """.trimIndent()
        )
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

        val meta = tokenClient.tokenMeta("KT1TR1ErEQPTdtaJ7hbvKTJSa1tsGnHGZTpf:69")

        assertThat(meta.name).isEqualTo("f09f928038c9aec9a8c8b6d684ca8ad5bcd3bcf09f9280")
        assertThat(meta.description).isEqualTo("f09fa498e29aa1efb88f")
        assertThat(meta.attributes).hasSize(2)
        assertThat(meta.content.first()).isEqualTo(
            TokenMeta.Content(
                //TODO: to update once image generation is done
                uri = "",
                mimeType = "image/jpeg",
                representation = Representation.PREVIEW
            )
        )
        assertThat(meta.attributes.map { it.key }).containsAll(listOf("creator", "creator_name"))
    }

    @Test
    fun `should return meta from ipfs (with tzkt not providing meta)`() = runBlocking<Unit> {
        mock(
            """
                [
                    {
                        "id": 2399760,
                        "contract": {
                            "address": "KT1PKFgv1ZrDVJoYcWczT1eDpw7qezbjxwKw"
                        },
                        "tokenId": "38",
                        "standard": "fa2",
                        "firstLevel": 2203301,
                        "firstTime": "2022-03-17T07:49:44Z",
                        "lastLevel": 2203301,
                        "lastTime": "2022-03-17T07:49:44Z",
                        "transfersCount": 1,
                        "balancesCount": 1,
                        "holdersCount": 1,
                        "totalMinted": "5",
                        "totalBurned": "0",
                        "totalSupply": "5"
                    }
                ]
            """.trimIndent()
        )
        mock(
            """
                {
                	"id": 21154224,
                	"active": true,
                	"hash": "expru5TKqwxDRYQUdNBnFH9byzyLDYppZGbof8MPP8MFkxRdjBja9b",
                	"key": "38",
                	"value": {
                		"token_id": "38",
                		"token_info": {
                			"": "697066733a2f2f516d4e705a5077696d4e6f35446d385454714e6b4b576f5931446e69673674786a73564c77626931683144614362"
                		}
                	},
                	"firstLevel": 2203301,
                	"lastLevel": 2203301,
                	"updates": 1
                }
            """.trimIndent()
        )
        mock(
            """
                {
                	"name": "FFC 032",
                	"decimals": 0,
                	"description": "Fabulous Architectural Flourish 032 from the city of Capreesh ",
                	"artifactUri": "ipfs://QmSAgKM9HsHA9KEiQxqKiVdHY4agLfsTnZQVeU7CquT68G/image.jpeg",
                	"displayUri": "ipfs://QmSAgKM9HsHA9KEiQxqKiVdHY4agLfsTnZQVeU7CquT68G/image.jpeg",
                	"attributes": [],
                	"formats": [{
                		"mimeType": "image/jpeg",
                		"fileSize": 13487767,
                		"fileName": "FFC_032.jpg",
                		"uri": "ipfs://QmSAgKM9HsHA9KEiQxqKiVdHY4agLfsTnZQVeU7CquT68G/image.jpeg"
                	}]
                }
            """.trimIndent()
        )

        val meta = tokenClient.tokenMeta("KT1PKFgv1ZrDVJoYcWczT1eDpw7qezbjxwKw:38")
        assertThat(meta.name).isEqualTo("FFC 032")
        assertThat(meta.tags).hasSize(0)
        assertThat(meta.content).contains(
            TokenMeta.Content(
                uri = "ipfs://QmSAgKM9HsHA9KEiQxqKiVdHY4agLfsTnZQVeU7CquT68G/image.jpeg",
                mimeType = "image/jpeg",
                representation = Representation.ORIGINAL
            )
        )
        assertThat(meta.description).isEqualTo("Fabulous Architectural Flourish 032 from the city of Capreesh ")
    }

    @Test
    fun `should return content from display & artifact uri`() = runBlocking<Unit> {
        mock("""[{
                    "id": 1231620,
                    "contract": {
                        "alias": "Rarible",
                        "address": "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS"
                    },
                    "tokenId": "3905",
                    "standard": "fa2",
                    "firstLevel": 1951143,
                    "firstTime": "2021-12-16T21:41:16Z",
                    "lastLevel": 1951143,
                    "lastTime": "2021-12-16T21:41:16Z",
                    "transfersCount": 1,
                    "balancesCount": 1,
                    "holdersCount": 1,
                    "totalMinted": "1",
                    "totalBurned": "0",
                    "totalSupply": "1",
                    "metadata": {
                        "name": "Nest ",
                        "attributes": [],
                        "displayUri": "ipfs://QmVahURZU7xaNn3u714ZrSkAQbQm542qEXeNp9Zhd56jHB/image.jpeg",
                        "artifactUri": "ipfs://QmVahURZU7xaNn3u714ZrSkAQbQm542qEXeNp9Zhd56jHB/image.jpeg",
                        "description": "Maybe one day this white hawk will pass above our house\n\nشايد روزي اين باز سپيد گذر كند از خانه ما\n\n\nLocation: asalem,gilan,iran\n\n",
                        "externalUri": "https://rarible.com/token/KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS:3905"
                    }
                }]""".trimIndent())

        val meta = tokenClient.tokenMeta("KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS:3905")
        assertThat(meta.name).isEqualTo("Nest ")
        assertThat(meta.content).contains(
            TokenMeta.Content(
                uri = "ipfs://QmVahURZU7xaNn3u714ZrSkAQbQm542qEXeNp9Zhd56jHB/image.jpeg",
                mimeType = "image/jpeg",
                representation = Representation.PREVIEW
            )
        )
        assertThat(meta.content).contains(
            TokenMeta.Content(
                uri = "ipfs://QmVahURZU7xaNn3u714ZrSkAQbQm542qEXeNp9Zhd56jHB/image.jpeg",
                mimeType = "image/jpeg",
                representation = Representation.ORIGINAL
            )
        )
    }
}
