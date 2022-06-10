package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetOrderByIdQuery
import com.rarible.dipdup.client.GetOrdersByIdsQuery
import com.rarible.dipdup.client.GetOrdersByItemQuery
import com.rarible.dipdup.client.GetOrdersQuery
import com.rarible.dipdup.client.GetOrdersTakeContractsByMakeCollectionQuery
import com.rarible.dipdup.client.GetOrdersTakeContractsByMakeItemQuery
import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.client.core.model.OrderStatus
import com.rarible.dipdup.client.core.model.TezosPlatform
import com.rarible.dipdup.client.fragment.Order
import com.rarible.dipdup.client.model.DipDupContinuation
import com.rarible.dipdup.client.model.DipDupOrdersPage
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*
import kotlin.math.min

fun convert(source: GetOrderByIdQuery.Marketplace_order_by_pk) = DipDupOrder(
    id = source.id.toString(),
    cancelled = source.cancelled,
    createdAt = OffsetDateTime.parse(source.created_at.toString()),
    endedAt = source.ended_at?.let { OffsetDateTime.parse(it.toString()) },
    endAt = source.end_at?.let { OffsetDateTime.parse(it.toString()) },
    startAt = OffsetDateTime.parse(source.start_at.toString()),
    fill = BigDecimal(source.fill.toString()),
    lastUpdatedAt = OffsetDateTime.parse(source.last_updated_at.toString()),
    make = getAsset(source.make_asset_class, source.make_contract, source.make_token_id, source.make_value),
    maker = source.maker,
    take = getAsset(source.take_asset_class, source.take_contract, source.take_token_id, source.take_value),
    taker = source.taker,
    platform = TezosPlatform.valueOf(source.platform),
    status = OrderStatus.valueOf(source.status),
    salt = BigInteger(source.salt.toString()),
    makeStock = BigDecimal(source.make_stock.toString()),
    makePrice = BigDecimal(source.make_price.toString())
)

fun convertAll(source: List<GetOrdersQuery.Marketplace_order>, limit: Int): DipDupOrdersPage {
    val continuation = when {
        source.size == limit -> convert(source[limit - 1].order)
            .let { DipDupContinuation(it.lastUpdatedAt, UUID.fromString(it.id)) }.toString()
        else -> null
    }
    return DipDupOrdersPage(
        source.subList(0, min(source.size, limit)).map { convert(it.order) },
        continuation = continuation
    )
}

fun convertByIds(source: List<GetOrdersByIdsQuery.Marketplace_order>): List<DipDupOrder> {
    return source.map { convert(it.order) }
}

fun convertByItem(source: List<GetOrdersByItemQuery.Marketplace_order>, limit: Int): DipDupOrdersPage {
    val continuation = when {
        source.size == limit -> convert(source[limit - 1].order)
            .let { DipDupContinuation(it.lastUpdatedAt, UUID.fromString(it.id)) }.toString()
        else -> null
    }
    return DipDupOrdersPage(
        source.subList(0, min(source.size, limit)).map { convert(it.order) },
        continuation = continuation
    )
}

fun convert(source: Order) = DipDupOrder(
    id = source.id.toString(),
    cancelled = source.cancelled,
    createdAt = OffsetDateTime.parse(source.created_at.toString()),
    endedAt = source.ended_at?.let { OffsetDateTime.parse(it.toString()) },
    endAt = source.end_at?.let { OffsetDateTime.parse(it.toString()) },
    startAt = OffsetDateTime.parse(source.start_at.toString()),
    fill = BigDecimal(source.fill.toString()),
    lastUpdatedAt = OffsetDateTime.parse(source.last_updated_at.toString()),
    make = getAsset(source.make_asset_class, source.make_contract, source.make_token_id, source.make_value),
    maker = source.maker,
    take = getAsset(source.take_asset_class, source.take_contract, source.take_token_id, source.take_value),
    taker = source.taker,
    platform = TezosPlatform.valueOf(source.platform),
    status = OrderStatus.valueOf(source.status),
    salt = BigInteger(source.salt.toString()),
    makeStock = BigDecimal(source.make_stock.toString()),
    makePrice = BigDecimal(source.make_price.toString())
)

fun convert(source: com.rarible.dipdup.client.fragment.AssetType) =
    when (source.take_contract) {
        null -> Asset.XTZ()
        else -> Asset.FT(contract = source.take_contract, tokenId = BigInteger(source.take_token_id))
    }

fun convert(source: GetOrdersTakeContractsByMakeItemQuery.Data): List<Asset.AssetType> = source.marketplace_order.map {
    convert(it.assetType)
}

fun convert(source: GetOrdersTakeContractsByMakeCollectionQuery.Data): List<Asset.AssetType> = source.marketplace_order.map {
    convert(it.assetType)
}
