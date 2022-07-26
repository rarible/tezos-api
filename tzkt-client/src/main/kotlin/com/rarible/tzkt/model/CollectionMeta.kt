package com.rarible.tzkt.model

data class CollectionMeta(
    val name: String?,
    val symbol: String?
) {
    fun isEmpty() = name == null && symbol == null
}
