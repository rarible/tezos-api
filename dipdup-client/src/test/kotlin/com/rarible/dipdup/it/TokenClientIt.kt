package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.TokenClient
import com.rarible.dipdup.client.client.AuthorizationInterceptor
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

    val client: ApolloClient = runBlocking {
        ApolloClient.Builder()
            .serverUrl("https://testnet-tezos-indexer.rarible.org/v1/graphql")
//            .addHttpInterceptor(AuthorizationInterceptor(""))
            .build()
    }
    val tokenClient = TokenClient(client)

    @Test
    fun `should return token`() = runBlocking<Unit> {
        val token = tokenClient.getTokenById("KT1RuoaCbnZpMgdRpSoLfJUzSkGz1ZSiaYwj:572")
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
        val meta = tokenClient.getTokenMetaById("KT1RuoaCbnZpMgdRpSoLfJUzSkGz1ZSiaYwj:570")
        assertThat(meta).isNotNull
    }

    @Test
    fun `should return tokens by owner`() = runBlocking<Unit> {
        val tokens1 = tokenClient.getTokensByOwner("tz1ft1iuDeZEX26erHzD12chToXkc4EYTQZS", 100, null)
        assertThat(tokens1.items).hasSize(100)
        assertThat(tokens1.continuation).isNotNull
        val tokens2 = tokenClient.getTokensByOwner("tz1ft1iuDeZEX26erHzD12chToXkc4EYTQZS", 100, tokens1.continuation)
        assertThat(tokens2.items).hasSize(100)
        assertThat(tokens2.continuation).isNotNull

        assertThat(tokens1).isNotEqualTo(tokens2)
    }

    @Test
    fun `should return tokens by creator`() = runBlocking<Unit> {
        val tokens1 = tokenClient.getTokensByCreator("tz1Qej2aPmeZECBZHV5meTLC1X6DWRhSCoY4", 100, null)
        assertThat(tokens1.items).hasSize(100)
        assertThat(tokens1.continuation).isNotNull
        val tokens2 = tokenClient.getTokensByCreator("tz1Qej2aPmeZECBZHV5meTLC1X6DWRhSCoY4", 100, tokens1.continuation)
        assertThat(tokens2.items).hasSize(100)
        assertThat(tokens2.continuation).isNotNull

        assertThat(tokens1).isNotEqualTo(tokens2)
    }

    @Test
    fun `should return tokens by collection`() = runBlocking<Unit> {
        val tokens1 = tokenClient.getTokensByCollection("KT1GwyxE3VRLPwj5AWCuYCA2sTAM4a6kKGRm", 100, null)
        assertThat(tokens1.items).hasSize(100)
        assertThat(tokens1.continuation).isNotNull
        val tokens2 = tokenClient.getTokensByCollection("KT1GwyxE3VRLPwj5AWCuYCA2sTAM4a6kKGRm", 100, tokens1.continuation)
        assertThat(tokens2.items).hasSize(100)
        assertThat(tokens2.continuation).isNotNull

        assertThat(tokens1).isNotEqualTo(tokens2)
    }

    @Test
    @Disabled
    fun `should remove token meta`() = runBlocking<Unit> {
        val token = tokenClient.removeTokenMetaById("KT1Qvfy45PkSn6kLddmZk39KE4LWhTvMcunp:494")
        assertThat(token).isNotNull
    }

}
