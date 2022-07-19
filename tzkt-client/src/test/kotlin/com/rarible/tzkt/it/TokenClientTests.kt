package com.rarible.tzkt.it

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.rarible.tzkt.client.BigMapKeyClient
import com.rarible.tzkt.client.IPFSClient
import com.rarible.tzkt.client.TokenClient
import com.rarible.tzkt.config.KnownAddresses
import com.rarible.tzkt.meta.MetaService
import com.rarible.tzkt.model.Part
import com.rarible.tzkt.model.TokenMeta
import com.rarible.tzkt.royalties.RoyaltiesHandler
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import org.springframework.web.reactive.function.client.WebClient

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
class TokenClientTests {

    val mapper = ObjectMapper().registerKotlinModule()

    val HEN = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
    val HEN_ROYALTIES = "KT1Hkg5qeNhfwpKW4fXvq7HGZB9z2EnmCCA9"
    val KALAMINT = "KT1EpGgjQs73QfFJs9z7m1Mxm5MTnpC2tqse"
    val FXHASH_V1 = "KT1KEa8z6vWXDJrVqtMrAeDVzsvxat3kHaCE"
    val FXHASH_MANAGER_LEGACY_V1 = "KT1XCoGnfupWk7Sp8536EfrxcP73LmT68Nyr"
    val FXHASH_V2 = "KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi"
    val VERSUM = "KT1LjmAdYQCLBjwv4S2oFkEzyHVkomAf5MrW"
    val ROYALTIES_MANAGER = "KT1HNNrmCk1fpqveRDz8Fvww2GM4gPzmA7fo"
    val BIDOU_8x8 = "KT1MxDwChiDwd6WBVs24g1NjERUoK622ZEFp"
    val BIDOU_24x24 = "KT1TR1ErEQPTdtaJ7hbvKTJSa1tsGnHGZTpf"

    val client = WebClient.create("https://api.tzkt.io")

    val config = KnownAddresses(
        hen = HEN,
        henRoyalties = HEN_ROYALTIES,
        kalamint = KALAMINT,
        fxhashV1 = FXHASH_V1,
        fxhashV1Manager = FXHASH_MANAGER_LEGACY_V1,
        fxhashV2 = FXHASH_V2,
        versum = VERSUM,
        royaltiesManager = ROYALTIES_MANAGER,
        bidou8x8 = BIDOU_8x8,
        bidou24x24 = BIDOU_24x24
    )

    val bigMapKeyClient = BigMapKeyClient(client)
    val metaService = MetaService(ObjectMapper().registerKotlinModule(), bigMapKeyClient, config)
    val ipfsWb = WebClient.create("https://ipfs.io/ipfs/")
    val ipfsClient = IPFSClient(ipfsWb, mapper)
    val handler = RoyaltiesHandler(bigMapKeyClient, ipfsClient, config)
    val tokenClient = TokenClient(client, metaService, handler)

    @Test
    fun `should return token tag from string`() = runBlocking<Unit> {
        val item = tokenClient.token("KT1Aq1umaV8gcDQmi4CLDk7KeKpoUjFQeg1B:9")
        assertThat(item.meta?.attributes?.first()).isEqualTo(TokenMeta.Attribute("#climatechange"))
    }

    @Test
    fun `should return token tag from string list`() = runBlocking<Unit> {
        val item = tokenClient.token("KT1Aq1umaV8gcDQmi4CLDk7KeKpoUjFQeg1B:7")
        assertThat(item.meta?.attributes?.first()).isEqualTo(TokenMeta.Attribute("summer, ice cream"))
    }

    @Test
    fun `should have correct royalty`() = runBlocking<Unit> {
        val parts = tokenClient.royalty("KT1L7GvUxZH5tfa6cgZKnH6vpp2uVxnFVHKu:945")
        assertThat(parts.first()).isEqualTo(Part("tz29DrxbfkcfpUveVGsmhgvWqjgkVtGXbQyP", 700))
    }
}
