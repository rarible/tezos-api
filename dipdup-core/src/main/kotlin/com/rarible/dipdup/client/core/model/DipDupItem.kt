package com.rarible.dipdup.client.core.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigInteger
import java.time.Instant

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DipDupItem(
        override val id: String,
        val minted: BigInteger?,
        val mintedAt: Instant,
        val supply: BigInteger,
        val tokenId: BigInteger,
        override val updated: Instant,
        val contract: String,
        val deleted: Boolean,
        val tzktId: BigInteger,
        val metadata: TokenMeta
) : DipDupEntity {
        companion object {
                fun itemId(contract: String, tokenId: BigInteger): String {
                        return "$contract:$tokenId"
                }
        }
}
