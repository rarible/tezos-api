package com.rarible.tzkt.it

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.rarible.tzkt.client.BigMapKeyClient
import com.rarible.tzkt.client.IPFSClient
import com.rarible.tzkt.client.OwnershipClient
import com.rarible.tzkt.client.TokenClient
import com.rarible.tzkt.config.KnownAddresses
import com.rarible.tzkt.meta.MetaService
import com.rarible.tzkt.model.Part
import com.rarible.tzkt.model.TimestampIdContinuation
import com.rarible.tzkt.royalties.RoyaltiesHandler
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import org.springframework.web.reactive.function.client.WebClient
import preparedClient
import java.time.Instant

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
class TokenClientIt {

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

    // https://api.ithacanet.tzkt.io
    val client = preparedClient("https://api.tzkt.io")

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
    val ownershipClient = OwnershipClient(client)
    val ipfsWb = WebClient.create("https://ipfs.io/ipfs/")
    val ipfsClient = IPFSClient(ipfsWb, mapper)
    val metaService = MetaService(ObjectMapper().registerKotlinModule(), bigMapKeyClient, ipfsClient, config)
    val handler = RoyaltiesHandler(bigMapKeyClient, ownershipClient, ipfsClient, config)
    val tokenClient = TokenClient(client, metaService, handler)

    @Test
    fun `should return token tag from string`() = runBlocking<Unit> {
        val item = tokenClient.token("KT1Aq1umaV8gcDQmi4CLDk7KeKpoUjFQeg1B:9", true)
        assertThat(item.meta?.tags?.first()).isEqualTo("#climatechange")
    }

    @Test
    fun `should return token tag from string list`() = runBlocking<Unit> {
        val item = tokenClient.token("KT1Aq1umaV8gcDQmi4CLDk7KeKpoUjFQeg1B:7", true)
        assertThat(item.meta?.tags?.first()).isEqualTo("summer, ice cream")
    }

    @Test
    fun `should have correct royalty`() = runBlocking<Unit> {
        val parts = tokenClient.royalty("KT1L7GvUxZH5tfa6cgZKnH6vpp2uVxnFVHKu:945")
        assertThat(parts.first()).isEqualTo(Part("tz29DrxbfkcfpUveVGsmhgvWqjgkVtGXbQyP", 700))
    }

    @Test
    fun `should have correct attributes`() = runBlocking<Unit> {
        val token = tokenClient.token("KT1NUMZqQ4SNg7VyM2T9WyidkdV7RLhU6SsK:71", true)
        assertThat(token.meta?.attributes).hasSize(12)
    }

    @Test
    fun `should have correct tags`() = runBlocking<Unit> {
        val token = tokenClient.token("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton:2382", true)
        assertThat(token.meta?.tags).isEqualTo(
            listOf(
                "tattoo",
                "money",
                "animation",
                "shit",
                "renatomoll",
                "capitalism",
                "life"
            )
        )
    }

    @Test
    fun `should have correct attributes in meta`() = runBlocking<Unit> {
        val meta = tokenClient.tokenMeta("KT1NUMZqQ4SNg7VyM2T9WyidkdV7RLhU6SsK:71")
        assertThat(meta.attributes).hasSize(12)
    }

    @Test
    fun `should have correct royalties`() = runBlocking<Unit> {
        val parts = tokenClient.royalty("KT1RCzrLhBpdKXkyasKGpDTCcdbBTipUk77x:7027")
        assertThat(parts).hasSize(1)
    }

    @Test
    fun `should get items all with continuation`() = runBlocking<Unit> {
        val page = tokenClient.allTokensByLastUpdate(2, null, false)

        assertThat(page.items).hasSize(2)
        val continuation = page.continuation?.let { TimestampIdContinuation.parse(it) }
        assertThat(continuation?.date).isBefore(Instant.now())
        assertThat(continuation?.id).isEqualTo(page.items.last().itemId())
        assertThat(continuation?.date).isEqualTo(page.items.last().lastTime?.toInstant())

        val nextPage = tokenClient.allTokensByLastUpdate(2, continuation.toString(), false)

        assertThat(nextPage.items).hasSize(2)
        val nextContinuation = nextPage.continuation?.let { TimestampIdContinuation.parse(it) }
        assertThat(nextContinuation?.date).isBefore(Instant.now())
        assertThat(nextContinuation?.id).isEqualTo(nextPage.items.last().itemId())
        assertThat(nextContinuation?.date).isEqualTo(nextPage.items.last().lastTime?.toInstant())

        assertThat(nextContinuation?.date).isBeforeOrEqualTo(continuation?.date)
        assertThat(nextPage.items).isNotEqualTo(page.items)
    }

    @Test
    @Disabled
    fun `shouldn't have duplications`() = runBlocking<Unit> {
        var total = 100_000
        var current = 0
        val itemIds = setOf<String>().toMutableSet()
        var continuation: String? = "1658761109000_KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi:1029163"
        while (current < total) {
            val page = tokenClient.allTokensByLastUpdate(1000, continuation, false, false)
            continuation = page.continuation
            current += page.items.size
            itemIds.addAll(page.items.map { it.itemId() })
        }
        assertThat(current).isEqualTo(itemIds.size)
    }

    @Test
    fun `should have supply = 0`() = runBlocking<Unit> {
        val token = tokenClient.token("KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS:73086")
        assertThat(token.isDeleted()).isTrue()
    }

    @Test
    fun `shouldn have 1000 items`() = runBlocking<Unit> {
        val page = tokenClient.allTokensByLastUpdate(
            1000,
            "1654536194000_KT1U6EHmNxJTkvaWJ4ThczG4FSDaHC21ssvi:831691",
            sortAsc = false,
            loadMeta = false,
            checkBalance = true
        )
        assertThat(page.items).hasSize(1000)
    }

    @Test
    fun `shouldn have royalty`() = runBlocking<Unit> {
        val page = handler.processRoyalties("KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS:68056")
        println(page)
    }
}
