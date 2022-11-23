package com.rarible.dipdup.e2e

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.OrderClient
import com.rarible.dipdup.client.model.DipDupSyncSort
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class OrdersTest {

    @Nested
    @Disabled
    class Dev {
        val client: ApolloClient =
            runBlocking { ApolloClient.Builder().serverUrl("https://dev-tezos-indexer.rarible.org/v1/graphql").build() }
        val orderClient = OrderClient(client)

        @Test
        fun `should iterate orders via sync`() = runBlocking<Unit> {
            var continuation: String? = null
            var step = 1000
            var sum = 0

            do {
                val page = orderClient.getOrdersSync(step, continuation, DipDupSyncSort.DB_UPDATE_DESC)

                sum += page.orders.size
                continuation = page.continuation
                println("$sum | $continuation")
            } while (continuation != null)
        }
    }

    @Nested
    @Disabled
    class Prod {
        val client: ApolloClient =
            runBlocking { ApolloClient.Builder().serverUrl("https://tezos-indexer.rarible.org/v1/graphql").build() }
        val orderClient = OrderClient(client)

        @Test
        fun `should iterate orders via sync`() = runBlocking<Unit> {
            var continuation: String? = null
            var step = 1000
            var sum = 0

            do {
                val page = orderClient.getOrdersSync(step, continuation, DipDupSyncSort.DB_UPDATE_DESC)

                sum += page.orders.size
                continuation = page.continuation
                println("$sum | $continuation")
            } while (continuation != null)
        }
    }
}
