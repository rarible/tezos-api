package com.rarible.dipdup.client.model

import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.Status
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

data class DipDupOrder(
    val id: BigInteger,
    val cancelled: Boolean,
    val createdAt: OffsetDateTime,
    val endedAt: OffsetDateTime?,
    val startedAt: OffsetDateTime,
    val fill: BigDecimal,
    val internalOrderId: String,
    val lastUpdatedAt: OffsetDateTime,
    val maker: String,
    val make: Asset,
    val take: Asset,
    val taker: String?,
    val status: Status
)
