package com.rarible.tzkt.meta

import com.fasterxml.jackson.databind.JsonNode
import com.rarible.tzkt.client.BaseClient
import com.rarible.tzkt.client.CollectionClient
import com.rarible.tzkt.client.IPFSClient
import com.rarible.tzkt.model.CollectionMeta
import org.springframework.web.reactive.function.client.WebClient

class MetaCollectionService(
    webClient: WebClient,
    private val ipfsClient: IPFSClient
) : BaseClient(webClient) {

    suspend fun get(contract: String): CollectionMeta {
        return try {
            val props = invoke<List<Map<String, Any>>> {
                it.path("${CollectionClient.BASE_PATH}/$contract/bigmaps/metadata/keys")
            }

            var meta = CollectionMeta(getKey(props, "name"), getKey(props, "symbol"))
            if (!meta.isEmpty()) {
                logger.info("Got collection meta to $contract from Bigmap")
            }

            if (meta.isEmpty()) {
                meta = getViaIpfs(props)
                if (!meta.isEmpty()) {
                    logger.info("Got collection meta to $contract from ipfs")
                }
            }
            meta
        } catch (ex: Exception) {
            logger.error("Getting meta for collection $contract failed", ex)
            CollectionMeta(null, null)
        }
    }

    suspend fun getViaIpfs(props: List<Map<String, Any>>): CollectionMeta {
        val urls = props.mapNotNull {
            when (val bytes = it.get("value").toString().decodeHex()) {
                null -> null
                else -> String(bytes)
            }
        }.filter { it.startsWith("ipfs") }
        urls.forEach { url ->
            try {
                val data = fetchIpfsData(url)
                return CollectionMeta(data.get("name").asText(), null)
            } catch (ex: Exception) {
                logger.error("Error getting meta from $url")
            }
        }
        return CollectionMeta(null, null)
    }

    private fun getKey(meta: List<Map<String, Any>>, key: String): String? =
        meta.find { it["key"] == key }?.get("value")?.let {
            when (val bytes = it.toString().decodeHex()) {
                null -> null
                else -> String(bytes)
            }
        }

    private suspend fun fetchIpfsData(url: String): JsonNode {
        return if (url.startsWith("ipfs://")){
            val hash = url!!.split("//")
            ipfsClient.ipfsData(hash[1])
        } else {
            ipfsClient.data(url)
        }
    }

    private fun String.decodeHex(): ByteArray? {
        return if (length % 2 == 0) {
            chunked(2)
                .map { it.toInt(16).toByte() }
                .toByteArray()
        } else {
            null
        }
    }

}
