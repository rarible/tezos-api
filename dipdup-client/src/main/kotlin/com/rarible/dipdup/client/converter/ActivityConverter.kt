package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetActivitiesAscQuery
import com.rarible.dipdup.client.GetActivitiesByIdsQuery
import com.rarible.dipdup.client.GetActivitiesByItemAscQuery
import com.rarible.dipdup.client.GetActivitiesByItemDescQuery
import com.rarible.dipdup.client.GetActivitiesDescQuery
import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.core.model.DipDupOrderCancelActivity
import com.rarible.dipdup.client.core.model.DipDupOrderListActivity
import com.rarible.dipdup.client.core.model.DipDupOrderSellActivity
import com.rarible.dipdup.client.core.model.TezosPlatform
import com.rarible.dipdup.client.fragment.Activity
import java.time.OffsetDateTime

fun convert(source: Activity) = activityEvent(
    type = source.type,
    id = source.id.toString(),
    operationCounter = source.operation_counter,
    reverted = false,
    date = OffsetDateTime.parse(source.operation_timestamp.toString()),
    hash = source.operation_hash,
    source = TezosPlatform.valueOf(source.platform),
    maker = source.maker,
    taker = source.taker,
    make = getAsset(source.make_asset_class, source.make_contract, source.make_token_id, source.make_value),
    take = getAsset(source.take_asset_class, source.take_contract, source.take_token_id, source.take_value)
)

fun convertAllDesc(source: List<GetActivitiesDescQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.activity) }
}

fun convertAllAsc(source: List<GetActivitiesAscQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.activity) }
}

fun convertByItemDesc(source: List<GetActivitiesByItemDescQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.activity) }
}

fun convertByItemAsc(source: List<GetActivitiesByItemAscQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.activity) }
}

fun convertByIds(source: List<GetActivitiesByIdsQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.activity) }
}

fun activityEvent(
    type: String, id: String, operationCounter: Int,
    date: OffsetDateTime,
    reverted: Boolean,
    hash: String,
    source: TezosPlatform,
    maker: String,
    taker: String?,
    make: Asset,
    take: Asset,
): DipDupActivity {
    return when (type) {
        "LIST" -> DipDupOrderListActivity(
            id = id,
            operationCounter = operationCounter,
            date = date,
            reverted = reverted,
            hash = hash,
            source = source,
            maker = maker,
            make = make,
            take = take
        )
        "SELL" -> DipDupOrderSellActivity(
            id = id,
            operationCounter = operationCounter,
            date = date,
            reverted = reverted,
            hash = hash,
            source = source,
            nft = make,
            payment = take,
            buyer = taker!!,
            seller = maker
        )
        "CANCEL_LIST" -> DipDupOrderCancelActivity(
            id = id,
            operationCounter = operationCounter,
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


