package com.rarible.dipdup.client.core.model

import java.math.BigDecimal
import java.math.BigInteger
import java.time.Instant

data class DipDupToken(
        override val id: String,
        val metadataSynced: Boolean,
        val minted: BigDecimal,
        val mintedAt: Instant,
        val supply: BigDecimal,
        val tokenId: BigInteger,
        override val updated: Instant,
        val contract: String,
        val deleted: Boolean,
        val metadataRetries: Int,
        val tzktId: Int
) : DipDupEntity
