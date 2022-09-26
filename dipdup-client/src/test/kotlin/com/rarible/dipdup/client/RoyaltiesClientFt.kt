package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
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
//    val royaltiesClient = RoyaltiesClient(local)

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

    @Test
    fun `should return royalties all`() = runBlocking<Unit> {
        mock(
            """
                {
                  "data": {
                    "royalties": [
                      {
                        "__typename": "royalties",
                        "id": "3c8296eb-0026-5a8e-a372-8a4d79f579e7",
                        "contract": "KT1XTKgrxyaqV8gWUHkwJmar2be4AGjHCENH",
                        "db_updated_at": "2022-09-23T12:58:11.881682+00:00",
                        "parts": [
                          {
                            "part_value": "0",
                            "part_account": "tz3dZ5sB9uvmCV6UTB4DcFE1tnzTeY8AjBsw"
                          }
                        ],
                        "royalties_retries": 0,
                        "royalties_synced": true,
                        "token_id": "0"
                      }
                    ]
                  }
                }
            """.trimIndent()
        )

        val page = royaltiesClient.getRoyaltiesAll(limit = 1, continuation = null, sortAsc = false)
        assertThat(page.items).hasSize(1)
        assertThat(TimestampIdContinuation.parse(page.continuation)).isNotNull()
    }

    @Test
    fun `should return royalties all with continuation desc`() = runBlocking<Unit> {
        mock(
            """
                {
                  "data": {
                    "royalties": [
                      {
                        "__typename": "royalties",
                        "id": "38f59907-5df1-564e-b6d9-5ac45e0228117",
                        "parts": [
                          {
                            "part_value": "0",
                            "part_account": "tz3efcEPzDxEdjd8N1KFTGiyAWnnMAJ4BtDR"
                          }
                        ],
                        "token_id": "1",
                        "royalties_synced": true,
                        "royalties_retries": 0,
                        "contract": "KT1FdHwNfpZnRm2dDxNtv1B2SFyx541xww3a",
                        "db_updated_at": "2022-09-26T10:57:58+00:00"
                      }
                    ]
                  }
                }
            """.trimIndent()
        )

        val page = royaltiesClient.getRoyaltiesAll(
            limit = 1,
            continuation = "1664189878000_KT1XTKgrxyaqV8gWUHkwJmar2be4AGjHCENH:0",
            sortAsc = false
        )
        assertThat(page.items).hasSize(1)
        val continuation = TimestampIdContinuation.parse(page.continuation)
        assertThat(continuation.date.toEpochMilli() <= 1664189878000).isTrue
        assertThat(continuation.id).isNotEqualTo("KT1XTKgrxyaqV8gWUHkwJmar2be4AGjHCENH:0")
    }


    @Test
    fun `should return royalties all with continuation asc`() = runBlocking<Unit> {
        mock(
            """
                {
                  "data": {
                    "royalties": [
                      {
                        "__typename": "royalties",
                        "id": "4714c5de-535a-58fd-b3eb-beedf0f43c2a",
                        "contract": "KT1HokWL7t295RaWEoFFKcr8JKUtnTNi5qWS",
                        "db_updated_at": "2022-09-26T10:57:58+00:00",
                        "parts": [
                          {
                            "part_value": "0",
                            "part_account": "tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1"
                          }
                        ],
                        "royalties_retries": 0,
                        "royalties_synced": true,
                        "token_id": "0"
                      }
                    ]
                  }
                }
            """.trimIndent()
        )

        val page = royaltiesClient.getRoyaltiesAll(
            limit = 1,
            continuation = "1664189878000_KT1XTKgrxyaqV8gWUHkwJmar2be4AGjHCENH:0",
            sortAsc = true
        )
        assertThat(page.items).hasSize(1)
        val continuation = TimestampIdContinuation.parse(page.continuation)
        assertThat(continuation.date.toEpochMilli() >= 1664189878000).isTrue
        assertThat(continuation.id).isNotEqualTo("KT1XTKgrxyaqV8gWUHkwJmar2be4AGjHCENH:0")
    }


}
