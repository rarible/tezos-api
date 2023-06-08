package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.TokenActivityClient
import com.rarible.dipdup.client.model.DipDupActivityContinuation
import com.rarible.dipdup.client.model.DipDupActivityType
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class TokenActivityClientIt {

    val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://testnet-tezos-indexer.rarible.org/v1/graphql").build() }
    val tokenActivityClient = TokenActivityClient(client)

    @Test
    fun `should return all activities desc`() = runBlocking<Unit> {
        val transfers = tokenActivityClient.getActivitiesAll(listOf(DipDupActivityType.TRANSFER), 10, null, false)
        assertThat(transfers.activities).hasSize(10)

        val mints = tokenActivityClient.getActivitiesAll(listOf(DipDupActivityType.MINT), 10, null, false)
        assertThat(mints.activities).hasSize(10)

        val burns = tokenActivityClient.getActivitiesAll(listOf(DipDupActivityType.BURN), 10, null, false)
        assertThat(burns.activities).hasSize(10)
    }

    @Test
    fun `should return all activities asc`() = runBlocking<Unit> {
        val transfers = tokenActivityClient.getActivitiesAll(listOf(DipDupActivityType.TRANSFER), 10, null, true)
        assertThat(transfers.activities).hasSize(10)

        val mints = tokenActivityClient.getActivitiesAll(listOf(DipDupActivityType.MINT), 10, null, true)
        assertThat(mints.activities).hasSize(10)

        val burns = tokenActivityClient.getActivitiesAll(listOf(DipDupActivityType.BURN), 10, null, true)
        assertThat(burns.activities).hasSize(10)
    }

    @Test
    fun `should return activities by id`() = runBlocking<Unit> {
        val activities = tokenActivityClient.getActivitiesByIds(listOf("66548065632259"))
        assertThat(activities).hasSize(1)
    }

    @Test
    fun `should return activities by item`() = runBlocking<Unit> {
        val page = tokenActivityClient.getActivitiesByItem(
            types = listOf(DipDupActivityType.MINT),
            contract = "KT1RuoaCbnZpMgdRpSoLfJUzSkGz1ZSiaYwj",
            tokenId = "552",
            limit = 1,
            continuation = null,
            sortAsc = false
        )
        assertThat(page.activities).hasSize(1)
    }

    @Test
    fun `should return sync activities desc`() = runBlocking<Unit> {
        var continuation: String? = null  // this's fine for test
        var count = 0
        do {
            val transfers = tokenActivityClient.getActivitiesSync(1000, continuation)
            continuation = transfers.continuation
            count += transfers.activities.size
            DipDupActivityContinuation.parse(continuation)?.let {
                assertThat(transfers.activities.first().id).isNotEqualTo(it.id)
            }
            println("continuation: ${continuation}")

        } while (continuation != null)
        println("Count: ${count}")
    }
}
