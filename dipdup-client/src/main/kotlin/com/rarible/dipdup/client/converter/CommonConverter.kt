package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.Part
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

fun getParts(data: Any?): List<Part> {
    var parts: MutableList<Part> = mutableListOf()
    if(data != null){
        val rawParts = data as List<LinkedHashMap<String, Any>>
        for(rawPart in rawParts){
            if (rawPart.keys.containsAll(listOf("part_account", "part_value"))) {
                parts.add(Part(rawPart["part_account"] as String, (rawPart["part_value"] as String).toInt()))
            } else {
                throw RuntimeException("Unknown parts format: $rawParts")
            }
        }
    }
    return parts
}

fun takeXTZAsset(price: Any): Asset = Asset(
    assetType = Asset.XTZ(),
    assetValue = BigDecimal(price.toString())
)
