package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.core.model.Asset
import java.math.BigDecimal
import java.math.BigInteger

fun makeFTAsset(contract: Any, tokenId: Any, value: Any): Asset = Asset(
    type = Asset.FT(contract = contract.toString(), tokenId = BigInteger(tokenId.toString())),
    value = BigDecimal(value.toString())
)

fun takeXTZAsset(price: Any): Asset = Asset(
    type = Asset.XTZ(),
    value = BigDecimal(price.toString())
)
