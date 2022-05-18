package com.rarible.dipdup.client.core.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.math.BigDecimal
import java.math.BigInteger

data class Asset(
    val assetType: AssetType,
    val assetValue: BigDecimal
) {

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "assetClass")
    @JsonSubTypes(
        JsonSubTypes.Type(value = XTZ::class, name = XTZ_NAME),
        JsonSubTypes.Type(value = NFT::class, name = NFT_NAME),
        JsonSubTypes.Type(value = MT::class, name = MT_NAME),
        JsonSubTypes.Type(value = FT::class, name = FT_NAME)
    )
    sealed class AssetType

    data class XTZ(val assetClass: String = XTZ_NAME) : AssetType()
    data class NFT(val assetClass: String = NFT_NAME, val contract: String, val tokenId: BigInteger) : AssetType()
    data class MT(val assetClass: String = MT_NAME, val contract: String, val tokenId: BigInteger) : AssetType()
    data class FT(val assetClass: String = FT_NAME, val contract: String, val tokenId: BigInteger) : AssetType()

    companion object {
        const val FT_NAME = "TEZOS_FT"
        const val NFT_NAME = "TEZOS_NFT"
        const val MT_NAME = "TEZOS_MT"
        const val XTZ_NAME = "XTZ"
    }

}
