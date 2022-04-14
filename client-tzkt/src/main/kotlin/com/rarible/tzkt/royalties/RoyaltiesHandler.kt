package com.rarible.tzkt.royalties

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.rarible.tzkt.client.BigMapKeyClient
import com.rarible.tzkt.client.IPFSClient
import com.rarible.tzkt.model.Part
import kotlinx.coroutines.runBlocking
import okio.ByteString.Companion.decodeHex

class RoyaltiesHandler(val bigMapKeyClient: BigMapKeyClient, val ipfsClient: IPFSClient) {
    companion object KnownAddresses {
        const val HEN = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        const val KALAMINT = "KT1EpGgjQs73QfFJs9z7m1Mxm5MTnpC2tqse"
        const val FXHASH = "KT1KEa8z6vWXDJrVqtMrAeDVzsvxat3kHaCE"
        const val VERSUM = "KT1LjmAdYQCLBjwv4S2oFkEzyHVkomAf5MrW"
        const val RARIBLE = "KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS"
    }

    fun processRoyalties(ids: List<String>): Map<String, List<Part>?> {
        //check if address is known
        //check storage
        var allRoyalties = mutableMapOf<String, List<Part>?>()
        ids.forEach { it ->
            val contract = it.split(":")[0]
            val tokenId = it.split(":")[1]
            if (contract == HEN) {
                allRoyalties[it] = getHENRoyalties(tokenId)
                return@forEach
            }
            if (contract == KALAMINT) {
                allRoyalties[it] = getKalamintRoyalties(tokenId)
                return@forEach
            }
            if (contract == FXHASH) {
                allRoyalties[it] = getFxHashRoyalties(tokenId)
                return@forEach
            }
            if (contract == VERSUM) {
                allRoyalties[it] = getVersumRoyalties(tokenId)
                return@forEach
            }

            //check rarible pattern in generated contract storage
            allRoyalties[it] = getRaribleRoyalties(contract, tokenId)

            if (!allRoyalties[it].isNullOrEmpty()) {
                return@forEach
            }

            //fetch metadata from ipfs
            //check ipfs pattern
            runBlocking {
                try {
                    val tokenMetadata = bigMapKeyClient.bigMapKeyWithName(contract, "token_metadata", tokenId)
                    val metadataMap = tokenMetadata.value as LinkedHashMap<String, String>
                    val metadata = metadataMap["token_info"] as LinkedHashMap<String, String>
                    val uri = metadata[""]?.decodeHex()?.utf8()
                    val hash = uri!!.split("//")
                    val ipfsData = ipfsClient.data(hash[1])

                    if (ipfsData.has("royalties")) {
                        val royalties = ipfsData["royalties"] as JsonObject
                        if (royalties.has("shares") && royalties.has("decimals")) {
                            allRoyalties[it] = getObjktRoyalties(royalties)
                            return@runBlocking
                        }
                    }

                    if (ipfsData.has("attributes")) {
                        allRoyalties[it] = getSweetIORoyalties(ipfsData)
                    }
                } catch (e: NoSuchElementException) {
                    allRoyalties[it] = null
                }
            }
        }
        return allRoyalties
    }

    fun getHENRoyalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            runBlocking {
                val key = bigMapKeyClient.bigMapKeyWithId("522", tokenId)
                royaltiesMap = key.value as LinkedHashMap<String, String>
                parts.add(Part(royaltiesMap["issuer"]!!, royaltiesMap["royalties"]!!.toLong() * 10))
            }
        } catch(e: Exception){}
        return parts
    }

    fun getKalamintRoyalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            runBlocking {
                val key = bigMapKeyClient.bigMapKeyWithId("861", tokenId)
                royaltiesMap = key.value as LinkedHashMap<String, String>
                parts.add(Part(royaltiesMap["creator"]!!, royaltiesMap["creator_royalty"]!!.toLong() * 100))
            }
        } catch (e: Exception){}
        return parts
    }

    fun getFxHashRoyalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        var authorMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            runBlocking {
                val royaltiesKey = bigMapKeyClient.bigMapKeyWithId("22788", tokenId)
                royaltiesMap = royaltiesKey.value as LinkedHashMap<String, String>
                val authorKey = bigMapKeyClient.bigMapKeyWithId("70072", royaltiesMap["issuer_id"]!!)
                authorMap = authorKey.value as LinkedHashMap<String, String>
                parts.add(Part(authorMap["author"]!!, royaltiesMap["royalties"]!!.toLong() * 10))
            }
        }catch (e: Exception){}
        return parts
    }

    //TODO: need to verify if splits need to be handled for royalties in VERSUM
    fun getVersumRoyalties(tokenId: String): List<Part> {
        var royaltiesMap: LinkedHashMap<String, String>
        val parts = mutableListOf<Part>()
        try {
            runBlocking {
                val key = bigMapKeyClient.bigMapKeyWithId("75555", tokenId)
                royaltiesMap = key.value as LinkedHashMap<String, String>
                parts.add(Part(royaltiesMap["minter"]!!, royaltiesMap["royalty"]!!.toLong() * 10))
            }
        } catch (e: Exception){}
        return parts
    }

    fun getRaribleRoyalties(contract: String, tokenId: String): List<Part> {
        val parts = mutableListOf<Part>()
        try{
            runBlocking {
                val key = bigMapKeyClient.bigMapKeyWithName(contract, "royalties", tokenId)
                val royaltiesMap = key.value as List<LinkedHashMap<String, String>>
                royaltiesMap.forEach { part ->
                    parts.add(Part(part["partAccount"]!!, part["partValue"]!!.toLong()))
                }
            }
        } catch (e: Exception){}
        return parts
    }

    fun getObjktRoyalties(data: JsonObject): List<Part> {
        var partList: MutableList<Part> = ArrayList()
        try{
            val shares = data["shares"] as JsonObject
            shares.keySet().forEach {
                partList.add(
                    Part(
                        it, shares[it].asLong * 10
                    )
                )
            }
        } catch (e: Exception){}
        return partList
    }

    fun getSweetIORoyalties(data: JsonObject): List<Part> {
        var partList: MutableList<Part> = ArrayList()
        var recipient = ""
        var share = ""
        try {
            val attributes = data["attributes"].asJsonArray
            attributes.forEach {
                val item = it.asJsonObject
                if (item.has("name") && item["name"].asString == "fee_recipient") {
                    recipient = item["value"].asString
                }
                if (item.has("name") && item["name"].asString == "seller_fee_basis_points") {
                    share = item["value"].asString
                }
            }
            if (recipient.isNotEmpty() && share.isNotEmpty()) {
                partList.add(Part(recipient, share.toLong()))
            }
        } catch (e: Exception){}

        return partList
    }
}
