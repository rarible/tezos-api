package com.rarible.tzkt.it

import com.rarible.tzkt.client.OwnershipClient
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import org.springframework.web.reactive.function.client.WebClient

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
class OwnershipClientIt {

    val client = WebClient.create("https://api.tzkt.io")
    val ownershipClient = OwnershipClient(client)

    @Test
    fun `should return ownerships without burn addresses`() = runBlocking<Unit> {
        val ownerships = ownershipClient.ownershipsByToken("KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS:73086", 10, null)
        assertThat(ownerships.items).hasSize(1) // flaky

    }
}
