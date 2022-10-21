package com.rarible.dipdup.e2e

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.OrderActivityClient
import com.rarible.dipdup.client.TokenActivityClient
import com.rarible.dipdup.client.TokenClient
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
class ItemsTest {

    val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://testnet-tezos-indexer.rarible.org/v1/graphql").build() }
    val itemsClient = TokenClient(client)

    @Test
    fun `should iterate all items`() = runBlocking<Unit> {
        var continuation : String? = null
        var step = 1000
        var sum = 0

        do {
            val transfers = itemsClient.getTokensAll(step, false, continuation, false)
            continuation = transfers.continuation
            sum += transfers.items.size
            println("$sum | $continuation")
        } while (continuation != null)
    }
}
