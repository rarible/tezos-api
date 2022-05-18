package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetActivitiesByIdsQuery
import com.rarible.dipdup.client.GetActivitiesQuery
import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.core.model.DipDupOrderCancelActivity
import com.rarible.dipdup.client.core.model.DipDupOrderListActivity
import com.rarible.dipdup.client.core.model.DipDupOrderSellActivity
import com.rarible.dipdup.client.core.model.TezosPlatform
import java.math.BigDecimal
import java.time.OffsetDateTime

fun convert(source: GetActivitiesQuery.Marketplace_activity) = activityEvent(
    type = source.type,
    id = source.id.toString(),
    reverted = false,
    date = OffsetDateTime.parse(source.operation_timestamp.toString()),
    hash = source.operation_hash,
    source = TezosPlatform.valueOf(source.platform),
    maker = source.maker,
    taker = source.taker,
    make = getAsset(source.make_asset_class, source.make_contract, source.make_token_id, source.make_value),
    take = getAsset(source.take_asset_class, source.take_contract, source.take_token_id, source.take_value),
    price = source.sell_price
)

fun convert(source: GetActivitiesByIdsQuery.Marketplace_activity) = activityEvent(
    type = source.type,
    id = source.id.toString(),
    reverted = false,
    date = OffsetDateTime.parse(source.operation_timestamp.toString()),
    hash = source.operation_hash,
    source = TezosPlatform.valueOf(source.platform),
    maker = source.maker,
    taker = source.taker,
    make = getAsset(source.make_asset_class, source.make_contract, source.make_token_id, source.make_value),
    take = getAsset(source.take_asset_class, source.take_contract, source.take_token_id, source.take_value),
    price = source.sell_price
)

fun convertAll(source: List<GetActivitiesQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it) }
}

fun convertByIds(source: List<GetActivitiesByIdsQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it) }
}

fun activityEvent(
    type: String, id: String,
    date: OffsetDateTime,
    reverted: Boolean,
    hash: String,
    source: TezosPlatform,
    maker: String,
    taker: String?,
    make: Asset,
    take: Asset,
    price: Any
): DipDupActivity {
    return when (type) {
        "LIST" -> DipDupOrderListActivity(
            id = id,
            date = date,
            reverted = reverted,
            hash = hash,
            source = source,
            maker = maker,
            make = make,
            take = take,
            price = BigDecimal(price.toString())
        )
        "SELL" -> DipDupOrderSellActivity(
            id = id,
            date = date,
            reverted = reverted,
            hash = hash,
            source = source,
            nft = make,
            payment = take,
            buyer = taker!!,
            seller = maker,
            price = BigDecimal(price.toString())
        )
        "CANCEL_LIST" -> DipDupOrderCancelActivity(
            id = id,
            date = date,
            reverted = reverted,
            hash = hash,
            source = source,
            maker = maker,
            make = make,
            take = take
        )
        else -> throw RuntimeException("Unknown activity type: $type")
    }
}


