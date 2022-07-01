package com.rarible.tzkt.model

data class TokenMeta(
    val name: String,
    val description: String? = null,
    val attributes: List<Attribute> = emptyList(),
    val content: List<Content> = emptyList()
) {

    companion object {
        val EMPTY = TokenMeta(name = "Untitled")
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

}
