package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.core.model.DipDupItem
import com.rarible.dipdup.client.core.model.TimestampIdContinuation
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.time.OffsetDateTime

class TokenClientFt : BaseClientFt() {

    // For local testing
//    val local: ApolloClient =
//        runBlocking { ApolloClient.Builder().serverUrl("https://dev-tezos-indexer.rarible.org/v1/graphql").build() }
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
                    "contract": "KT1AxXMGmoQUH3wdDF5gqpANSGKHpTR4xsaj",
                    "db_updated_at": "2022-09-20T10:04:23.840596+00:00",
                    "deleted": false,
                    "id": "8f378bc9-d266-57f3-b117-59547e0215df",
                    "updated": "2022-09-20T09:50:45+00:00",
                    "tzkt_id": "120741",
                    "token_id": "3",
                    "supply": "1.000000000000000000000000000000000000",
                    "minted_at": "2022-09-20T09:50:45+00:00",
                    "minted": "1.000000000000000000000000000000000000",
                    "metadata_synced": false,
                    "metadata_retries": 0
                  }
                ]
              }
            }"""
        )

        val token = tokenClient.getTokenById("KT1AxXMGmoQUH3wdDF5gqpANSGKHpTR4xsaj:3") // 53802e5b-8ff1-5db5-b3b3-2d5e610c7b14
        assertThat(token).isEqualTo(
            DipDupItem(
                contract = "KT1AxXMGmoQUH3wdDF5gqpANSGKHpTR4xsaj",
                deleted = false,
                id = "KT1AxXMGmoQUH3wdDF5gqpANSGKHpTR4xsaj:3",
                metadataRetries = 0,
                metadataSynced = false,
                minted = BigInteger("1"),
                mintedAt = OffsetDateTime.parse("2022-09-20T09:50:45Z").toInstant(),
                supply = BigInteger("1"),
                tokenId = BigInteger("3"),
                tzktId = 120741,
                updated = OffsetDateTime.parse("2022-09-20T09:50:45Z").toInstant()
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
                    "id": "8f378bc9-d266-57f3-b117-59547e0215df",
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
                    "id": "8f378bc9-d266-57f3-b117-59547e0215df",
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
                    "id": "8f378bc9-d266-57f3-b117-59547e0215df",
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
