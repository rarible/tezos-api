package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.TokenClient
import com.rarible.dipdup.client.exception.DipDupNotFound
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
//@Disabled
class TokenClientIt {

    val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://dev-tezos-indexer.rarible.org/v1/graphql").build() }
    val tokenClient = TokenClient(client)

    @Test
    fun `should return token`() = runBlocking<Unit> {
        val token = tokenClient.getTokenById("KT1RuoaCbnZpMgdRpSoLfJUzSkGz1ZSiaYwj:504")
        assertThat(token).isNotNull
    }

    @Test
    fun `should return 404 for meta`() = runBlocking<Unit> {
        assertThrows<DipDupNotFound> {
            tokenClient.getTokenMetaById("KT1RuoaCbnZpMgdRpSoLfJUzSkGz1ZSiaYwj:-1")
        }
    }

    @Test
    fun `should return meta`() = runBlocking<Unit> {
        val meta = tokenClient.getTokenMetaById("KT1RuoaCbnZpMgdRpSoLfJUzSkGz1ZSiaYwj:504")
        assertThat(meta).isNotNull
    }

}
