package com.rarible.dipdup.clinet.core.util

import com.rarible.dipdup.client.core.util.Namespace
import com.rarible.dipdup.client.core.util.uuid5
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class UuidTest {

    @Test
    fun `should have uuid5`() {
        val tokenId = "KT1Pz65ssbPF7Zv9Dh7ggqUkgAYNSuJ9iia7:1"
        val uuid = uuid5(Namespace.OID, tokenId)
        assertThat(uuid).isEqualTo(UUID.fromString("53802e5b-8ff1-5db5-b3b3-2d5e610c7b14"))
    }

    @Test
    fun `should have uuid5 google`() {
        val tokenId = "google.com"
        val uuid = uuid5(Namespace.URL, tokenId)
        assertThat(uuid).isEqualTo(UUID.fromString("fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9"))
    }

}
