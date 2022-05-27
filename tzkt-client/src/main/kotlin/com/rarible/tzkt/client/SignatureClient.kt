package com.rarible.tzkt.client

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.awaitBody

class SignatureValidationException(message: String) : Exception(message)

class SignatureClient(
    private val nodeUrl: String,
    private val chainId: String,
    private val sigChecker: String
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val client = WebClient.create(nodeUrl)

    suspend fun validate(
        publicKey: String,
        signature: String,
        message: String
    ): Boolean {
        try {
            val response: Map<*, *> = client.post().uri {
                val build = it.path("/chains/main/blocks/head/helpers/scripts/run_view").build()
                logger.info("Request to ${build}")
                build
            }.contentType(APPLICATION_JSON)
                .bodyValue(payload(chainId, sigChecker, publicKey, message, signature))
                .accept(APPLICATION_JSON)
                .retrieve()
                .awaitBody()

            val result = response["data"] as LinkedHashMap<String, String>
            return result["prim"].toString() == "True"
        } catch (e: Exception) {
            if(e is WebClientResponseException){
                throw SignatureValidationException("Could not verify signature: ${e.message}: ${e.responseBodyAsString}")
            } else {
                throw SignatureValidationException("Could not verify signature: ${e.message}")
            }
        }
    }

    private fun payload(chainId: String, contract: String, pk: String, message: String, signature: String): Map<*, *> {
        return mapOf(
            "chain_id" to chainId,
            "contract" to contract,
            "entrypoint" to "check_signature",
            "gas" to "100000",
            "input" to mapOf(
                "prim" to "Pair",
                "args" to listOf(
                    mapOf("string" to pk),
                    mapOf(
                        "prim" to "Pair",
                        "args" to listOf(
                            mapOf("bytes" to message),
                            mapOf("string" to signature)
                        )
                    )
                )
            ),
            "payer" to "tz1gkQ4rNzPTgf1Yn3oBweWHAAvvF9VJv9hh",
            "source" to "tz1gkQ4rNzPTgf1Yn3oBweWHAAvvF9VJv9hh",
            "unparsing_mode" to "Readable"
        )
    }
}
