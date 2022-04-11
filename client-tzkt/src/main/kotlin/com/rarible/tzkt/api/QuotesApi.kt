/**
 * TzKT API
 *
 * # Introduction  TzKT Explorer provides free REST API and WebSocket API for accessing detailed Tezos blockchain data and helps developers build more services and applications on top of Tezos. TzKT is an open-source project, so you can easily clone and build it and use it as a self-hosted service to avoid any risks of depending on third-party services.  TzKT API is available for the following Tezos networks with the following base URLs:  - Mainnet: `https://api.tzkt.io/` or `https://api.mainnet.tzkt.io/` ([view docs](https://api.tzkt.io))  - Granadanet: `https://api.granadanet.tzkt.io/` ([view docs](https://api.granadanet.tzkt.io))     - Hangzhou2net: `https://api.hangzhou2net.tzkt.io/` ([view docs](https://api.hangzhou2net.tzkt.io))  We also provide a staging environment for testing newest features and pre-updating client applications before deploying to production:  - Mainnet staging: `https://api.tzkt.io/` or `https://staging.api.mainnet.tzkt.io/` ([view docs](https://api.tzkt.io))  Feel free to contact us if you have any questions or feature requests. Your feedback really helps us make TzKT better!  - Discord: https://discord.gg/aG8XKuwsQd - Telegram: https://t.me/baking_bad_chat - Slack: https://tezos-dev.slack.com/archives/CV5NX7F2L - Twitter: https://twitter.com/TezosBakingBad - Email: hello@baking-bad.org  And don't forget to star TzKT project [on GitHub](https://github.com/baking-bad/tzkt) ;)  # Terms of Use  TzKT API is free for everyone and for both commercial and non-commercial usage.  If your application or service uses the TzKT API in any forms: directly on frontend or indirectly on backend, you should mention that fact on your website or application by placing the label **\"Powered by TzKT API\"** with a direct link to [tzkt.io](https://tzkt.io).   # Rate Limits  There will be no rate limits as long as our servers can handle the load without additional infrastructure costs. However, any apparent abuse will be prevented by setting targeted rate limits.  Check out [Tezos Explorer API Best Practices](https://baking-bad.org/blog/tag/TzKT/) and in particular [how to optimize requests count](https://baking-bad.org/blog/2020/07/29/tezos-explorer-api-tzkt-how-often-to-make-requests/).  --- 
 *
 * The version of the OpenAPI document: v1.7.0
 * Contact: hello@baking-bad.org
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.rarible.tzkt.api

import java.io.IOException

import com.rarible.tzkt.model.parameters.DateTimeParameter
import com.rarible.tzkt.model.parameters.OffsetParameter
import com.rarible.tzkt.model.parameters.SortParameter
import com.rarible.tzkt.models.Quote

import com.rarible.tzkt.infrastructure.ApiClient
import com.rarible.tzkt.infrastructure.ApiResponse
import com.rarible.tzkt.infrastructure.ClientException
import com.rarible.tzkt.infrastructure.ClientError
import com.rarible.tzkt.infrastructure.ServerException
import com.rarible.tzkt.infrastructure.ServerError
import com.rarible.tzkt.infrastructure.MultiValueMap
import com.rarible.tzkt.infrastructure.RequestConfig
import com.rarible.tzkt.infrastructure.RequestMethod
import com.rarible.tzkt.infrastructure.ResponseType
import com.rarible.tzkt.infrastructure.Success
import com.rarible.tzkt.model.parameters.IntParameter
import com.rarible.tzkt.model.parameters.SelectionParameter

class QuotesApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "https://api.tzkt.io")
        }
    }

    /**
    * Get quotes
    * Returns a list of quotes aligned with blocks.
    * @param level Filters quotes by level. (optional)
    * @param timestamp Filters quotes by timestamp. (optional)
    * @param select Specify comma-separated list of fields to include into response or leave it undefined to return full object. If you select single field, response will be an array of values in both &#x60;.fields&#x60; and &#x60;.values&#x60; modes. (optional)
    * @param sort Sorts quotes by specified field. Supported fields: &#x60;level&#x60; (default). (optional)
    * @param offset Specifies which or how many items should be skipped (optional)
    * @param limit Maximum number of items to return (optional, default to 100)
    * @return kotlin.collections.List<Quote>
    * @throws IllegalStateException If the request is not correctly configured
    * @throws IOException Rethrows the OkHttp execute method exception
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun quotesGet(level: IntParameter?, timestamp: DateTimeParameter?, select: SelectionParameter?, sort: SortParameter?, offset: OffsetParameter?, limit: kotlin.Int?) : kotlin.collections.List<Quote> {
        val localVarResponse = quotesGetWithHttpInfo(level = level, timestamp = timestamp, select = select, sort = sort, offset = offset, limit = limit)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.collections.List<Quote>
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
    * Get quotes
    * Returns a list of quotes aligned with blocks.
    * @param level Filters quotes by level. (optional)
    * @param timestamp Filters quotes by timestamp. (optional)
    * @param select Specify comma-separated list of fields to include into response or leave it undefined to return full object. If you select single field, response will be an array of values in both &#x60;.fields&#x60; and &#x60;.values&#x60; modes. (optional)
    * @param sort Sorts quotes by specified field. Supported fields: &#x60;level&#x60; (default). (optional)
    * @param offset Specifies which or how many items should be skipped (optional)
    * @param limit Maximum number of items to return (optional, default to 100)
    * @return ApiResponse<kotlin.collections.List<Quote>?>
    * @throws IllegalStateException If the request is not correctly configured
    * @throws IOException Rethrows the OkHttp execute method exception
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun quotesGetWithHttpInfo(level: IntParameter?, timestamp: DateTimeParameter?, select: SelectionParameter?, sort: SortParameter?, offset: OffsetParameter?, limit: kotlin.Int?) : ApiResponse<kotlin.collections.List<Quote>?> {
        val localVariableConfig = quotesGetRequestConfig(level = level, timestamp = timestamp, select = select, sort = sort, offset = offset, limit = limit)

        return request<Unit, kotlin.collections.List<Quote>>(
            localVariableConfig
        )
    }

    /**
    * To obtain the request config of the operation quotesGet
    *
    * @param level Filters quotes by level. (optional)
    * @param timestamp Filters quotes by timestamp. (optional)
    * @param select Specify comma-separated list of fields to include into response or leave it undefined to return full object. If you select single field, response will be an array of values in both &#x60;.fields&#x60; and &#x60;.values&#x60; modes. (optional)
    * @param sort Sorts quotes by specified field. Supported fields: &#x60;level&#x60; (default). (optional)
    * @param offset Specifies which or how many items should be skipped (optional)
    * @param limit Maximum number of items to return (optional, default to 100)
    * @return RequestConfig
    */
    fun quotesGetRequestConfig(level: IntParameter?, timestamp: DateTimeParameter?, select: SelectionParameter?, sort: SortParameter?, offset: OffsetParameter?, limit: kotlin.Int?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
            .apply {
                if (level != null) {
                    put("level", listOf(level.toString()))
                }
                if (timestamp != null) {
                    put("timestamp", listOf(timestamp.toString()))
                }
                if (select != null) {
                    put("select", listOf(select.toString()))
                }
                if (sort != null) {
                    put("sort", listOf(sort.toString()))
                }
                if (offset != null) {
                    put("offset", listOf(offset.toString()))
                }
                if (limit != null) {
                    put("limit", listOf(limit.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/quotes",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get quotes count
    * Returns the total number of quotes aligned with blocks.
    * @return kotlin.Int
    * @throws IllegalStateException If the request is not correctly configured
    * @throws IOException Rethrows the OkHttp execute method exception
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun quotesGetCount() : kotlin.Int {
        val localVarResponse = quotesGetCountWithHttpInfo()

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.Int
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
    * Get quotes count
    * Returns the total number of quotes aligned with blocks.
    * @return ApiResponse<kotlin.Int?>
    * @throws IllegalStateException If the request is not correctly configured
    * @throws IOException Rethrows the OkHttp execute method exception
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun quotesGetCountWithHttpInfo() : ApiResponse<kotlin.Int?> {
        val localVariableConfig = quotesGetCountRequestConfig()

        return request<Unit, kotlin.Int>(
            localVariableConfig
        )
    }

    /**
    * To obtain the request config of the operation quotesGetCount
    *
    * @return RequestConfig
    */
    fun quotesGetCountRequestConfig() : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/quotes/count",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

    /**
    * Get last quote
    * Returns last known quote.
    * @return Quote
    * @throws IllegalStateException If the request is not correctly configured
    * @throws IOException Rethrows the OkHttp execute method exception
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun quotesGetLast() : Quote {
        val localVarResponse = quotesGetLastWithHttpInfo()

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Quote
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
    * Get last quote
    * Returns last known quote.
    * @return ApiResponse<Quote?>
    * @throws IllegalStateException If the request is not correctly configured
    * @throws IOException Rethrows the OkHttp execute method exception
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun quotesGetLastWithHttpInfo() : ApiResponse<Quote?> {
        val localVariableConfig = quotesGetLastRequestConfig()

        return request<Unit, Quote>(
            localVariableConfig
        )
    }

    /**
    * To obtain the request config of the operation quotesGetLast
    *
    * @return RequestConfig
    */
    fun quotesGetLastRequestConfig() : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v1/quotes/last",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )
    }

}
