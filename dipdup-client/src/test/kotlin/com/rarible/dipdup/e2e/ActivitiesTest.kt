package com.rarible.dipdup.e2e

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.OrderActivityClient
import com.rarible.dipdup.client.TokenActivityClient
import com.rarible.dipdup.client.core.model.DipDupBurnActivity
import com.rarible.dipdup.client.core.model.DipDupMintActivity
import com.rarible.dipdup.client.core.model.DipDupOrderCancelActivity
import com.rarible.dipdup.client.core.model.DipDupOrderListActivity
import com.rarible.dipdup.client.core.model.DipDupOrderSellActivity
import com.rarible.dipdup.client.core.model.DipDupTransferActivity
import com.rarible.dipdup.client.model.DipDupActivityType
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

// this test will be disabled on jenkins
@Disabled
class ActivitiesTest {

    val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://testnet-tezos-indexer.rarible.org/v1/graphql").build() }
    val tokenActivityClient = TokenActivityClient(client)
    val orderActivityClient = OrderActivityClient(client)

    @Test
    fun `should iterate all activities`() = runBlocking<Unit> {
        var continuation : String? = null
        var types : MutableMap<DipDupActivityType, Int> = mapOf<DipDupActivityType, Int>().toMutableMap()
        var step = 1000

        // orders
        do {
            val transfers = orderActivityClient.getActivitiesAll(DipDupActivityType.values().asList(), step, continuation, false)
            transfers.activities.map {
                val t = when (it) {
                    is DipDupOrderListActivity -> DipDupActivityType.LIST
                    is DipDupOrderCancelActivity -> DipDupActivityType.CANCEL_LIST
                    is DipDupOrderSellActivity -> DipDupActivityType.SELL
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
            val transfers = tokenActivityClient.getActivitiesAll(DipDupActivityType.values().asList(), step, continuation, false)
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
}
