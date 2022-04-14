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
    source = TezosPlatform.get(source.platform),
    maker = source.maker,
    taker = source.taker,
    make = makeFTAsset(source.contract, source.token_id, source.amount),
    take = takeXTZAsset(source.sell_price),
    price = source.sell_price
)

fun convert(source: GetActivitiesByIdsQuery.Marketplace_activity) = activityEvent(
    type = source.type,
    id = source.id.toString(),
    reverted = false,
    date = OffsetDateTime.parse(source.operation_timestamp.toString()),
    hash = source.operation_hash,
    source = TezosPlatform.get(source.platform),
    maker = source.maker,
    taker = source.taker,
    make = makeFTAsset(source.contract, source.token_id, source.amount),
    take = takeXTZAsset(source.sell_price),
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
        "list" -> DipDupOrderListActivity(
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
        "match" -> DipDupOrderSellActivity(
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
        "cancel" -> DipDupOrderCancelActivity(
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


