package com.rarible.tzkt.client

import com.rarible.tzkt.utils.flatMapAsync
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriBuilder
import java.net.URI

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

    suspend inline fun <reified T : Any> invokePost(crossinline builder: (b: UriBuilder) -> UriBuilder, body: Any): T {
        return webClient.post()
            .uri {
                val build = builder(it).build()
                logger.info("Request to ${build}")
                build
            }
            .accept(APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .awaitBody()
    }

    suspend inline fun <reified T : Any> invokeURI(crossinline builder: (b: UriBuilder) -> URI): T {
        return webClient.get()
            .uri {
                val build = builder(it)
                logger.info("Request to ${build}")
                build
            }
                .accept(APPLICATION_JSON)
            .retrieve()
            .awaitBody()
    }

    suspend inline fun <T> withBatch(
        ids: List<String>, crossinline builder: suspend (batch: List<String>) -> List<T>
    ) = ids.chunked(MAX_BATCH_SIZE).flatMapAsync {
        builder(it)
    }

    fun sorting(sortAsc: Boolean) = if (sortAsc) "asc" else "desc"
    fun directionEqual(asc: Boolean) = if (asc) "ge" else "le"
    fun direction(asc: Boolean = false) = if (asc) "gt" else "lt"

    companion object {
        const val MAX_BATCH_SIZE = 100 // tzkt supports only 100 items per request
    }
}
