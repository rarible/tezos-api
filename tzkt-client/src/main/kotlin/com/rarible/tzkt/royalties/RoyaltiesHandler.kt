package com.rarible.tzkt.royalties

import com.fasterxml.jackson.databind.JsonNode
import com.rarible.tzkt.client.BigMapKeyClient
import com.rarible.tzkt.client.IPFSClient
import com.rarible.tzkt.model.Part
import okio.ByteString.Companion.decodeHex
import org.slf4j.LoggerFactory

class RoyaltiesHandler(val bigMapKeyClient: BigMapKeyClient, val ipfsClient: IPFSClient) {
    companion object KnownAddresses {
        const val HEN = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        const val KALAMINT = "KT1EpGgjQs73QfFJs9z7m1Mxm5MTnpC2tqse"
        const val FXHASH = "KT1KEa8z6vWXDJrVqtMrAeDVzsvxat3kHaCE"
        const val VERSUM = "KT1LjmAdYQCLBjwv4S2oFkEzyHVkomAf5MrW"
    }

    val logger = LoggerFactory.getLogger(javaClass)

    suspend fun processRoyalties(id: String): List<Part> {
        logger.info("Checking royalties for token $id")
        //check if address is known
        //check storage
        val contract = id.split(":")[0]
        val tokenId = id.split(":")[1]
        var part: List<Part>
        if (contract == HEN) {
            logger.info("Token $id royalties pattern is HEN")
            part = getHENRoyalties(tokenId)
            return part
        }
        if (contract == KALAMINT) {
            logger.info("Token $id royalties pattern is KALAMINT")
            part = getKalamintRoyalties(tokenId)
            return part
        }
        if (contract == FXHASH) {
            logger.info("Token $id royalties pattern is FXHASH")
            part = getFxHashRoyalties(tokenId)
            return part
        }
        if (contract == VERSUM) {
            logger.info("Token $id royalties pattern is VERSUM")
            part = getVersumRoyalties(tokenId)
            return part
        }

        //check rarible pattern in generated contract storage
        part = getRaribleRoyalties(contract, tokenId)

        if (!part.isNullOrEmpty()) {
            logger.info("Token $id royalties pattern is RARIBLE")
            return part
        }

        //fetch metadata from ipfs
        //check ipfs pattern
        try {
            val tokenMetadata = bigMapKeyClient.bigMapKeyWithName(contract, "token_metadata", tokenId)
            val metadataMap = tokenMetadata.value as LinkedHashMap<String, String>
            val metadata = metadataMap["token_info"] as LinkedHashMap<String, String>
            val uri = metadata[""]?.decodeHex()?.utf8()
            val hash = uri!!.split("//")
            val ipfsData = ipfsClient.data(hash[1])

            if (ipfsData.has("royalties")) {
                val royalties = ipfsData["royalties"] as JsonNode
                if (royalties.has("shares") && royalties.has("decimals")) {
                    logger.info("Token $id royalties pattern is OBJKT")
                    part = getObjktRoyalties(royalties)
                    return part
                }
            }

            if (ipfsData.has("attributes")) {
                part = getSweetIORoyalties(ipfsData)
            }
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token $id from IPFS metadata: ${e.message}")
        }

        return part
    }

    private suspend fun getHENRoyalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            val key = bigMapKeyClient.bigMapKeyWithId("522", tokenId)
            royaltiesMap = key.value as LinkedHashMap<String, String>
            parts.add(Part(royaltiesMap["issuer"]!!, royaltiesMap["royalties"]!!.toLong() * 10))
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token $tokenId with HEN pattern: ${e.message}")
        }
        return parts
    }

    private suspend fun getKalamintRoyalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            val key = bigMapKeyClient.bigMapKeyWithId("861", tokenId)
            royaltiesMap = key.value as LinkedHashMap<String, String>
            parts.add(Part(royaltiesMap["creator"]!!, royaltiesMap["creator_royalty"]!!.toLong() * 100))
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token $tokenId with Kalamint pattern: ${e.message}")
        }
        return parts
    }

    private suspend fun getFxHashRoyalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        var authorMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            val royaltiesKey = bigMapKeyClient.bigMapKeyWithId("22788", tokenId)
            royaltiesMap = royaltiesKey.value as LinkedHashMap<String, String>
            val authorKey = bigMapKeyClient.bigMapKeyWithId("70072", royaltiesMap["issuer_id"]!!)
            authorMap = authorKey.value as LinkedHashMap<String, String>
            parts.add(Part(authorMap["author"]!!, royaltiesMap["royalties"]!!.toLong() * 10))
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token $tokenId with FXHASH pattern: ${e.message}")
        }
        return parts
    }

    //TODO: need to verify if splits need to be handled for royalties in VERSUM
    private suspend fun getVersumRoyalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            val key = bigMapKeyClient.bigMapKeyWithId("75555", tokenId)
            royaltiesMap = key.value as LinkedHashMap<String, String>
            parts.add(Part(royaltiesMap["minter"]!!, royaltiesMap["royalty"]!!.toLong() * 10))
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token $tokenId with VERSUM pattern: ${e.message}")
        }
        return parts
    }

    private suspend fun getRaribleRoyalties(contract: String, tokenId: String): List<Part> {
        val parts = mutableListOf<Part>()
        try {
            val key = bigMapKeyClient.bigMapKeyWithName(contract, "royalties", tokenId)
            val royaltiesMap = key.value as List<LinkedHashMap<String, String>>
            royaltiesMap.forEach { part ->
                parts.add(Part(part["partAccount"]!!, part["partValue"]!!.toLong()))
            }
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token $tokenId with RARIBLE pattern: ${e.message}")
        }
        return parts
    }

    private fun getObjktRoyalties(data: JsonNode): List<Part> {
        var partList: MutableList<Part> = ArrayList()
        try {
            val shares = data["shares"].fieldNames()
            shares.forEach {
                partList.add(
                    Part(
                        it, data["shares"][it].asLong() * 10
                    )
                )
            }
        } catch (e: Exception) {
            logger.warn("Could not parse royalties with OBJKT pattern: ${e.message}")
        }
        return partList
    }

    private fun getSweetIORoyalties(data: JsonNode): List<Part> {
        var partList: MutableList<Part> = ArrayList()
        var recipient = ""
        var share = ""
        try {
            val attributes = data["attributes"].asIterable()
            attributes.forEach {
                if (it.has("name") && it["name"].asText() == "fee_recipient") {
                    recipient = it["value"].asText()
                }
                if (it.has("name") && it["name"].asText() == "seller_fee_basis_points") {
                    share = it["value"].asText()
                }
            }
            if (recipient.isNotEmpty() && share.isNotEmpty()) {
                logger.info("Token royalties pattern is SWEET IO")
                partList.add(Part(recipient, share.toLong()))
            }
        } catch (e: Exception) {
            logger.warn("Could not parse royalties with SWEET IO pattern: ${e.message}")
        }

        return partList
    }
}
