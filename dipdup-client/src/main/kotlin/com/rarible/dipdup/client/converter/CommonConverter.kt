package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.core.model.Asset
import java.math.BigDecimal
import java.math.BigInteger

fun getAsset(assetClass: String?, contract: String?, tokenId: String?, value: Any?): Asset {
    return when(assetClass) {
        Asset.FT_NAME -> Asset(
            type = Asset.FT(contract = contract!!, tokenId = BigInteger(tokenId)),
            value = BigDecimal(value.toString())
        )
        Asset.MT_NAME -> Asset(
            type = Asset.MT(contract = contract!!, tokenId = BigInteger(tokenId)),
            value = BigDecimal(value.toString())
        )
        Asset.NFT_NAME -> Asset(
            type = Asset.NFT(contract = contract!!, tokenId = BigInteger(tokenId)),
            value = BigDecimal(value.toString())
        )
        Asset.XTZ_NAME -> Asset(
            type = Asset.XTZ(),
            value = BigDecimal(value.toString())
        )
        else -> throw RuntimeException("Unknown assetClass: $assetClass")
    }
}

fun takeXTZAsset(price: Any): Asset = Asset(
    type = Asset.XTZ(),
    value = BigDecimal(price.toString())
)
