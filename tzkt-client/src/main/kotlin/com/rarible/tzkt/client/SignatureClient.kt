package com.rarible.tzkt.client

import com.rarible.tzkt.utils.decodeBase58
import com.rarible.tzkt.utils.encodeToBase58String
import com.rfksystems.blake2b.Blake2b
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

class SignatureClient(
    private val nodeUrl: String,
    private val chainId: String,
    private val sigChecker: String
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val client = WebClient.create(nodeUrl)

    suspend fun validate(
        signer: String,
        publicKey: String,
        signature: String,
        message: String
    ): Boolean {
        val response: Map<*, *> = client.post().uri {
            val build = it.path("/chains/main/blocks/head/helpers/scripts/run_view").build()
            logger.info("Request to ${build}")
            build
        }.contentType(APPLICATION_JSON)

            // TODO: don't undestand how to use signer...
            .bodyValue(payload(chainId, sigChecker, publicKey, message, signature))
            .accept(APPLICATION_JSON)
            .retrieve()
            .awaitBody()

        return response["prim"] == true
    }

    private fun payload(chainId: String, contract: String, pk: String, message: String, signature: String): Map<*, *> {
        val pkh = pkh(pk)
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
            "payer" to pkh,
            "source" to pkh,
            "unparsing_mode" to "Readable"
        )
    }

    private fun pkh(pk: String): String {
        val (pkh_prefix, pk_prefix) = when (pk.subSequence(0, 2)) {
            "ed" -> Pair(byteArrayOfInts(6, 161, 159), byteArrayOfInts(13, 15, 37, 217))
            "sp" -> Pair(byteArrayOfInts(6, 161, 161), byteArrayOfInts(3, 254, 226, 86))
            "p2" -> Pair(byteArrayOfInts(6, 161, 164), byteArrayOfInts(13, 178, 139, 127))
            else -> throw RuntimeException("don't handle base58 key ${pk}")
        }
        val base58 = pk.substring(pk_prefix.size).decodeBase58()
        val blake2d = Blake2b()
        val digest = blake2d.digest(base58, 20)
        return (pkh_prefix + numberToByteArray(digest)).encodeToBase58String()
    }

    private fun numberToByteArray (data: Number, size: Int = 4) : ByteArray =
        ByteArray (size) {i -> (data.toLong() shr (i*8)).toByte()}

    private fun byteArrayOfInts(vararg ints: Int) = ByteArray(ints.size) { pos -> ints[pos].toByte() }

}
