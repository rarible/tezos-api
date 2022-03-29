package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.mockserver.MockResponse
import com.apollographql.apollo3.mockserver.MockServer
import kotlinx.coroutines.runBlocking

abstract class BaseClientFt {

    val mockServer = MockServer()
    val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl(mockServer.url()).build() }

    fun mock(body: String) {
        mockServer.enqueue(MockResponse(body = body.trimIndent()))
    }
}
