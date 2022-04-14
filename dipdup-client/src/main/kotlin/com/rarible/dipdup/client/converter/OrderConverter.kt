package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetOrderByIdQuery
import com.rarible.dipdup.client.GetOrdersByIdsQuery
import com.rarible.dipdup.client.GetOrdersByItemQuery
import com.rarible.dipdup.client.GetOrdersQuery
import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.Status
import com.rarible.dipdup.client.model.DipDupOrder
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

fun convert(source: GetOrderByIdQuery.Marketplace_order_by_pk) = DipDupOrder(
    id = BigInteger(source.id.toString()),
    cancelled = source.cancelled,
    createdAt = OffsetDateTime.parse(source.created_at.toString()),
    endedAt = source.ended_at?.let { OffsetDateTime.parse(it.toString()) },
    startedAt = OffsetDateTime.parse(source.started_at.toString()),
    fill = BigDecimal(source.fill.toString()),
    internalOrderId = source.internal_order_id,
    lastUpdatedAt = OffsetDateTime.parse(source.last_updated_at.toString()),
    make = makeFTAsset(source.make_contract, source.make_token_id, source.make_value),
    maker = source.maker,
    take = takeXTZAsset(source.make_price),
    taker = null,
    status = status(source.status)
)

fun convert(source: GetOrdersQuery.Marketplace_order) = DipDupOrder(
    id = BigInteger(source.id.toString()),
    cancelled = source.cancelled,
    createdAt = OffsetDateTime.parse(source.created_at.toString()),
    endedAt = source.ended_at?.let { OffsetDateTime.parse(it.toString()) },
    startedAt = OffsetDateTime.parse(source.started_at.toString()),
    fill = BigDecimal(source.fill.toString()),
    internalOrderId = source.internal_order_id,
    lastUpdatedAt = OffsetDateTime.parse(source.last_updated_at.toString()),
    make = makeFTAsset(source.make_contract, source.make_token_id, source.make_value),
    maker = source.maker,
    take = takeXTZAsset(source.make_price),
    taker = null,
    status = status(source.status)
)

fun convert(source: GetOrdersByIdsQuery.Marketplace_order) = DipDupOrder(
    id = BigInteger(source.id.toString()),
    cancelled = source.cancelled,
    createdAt = OffsetDateTime.parse(source.created_at.toString()),
    endedAt = source.ended_at?.let { OffsetDateTime.parse(it.toString()) },
    startedAt = OffsetDateTime.parse(source.started_at.toString()),
    fill = BigDecimal(source.fill.toString()),
    internalOrderId = source.internal_order_id,
    lastUpdatedAt = OffsetDateTime.parse(source.last_updated_at.toString()),
    make = makeFTAsset(source.make_contract, source.make_token_id, source.make_value),
    maker = source.maker,
    take = takeXTZAsset(source.make_price),
    taker = null,
    status = status(source.status)
)

fun convert(source: GetOrdersByItemQuery.Marketplace_order) = DipDupOrder(
    id = BigInteger(source.id.toString()),
    cancelled = source.cancelled,
    createdAt = OffsetDateTime.parse(source.created_at.toString()),
    endedAt = source.ended_at?.let { OffsetDateTime.parse(it.toString()) },
    startedAt = OffsetDateTime.parse(source.started_at.toString()),
    fill = BigDecimal(source.fill.toString()),
    internalOrderId = source.internal_order_id,
    lastUpdatedAt = OffsetDateTime.parse(source.last_updated_at.toString()),
    make = makeFTAsset(source.make_contract, source.make_token_id, source.make_value),
    maker = source.maker,
    take = takeXTZAsset(source.make_price),
    taker = null,
    status = status(source.status)
)

fun convertAll(source: List<GetOrdersQuery.Marketplace_order>): List<DipDupOrder> {
    return source.map { convert(it) }
}

fun convertByIds(source: List<GetOrdersByIdsQuery.Marketplace_order>): List<DipDupOrder> {
    return source.map { convert(it) }
}

fun convertByItem(source: List<GetOrdersByItemQuery.Marketplace_order>): List<DipDupOrder> {
    return source.map { convert(it) }
}

fun makeFTAsset(contract: Any, tokenId: Any, value: Any): Asset = Asset(
    type = Asset.FT(contract = contract.toString(), tokenId = BigInteger(tokenId.toString())),
    value = BigDecimal(value.toString())
)

fun takeXTZAsset(price: Any): Asset = Asset(
    type = Asset.XTZ(),
    value = BigDecimal(price.toString())
)

fun status(source: String): Status = when (source) {
    "ACTIVE" -> Status.ACTIVE
    "FILLED" -> Status.FILLED
    else -> Status.CANCELLED
}
