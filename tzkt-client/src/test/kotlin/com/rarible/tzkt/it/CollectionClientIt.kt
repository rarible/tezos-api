package com.rarible.tzkt.it

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.rarible.tzkt.client.CollectionClient
import com.rarible.tzkt.client.IPFSClient
import com.rarible.tzkt.meta.MetaCollectionService
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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
        return CollectionClient(client, metaCollectionService)
    }

    @Test
    fun `should return meta for collection from bigmap (ithaca)`() = runBlocking<Unit> {
        val collectionClient = client("https://api.ithacanet.tzkt.io")
        val collection = collectionClient.collection("KT1UFkqihyjz1GhxM1hk78CjfcChsBbLGYMm")
        assertThat(collection.name).isEqualTo("123-000")
        assertThat(collection.symbol).isEqualTo("123")
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
}