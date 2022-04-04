package com.rarible.dipdup.listener.model

import java.math.BigDecimal
import java.time.OffsetDateTime

data class OrderEvent(
    val id: String,
    val fill: BigDecimal,
    val platform: Platform,
    val status: Status,
    val startedAt: OffsetDateTime?,
    val endedAt: OffsetDateTime?,
    val lastUpdatedAt: OffsetDateTime?,
    val makePrice: BigDecimal,
    val maker: String,
    val make: Asset,
    val take: Asset
)
