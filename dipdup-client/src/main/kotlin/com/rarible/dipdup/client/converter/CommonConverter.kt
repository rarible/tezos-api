package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.core.model.Asset
import java.math.BigDecimal
import java.math.BigInteger

fun getAsset(assetClass: String?, contract: String?, tokenId: String?, value: Any?): Asset {
    return when(assetClass) {
        Asset.FT_NAME -> Asset(
            assetType = Asset.FT(contract = contract!!, tokenId = BigInteger(tokenId)),
            assetValue = BigDecimal(value.toString())
        )
        Asset.MT_NAME -> Asset(
            assetType = Asset.MT(contract = contract!!, tokenId = BigInteger(tokenId)),
            assetValue = BigDecimal(value.toString())
        )
        Asset.NFT_NAME -> Asset(
            assetType = Asset.NFT(contract = contract!!, tokenId = BigInteger(tokenId)),
            assetValue = BigDecimal(value.toString())
        )
        Asset.XTZ_NAME -> Asset(
            assetType = Asset.XTZ(),
            assetValue = BigDecimal(value.toString())
        )
        else -> throw RuntimeException("Unknown assetClass: $assetClass")
    }
}

fun takeXTZAsset(price: Any): Asset = Asset(
    assetType = Asset.XTZ(),
    assetValue = BigDecimal(price.toString())
)
