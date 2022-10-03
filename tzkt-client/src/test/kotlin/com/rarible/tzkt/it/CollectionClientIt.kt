package com.rarible.tzkt.it

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.rarible.tzkt.client.CollectionClient
import com.rarible.tzkt.client.IPFSClient
import com.rarible.tzkt.config.TzktSettings
import com.rarible.tzkt.meta.MetaCollectionService
import com.rarible.tzkt.model.TzktNotFound
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import org.springframework.web.reactive.function.client.WebClient
import preparedClient

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
class CollectionClientIt {

    fun client(url: String): CollectionClient {
        val mapper = ObjectMapper().registerKotlinModule()
        val client = preparedClient(url)
        val ipfsWb = WebClient.create("https://ipfs.io/ipfs/")
        val ipfsClient = IPFSClient(ipfsWb, mapper)

        val metaCollectionService = MetaCollectionService(client, ipfsClient)
        return CollectionClient(client, metaCollectionService, TzktSettings(useCollectionBatch = true))
    }

    @Disabled
    @Test
    fun `should return meta for collection from bigmap (ithaca)`() = runBlocking<Unit> {
        val collectionClient = client("https://api.ithacanet.tzkt.io")
        val collection = collectionClient.collection("KT1UFkqihyjz1GhxM1hk78CjfcChsBbLGYMm", true)
        assertThat(collection.name).isEqualTo("123-000")
        assertThat(collection.symbol).isEqualTo("123")
    }

    @Test
    fun `should return collection`() = runBlocking<Unit> {
        val collectionClient = client("https://api.ithacanet.tzkt.io")
        assertThrows<TzktNotFound> {
            collectionClient.collection("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton", false)
        }
        assertThrows<TzktNotFound> {
            collectionClient.collection("KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton", true)
        }
    }

    @Test
    fun `should return collection meta`() = runBlocking<Unit> {
        val collectionClient = client("https://api.tzkt.io")
        val collection = collectionClient.collectionMeta("KT1PekmBSVEoDXkFcw3B99AzEoZxrE4BviCN")
        assertThat(collection.name).isEqualTo("Films Studio Exception")
    }

    @Test
    fun `should return name for rarible collection`() = runBlocking<Unit> {
        val collectionClient = client("https://api.tzkt.io")
        val collection = collectionClient.collectionMeta("KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS")
        assertThat(collection.name).isEqualTo("Rarible")
    }

    @Test
    fun `should return collections`() = runBlocking<Unit> {
        val collectionClient = client("http://tezos-tzkt.testnet.rarible.int")
        val collections = collectionClient.collectionsByIds(listOf("KT1Wu8T6APWm9hfn8cjkWthiPNVSRBeht7r3"))
        assertThat(collections).hasSize(1)
    }

    @Test
    fun `should return collections all by continuation`() = runBlocking<Unit> {
        val collectionClient = client("https://api.tzkt.io")
        val collections = collectionClient.collectionsAll(size = 10, continuation = null, sortAsc = false)
        assertThat(collections.items).hasSize(10)

        val collectionsNext = collectionClient.collectionsAll(size = 10, continuation = collections.continuation, sortAsc = false)
        assertThat(collectionsNext.items).hasSize(10)
    }
}
