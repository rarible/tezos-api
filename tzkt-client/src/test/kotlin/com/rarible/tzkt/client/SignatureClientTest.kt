package com.rarible.tzkt.client

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SignatureClientTest : BaseClientTests() {

    val signatureClient = SignatureClient(mockServer.url("").toString(), "NetXfHjxW3qBoxi", "KT1EiyFnYEGUtfMLKBcWnYzJ95d1hakR5qaX")
    //val signatureClient = SignatureClient("https://dev-tezos-node.rarible.org", "NetXfHjxW3qBoxi", "KT1EiyFnYEGUtfMLKBcWnYzJ95d1hakR5qaX")

    @Test
    fun `should validate and return true`() = runBlocking<Unit> {
        mock("""{
            "data": {
                "prim": "True"
                }
            }""")
        val result = signatureClient.validate(
            "edpkvGfYw3LyB1UcCahKQk4rF2tvbMUk8GFiTuMjL75uGXrpvKXhjn",
            "edsigtpJtWK2gvgqiMSWpZGutcsxEip92DCZobjL9x7Qz7BdkGdLqNYoMBACQcQPFPmhwxYQYvzFsN96np4J9f4kZnULPiMVWkA",
            "message to sign"
        )

        assertThat(result).isTrue
    }

    @Test
    fun `should validate and return false with wrong signature`() = runBlocking<Unit> {
        mock("""{
            "data": {
                "prim": "False"
                }
            }""")
        val result = signatureClient.validate(
            "edpkuaNBQd9rgqeDHUuCVpwRLFBK8DzneLVLLrFTKmam8A7BAyYir9",
            "edsigtpJtWK2gvgqiMSWpZGutcsxEip92DCZobjL9x7Qz7BdkGdLqNYoMBACQcQPFPmhwxYQYvzFsN96np4J9f4kZnULPiMVWkA",
            "message to sign"
        )

        assertThat(result).isFalse
    }

    @Test
    fun `should not validate and return false (wrongly typed public key)`() = runBlocking<Unit> {
        mock("[]")
        assertThrows<SignatureValidationException> {
            signatureClient.validate(
                "pkuaNBQd9rgqeDHUuCVpwRLFBK8DzneLVLLrFTKmam8A7BAyYir9",
                "edsigtxMxuHNcwMA1J72dNfFjt5bguyfH2m3VWz3bTfWoDoKcQgpQkGVN8xkrRKemAhjdKtYoHZ7zaZK4LWpsSkTKxvJDC75VSj",
                "message to sign"
            )
        }
    }

    @Test
    fun `should not validate and return false (wrongly typed signature)`() = runBlocking<Unit> {
        mock("[]")
        assertThrows<SignatureValidationException> {
            signatureClient.validate(
                "edpkuaNBQd9rgqeDHUuCVpwRLFBK8DzneLVLLrFTKmam8A7BAyYir9",
                "txMxuHNcwMA1J72dNfFjt5bguyfH2m3VWz3bTfWoDoKcQgpQkGVN8xkrRKemAhjdKtYoHZ7zaZK4LWpsSkTKxvJDC75VSj",
                "message to sign"
            )
        }
    }

}
