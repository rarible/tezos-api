package com.rarible.tzkt.client

import com.rarible.tzkt.model.BigMapKey
import org.springframework.web.reactive.function.client.WebClient

class BigMapKeyClient(
    webClient: WebClient
) : BaseClient(webClient) {
    suspend fun bigMapKeyWithId(bigMapId: String, keyId: String): BigMapKey {
        val key = invoke<BigMapKey> {
            it.path("/v1/bigmaps/$bigMapId/keys/$keyId")
        }
        return key
    }

    suspend fun bigMapKeyWithName(contract: String, bigMapName: String, keyId: String): BigMapKey {
        val key = invoke<BigMapKey> {
            it.path("v1/contracts/$contract/bigmaps/$bigMapName/keys/$keyId")
        }
        return key
    }
}
