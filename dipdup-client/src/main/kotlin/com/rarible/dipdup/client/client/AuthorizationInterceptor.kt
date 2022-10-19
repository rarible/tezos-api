package com.rarible.dipdup.client.client

import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain

class AuthorizationInterceptor(val token: String) : HttpInterceptor {
    override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
        return chain.proceed(request.newBuilder().addHeader("X-Hasura-Admin-Secret", token).build())
    }
}
