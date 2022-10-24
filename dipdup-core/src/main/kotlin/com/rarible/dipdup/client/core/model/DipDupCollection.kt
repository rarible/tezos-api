package com.rarible.dipdup.client.core.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.Instant


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DipDupCollection(
    val id: String,
    val owner: String,
    val name: String?,
    val symbol: String?,
    val standard: String?,
    val minters: List<String> = emptyList()
)
