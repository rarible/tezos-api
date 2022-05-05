package com.rarible.tzkt.meta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.rarible.tzkt.client.BigMapKeyClient
import com.rarible.tzkt.config.KnownAddresses
import com.rarible.tzkt.model.Token
import com.rarible.tzkt.model.TokenMeta
import com.rarible.tzkt.model.TokenMeta.Attribute
import com.rarible.tzkt.model.TokenMeta.Content
import com.rarible.tzkt.model.TokenMeta.Representation
import com.rarible.tzkt.tokens.BidouHandler
import kotlinx.coroutines.runBlocking

class MetaService(private val mapper: ObjectMapper, private val bigMapKeyClient: BigMapKeyClient, private val knownAddresses: KnownAddresses) {

    fun meta(token: Token): TokenMeta {
        return if (null != token.metadata) {
            val meta: TzktMeta = mapper.convertValue(token.metadata)
            TokenMeta(
                name = meta.name ?: TokenMeta.UNTITLED,
                description = meta.description,
                content = meta.contents(),
                attributes = meta.attrs()
            )
        } else {
            when (token.contract?.address) {
                knownAddresses.bidou8x8, knownAddresses.bidou24x24 -> {
                    val bidouHandler = BidouHandler(bigMapKeyClient)
                    val properties = runBlocking {
                        bidouHandler.getData(token.contract.address, token.tokenId!!)
                    }
                    if (properties != null) {
                        TokenMeta(
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
                        TokenMeta.EMPTY
                    }
                }
                else -> TokenMeta.EMPTY
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TzktMeta(
        val name: String? = null,
        val description: String? = null,
        val tags: List<String> = emptyList(),
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
            val uri: String,
            val mimeType: String
        )

        fun attrs() = tags.map { Attribute(it) }

        fun contents() = formats.map {
            Content(
                uri = it.uri,
                mimeType = it.mimeType,
                representation = representation(it)
            )
        }

        private fun representation(content: TzktContent): Representation {
            return when (content.uri) {
                artifactUri -> Representation.ORIGINAL
                thumbnailUri -> Representation.PREVIEW
                else -> Representation.BIG
            }
        }
    }

}
