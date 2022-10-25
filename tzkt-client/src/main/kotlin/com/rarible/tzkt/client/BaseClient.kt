package com.rarible.tzkt.client

import com.rarible.tzkt.model.TzktNotFound
import com.rarible.tzkt.model.TzktBadRequest
import com.rarible.tzkt.utils.flatMapAsync
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.util.UriBuilder
import java.net.URI

abstract class BaseClient(
    val webClient: WebClient
) {

    val logger = LoggerFactory.getLogger(javaClass)

    suspend inline fun <reified T : Any> invoke(crossinline builder: (b: UriBuilder) -> UriBuilder): T {
        var build: URI? = null
        val body = try {
            webClient.get()
                .uri {
                    build = builder(it).build()
                    logger.info("Request to $build")
                    build
                }
                .accept(APPLICATION_JSON)
                .retrieve()
                .awaitBodyOrNull<T>()
        } catch (ex: WebClientResponseException) {
            if (ex.statusCode == HttpStatus.BAD_REQUEST) throw TzktBadRequest(ex)
            else throw ex
        }
        return body ?: throw TzktNotFound("Empty response for url: $build")
    }

    suspend inline fun <reified T : Any> invokePost(crossinline builder: (b: UriBuilder) -> UriBuilder, body: Any): T {
        return try {
            webClient.post()
                .uri {
                    val build = builder(it).build()
                    logger.info("Request to ${build}")
                    build
                }
                .accept(APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .awaitBody()
        } catch (ex: WebClientResponseException) {
            if (ex.statusCode == HttpStatus.BAD_REQUEST) throw TzktBadRequest(ex)
            else throw ex
        }
    }

    suspend inline fun <reified T : Any> invokeURI(crossinline builder: (b: UriBuilder) -> URI): T {
        return try {
            webClient.get()
                .uri {
                    val build = builder(it)
                    logger.info("Request to ${build}")
                    build
                }
                .accept(APPLICATION_JSON)
                .retrieve()
                .awaitBody()
        } catch (ex: WebClientResponseException) {
            if (ex.statusCode == HttpStatus.BAD_REQUEST) throw TzktBadRequest(ex)
            else throw ex
        }
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
