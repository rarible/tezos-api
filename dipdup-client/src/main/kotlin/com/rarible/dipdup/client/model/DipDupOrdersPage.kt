package com.rarible.dipdup.client.model

import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.client.core.model.OrderStatus
import com.rarible.dipdup.client.core.model.TezosPlatform
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

data class DipDupOrdersPage(
    val orders: List<DipDupOrder> = emptyList(),
    val continuation: String? = null
)
