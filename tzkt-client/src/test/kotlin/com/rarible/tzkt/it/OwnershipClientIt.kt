package com.rarible.tzkt.it

import com.rarible.tzkt.client.OwnershipClient
import com.rarible.tzkt.config.TzktSettings
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import preparedClient

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
class OwnershipClientIt {

    val client = preparedClient("https://api.tzkt.io")
//    val client = preparedClient("http://tezos-tzkt.testnet.rarible.int")
    val ownershipClient = OwnershipClient(client, TzktSettings(useOwnershipsBatch = true))

    @Disabled
    @Test
    fun `should return ownerships without burn addresses`() = runBlocking<Unit> {
        val ownerships = ownershipClient.ownershipsByToken("KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS:73086", 10, null)
        assertThat(ownerships.items).hasSize(1) // flaky
    }

    @Disabled
    @Test
    fun `should return ownerships with continuation`() = runBlocking<Unit> {
        var total = 10_000
        var current = 0
        val ids = setOf<String>().toMutableSet()
        var continuation: String? = null
        while (current < total) {
            val page = ownershipClient.ownershipsAll(continuation, 1000)
            continuation = page.continuation
            current += page.items.size
            ids.addAll(page.items.map { it.ownershipId() })
        }
        assertThat(current).isEqualTo(total)
        assertThat(ids).hasSize(total)
    }

    @Disabled
    @Test
    fun `should return ownerships with continuation for token`() = runBlocking<Unit> {
        var total = 10_000
        var current = 0
        val ids = setOf<String>().toMutableSet()
        var continuation: String? = null
        while (current < total) {
            val page = ownershipClient.ownershipsByToken("KT1BRhcRAdLia3XQT1mPSofHyrmYpRddgj3s:6", 1000, continuation)
            continuation = page.continuation
            current += page.items.size
            ids.addAll(page.items.map { it.ownershipId() })
        }
        assertThat(current).isEqualTo(total)
        assertThat(ids).hasSize(total)
    }

    @Disabled
    @Test
    fun `should return ownerships in batch`() = runBlocking<Unit> {
        val ownerships = ownershipClient.ownershipsByIds(listOf("KT1MmTE786dG3mgzKsa9uWqhoWpMbUtEMXQC:12:tz1QNpkxEufumXqpVkK24HyYi3gca9HzjJZ6"))
        assertThat(ownerships).hasSize(1)
    }
}
