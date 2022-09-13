package com.rarible.dipdup.client.core.model

import java.math.BigInteger
import java.time.Instant

data class DipDupOwnership(
        override val id: String,
        override val updated: Instant,
        val created: Instant,
        val contract: String,
        val tokenId: BigInteger,
        val owner: String,
        val balance: BigInteger
) : DipDupEntity
