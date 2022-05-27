package com.rarible.tzkt.client

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SignatureClientTest {

    val client = SignatureClient("https://dev-tezos-api.rarible.org", "NetXfHjxW3qBoxi", "KT1ShTc4haTgT76z5nTLSQt3GSTLzeLPZYfT")

    @Test
    fun `should validate and return true`() = runBlocking<Unit> {

        // TODO: needs example
        val result = client.validate(
            "",
            "",
            "",
            ""
        )

        assertThat(result).isTrue
    }

}
