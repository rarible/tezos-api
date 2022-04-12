package com.rarible.tzkt.client

import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriBuilder

abstract class BaseClient(
    val webClient: WebClient
) {
    suspend inline fun <reified T : Any> invoke(crossinline builder: (b: UriBuilder) -> UriBuilder): T {
        return webClient.get()
            .uri { builder(it).build() }
            .accept(APPLICATION_JSON)
            .retrieve()
            .awaitBody()
    }
}
