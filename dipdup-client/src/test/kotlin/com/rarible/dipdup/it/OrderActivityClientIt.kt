package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.OrderActivityClient
import com.rarible.dipdup.client.model.DipDupActivityType
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
class OrderActivityClientIt {

    val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://test-tezos-indexer.rarible.org/v1/graphql").build() }
    val orderActivityClient = OrderActivityClient(client)

    @Test
    @Disabled
    fun `should return currency for legacy order test network`() = runBlocking<Unit> {
        val page = orderActivityClient.getActivitiesAll(listOf(DipDupActivityType.SELL, DipDupActivityType.LIST), 100, "1650488385000_a5d8c969-eafc-5a74-b6f6-6c9001f5fcad", true)
        assertThat(page.activities).hasSize(100)
        assertThat(page.continuation).isNotEqualTo("1650488385000_a5d8c969-eafc-5a74-b6f6-6c9001f5fcad")
        val lastActivity = page.activities.last()
        assertThat(page.continuation).isEqualTo("${lastActivity.date.toEpochSecond()*1000}_${lastActivity.id}")
    }
}
