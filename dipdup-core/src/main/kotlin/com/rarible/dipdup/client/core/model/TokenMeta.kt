package com.rarible.dipdup.client.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class TokenMeta(
    val name: String,
    val description: String? = null,
    val attributes: List<Attribute> = emptyList(),
    val tags: List<String> = emptyList(),
    val content: List<Content> = emptyList()
) {

    companion object {
        val UNTITLED = "Untitled"
    }

    data class Attribute(
        val key: String,
        val value: String? = null,
        val type: String? = null,
        val format: String? = null
    )

    data class Content(
        val uri: String?,
        val mimeType: String,
        val representation: Representation
    )

    enum class Representation {
        ORIGINAL, BIG, PREVIEW
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

        fun processURI(uri: String): String {
            return if (uri.contains("ipfs://ipfs/")) {
                "ipfs://" + uri.split("ipfs://ipfs/")[1]
            } else
                uri
        }

        fun contents(): List<Content> {
            var contents = formats.filter { it.uri != null && it.mimeType != null }.map {
                Content(
                    uri = it.uri?.let { itURI -> processURI(itURI) },
                    mimeType = it.mimeType!!,
                    representation = representation(it)
                )
            }.toMutableList()
            if (contents.count { it.representation == Representation.PREVIEW } == 0 && displayUri != null) {
                contents.add(
                    Content(
                        uri = processURI(displayUri),
                        mimeType = guessMime(displayUri),
                        representation = Representation.PREVIEW
                    )
                )
            }
            if (contents.count { it.representation == Representation.ORIGINAL } == 0 && artifactUri != null) {
                contents.add(
                    Content(
                        uri = processURI(artifactUri),
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
                else -> "image/jpeg"
            }
        }
    }
}

