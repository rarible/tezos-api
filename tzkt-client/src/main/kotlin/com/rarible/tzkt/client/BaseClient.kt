package com.rarible.tzkt.client

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriBuilder

abstract class BaseClient(
    val webClient: WebClient
) {

    val logger = LoggerFactory.getLogger(javaClass)

    suspend inline fun <reified T : Any> invoke(crossinline builder: (b: UriBuilder) -> UriBuilder): T {
        return webClient.get()
            .uri {
                val build = builder(it).build()
                logger.info("Request to ${build}")
                build
            }
            .accept(APPLICATION_JSON)
            .retrieve()
            .awaitBody()
    }

    fun sorting(sortAsc: Boolean) = if (sortAsc) "asc" else "desc"

    fun sortPredicate(asc: Boolean = false) = when (asc) {
        true -> "gt"
        else -> "lt"
    }
}
