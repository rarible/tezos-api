package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.OrderClient
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import java.math.BigDecimal

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
@Disabled
class OrderClientIt {

    val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://tezos-indexer.rarible.org/v1/graphql").build() }
    val orderClient = OrderClient(client)

    @Test
    fun `should return currency for legacy order`() = runBlocking<Unit> {
        val page = orderClient.getOrdersCurrenciesByItem("KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS", "46075")
        assertThat(page).hasSize(1)
    }

    @Test
    fun `should have for legacy order`() = runBlocking<Unit> {
        val order = orderClient.getOrderById("6345c41b-b8a2-5697-8e29-1438cc5ddf6b")
        assertThat(order.fill).isBetween(BigDecimal("2.0"), order.make.assetValue)
    }
}
