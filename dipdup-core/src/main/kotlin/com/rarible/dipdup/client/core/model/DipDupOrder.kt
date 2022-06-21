package com.rarible.dipdup.client.core.model

import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime


data class Part(
    val account: String,
    val value: Int
)

data class DipDupOrder(
    val id: String,
    val fill: BigDecimal,
    val platform: TezosPlatform,
    val cancelled: Boolean,
    val status: OrderStatus,
    val startAt: OffsetDateTime?,
    val endedAt: OffsetDateTime?,
    val endAt: OffsetDateTime?,
    val createdAt: OffsetDateTime,
    val lastUpdatedAt: OffsetDateTime,
    val maker: String,
    val make: Asset,
    val taker: String?,
    val take: Asset,
    val salt: BigInteger,
    val originFees: List<Part>,
    val payouts: List<Part>
)
