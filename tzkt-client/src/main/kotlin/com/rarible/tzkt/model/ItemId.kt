package com.rarible.tzkt.model

data class ItemId(
    val contract: String,
    val tokenId: String
) {

    override fun toString(): String {
        return listOf(contract, tokenId).joinToString(":")
    }

    companion object {
        fun parse(value: String): ItemId {
            val parsed = value.split(":")
            return ItemId(parsed[0], parsed[1])
        }
    }
}
