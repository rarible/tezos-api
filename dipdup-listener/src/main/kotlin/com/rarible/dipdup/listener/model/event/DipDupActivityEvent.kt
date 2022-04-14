package com.rarible.dipdup.listener.model.event

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.TezosPlatform
import java.math.BigDecimal
import java.time.OffsetDateTime

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = DipDupActivityEvent.TYPE)
@JsonSubTypes(
    JsonSubTypes.Type(value = DipDupOrderListActivityEvent::class, name = DipDupActivityEvent.LIST),
    JsonSubTypes.Type(value = DipDupOrderCancelActivityEvent::class, name = DipDupActivityEvent.CANCEL),
    JsonSubTypes.Type(value = DipDupOrderSellActivityEvent::class, name = DipDupActivityEvent.SELL)
)
sealed class DipDupActivityEvent {
    abstract val id: String
    abstract val date: OffsetDateTime
    abstract val reverted: Boolean

    companion object {
        const val TYPE = "type"
        const val LIST = "LIST"
        const val CANCEL = "CANCEL_LIST"
        const val SELL = "SELL"
    }
}

data class DipDupOrderListActivityEvent(
    override val id: String,
    override val date: OffsetDateTime,
    override val reverted: Boolean,
    val hash: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset,
    val price: BigDecimal
) : DipDupActivityEvent()

data class DipDupOrderCancelActivityEvent(
    override val id: String,
    override val date: OffsetDateTime,
    override val reverted: Boolean,
    val hash: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset,
) : DipDupActivityEvent()

data class DipDupOrderSellActivityEvent(
    override val id: String,
    override val date: OffsetDateTime,
    override val reverted: Boolean,
    val hash: String,
    val source: TezosPlatform,
    val seller: String,
    val nft: Asset,
    val payment: Asset,
    val buyer: String,
    val price: BigDecimal
) : DipDupActivityEvent()
