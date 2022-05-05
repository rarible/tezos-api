package com.rarible.tzkt.tokens

import com.rarible.tzkt.client.BigMapKeyClient
import org.slf4j.LoggerFactory

class BidouHandler(val bigMapKeyClient: BigMapKeyClient) {

    val logger = LoggerFactory.getLogger(javaClass)

     suspend fun getData(contract: String, tokenId: String): BidouProperties? {
        var properties: BidouProperties? = null
        try {
            val key = bigMapKeyClient.bigMapKeyWithName(contract, "rgb", tokenId)
            val map = key.value as LinkedHashMap<String, String>
            properties = BidouProperties.fromMap(map)
        } catch (e: Exception) {
            logger.warn("Could not parse token: ${e.message}")
        }
        return properties
    }
}