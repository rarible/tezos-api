package com.rarible.tzkt.meta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.rarible.tzkt.model.Token
import com.rarible.tzkt.model.TokenMeta
import com.rarible.tzkt.model.TokenMeta.Attribute
import com.rarible.tzkt.model.TokenMeta.Content
import com.rarible.tzkt.model.TokenMeta.Representation

class MetaService(val mapper: ObjectMapper) {

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
            TokenMeta.EMPTY
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
