package com.rarible.tzkt.client

import com.rarible.tzkt.meta.MetaService
import com.rarible.tzkt.model.TokenMeta
import com.rarible.tzkt.model.TokenMeta.Representation
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TokenClientMetaTests : BaseClientTests() {

    val metaService = MetaService(mapper)
    val tokenClient = TokenClient(client, metaService, mockk())

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

        val meta = tokenClient.tokenMeta("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton", "2453767")

        assertThat(meta.name).isEqualTo("Smoking area 07")
        assertThat(meta.attributes).hasSize(3)
        assertThat(meta.content.first()).isEqualTo(
            TokenMeta.Content(
                uri = "ipfs://QmUfR1S71rA7krc8YZTZbSwHEToJ2ZjNsbcQuTLfLyfLH2",
                mimeType = "image/gif",
                representation = Representation.ORIGINAL
            )
        )
        assertThat(meta.attributes.map { it.key }).containsAll(listOf("pixelart", "gif", "goodvibe"))
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

        val meta = tokenClient.tokenMeta("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton", "2453767")

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

        val meta = tokenClient.tokenMeta("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton", "2453767")

        assertThat(meta.name).isEqualTo("Thinker 02: To share")
        assertThat(meta.attributes).hasSize(3)
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
        assertThat(meta.attributes.map { it.key }).containsAll(listOf("thinker","share","fantasy"))
        assertThat(meta.description).isEqualTo("Sharing is growing")
    }
}
