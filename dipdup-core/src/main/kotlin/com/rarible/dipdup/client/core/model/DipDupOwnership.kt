package com.rarible.dipdup.client.core.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigInteger
import java.time.Instant

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DipDupOwnership(
        override val id: String,
        override val updated: Instant,
        val created: Instant,
        val contract: String,
        val tokenId: BigInteger,
        val owner: String,
        val balance: BigInteger
) : DipDupEntity {
        companion object {
                fun ownershipId(contract: String, tokenId: BigInteger, owner: String): String {
                        return "$contract:$tokenId:$owner"
                }
        }
}
