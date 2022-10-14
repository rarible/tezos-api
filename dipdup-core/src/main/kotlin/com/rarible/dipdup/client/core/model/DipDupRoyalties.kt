package com.rarible.dipdup.client.core.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigInteger
import java.time.Instant

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DipDupRoyalties(
        override val id: String,
        override val updated: Instant,
        val tokenId: BigInteger,
        val contract: String,
        val parts: List<Part>
): DipDupEntity  {
        companion object {
                fun royaltiesId(contract: String, tokenId: BigInteger): String {
                        return "$contract:$tokenId"
                }
        }
}
