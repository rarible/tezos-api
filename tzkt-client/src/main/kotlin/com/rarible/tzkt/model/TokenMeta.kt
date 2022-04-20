package com.rarible.tzkt.model

data class TokenMeta(
    val name: String,
    val description: String? = null,
    val image: String? = null,
    val imagePreview: String? = null,
    val imageBig: String? = null,
    val animationUrl: String? = null,
    val attributes: List<TokenAttribute> = emptyList(),
    val rawJsonContent: String? = null
) {
    companion object {
        val EMPTY = TokenMeta(name = "Untitled")
        val UNTITLED = "Untitled"
    }
}

data class TokenAttribute(
    val key: String,
    val value: String? = null,
    val type: String? = null,
    val format: String? = null
)
