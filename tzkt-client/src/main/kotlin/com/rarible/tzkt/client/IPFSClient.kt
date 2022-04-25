package com.rarible.tzkt.client

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

class IPFSClient (
    webClient: WebClient
) : BaseClient(webClient) {
    suspend fun ipfsData(hash: String): JsonNode {
        val content = invoke<String> {
            it.path(hash)
        }
        return ObjectMapper().readTree(content)
    }

    suspend fun data(url: String): JsonNode {
        val content = webClient.get().uri(url).retrieve().awaitBody<String>()
        return ObjectMapper().readTree(content)
    }
}