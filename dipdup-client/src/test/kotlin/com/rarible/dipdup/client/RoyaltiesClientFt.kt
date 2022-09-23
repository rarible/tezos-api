package com.rarible.dipdup.client

import com.rarible.dipdup.client.core.model.DipDupItem
import com.rarible.dipdup.client.core.model.DipDupRoyalties
import com.rarible.dipdup.client.core.model.Part
import com.rarible.dipdup.client.core.model.TimestampIdContinuation
import com.rarible.dipdup.client.core.model.TokenMeta
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.time.OffsetDateTime

class RoyaltiesClientFt : BaseClientFt() {

    // For local testing
//    val local: ApolloClient =
//        runBlocking { ApolloClient.Builder().serverUrl("https://dev-tezos-indexer.rarible.org/v1/graphql").build() }
//    val tokenClient = TokenClient(local)

   val royaltiesClient = RoyaltiesClient(client)

    @Test
    fun `should return royalties`() = runBlocking<Unit> {
        mock(
            """
                {
                  "data": {
                    "royalties": [
                      {
                        "__typename": "royalties",
                        "id": "8972a13b-2c50-5f50-b2ad-8b21c0a69708",
                        "parts": [
                          {
                            "part_value": "0",
                            "part_account": "tz1hL2a2F5thFfGJUkcSR2fRko3G2NRaHucK"
                          }
                        ],
                        "token_id": "182",
                        "contract": "KT1K1RoMrQkqQG5qioYisjpsLLFgD1U9bRfm",
                        "db_updated_at": "2022-09-23T12:48:06.585213+00:00"
                      }
                    ]
                  }
                }
            """.trimIndent()
        )

        val token = royaltiesClient.getRoyaltiesById("KT1K1RoMrQkqQG5qioYisjpsLLFgD1U9bRfm:182") // 53802e5b-8ff1-5db5-b3b3-2d5e610c7b14
        assertThat(token).isEqualTo(
            DipDupRoyalties(
                id = "KT1K1RoMrQkqQG5qioYisjpsLLFgD1U9bRfm:182",
                contract = "KT1K1RoMrQkqQG5qioYisjpsLLFgD1U9bRfm",
                tokenId = BigInteger("182"),
                updated = OffsetDateTime.parse("2022-09-23T12:48:06.585213Z").toInstant(),
                parts = listOf(Part(account = "tz1hL2a2F5thFfGJUkcSR2fRko3G2NRaHucK", value = 0 )),
            )
        )
    }
//
//    @Test
//    fun `should return token all`() = runBlocking<Unit> {
//        mock(
//            """{
//              "data": {
//                "token_with_meta": [
//                  {
//                    "__typename": "token",
//                    "contract": "KT1HWoi3YovbJfzymqZMsW3ae3r4z4LzTDzp",
//                    "deleted": false,
//                    "id": "8f378bc9-d266-57f3-b117-59547e0215df",
//                    "minted": "1.000000000000000000000000000000000000",
//                    "minted_at": "2022-09-08T14:57:10+00:00",
//                    "supply": "1.000000000000000000000000000000000000",
//                    "token_id": "20",
//                    "tzkt_id": 113511,
//                    "updated": "2022-09-08T14:57:10+00:00",
//                    "metadata": "{'artifactUri': 'ipfs://QmUaSSCpDs75Mnfur1TRRzYPxxx4U6G3uCkzAByZaTqc9S/kryptosphere.mp4', 'attributes': [{'name': 'scanned', 'value': 'false'}, {'name': 'revealed', 'value': 'false'}, {'name': 'rarity', 'value': 'unknown'}], 'creators': ['Billyapp.live team'], 'decimals': 0, 'description': 'Crypto tour event by Kryptosphere, Marseille, 22/10/2022', 'displayUri': 'ipfs://QmUaSSCpDs75Mnfur1TRRzYPxxx4U6G3uCkzAByZaTqc9S/kryptosphere.png', 'formats': [{'mimeType': 'video/mp4', 'uri': 'ipfs://QmUaSSCpDs75Mnfur1TRRzYPxxx4U6G3uCkzAByZaTqc9S/kryptosphere.mp4'}, {'mimeType': 'image/png', 'uri': 'ipfs://QmUaSSCpDs75Mnfur1TRRzYPxxx4U6G3uCkzAByZaTqc9S/kryptosphere.png'}, {'mimeType': 'image/png', 'uri': 'ipfs://QmUaSSCpDs75Mnfur1TRRzYPxxx4U6G3uCkzAByZaTqc9S/kryptosphere_thumbnail.png'}], 'name': 'Crypto tour', 'rights': '©Billy 2022 Rights. All rights reserved.', 'royalties': {'decimals': 2, 'shares': {'tz1PJ5xxUFDAwgKHLMUPe5SL3eJkVehfiDL6': '10'}}, 'symbol': 'XTZ', 'thumbnailUri': 'ipfs://QmUaSSCpDs75Mnfur1TRRzYPxxx4U6G3uCkzAByZaTqc9S/kryptosphere_thumbnail.png'}"
//                  }
//                ]
//              }
//            }"""
//        )
//
//        val page = royaltiesClient.getTokensAll(limit = 1, showDeleted = true, continuation = null, sortAsc = false)
//        assertThat(page.items).hasSize(1)
//        assertThat(TimestampIdContinuation.parse(page.continuation)).isNotNull()
//    }
//
//    @Test
//    fun `should return token all with continuation desc`() = runBlocking<Unit> {
//        mock(
//            """{
//              "data": {
//                "token_with_meta": [
//                  {
//                    "__typename": "token",
//                    "contract": "KT1DtQV5qTnxdG49GbMRdKC8fg7bpvPLNcpm",
//                    "deleted": true,
//                    "id": "8f378bc9-d266-57f3-b117-59547e0215df",
//                    "minted": "1.000000000000000000000000000000000000",
//                    "minted_at": "2022-09-08T14:37:30+00:00",
//                    "supply": "0.000000000000000000000000000000000000",
//                    "token_id": "260",
//                    "tzkt_id": 113503,
//                    "updated": "2022-09-08T14:37:45+00:00",
//                    "metadata": "{'name': 'FFF', 'description': '', 'image': 'ipfs://ipfs/QmPe1W8XiUYdsvdpvgWVwekQ7cDQivRMak3YPX15vxvSJs/image.png', 'external_url': 'https://rinkeby.rarible.com/token/0x509fd4cdaa29be7b1fad251d8ea0fca2ca91eb60:18', 'attributes': [], 'decimals': 1, 'symbol': 'FFF'}"
//                  }
//                ]
//              }
//            }"""
//        )
//
//        val page = royaltiesClient.getTokensAll(
//            limit = 1,
//            continuation = "1662647955000_KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo:8",
//            sortAsc = false
//        )
//        assertThat(page.items).hasSize(1)
//        val continuation = TimestampIdContinuation.parse(page.continuation)
//        assertThat(continuation.date.toEpochMilli() < 1662647955000).isTrue
//        assertThat(continuation.id).isNotEqualTo("KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo:8")
//    }
//
//    @Test
//    fun `should return token all with continuation abc`() = runBlocking<Unit> {
//        mock(
//            """{
//              "data": {
//                "token_with_meta": [
//                  {
//                    "__typename": "token",
//                    "contract": "KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo",
//                    "deleted": false,
//                    "id": "8f378bc9-d266-57f3-b117-59547e0215df",
//                    "minted": "1.000000000000000000000000000000000000",
//                    "minted_at": "2022-09-08T14:50:40+00:00",
//                    "supply": "1.000000000000000000000000000000000000",
//                    "token_id": "9",
//                    "tzkt_id": 113509,
//                    "updated": "2022-09-08T14:50:40+00:00",
//                    "metadata": null
//                  }
//                ]
//              }
//            }"""
//        )
//
//        val page = royaltiesClient.getTokensAll(
//            limit = 1,
//            continuation = "1662647955000_KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo:8",
//            sortAsc = true
//        )
//        assertThat(page.items).hasSize(1)
//        val continuation = TimestampIdContinuation.parse(page.continuation)
//        assertThat(continuation.date.toEpochMilli() > 1662647955000).isTrue
//        assertThat(continuation.id).isNotEqualTo("KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo:8")
//    }
//

}
