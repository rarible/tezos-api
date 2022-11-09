package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetOrderActivitiesAscQuery
import com.rarible.dipdup.client.GetOrderActivitiesByIdsQuery
import com.rarible.dipdup.client.GetOrderActivitiesByItemAscQuery
import com.rarible.dipdup.client.GetOrderActivitiesByItemDescQuery
import com.rarible.dipdup.client.GetOrderActivitiesDescQuery
import com.rarible.dipdup.client.GetOrderActivitiesSyncAscQuery
import com.rarible.dipdup.client.GetOrderActivitiesSyncDescQuery
import com.rarible.dipdup.client.GetTokenActivitiesAscQuery
import com.rarible.dipdup.client.GetTokenActivitiesByIdsQuery
import com.rarible.dipdup.client.GetTokenActivitiesByItemAscQuery
import com.rarible.dipdup.client.GetTokenActivitiesByItemDescQuery
import com.rarible.dipdup.client.GetTokenActivitiesDescQuery
import com.rarible.dipdup.client.GetTokenActivitiesSyncAscQuery
import com.rarible.dipdup.client.GetTokenActivitiesSyncDescQuery
import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.core.model.DipDupBurnActivity
import com.rarible.dipdup.client.core.model.DipDupCancelBidActivity
import com.rarible.dipdup.client.core.model.DipDupGetBidActivity
import com.rarible.dipdup.client.core.model.DipDupMakeBidActivity
import com.rarible.dipdup.client.core.model.DipDupMintActivity
import com.rarible.dipdup.client.core.model.DipDupOrderCancelActivity
import com.rarible.dipdup.client.core.model.DipDupOrderListActivity
import com.rarible.dipdup.client.core.model.DipDupOrderSellActivity
import com.rarible.dipdup.client.core.model.DipDupTransferActivity
import com.rarible.dipdup.client.core.model.TezosPlatform
import com.rarible.dipdup.client.fragment.Order_activity
import com.rarible.dipdup.client.fragment.Token_activity
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.time.OffsetDateTime

fun convert(source: Order_activity) = orderActivity(
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

fun convert(source: Token_activity) = tokenActivity(
    type = source.type,
    id = source.id.toString(),
    date = OffsetDateTime.parse(source.date.toString()),
    transactionId = getTransactionId(source.tzkt_transaction_id, source.tzkt_origination_id),
    transferId = source.id.toString(),
    hash = source.hash,
    contract = source.contract,
    tokenId = BigInteger(source.token_id),
    value = BigDecimal(source.amount.toString()),
    from = source.from_address,
    owner = source.to_address
)

fun getTransactionId(tz: Any?, originationId: Any?): String {
    return when {
        tz != null -> tz
        originationId != null -> originationId
        else -> ""
    }.toString()
}

fun convertAllDesc(source: List<GetOrderActivitiesDescQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.order_activity) }
}

fun convertTokenActivitiesAllDesc(source: List<GetTokenActivitiesDescQuery.Token_transfer>): List<DipDupActivity> {
    return source.map { convert(it.token_activity) }
}

fun convertAllAsc(source: List<GetOrderActivitiesAscQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.order_activity) }
}

fun convertTokenActivitiesAllAsc(source: List<GetTokenActivitiesAscQuery.Token_transfer>): List<DipDupActivity> {
    return source.map { convert(it.token_activity) }
}

fun convertTokenActivitiesSyncDesc(source: List<GetTokenActivitiesSyncDescQuery.Token_transfer>): List<DipDupActivity> {
    return source.map { convert(it.token_activity) }
}

fun convertTokenActivitiesSyncAsc(source: List<GetTokenActivitiesSyncAscQuery.Token_transfer>): List<DipDupActivity> {
    return source.map { convert(it.token_activity) }
}

fun convertOrderActivitySyncDesc(source: List<GetOrderActivitiesSyncDescQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.order_activity) }
}

fun convertOrderActivitySyncAsc(source: List<GetOrderActivitiesSyncAscQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.order_activity) }
}

fun convertByItemDesc(source: List<GetOrderActivitiesByItemDescQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.order_activity) }
}

fun convertTokenActivitiesByItemDesc(source: List<GetTokenActivitiesByItemDescQuery.Token_transfer>): List<DipDupActivity> {
    return source.map { convert(it.token_activity) }
}

fun convertByItemAsc(source: List<GetOrderActivitiesByItemAscQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.order_activity) }
}

fun convertTokenActivitiesByItemAsc(source: List<GetTokenActivitiesByItemAscQuery.Token_transfer>): List<DipDupActivity> {
    return source.map { convert(it.token_activity) }
}

fun convertByIds(source: List<GetOrderActivitiesByIdsQuery.Marketplace_activity>): List<DipDupActivity> {
    return source.map { convert(it.order_activity) }
}

fun convertTokensActivitiesByIds(source: List<GetTokenActivitiesByIdsQuery.Token_transfer>): List<DipDupActivity> {
    return source.map { convert(it.token_activity) }
}

fun orderActivity(
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
        DipDupActivity.LIST -> DipDupOrderListActivity(
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
        DipDupActivity.SELL -> DipDupOrderSellActivity(
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
        DipDupActivity.CANCEL_LIST -> DipDupOrderCancelActivity(
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
        DipDupActivity.MAKE_BID -> DipDupMakeBidActivity(
            id = id,
            date = date,
            reverted = reverted,
            hash = hash,
            source = source,
            maker = maker,
            make = make,
            take = take,
            price = price(make.assetValue, take.assetValue)
        )
        DipDupActivity.GET_BID -> DipDupGetBidActivity(
            id = id,
            date = date,
            reverted = reverted,
            hash = hash,
            source = source,
            nft = take,
            payment = make,
            seller = taker!!,
            buyer = maker,
            price = price(make.assetValue, take.assetValue)
        )
        DipDupActivity.CANCEL_BID -> DipDupCancelBidActivity(
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

fun tokenActivity(
    type: String, id: String, date: OffsetDateTime, transferId: String, hash: String?, contract: String, tokenId: BigInteger,
    value: BigDecimal, transactionId: String, from: String?, owner: String?
): DipDupActivity {
    return when (type) {
        DipDupActivity.MINT -> DipDupMintActivity(
            id = id,
            date = date,
            reverted = false,
            transferId = transferId,
            contract = contract,
            tokenId = tokenId,
            value = value,
            transactionId = hash ?: transactionId,
            owner = owner!!
        )
        DipDupActivity.TRANSFER -> DipDupTransferActivity(
            id = id,
            date = date,
            reverted = false,
            transferId = transferId,
            contract = contract,
            tokenId = tokenId,
            value = value,
            transactionId = hash ?: transactionId,
            from = from!!,
            owner = owner!!
        )
        DipDupActivity.BURN -> DipDupBurnActivity(
            id = id,
            date = date,
            reverted = false,
            transferId = transferId,
            contract = contract,
            tokenId = tokenId,
            value = value,
            transactionId = hash ?: transactionId,
            owner = from!!
        )
        else -> throw RuntimeException("Unknown activity type: $type")
    }
}

fun price(value: BigDecimal, makeValue: BigDecimal) =
    try {
        value.divide(makeValue, MathContext.DECIMAL128)
    } catch (e: Exception) {
        value
    }


