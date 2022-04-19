package com.rarible.tzkt.client

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.reactive.function.client.WebClient

class IPFSClient (
    webClient: WebClient
) : BaseClient(webClient) {
    suspend fun data(hash: String): JsonNode {
        val content = invoke<String> {
            it.path(hash)
        }
        return ObjectMapper().readTree(content)
    }
}