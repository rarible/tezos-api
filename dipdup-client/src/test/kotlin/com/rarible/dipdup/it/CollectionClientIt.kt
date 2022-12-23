package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.CollectionClient
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
//@Disabled
class CollectionClientIt {

    @Nested
    @Disabled
    class Dev {
        val client: ApolloClient =
            runBlocking { ApolloClient.Builder().serverUrl("https://dev-tezos-indexer.rarible.org/v1/graphql").build() }
        val collectionClient = CollectionClient(client)

        @Test
        fun `should return collections`() = runBlocking<Unit> {
            val count = collectionClient.getTokensCount("KT1Uke8qc4YTfP41dGuoGC8UsgRyCtyvKPLA")
            assertThat(count.toInt()).isGreaterThan(1000)
        }
    }

    @Nested
    @Disabled
    class Testnet {
        val client: ApolloClient = runBlocking {
            ApolloClient.Builder().serverUrl("https://testnet-tezos-indexer.rarible.org/v1/graphql").build()
        }
        val collectionClient = CollectionClient(client)

        @Test
        fun `should return collections`() = runBlocking<Unit> {
            val collections = collectionClient.getCollectionsAll(
                limit = 100,
                continuation = null,
                sortAsc = false
            )
            assertThat(collections).isNotNull
        }

        // KT1GwtE3PeTxczqnqKwdFwxaZWgEKcg8xpY5
        @Test
        fun `should return collection`() = runBlocking<Unit> {
            val collection = collectionClient.getCollectionById("KT1FZNJXVnELhB2ufr1Lqy8AZXtH3P8a3svL")
            assertThat(collection).isNotNull
        }

        @Test
        fun `should return collection meta`() = runBlocking<Unit> {
            val collection = collectionClient.getCollectionById("KT1RuoaCbnZpMgdRpSoLfJUzSkGz1ZSiaYwj")
            assertThat(collection).isNotNull
        }
    }

    @Nested
    @Disabled
    class Prod {
        val client: ApolloClient =
            runBlocking { ApolloClient.Builder().serverUrl("https://tezos-indexer.rarible.org/v1/graphql").build() }
        val collectionClient = CollectionClient(client)

        @Test
        fun `should return collection`() = runBlocking<Unit> {
            val collection = collectionClient.getCollectionById("KT1U7zAqxgxuNcmci1rt55Ghcgynnx1ui2sj")
            assertThat(collection).isNotNull
        }

        @Test
        fun `should return collections`() = runBlocking<Unit> {
            val collections = collectionClient.getCollectionsAll(100, null, false)
            assertThat(collections).isNotNull
        }
    }

}
