package com.rarible.dipdup.client

import com.rarible.dipdup.client.core.model.DipDupOwnership
import com.rarible.dipdup.client.core.model.TimestampIdContinuation
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

class OwnershipClientFt : BaseClientFt() {

    // For local testing
//    val local: ApolloClient =
//        runBlocking { ApolloClient.Builder().serverUrl("http://192.168.1.63:49180/v1/graphql").build() }
//    val owneshipClient = OwneshipClient(local)

   val owneshipClient = OwneshipClient(client)

    @Test
    fun `should return ownership`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "ownership": [
                  {
                    "__typename": "ownership",
                    "balance": "50.000000000000000000000000000000000000",
                    "contract": "KT1HS9P3RzaUuJjioPvPCJXH3Vk2wgmraAg5",
                    "id": "KT1HS9P3RzaUuJjioPvPCJXH3Vk2wgmraAg5:0:tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1",
                    "owner": "tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1",
                    "token_id": "0",
                    "updated": "2022-04-08T22:35:10+00:00"
                  }
                ]
              }
            }"""
        )

        val ownership =
            owneshipClient.getOwnershipById("KT1HS9P3RzaUuJjioPvPCJXH3Vk2wgmraAg5:0:tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1")
        assertThat(ownership).isEqualTo(
            DipDupOwnership(
                id = "KT1HS9P3RzaUuJjioPvPCJXH3Vk2wgmraAg5:0:tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1",
                contract = "KT1HS9P3RzaUuJjioPvPCJXH3Vk2wgmraAg5",
                tokenId = BigInteger("0"),
                owner = "tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1",
                balance = BigDecimal("50.000000000000000000000000000000000000"),
                updated = OffsetDateTime.parse("2022-04-08T22:35:10Z").toInstant()
            )
        )
    }

    @Test
    fun `should return ownerships all`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "ownership": [
                  {
                    "__typename": "ownership",
                    "balance": "50.000000000000000000000000000000000000",
                    "contract": "KT1HS9P3RzaUuJjioPvPCJXH3Vk2wgmraAg5",
                    "id": "KT1HS9P3RzaUuJjioPvPCJXH3Vk2wgmraAg5:0:tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1",
                    "owner": "tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1",
                    "token_id": "0",
                    "updated": "2022-04-08T22:35:10+00:00"
                  }
                ]
              }
            }"""
        )

        val page = owneshipClient.getOwnershipsAll(limit = 1, continuation = null, sortAsc = false)
        assertThat(page.items).hasSize(1)
        assertThat(TimestampIdContinuation.parse(page.continuation)).isNotNull()
    }

    @Test
    fun `should return ownerships all with continuation desc`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "ownership": [
                    {
                        "__typename": "ownership",
                        "balance": "18000000.000000000000000000000000000000000000",
                        "contract": "KT19Wk4FYFd9LgEnqB7badwRyUGoPQdg7GiY",
                        "id": "KT19Wk4FYFd9LgEnqB7badwRyUGoPQdg7GiY:1:tz3Q3zwaTxvWkZme6TD7HNLNJ1aUWya8U1nf",
                        "owner": "tz3Q3zwaTxvWkZme6TD7HNLNJ1aUWya8U1nf",
                        "token_id": "1",
                        "updated": "2022-04-08T22:45:45+00:00"
                      }
                ]
              }
            }"""
        )

        val page = owneshipClient.getOwnershipsAll(
            limit = 1,
            continuation = "1662730295000_KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo:14:tz1UqQsGsTnw39kwCyhVp2YwhsgKpbBMmNVb",
            sortAsc = false
        )
        assertThat(page.items).hasSize(1)
        val continuation = TimestampIdContinuation.parse(page.continuation)
        assertThat(continuation.date.toEpochMilli() < 1662730295000).isTrue()
        assertThat(continuation.id).isNotEqualTo("KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo:14:tz1UqQsGsTnw39kwCyhVp2YwhsgKpbBMmNVb")
    }

    @Test
    fun `should return ownerships all with continuation asc`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "ownership": [
                    {
                        "__typename": "ownership",
                        "balance": "18000000.000000000000000000000000000000000000",
                        "contract": "KT19Wk4FYFd9LgEnqB7badwRyUGoPQdg7GiY",
                        "id": "KT19Wk4FYFd9LgEnqB7badwRyUGoPQdg7GiY:1:tz3Q3zwaTxvWkZme6TD7HNLNJ1aUWya8U1nf",
                        "owner": "tz3Q3zwaTxvWkZme6TD7HNLNJ1aUWya8U1nf",
                        "token_id": "1",
                        "updated": "2022-09-10T22:45:45+00:00"
                      }
                ]
              }
            }"""
        )

        val page = owneshipClient.getOwnershipsAll(
            limit = 1,
            continuation = "1662726170000_KT1HwULLUDRsNDdbFxGNviZ7xJByhCx5tuDL:2:tz1hSkdiNqtMoaEZan1JPt4AsWQtppGK5EYq",
            sortAsc = true
        )
        assertThat(page.items).hasSize(1)
        val continuation = TimestampIdContinuation.parse(page.continuation)
        assertThat(continuation.date.toEpochMilli() > 1662726170000).isTrue
        assertThat(continuation.id).isNotEqualTo("KT1HwULLUDRsNDdbFxGNviZ7xJByhCx5tuDL:2:tz1hSkdiNqtMoaEZan1JPt4AsWQtppGK5EYq")
    }

    @Test
    fun `should return ownerships by item`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "ownership": [
                  {
                    "__typename": "ownership",
                    "balance": "50.000000000000000000000000000000000000",
                    "contract": "KT1XsMpwriRTDRdD38QtmdeLqVQxt9eTL2ys",
                    "id": "KT1XsMpwriRTDRdD38QtmdeLqVQxt9eTL2ys:0:tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1",
                    "owner": "tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1",
                    "token_id": "0",
                    "updated": "2022-04-08T22:35:10+00:00"
                  }
                ]
              }
            }"""
        )

        val page = owneshipClient.getOwnershipsByItem(
            limit = 1,
            continuation = null,
            itemId = "KT1XsMpwriRTDRdD38QtmdeLqVQxt9eTL2ys:0"
        )
        assertThat(page.items).hasSize(1)
        assertThat(TimestampIdContinuation.parse(page.continuation)).isNotNull()
    }

    @Test
    fun `should return ownerships by item with continuation`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "ownership": [
                  {
                    "__typename": "ownership",
                    "balance": "50.000000000000000000000000000000000000",
                    "contract": "KT1HS9P3RzaUuJjioPvPCJXH3Vk2wgmraAg5",
                    "id": "KT1HS9P3RzaUuJjioPvPCJXH3Vk2wgmraAg5:0:tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1",
                    "owner": "tz1c1X8vD4pKV9TgV1cyosR7qdnkc8FTEyM1",
                    "token_id": "0",
                    "updated": "2022-04-08T22:35:10+00:00"
                  }
                ]
              }
            }"""
        )

        val page = owneshipClient.getOwnershipsByItem(
            limit = 1,
            continuation = "1656577825000_KT1XsMpwriRTDRdD38QtmdeLqVQxt9eTL2ys:0:tz2JLHVycezFDPzoQZ5Y1kjND1wY5wr9v8TU",
            itemId = "KT1XsMpwriRTDRdD38QtmdeLqVQxt9eTL2ys:0"
        )
        assertThat(page.items).hasSize(1)
        assertThat(TimestampIdContinuation.parse(page.continuation)).isNotNull()
    }
}
