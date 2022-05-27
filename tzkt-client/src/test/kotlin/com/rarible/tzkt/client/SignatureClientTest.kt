package com.rarible.tzkt.client

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SignatureClientTest : BaseClientTests() {

    val signatureClient = SignatureClient(mockServer.url("").toString(), "NetXfHjxW3qBoxi", "KT1ShTc4haTgT76z5nTLSQt3GSTLzeLPZYfT")
//    val signatureClient = SignatureClient("https://dev-tezos-node.rarible.org", "NetXfHjxW3qBoxi", "KT1ShTc4haTgT76z5nTLSQt3GSTLzeLPZYfT")

    @Test
    fun `should validate and return true`() = runBlocking<Unit> {
        mock("""{
            "data": {
                "prim": "True"
                }
            }""")
        val result = signatureClient.validate(
            "edpkuaNBQd9rgqeDHUuCVpwRLFBK8DzneLVLLrFTKmam8A7BAyYir9",
            "edsigtxMxuHNcwMA1J72dNfFjt5bguyfH2m3VWz3bTfWoDoKcQgpQkGVN8xkrRKemAhjdKtYoHZ7zaZK4LWpsSkTKxvJDC75VSj",
            "05010000000f7061796c6f616420746f207369676e"
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
            "edsigtYX9ti5iMMmbybuMp5mUh7PRD9YXbukesysLhF7N5eSuQhv7TugscG2XUB3w4MF8eat7h94G1BL8JRufxP44Qrgeu4de9A",
            "05010000000f7061796c6f616420746f207369676e"
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
                "05010000000f7061796c6f616420746f207369676e"
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
                "05010000000f7061796c6f616420746f207369676e"
            )
        }
    }

}
