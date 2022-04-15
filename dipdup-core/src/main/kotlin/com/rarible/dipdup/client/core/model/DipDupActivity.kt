package com.rarible.dipdup.client.core.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.math.BigDecimal
import java.time.OffsetDateTime

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = DipDupActivity.TYPE)
@JsonSubTypes(
    JsonSubTypes.Type(value = DipDupOrderListActivity::class, name = DipDupActivity.LIST),
    JsonSubTypes.Type(value = DipDupOrderCancelActivity::class, name = DipDupActivity.CANCEL),
    JsonSubTypes.Type(value = DipDupOrderSellActivity::class, name = DipDupActivity.SELL)
)
sealed class DipDupActivity {
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

data class DipDupOrderListActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val reverted: Boolean,
    val hash: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset,
    val price: BigDecimal
) : DipDupActivity()

data class DipDupOrderCancelActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val reverted: Boolean,
    val hash: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset,
) : DipDupActivity()

data class DipDupOrderSellActivity(
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
) : DipDupActivity()
