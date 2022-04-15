package com.rarible.dipdup.client.converter

import com.rarible.dipdup.client.GetOrderByIdQuery
import com.rarible.dipdup.client.GetOrdersByIdsQuery
import com.rarible.dipdup.client.GetOrdersByItemQuery
import com.rarible.dipdup.client.GetOrdersQuery
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.client.core.model.OrderStatus
import com.rarible.dipdup.client.core.model.TezosPlatform
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

fun convert(source: GetOrderByIdQuery.Marketplace_order_by_pk) = DipDupOrder(
    id = source.id.toString(),
    cancelled = source.cancelled,
    createdAt = OffsetDateTime.parse(source.created_at.toString()),
    endedAt = source.ended_at?.let { OffsetDateTime.parse(it.toString()) },
    startedAt = OffsetDateTime.parse(source.started_at.toString()),
    fill = BigDecimal(source.fill.toString()),
    lastUpdatedAt = OffsetDateTime.parse(source.last_updated_at.toString()),
    make = getAsset(source.make_asset_class, source.make_contract, source.make_token_id, source.make_value),
    maker = source.maker,
    take = getAsset(source.take_asset_class, source.take_contract, source.take_token_id, source.take_value),
    taker = source.taker,
    platform = TezosPlatform.get(source.platform),
    status = OrderStatus.get(source.status),
    salt = BigInteger(source.salt.toString()),
    makeStock = BigInteger(source.make_stock.toString()),
    makePrice = BigDecimal(source.make_price.toString())
)

fun convert(source: GetOrdersQuery.Marketplace_order) = DipDupOrder(
    id = source.id.toString(),
    cancelled = source.cancelled,
    createdAt = OffsetDateTime.parse(source.created_at.toString()),
    endedAt = source.ended_at?.let { OffsetDateTime.parse(it.toString()) },
    startedAt = OffsetDateTime.parse(source.started_at.toString()),
    fill = BigDecimal(source.fill.toString()),
    lastUpdatedAt = OffsetDateTime.parse(source.last_updated_at.toString()),
    make = getAsset(source.make_asset_class, source.make_contract, source.make_token_id, source.make_value),
    maker = source.maker,
    take = getAsset(source.take_asset_class, source.take_contract, source.take_token_id, source.take_value),
    taker = source.taker,
    platform = TezosPlatform.get(source.platform),
    status = OrderStatus.get(source.status),
    salt = BigInteger(source.salt.toString()),
    makeStock = BigInteger(source.make_stock.toString()),
    makePrice = BigDecimal(source.make_price.toString())
)

fun convert(source: GetOrdersByIdsQuery.Marketplace_order) = DipDupOrder(
    id = source.id.toString(),
    cancelled = source.cancelled,
    createdAt = OffsetDateTime.parse(source.created_at.toString()),
    endedAt = source.ended_at?.let { OffsetDateTime.parse(it.toString()) },
    startedAt = OffsetDateTime.parse(source.started_at.toString()),
    fill = BigDecimal(source.fill.toString()),
    lastUpdatedAt = OffsetDateTime.parse(source.last_updated_at.toString()),
    make = getAsset(source.make_asset_class, source.make_contract, source.make_token_id, source.make_value),
    maker = source.maker,
    take = getAsset(source.take_asset_class, source.take_contract, source.take_token_id, source.take_value),
    taker = source.taker,
    platform = TezosPlatform.get(source.platform),
    status = OrderStatus.get(source.status),
    salt = BigInteger(source.salt.toString()),
    makeStock = BigInteger(source.make_stock.toString()),
    makePrice = BigDecimal(source.make_price.toString())
)

fun convert(source: GetOrdersByItemQuery.Marketplace_order) = DipDupOrder(
    id = source.id.toString(),
    cancelled = source.cancelled,
    createdAt = OffsetDateTime.parse(source.created_at.toString()),
    endedAt = source.ended_at?.let { OffsetDateTime.parse(it.toString()) },
    startedAt = OffsetDateTime.parse(source.started_at.toString()),
    fill = BigDecimal(source.fill.toString()),
    lastUpdatedAt = OffsetDateTime.parse(source.last_updated_at.toString()),
    make = getAsset(source.make_asset_class, source.make_contract, source.make_token_id, source.make_value),
    maker = source.maker,
    take = getAsset(source.take_asset_class, source.take_contract, source.take_token_id, source.take_value),
    taker = source.taker,
    platform = TezosPlatform.get(source.platform),
    status = OrderStatus.get(source.status),
    salt = BigInteger(source.salt.toString()),
    makeStock = BigInteger(source.make_stock.toString()),
    makePrice = BigDecimal(source.make_price.toString())
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
