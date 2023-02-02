package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.rarible.dipdup.client.OwnershipClient
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

// this test will be disabled on jenkins
//@DisabledOnOs(OS.LINUX)
@Disabled
class OwnershipClientIt {

    @Nested
    @Disabled
    class Prod {
        val client: ApolloClient =
            runBlocking { ApolloClient.Builder().serverUrl("https://tezos-indexer.rarible.org/v1/graphql").build() }
        val ownershipClient = OwnershipClient(client)

        @Test
        fun `should return ownerships by item`() = runBlocking<Unit> {
            val page = ownershipClient.getOwnershipsByItem(
                limit = 100,
                itemId = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:741140",
                continuation = null
            )
            val mapper = ObjectMapper()
                .registerModule(KotlinModule())
                .registerModule(JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//            println(mapper.writeValueAsString(page))
            assertThat(page.items.size).isEqualTo(223)
        }

        @Test
        fun `should have right number`() = runBlocking<Unit> {
            var continuation: String? = null
            var number = 0
            do {
                val page = ownershipClient.getOwnershipsByItem(
                    limit = 100,
                    itemId = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:741140",
                    continuation = continuation
                )
                number += page.items.size
                continuation = page.continuation
            } while (continuation != null)
            assertThat(number).isEqualTo(223)
        }

        @Test
        fun `should write missed ids in log`() = runBlocking<Unit> {
            var page = ownershipClient.getOwnershipsByIds(listOf(
                "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:741140:tz1WEZkz46AZmGbW32qwUHsdA2PBBATgixth",
                "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:741140:fake"
            ))
            assertThat(page.size).isEqualTo(1)
        }
    }
}
