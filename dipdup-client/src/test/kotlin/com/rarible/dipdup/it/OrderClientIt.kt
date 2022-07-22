package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.OrderClient
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
class OrderClientIt {

    val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://tezos-indexer.rarible.org/v1/graphql").build() }
    val orderClient = OrderClient(client)

    @Test
    fun `should return currency for legacy order`() = runBlocking<Unit> {
        val page = orderClient.getOrdersCurrenciesByItem("KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS", "46075")
        assertThat(page).hasSize(1)
    }

}
