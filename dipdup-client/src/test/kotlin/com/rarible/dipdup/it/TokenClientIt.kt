package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.TokenClient
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
@Disabled
class TokenClientIt {

    val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://dev-tezos-indexer.rarible.org/v1/graphql").build() }
    val tokenClient = TokenClient(client)

    @Test
    fun `should return token`() = runBlocking<Unit> {
        val token = tokenClient.getTokenById("KT1RuoaCbnZpMgdRpSoLfJUzSkGz1ZSiaYwj:504")
        assertThat(token).isNotNull
    }

}
