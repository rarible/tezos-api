package com.rarible.dipdup.client.core.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DipDupCollection(
    val id: String,
    val owner: String,
    val name: String?,
    val symbol: String?,
    val standard: String?,
    val minters: List<String> = emptyList(),
    val meta: Meta? = null,
) {

    data class Meta(
        val name: String? = null,
        val description: String? = null,
        val symbol: String? = null,
        val homepage: String? = null,
        val content: List<Content> = emptyList()
    )

    data class Content(
        val uri: String,
        val representation: Representation
    )

    enum class Representation {
        ORIGINAL, BIG, PREVIEW
    }

}
