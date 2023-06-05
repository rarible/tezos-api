package com.rarible.dipdup.e2e

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.OrderClient
import com.rarible.dipdup.client.core.model.OrderStatus
import com.rarible.dipdup.client.model.DipDupOrdersPage
import com.rarible.dipdup.client.model.DipDupSyncSort
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
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

        @Test
        fun `should sort orders by currency`() = runBlocking<Unit> {
            val page = orderClient.getOrdersByItem(contract = "KT1HVzCL4e4F4f4pRwxG9ye9oo85YB6t7cmd", tokenId = "34", maker = null, currencyId = "XTZ", continuation = null, statuses = listOf(OrderStatus.ACTIVE))

            var prev = page.orders.first().makePrice
            assertThat(prev?.toInt()).isEqualTo(5)

            page.orders.forEach { order ->
                assertThat(order.makePrice).isGreaterThanOrEqualTo(prev)
                prev = order.makePrice
            }
        }

        @Test
        fun `should iterate orders by currency`() = runBlocking<Unit> {
            val caller: suspend (c: String?) -> DipDupOrdersPage = {
                orderClient.getOrdersByItem(contract = "KT1HVzCL4e4F4f4pRwxG9ye9oo85YB6t7cmd", tokenId = "34", maker = null, currencyId = "XTZ", continuation = it, statuses = listOf(OrderStatus.ACTIVE), size = 1)
            }

            var page = caller(null)
            var continuation: String? = page.continuation
            var sum = 1

            var prev = page.orders.first().makePrice
            assertThat(prev?.toInt()).isEqualTo(5)

            do {
                var nextPage = caller(continuation)
                if (nextPage.orders.isNotEmpty()) {
                    assertThat(nextPage.orders.first()?.makePrice).isGreaterThanOrEqualTo(prev)
                    sum += 1
                }
                continuation = nextPage.continuation
            } while (continuation != null)

            assertThat(sum).isEqualTo(5)
        }
    }
}
