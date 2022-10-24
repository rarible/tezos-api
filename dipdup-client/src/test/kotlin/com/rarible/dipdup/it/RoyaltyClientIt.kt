package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.RoyaltiesClient
import com.rarible.dipdup.client.client.AuthorizationInterceptor
import com.rarible.dipdup.client.core.model.DipDupRoyalties
import com.rarible.dipdup.client.core.model.Part
import com.rarible.dipdup.client.core.util.uuid5Oid
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import java.math.BigInteger
import java.time.Instant
import java.util.*

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
//@Disabled
class RoyaltyClientIt {

    val client: ApolloClient = runBlocking {
        ApolloClient.Builder()
            .serverUrl("https://testnet-tezos-indexer.rarible.org/v1/graphql")
//            .addHttpInterceptor(AuthorizationInterceptor(""))
            .build()
    }
    val royaltyClient = RoyaltiesClient(client)

    @Test
    fun `should return royalty`() = runBlocking<Unit> {
        val royalty = royaltyClient.getRoyaltiesById("KT1Dc1j7mnB2X6cdXDgfvb1hytXDTWrFs1iN:12345")
        assertThat(royalty).isNotNull
    }

    @Test
    @Disabled
    fun `should save royalty`() = runBlocking<Unit> {
        val itemId = "KT1Dc1j7mnB2X6cdXDgfvb1hytXDTWrFs1iN:12345"
        val parts = listOf(Part("tz1fbKDvLgwjnuXDcVDUW8JdPTAsna5VhvKD", 1))
        royaltyClient.insertRoyalty(itemId, parts)
    }
}
