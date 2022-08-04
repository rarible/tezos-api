package com.rarible.tzkt.meta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.readValue
import com.rarible.tzkt.client.BigMapKeyClient
import com.rarible.tzkt.client.IPFSClient
import com.rarible.tzkt.config.KnownAddresses
import com.rarible.tzkt.model.BigMapKey
import com.rarible.tzkt.model.Token
import com.rarible.tzkt.model.TokenMeta
import com.rarible.tzkt.model.TokenMeta.Attribute
import com.rarible.tzkt.model.TokenMeta.Content
import com.rarible.tzkt.model.TokenMeta.Representation
import com.rarible.tzkt.tokens.BidouHandler
import kotlinx.coroutines.runBlocking
import okio.ByteString.Companion.decodeHex
import org.slf4j.LoggerFactory

class MetaService(
    private val mapper: ObjectMapper,
    private val bigMapKeyClient: BigMapKeyClient,
    private val ipfsClient: IPFSClient,
    private val knownAddresses: KnownAddresses
) {
    val logger = LoggerFactory.getLogger(javaClass)

    suspend fun meta(token: Token): TokenMeta {
        return if (null != token.metadata) {
            val meta: TzktMeta = mapper.convertValue(adjustMeta(token.metadata))
            TokenMeta(
                name = meta.name ?: TokenMeta.UNTITLED,
                description = meta.description,
                content = meta.contents(),
                attributes = meta.attributes ?: emptyList(),
                tags = meta.tags ?: emptyList()
            )
        } else {
            var meta: TokenMeta?
            when (token.contract?.address) {
                knownAddresses.bidou8x8, knownAddresses.bidou24x24 -> {
                    val bidouHandler = BidouHandler(bigMapKeyClient)
                    val properties = runBlocking {
                        bidouHandler.getData(token.contract.address, token.tokenId!!)
                    }
                    if (properties != null) {
                        meta = TokenMeta(
                            name = properties.tokenName,
                            description = properties.tokenDescription,
                            content = listOf(
                                Content(
                                    //TODO: fetch from union cache
                                    uri = "",
                                    mimeType = "image/jpeg",
                                    representation = Representation.PREVIEW
                                ),
                                Content(
                                    //TODO: fetch from union cache
                                    uri = "",
                                    mimeType = "image/jpeg",
                                    representation = Representation.ORIGINAL
                                ),
                            ),
                            attributes = listOf(
                                Attribute(key = "creator", value = properties.creator),
                                Attribute(key = "creator_name", value = properties.creatorName),
                            )
                        )
                    } else {
                        meta = TokenMeta.EMPTY
                    }
                }
                else -> meta = TokenMeta.EMPTY
            }
            if (meta == TokenMeta.EMPTY) {
                var ipfsMeta: TzktMeta? = null
                try {
                    var tokenMetadata: BigMapKey?
                    tokenMetadata =
                        bigMapKeyClient.bigMapKeyWithName(token.contract!!.address, "token_metadata", token.tokenId!!)
                    val metadataMap = tokenMetadata.value as LinkedHashMap<String, String>
                    val metadata = metadataMap["token_info"] as LinkedHashMap<String, String>
                    val uri = metadata[""]?.decodeHex()?.utf8()
                    if (!uri.isNullOrEmpty()) {
                        val ipfsData = ipfsClient.fetchIpfsDataFromBlockchain(uri)
                        ipfsMeta = mapper.convertValue(adjustMeta(mapper.convertValue(ipfsData)))
                    }
                } catch (e: Exception) {
                    logger.warn("Could not parse metadata for token ${token.contract!!.address}:${token.tokenId!!} from IPFS metadata: ${e.message}")
                }
                meta = if (ipfsMeta != null) {
                    TokenMeta(
                        name = ipfsMeta.name ?: TokenMeta.UNTITLED,
                        description = ipfsMeta.description,
                        content = ipfsMeta.contents(),
                        attributes = ipfsMeta.attributes ?: emptyList(),
                        tags = ipfsMeta.tags ?: emptyList()
                    )
                } else {
                    TokenMeta.EMPTY
                }
            }
            return meta
        }
    }

    private fun adjustMeta(source: Map<String, *>): Map<String, *> {
        val mutable = source.toMutableMap()
        mutable["formats"] = adjustListMap(mutable["formats"])
        mutable["creators"] = adjustList(mutable["creators"])
        mutable["tags"] = adjustList(mutable["tags"])
        if (mutable["attributes"] != null && mutable["attributes"] is List<*>) {
            mutable["attributes"] = (mutable["attributes"] as List<Map<String, *>>)
                .map { it.toMutableMap() }
                .filter {
                    it["key"] != null || it["name"] != null
                }
                .map {
                    it["key"] = it["name"]

                    // fill according to Attribute
                    mapOf(
                        "key" to it["key"],
                        "value" to it["value"],
                        "type" to it["type"],
                        "format" to it["format"]
                    )
                }
        } else {
            mutable.remove("attributes")
        }
        return mutable.toMap()
    }

    private fun adjustListMap(source: Any?): List<Map<String, Object>> {
        return when (source) {
            is String -> mapper.readValue(source)
            is List<*> -> source as List<Map<String, Object>>
            else -> emptyList()
        }
    }

    private fun adjustList(source: Any?): List<String> {
        return when (source) {
            is String -> listOf(source)
            is List<*> -> source as List<String>
            else -> emptyList()
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TzktMeta(
        val name: String? = null,
        val description: String? = null,
        val tags: List<String>? = emptyList(),
        val attributes: List<Attribute>? = emptyList(),
        val symbol: String? = null,
        val creators: List<String> = emptyList(),
        val formats: List<TzktContent> = emptyList(),
        val displayUri: String? = null,
        val artifactUri: String? = null,
        val thumbnailUri: String? = null,
        val decimals: String? = null
    ) {

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class TzktContent(
            val uri: String?,
            val mimeType: String?
        )

        fun contents(): List<TokenMeta.Content> {
            var contents = formats.filter { it.uri != null && it.mimeType != null }.map {
                Content(
                    uri = it.uri,
                    mimeType = it.mimeType!!,
                    representation = representation(it)
                )
            }.toMutableList()
            if (contents.count { it.representation == Representation.PREVIEW } == 0 && displayUri != null) {
                contents.add(
                    Content(
                        uri = displayUri,
                        mimeType = guessMime(displayUri),
                        representation = Representation.PREVIEW
                    )
                )
            }
            if (contents.count { it.representation == Representation.ORIGINAL } == 0 && artifactUri != null) {
                contents.add(
                    Content(
                        uri = artifactUri,
                        mimeType = guessMime(artifactUri),
                        representation = Representation.ORIGINAL
                    )
                )
            }
            return contents
        }

        private fun representation(content: TzktContent): Representation {
            return when (content.uri) {
                artifactUri -> Representation.ORIGINAL
                thumbnailUri -> Representation.PREVIEW
                else -> Representation.BIG
            }
        }

        private fun guessMime(value: String): String {
            return when {
                value.lowercase().endsWith("jpeg") || value.lowercase().endsWith("jpg") -> "image/jpeg"
                else -> ""
            }
        }
    }

}
