package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.OwnershipClient
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
class OwnershipClientIt {

    val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://testnet-tezos-indexer.rarible.org/v1/graphql").build() }
    val ownershipClientIt = OwnershipClient(client)

    @Test
    fun `should return token`() = runBlocking<Unit> {
//        val token = tokenClient.getTokenById("KT1RuoaCbnZpMgdRpSoLfJUzSkGz1ZSiaYwj:572")
//        assertThat(token).isNotNull
    }

}
