package com.rarible.dipdup.client.core.model

import java.math.BigDecimal
import java.math.BigInteger
import java.time.Instant

data class DipDupToken(
    override val id: String,
    val metadata_synced: Boolean,
    val minted: BigDecimal,
    val minted_at: Instant,
    val supply: BigDecimal,
    val token_id: BigInteger,
    override val updated: Instant,
    val contract: String,
    val deleted: Boolean,
    val metadata_retries: Int,
    val tzkt_id: Int
) : DipDupEntity
