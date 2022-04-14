package com.rarible.tzkt.client

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.springframework.web.reactive.function.client.WebClient

class IPFSClient (
    webClient: WebClient
) : BaseClient(webClient) {
    suspend fun data(hash: String): JsonObject {
        val content = invoke<String> {
            it.path(hash)
        }
        return JsonParser.parseString(content).asJsonObject
    }
}