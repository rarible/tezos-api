package com.rarible.dipdup.listener.model

import java.math.BigDecimal
import java.math.BigInteger

data class Asset(
    val type: Type,
    val value: BigDecimal
) {

    data class Type(
        val assetClass: AssetClass,
        val contract: String,
        val token_id: BigInteger
    )

    enum class AssetClass(val value: String) {
        FA("FA"),
        XTZ("XTZ"),
        MT("MT"),
        NFT("NFT")
    }

}
