package com.rarible.tzkt.model

data class OwnershipId(
    val contract: String,
    val tokenId: String,
    val owner: String
) {

    override fun toString(): String {
        return listOf(contract, tokenId, owner).joinToString(":")
    }

    companion object {
        fun parse(value: String): OwnershipId {
            val parsed = value.split(":")
            return OwnershipId(parsed[0], parsed[1], parsed[2])
        }
    }

}
