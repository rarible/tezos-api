package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.rarible.dipdup.client.RoyaltiesClient
import com.rarible.dipdup.client.core.model.DipDupRoyalties
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
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
            .addHttpInterceptor(AuthorizationInterceptor("er4oivAtW7U2ToO3TW4nScoc"))
            .build()
    }
    val royaltyClient = RoyaltiesClient(client)

    @Test
    fun `should return royalty`() = runBlocking<Unit> {
        val royalty = royaltyClient.getRoyaltiesById("KT1Dc1j7mnB2X6cdXDgfvb1hytXDTWrFs1iN:28")
        assertThat(royalty).isNotNull
    }

    @Test
    fun `should save royalty`() = runBlocking<Unit> {
        val id = UUID.randomUUID()
        val royalty = DipDupRoyalties(
            id = id.toString(),
            updated = Instant.now(),
            tokenId = BigInteger.ONE,
            contract = "KTtest",
            parts = listOf()
        )
        royaltyClient.insertRoyalty(royalty)
        println(royalty)
    }

    class AuthorizationInterceptor(val token: String) : HttpInterceptor {
        override suspend fun intercept(request: HttpRequest,  chain: HttpInterceptorChain): HttpResponse {
            return chain.proceed(request.newBuilder().addHeader("X-Hasura-Admin-Secret", token).build())
        }
    }
}
