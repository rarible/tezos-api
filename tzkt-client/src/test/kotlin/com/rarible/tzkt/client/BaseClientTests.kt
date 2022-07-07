package com.rarible.tzkt.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.client.WebClient

abstract class BaseClientTests {

    val mapper = ObjectMapper().registerKotlinModule()
    val mockServer = MockWebServer()
    val client = WebClient.create(mockServer.url("").toString())
//    val client = WebClient.builder()
//        .exchangeStrategies(
//            ExchangeStrategies.builder()
//                .codecs { it.defaultCodecs().maxInMemorySize(10_000_000) }
//                .build())
//        .baseUrl("https://api.ithacanet.tzkt.io")
//        .build()
//    val client = WebClient.create("https://dev-tezos-tzkt.rarible.org")

    fun mock(body: String) {
        mockServer.enqueue(
            MockResponse()
                .setBody(body)
                .setResponseCode(200)
                .addHeader("Content-Type", APPLICATION_JSON.toString())
        )
    }

    fun mock404() {
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(404)
        )
    }

    fun request() = mockServer.takeRequest()

    fun requests(): Set<String> {
        return (1..mockServer.requestCount).mapNotNull {
            mockServer.takeRequest().path
        }.toSet()
    }
}
