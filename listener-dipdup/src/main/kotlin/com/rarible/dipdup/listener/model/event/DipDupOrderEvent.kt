package com.rarible.dipdup.listener.model.event

import com.rarible.dipdup.listener.model.Asset
import com.rarible.dipdup.listener.model.Platform
import com.rarible.dipdup.listener.model.Status
import java.math.BigDecimal
import java.time.OffsetDateTime

data class DipDupOrderEvent(
    val id: String,
    val fill: BigDecimal,
    val makeStock: BigDecimal,
    val platform: Platform,
    val status: Status,
    val startedAt: OffsetDateTime?,
    val endedAt: OffsetDateTime?,
    val createdAt: OffsetDateTime,
    val lastUpdatedAt: OffsetDateTime,
    val makePrice: BigDecimal,
    val maker: String,
    val make: Asset,
    val taker: String?,
    val take: Asset
)
