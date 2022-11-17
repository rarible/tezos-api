package com.rarible.dipdup.e2e

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.ActivityClient
import com.rarible.dipdup.client.OrderActivityClient
import com.rarible.dipdup.client.TokenActivityClient
import com.rarible.dipdup.client.core.model.DipDupBurnActivity
import com.rarible.dipdup.client.core.model.DipDupCancelBidActivity
import com.rarible.dipdup.client.core.model.DipDupGetBidActivity
import com.rarible.dipdup.client.core.model.DipDupMakeBidActivity
import com.rarible.dipdup.client.core.model.DipDupMintActivity
import com.rarible.dipdup.client.core.model.DipDupOrderCancelActivity
import com.rarible.dipdup.client.core.model.DipDupOrderListActivity
import com.rarible.dipdup.client.core.model.DipDupOrderSellActivity
import com.rarible.dipdup.client.core.model.DipDupTransferActivity
import com.rarible.dipdup.client.model.DipDupActivityType
import com.rarible.dipdup.client.model.DipDupSyncSort
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class ActivitiesTest {

    @Nested
    @Disabled
    class Dev {
        val client: ApolloClient =
            runBlocking { ApolloClient.Builder().serverUrl("https://dev-tezos-indexer.rarible.org/v1/graphql").build() }
        val tokenActivityClient = TokenActivityClient(client)
        val orderActivityClient = OrderActivityClient(client)
        val activityClient = ActivityClient(tokenActivityClient, orderActivityClient)

        @Test
        fun `should iterate all activities`() = runBlocking<Unit> {
            var continuation: String? = null
            var types: MutableMap<DipDupActivityType, Int> = mapOf<DipDupActivityType, Int>().toMutableMap()
            var step = 1000

            // orders
            do {
                val transfers = orderActivityClient.getActivitiesAll(
                    DipDupActivityType.values().asList(),
                    step,
                    continuation,
                    false
                )
                transfers.activities.map {
                    val t = when (it) {
                        is DipDupOrderListActivity -> DipDupActivityType.LIST
                        is DipDupOrderCancelActivity -> DipDupActivityType.CANCEL_LIST
                        is DipDupOrderSellActivity -> DipDupActivityType.SELL
                        is DipDupMakeBidActivity -> DipDupActivityType.MAKE_BID
                        is DipDupGetBidActivity -> DipDupActivityType.GET_BID
                        is DipDupCancelBidActivity -> DipDupActivityType.CANCEL_BID
                        else -> throw RuntimeException("Unsupported: $it")
                    }
                    val current = types[t] ?: 0
                    types[t] = current + 1
                }
                val sum = types.map { it.value }.sum()
                continuation = transfers.continuation
                println("$sum | $continuation | $types")
            } while (continuation != null)

            // tokens
            do {
                val transfers = tokenActivityClient.getActivitiesAll(
                    DipDupActivityType.values().asList(),
                    step,
                    continuation,
                    false
                )
                transfers.activities.map {
                    val t = when (it) {
                        is DipDupBurnActivity -> DipDupActivityType.BURN
                        is DipDupMintActivity -> DipDupActivityType.MINT
                        is DipDupTransferActivity -> DipDupActivityType.TRANSFER
                        else -> throw RuntimeException("Unsupported: $it")
                    }
                    val current = types[t] ?: 0
                    types[t] = current + 1
                }
                val sum = types.map { it.value }.sum()
                continuation = transfers.continuation
                println("$sum | $continuation | $types")
            } while (continuation != null)
        }

        @Test
        fun `should iterate nft activities via sync`() = runBlocking<Unit> {
            var continuation: String? = null
            var types: MutableMap<DipDupActivityType, Int> = mapOf<DipDupActivityType, Int>().toMutableMap()
            var step = 1000

            // tokens
            do {
                val transfers = tokenActivityClient.getActivitiesSync(step, continuation, DipDupSyncSort.DB_UPDATE_DESC)
                transfers.activities.map {
                    val t = when (it) {
                        is DipDupBurnActivity -> DipDupActivityType.BURN
                        is DipDupMintActivity -> DipDupActivityType.MINT
                        is DipDupTransferActivity -> DipDupActivityType.TRANSFER
                        else -> throw RuntimeException("Unsupported: $it")
                    }
                    val current = types[t] ?: 0
                    types[t] = current + 1
                }
                val sum = types.map { it.value }.sum()
                continuation = transfers.continuation
                println("$sum | $continuation | $types")
            } while (continuation != null)
        }

        @Test
        fun `should iterate order activities via sync`() = runBlocking<Unit> {
            var continuation: String? = null
            var types: MutableMap<DipDupActivityType, Int> = mapOf<DipDupActivityType, Int>().toMutableMap()
            var step = 1000

            // orders
            do {
                val transfers = orderActivityClient.getActivitiesSync(step, continuation, DipDupSyncSort.DB_UPDATE_DESC)
                transfers.activities.map {
                    val t = when (it) {
                        is DipDupOrderListActivity -> DipDupActivityType.LIST
                        is DipDupOrderCancelActivity -> DipDupActivityType.CANCEL_LIST
                        is DipDupOrderSellActivity -> DipDupActivityType.SELL
//                    else -> throw RuntimeException("Unsupported: $it")
                        else -> {
                            print(".")
                            null
                        } // ignore
                    }
                    t?.let {
                        val current = types[it] ?: 0
                        types[it] = current + 1
                    }
                }
                val sum = types.map { it.value }.sum()
                continuation = transfers.continuation
                println("$sum | $continuation | $types")
            } while (continuation != null)
        }

        @Test
        fun `should iterate all activities via sync`() = runBlocking<Unit> {
            var continuation: String? = null
            var types: MutableMap<DipDupActivityType, Int> = mapOf<DipDupActivityType, Int>().toMutableMap()
            var step = 1000

            // tokens
            do {
                val transfers = activityClient.getActivitiesSync(null, DipDupSyncSort.DB_UPDATE_ASC, step, continuation)
                transfers.activities.map {
                    val t = when (it) {
                        is DipDupBurnActivity -> DipDupActivityType.BURN
                        is DipDupMintActivity -> DipDupActivityType.MINT
                        is DipDupTransferActivity -> DipDupActivityType.TRANSFER
                        is DipDupOrderListActivity -> DipDupActivityType.LIST
                        is DipDupOrderCancelActivity -> DipDupActivityType.CANCEL_LIST
                        is DipDupOrderSellActivity -> DipDupActivityType.SELL
                        is DipDupMakeBidActivity -> DipDupActivityType.MAKE_BID
                        is DipDupGetBidActivity -> DipDupActivityType.GET_BID
                        is DipDupCancelBidActivity -> DipDupActivityType.CANCEL_BID
                        else -> throw RuntimeException("Unsupported: $it")
                    }
                    val current = types[t] ?: 0
                    types[t] = current + 1
                }
                val sum = types.map { it.value }.sum()
                continuation = transfers.continuation
                println("$sum | $continuation | $types")
            } while (continuation != null)
            val sum = types.map { it.value }.sum()
            println("Sum: ${sum}")
        }
    }

    @Nested
    @Disabled
    class Prod {
        val client: ApolloClient =
            runBlocking { ApolloClient.Builder().serverUrl("https://tezos-indexer.rarible.org/v1/graphql").build() }
        val tokenActivityClient = TokenActivityClient(client)
        val orderActivityClient = OrderActivityClient(client)

        @Test
        fun `should get token activities`() = runBlocking<Unit> {
            val transfers = tokenActivityClient.getActivitiesAll(
                DipDupActivityType.values().asList(),
                100,
                null,
                true
            )
            println(transfers)
        }
    }
}
