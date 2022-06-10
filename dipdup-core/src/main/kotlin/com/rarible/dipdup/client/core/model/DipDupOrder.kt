package com.rarible.dipdup.client.core.model

import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

data class DipDupOrder(
    val id: String,
    val fill: BigDecimal,
    val makeStock: BigDecimal,
    val platform: TezosPlatform,
    val cancelled: Boolean,
    val status: OrderStatus,
    val startAt: OffsetDateTime?,
    val endedAt: OffsetDateTime?,
    val endAt: OffsetDateTime?,
    val createdAt: OffsetDateTime,
    val lastUpdatedAt: OffsetDateTime,
    val makePrice: BigDecimal,
    val maker: String,
    val make: Asset,
    val taker: String?,
    val take: Asset,
    val salt: BigInteger
)
