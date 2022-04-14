package com.rarible.dipdup.listener.model.event

import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.Status
import com.rarible.dipdup.client.core.model.TezosPlatform
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

data class DipDupOrderEvent(
    val id: String,
    val fill: BigDecimal,
    val makeStock: BigDecimal,
    val platform: TezosPlatform,
    val cancelled: Boolean,
    val status: Status,
    val startedAt: OffsetDateTime?,
    val endedAt: OffsetDateTime?,
    val createdAt: OffsetDateTime,
    val lastUpdatedAt: OffsetDateTime,
    val makePrice: BigDecimal,
    val maker: String,
    val make: Asset,
    val taker: String?,
    val take: Asset,
    val salt: BigInteger
)
