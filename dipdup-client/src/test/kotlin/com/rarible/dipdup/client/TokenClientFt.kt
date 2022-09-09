package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.core.model.DipDupToken
import com.rarible.dipdup.client.core.model.TimestampIdContinuation
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

class TokenClientFt : BaseClientFt() {

    // For local testing
//    val local: ApolloClient =
//        runBlocking { ApolloClient.Builder().serverUrl("http://192.168.1.63:49180/v1/graphql").build() }
//    val tokenClient = TokenClient(local)

   val tokenClient = TokenClient(client)

    @Test
    fun `should return token`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "token": [
                  {
                    "__typename": "token",
                    "contract": "KT1Pz65ssbPF7Zv9Dh7ggqUkgAYNSuJ9iia7",
                    "deleted": false,
                    "id": "KT1Pz65ssbPF7Zv9Dh7ggqUkgAYNSuJ9iia7:1",
                    "metadata_retries": 0,
                    "metadata_synced": false,
                    "minted": "18000000.000000000000000000000000000000000000",
                    "minted_at": "2022-01-25T16:45:09+00:00",
                    "supply": "18000000.000000000000000000000000000000000000",
                    "token_id": "1",
                    "tzkt_id": 2,
                    "updated": "2022-01-25T16:45:09+00:00"
                  }
                ]
              }
            }"""
        )

        val token = tokenClient.getTokenById("KT1Pz65ssbPF7Zv9Dh7ggqUkgAYNSuJ9iia7:1")
        assertThat(token).isEqualTo(
            DipDupToken(
                contract = "KT1Pz65ssbPF7Zv9Dh7ggqUkgAYNSuJ9iia7",
                deleted = false,
                id = "KT1Pz65ssbPF7Zv9Dh7ggqUkgAYNSuJ9iia7:1",
                metadata_retries = 0,
                metadata_synced = false,
                minted = BigDecimal("18000000.000000000000000000000000000000000000"),
                minted_at = OffsetDateTime.parse("2022-01-25T16:45:09+00:00").toInstant(),
                supply = BigDecimal("18000000.000000000000000000000000000000000000"),
                token_id = BigInteger("1"),
                tzkt_id = 2,
                updated = OffsetDateTime.parse("2022-01-25T16:45:09+00:00").toInstant()
            )
        )
    }

    @Test
    fun `should return token all`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "token": [
                  {
                    "__typename": "token",
                    "contract": "KT1HWoi3YovbJfzymqZMsW3ae3r4z4LzTDzp",
                    "deleted": false,
                    "id": "KT1HWoi3YovbJfzymqZMsW3ae3r4z4LzTDzp:20",
                    "metadata_retries": 0,
                    "metadata_synced": false,
                    "minted": "1.000000000000000000000000000000000000",
                    "minted_at": "2022-09-08T14:57:10+00:00",
                    "supply": "1.000000000000000000000000000000000000",
                    "token_id": "20",
                    "tzkt_id": 113511,
                    "updated": "2022-09-08T14:57:10+00:00"
                  }
                ]
              }
            }"""
        )

        val page = tokenClient.getTokensAll(limit = 1, showDeleted = true, continuation = null, sortAsc = false)
        assertThat(page.items).hasSize(1)
        assertThat(TimestampIdContinuation.parse(page.continuation)).isNotNull()
    }

    @Test
    fun `should return token all with continuation desc`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "token": [
                  {
                    "__typename": "token",
                    "contract": "KT1DtQV5qTnxdG49GbMRdKC8fg7bpvPLNcpm",
                    "deleted": true,
                    "id": "KT1DtQV5qTnxdG49GbMRdKC8fg7bpvPLNcpm:260",
                    "metadata_retries": 0,
                    "metadata_synced": false,
                    "minted": "1.000000000000000000000000000000000000",
                    "minted_at": "2022-09-08T14:37:30+00:00",
                    "supply": "0.000000000000000000000000000000000000",
                    "token_id": "260",
                    "tzkt_id": 113503,
                    "updated": "2022-09-08T14:37:45+00:00"
                  }
                ]
              }
            }"""
        )

        val page = tokenClient.getTokensAll(
            limit = 1,
            continuation = "1662647955000_KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo:8",
            sortAsc = false
        )
        assertThat(page.items).hasSize(1)
        val continuation = TimestampIdContinuation.parse(page.continuation)
        assertThat(continuation.date.toEpochMilli() < 1662647955000).isTrue
        assertThat(continuation.id).isNotEqualTo("KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo:8")
    }

    @Test
    fun `should return token all with continuation abc`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "token": [
                  {
                    "__typename": "token",
                    "contract": "KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo",
                    "deleted": false,
                    "id": "KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo:9",
                    "metadata_retries": 0,
                    "metadata_synced": false,
                    "minted": "1.000000000000000000000000000000000000",
                    "minted_at": "2022-09-08T14:50:40+00:00",
                    "supply": "1.000000000000000000000000000000000000",
                    "token_id": "9",
                    "tzkt_id": 113509,
                    "updated": "2022-09-08T14:50:40+00:00"
                  }
                ]
              }
            }"""
        )

        val page = tokenClient.getTokensAll(
            limit = 1,
            continuation = "1662647955000_KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo:8",
            sortAsc = true
        )
        assertThat(page.items).hasSize(1)
        val continuation = TimestampIdContinuation.parse(page.continuation)
        assertThat(continuation.date.toEpochMilli() > 1662647955000).isTrue
        assertThat(continuation.id).isNotEqualTo("KT1ME54FMgDcFjWPp272DzMHkeD2DuijwJfo:8")
    }


}
