package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.TokenActivityClient
import com.rarible.dipdup.client.model.DipDupActivityType
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
class TokenActivityClientIt {

    val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://dev-tezos-indexer.rarible.org/v1/graphql").build() }
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
        val activities = tokenActivityClient.getActivitiesByIds(listOf("65842556436483"))
        assertThat(activities).hasSize(1)
    }

    @Test
    fun `should return activities by item`() = runBlocking<Unit> {
        val page = tokenActivityClient.getActivitiesByItem(
            types = listOf(DipDupActivityType.MINT),
            contract = "KT1RuoaCbnZpMgdRpSoLfJUzSkGz1ZSiaYwj",
            tokenId = "548",
            limit = 1,
            continuation = null,
            sortAsc = false
        )
        assertThat(page.activities).hasSize(1)
    }
}
