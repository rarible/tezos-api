package com.rarible.tzkt.royalties

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.rarible.tzkt.client.BigMapKeyClient
import com.rarible.tzkt.client.IPFSClient
import com.rarible.tzkt.client.OwnershipClient
import com.rarible.tzkt.config.KnownAddresses
import com.rarible.tzkt.model.BigMapKey
import com.rarible.tzkt.model.Part
import com.rarible.tzkt.tokens.BidouHandler
import okio.ByteString.Companion.decodeHex
import org.slf4j.LoggerFactory

class RoyaltiesHandler(
    val bigMapKeyClient: BigMapKeyClient,
    val ownershipClient: OwnershipClient,
    val ipfsClient: IPFSClient,
    val royaltiesConfig: KnownAddresses
) {
    val logger = LoggerFactory.getLogger(javaClass)
    val bidouRoyalties = mapOf(
        Pair(royaltiesConfig.bidou8x8, 1000),
        Pair(royaltiesConfig.bidou24x24, 1500)
    )

    suspend fun processRoyalties(id: String): List<Part> {
        val contract = id.split(":")[0]
        val tokenId = id.split(":")[1]
        return processRoyalties(contract, tokenId)
    }

    suspend fun processRoyalties(contract: String, tokenId: String): List<Part> {
        logger.info("Checking royalties for token $contract:$tokenId")
        var part: List<Part>

        when (contract) {
            royaltiesConfig.hen -> {
                logger.info("Token $contract:$tokenId royalties pattern is HEN")
                part = getHENRoyalties(tokenId)
                return part
            }
            royaltiesConfig.kalamint -> {
                logger.info("Token $contract:$tokenId royalties pattern is KALAMINT (public collection)")
                part = getKalamintRoyalties(contract, tokenId)
                return part
            }
            royaltiesConfig.fxhashV1 -> {
                logger.info("Token $contract:$tokenId royalties pattern is FXHASH_V1")
                part = getFxHashV1Royalties(tokenId)
                return part
            }
            royaltiesConfig.fxhashV2 -> {
                logger.info("Token $contract:$tokenId royalties pattern is FXHASH_V2")
                part = getFxHashV2Royalties(tokenId)
                return part
            }
            royaltiesConfig.versum -> {
                logger.info("Token $contract:$tokenId royalties pattern is VERSUM")
                part = getVersumRoyalties(tokenId)
                return part
            }
            royaltiesConfig.bidou8x8, royaltiesConfig.bidou24x24 -> {
                logger.info("Token $contract:$tokenId royalties pattern is 8Bidou 8x8 (10% auto)")
                part = getBidouRoyalties(contract, tokenId)
                if(part.isNotEmpty()){
                    return part
                }
            }
        }

        //check rarible pattern in generated contract storage
        part = getRaribleRoyalties(contract, tokenId)

        if (part.isNotEmpty()) {
            logger.info("Token $contract:$tokenId royalties pattern is RARIBLE")
            return part
        }

        //check kalamint pattern for private collection in contract storage
        part = getKalamintRoyalties(contract, tokenId)

        if (part.isNotEmpty()) {
            logger.info("Token $contract:$tokenId royalties pattern is KALAMINT (private collection)")
            return part
        }

        //fetch metadata from ipfs
        //check ipfs pattern
        var tokenMetadata: BigMapKey? = null
        try {
            tokenMetadata = bigMapKeyClient.bigMapKeyWithName(contract, "token_metadata", tokenId)
            val metadataMap = tokenMetadata.value as LinkedHashMap<String, String>
            val metadata = metadataMap["token_info"] as LinkedHashMap<String, String>
            val uri = metadata[""]?.decodeHex()?.utf8()
            if (!uri.isNullOrEmpty()) {
                val ipfsData = ipfsClient.fetchIpfsDataFromBlockchain(uri)
                if (ipfsData.has("royalties")) {
                    val royalties = ipfsData["royalties"] as JsonNode
                    if (royalties.has("shares") && royalties.has("decimals")) {
                        logger.info("Token $contract:$tokenId royalties pattern is OBJKT")
                        part = getObjktRoyalties(contract, tokenId, royalties)
                        return part
                    }
                }
                if (ipfsData.has("attributes")) {
                    part = getSweetIORoyalties(contract, tokenId, ipfsData)
                }
            }
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token $contract:$tokenId from IPFS metadata: ${e.message}")
        }

        if (part.isNullOrEmpty()) {
            part = getRoyaltiesFromRoyaltiesManager(contract, tokenId)
        }

        if (part.isNullOrEmpty() && tokenMetadata != null) {
            part = getEmbeddedRoyalty(tokenMetadata.value, "$contract:$tokenId")
        }

        if (part.isNullOrEmpty()) {
            //fallback: if everything else failed, we set the royalties to 0 for the token first owner
            try {
                val ownerships = ownershipClient.ownershipsByToken(
                    "$contract:$tokenId",
                    continuation = null,
                    sortOnFirstLevel = true,
                    sortAsc = true,
                    removeEmptyBalances = false
                )
                if (ownerships.items.isNotEmpty()) {
                    val firstOwner = ownerships.items.first()
                    part = listOf(Part(address = firstOwner.account!!.address, share = 0))
                }
            } catch (e: Exception) {
                logger.warn("Could not get first owner for token $contract:$tokenId: ${e.message}")
            }
        }

        return part
    }

    private fun getEmbeddedRoyalty(tokenMetadata: Any?, tokenId: String): List<Part> {
        return try {
            val metadataMap = tokenMetadata as LinkedHashMap<String, String>
            val metadata = metadataMap["token_info"] as LinkedHashMap<String, String>
            val embeddedRoyalty = metadata["royalties"].toString().decodeHex().utf8()
            val royalty: EmbeddedRoyalty = ObjectMapper().registerModule(kotlinModule()).readValue(embeddedRoyalty)
            val decimal = royalty.decimals?.let { it.toDouble() }?.let { Math.pow(10.0, it).toInt() } ?: 1
            royalty.shares.map { Part(it.key, it.value * decimal) }
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token $tokenId from embedded metadata: ${e.message}")
            emptyList()
        }
    }

    data class EmbeddedRoyalty(val decimals: Int?, val shares: Map<String, Int>)

    private suspend fun getHENRoyalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            val key = bigMapKeyClient.bigMapKeyWithName(royaltiesConfig.henRoyalties, "royalties", tokenId)
            royaltiesMap = key.value as LinkedHashMap<String, String>
            parts.add(Part(royaltiesMap["issuer"]!!, royaltiesMap["royalties"]!!.toInt() * 10))
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token ${royaltiesConfig.henRoyalties}:$tokenId with HEN pattern: ${e.message}")
        }
        return parts
    }

    private suspend fun getKalamintRoyalties(contract: String, tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            val key = bigMapKeyClient.bigMapKeyWithName(contract, "tokens", tokenId)
            royaltiesMap = key.value as LinkedHashMap<String, String>
            parts.add(Part(royaltiesMap["creator"]!!, royaltiesMap["creator_royalty"]!!.toInt() * 100))
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token ${contract}:$tokenId with Kalamint pattern: ${e.message}")
        }
        return parts
    }

    private suspend fun getFxHashV1Royalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        var authorMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            val royaltiesKey = bigMapKeyClient.bigMapKeyWithName(royaltiesConfig.fxhashV1, "token_data", tokenId)
            royaltiesMap = royaltiesKey.value as LinkedHashMap<String, String>
            val authorKey = bigMapKeyClient.bigMapKeyWithName(
                royaltiesConfig.fxhashV1Manager,
                "ledger",
                royaltiesMap["issuer_id"]!!
            )
            authorMap = authorKey.value as LinkedHashMap<String, String>
            parts.add(Part(authorMap["author"]!!, royaltiesMap["royalties"]!!.toInt() * 10))
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token ${royaltiesConfig.fxhashV1}:$tokenId with FXHASH pattern: ${e.message}")
        }
        return parts
    }

    private suspend fun getFxHashV2Royalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        var authorMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            val royaltiesKey = bigMapKeyClient.bigMapKeyWithName(royaltiesConfig.fxhashV2, "token_data", tokenId)
            royaltiesMap = royaltiesKey.value as LinkedHashMap<String, String>
            val royaltiesAmount = royaltiesMap["royalties"]!!.toInt()
            val percentages = royaltiesMap["royalties_split"] as ArrayList<LinkedHashMap<String, String>>
            percentages.forEach {
                parts.add(Part(it["address"]!!, royaltiesAmount * it["pct"]!!.toInt() / 100))
            }
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token ${royaltiesConfig.fxhashV2}:$tokenId with FXHASH pattern: ${e.message}")
        }
        return parts
    }

    //TODO: need to verify if splits need to be handled for royalties in VERSUM
    private suspend fun getVersumRoyalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            val key = bigMapKeyClient.bigMapKeyWithName(royaltiesConfig.versum, "token_extra_data", tokenId)
            royaltiesMap = key.value as LinkedHashMap<String, String>
            parts.add(Part(royaltiesMap["minter"]!!, royaltiesMap["royalty"]!!.toInt() * 10))
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token ${royaltiesConfig.versum}:$tokenId with VERSUM pattern: ${e.message}")
        }
        return parts
    }

    private suspend fun getRaribleRoyalties(contract: String, tokenId: String): List<Part> {
        val parts = mutableListOf<Part>()
        try {
            val key = bigMapKeyClient.bigMapKeyWithName(contract, "royalties", tokenId)
            val royaltiesMap = key.value as List<LinkedHashMap<String, String>>
            royaltiesMap.forEach { part ->
                parts.add(Part(part["partAccount"]!!, part["partValue"]!!.toInt()))
            }
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token ${contract}:$tokenId with RARIBLE pattern: ${e.message}")
        }
        return parts
    }

    private fun getObjktRoyalties(contract: String, tokenId: String, data: JsonNode): List<Part> {
        var partList: MutableList<Part> = ArrayList()
        try {
            val shares = data["shares"].fieldNames()
            val decimals = data["decimals"].asDouble()
            shares.forEach {
                //check if it is codecrafting pattern
                val percentage = data["shares"][it].asDouble() * Math.pow(10.0, decimals * -1) * 10000
                partList.add(
                    Part(
                        it, percentage.toInt()
                    )
                )
            }
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for $contract:$tokenId with OBJKT pattern: ${e.message}")
        }
        return partList
    }

    private fun getSweetIORoyalties(contract: String, tokenId: String, data: JsonNode): List<Part> {
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
                partList.add(Part(recipient, share.toInt()))
            }
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for $contract:$tokenId with SWEET IO pattern: ${e.message}")
        }

        return partList
    }

    private suspend fun getRoyaltiesFromRoyaltiesManager(contract: String, tokenId: String): List<Part> {
        val parts = mutableListOf<Part>()
        var data: List<LinkedHashMap<String, String>>? = null
        try {
            val tokenKeyValue = "{\"address\":\"$contract\",\"nat\":$tokenId}"
            val key = bigMapKeyClient.bigMapKeyWithName(royaltiesConfig.royaltiesManager, "royalties", tokenKeyValue)
            data = key.value as List<LinkedHashMap<String, String>>
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token $contract:$tokenId with Royalties Manager (with token id) pattern: ${e.message}")
        }

        if(data.isNullOrEmpty()){
            try {
                val tokenKeyValue = "{\"address\":\"$contract\",\"nat\":null}"
                val key = bigMapKeyClient.bigMapKeyWithName(royaltiesConfig.royaltiesManager, "royalties", tokenKeyValue)
                data = key.value as List<LinkedHashMap<String, String>>
            } catch (e: Exception) {
                logger.warn("Could not parse royalties for token $contract:$tokenId with Royalties Manager (without token id) pattern: ${e.message}")
            }
        }
        data?.forEach { part ->
            parts.add(Part(part["partAccount"]!!, part["partValue"]!!.toInt()))
        }
        return parts
    }

    private suspend fun getBidouRoyalties(contract: String, tokenId: String): List<Part> {
        val parts = mutableListOf<Part>()
        try {
            val handler = BidouHandler(bigMapKeyClient)
            val properties = handler.getData(contract, tokenId)
            if(properties != null){
                parts.add(
                    Part(properties.creator, bidouRoyalties[contract]!!)
                )
            }
        } catch (e: Exception) {
            logger.warn("Could not parse royalties for token ${contract}:$tokenId with 8Bidou pattern: ${e.message}")
        }
        return parts
    }
}
