package com.rarible.dipdup.client.core.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = DipDupActivity.TYPE)
@JsonSubTypes(
    JsonSubTypes.Type(value = DipDupOrderListActivity::class, name = DipDupActivity.LIST),
    JsonSubTypes.Type(value = DipDupOrderCancelActivity::class, name = DipDupActivity.CANCEL_LIST),
    JsonSubTypes.Type(value = DipDupOrderSellActivity::class, name = DipDupActivity.SELL),

    JsonSubTypes.Type(value = DipDupMintActivity::class, name = DipDupActivity.MINT),
    JsonSubTypes.Type(value = DipDupTransferActivity::class, name = DipDupActivity.TRANSFER),
    JsonSubTypes.Type(value = DipDupBurnActivity::class, name = DipDupActivity.BURN),

    JsonSubTypes.Type(value = DipDupMakeBidActivity::class, name = DipDupActivity.MAKE_BID),
    JsonSubTypes.Type(value = DipDupGetBidActivity::class, name = DipDupActivity.GET_BID),
    JsonSubTypes.Type(value = DipDupCancelBidActivity::class, name = DipDupActivity.CANCEL_BID),

    JsonSubTypes.Type(value = DipDupMakeFloorBidActivity::class, name = DipDupActivity.MAKE_FLOOR_BID),
    JsonSubTypes.Type(value = DipDupGetFloorBidActivity::class, name = DipDupActivity.GET_FLOOR_BID),
    JsonSubTypes.Type(value = DipDupCancelFloorBidActivity::class, name = DipDupActivity.CANCEL_FLOOR_BID),
)
sealed class DipDupActivity {
    abstract val id: String
    abstract val date: OffsetDateTime
    abstract val dbUpdatedAt: OffsetDateTime?
    abstract val reverted: Boolean

    companion object {
        const val TYPE = "type"
        const val LIST = "LIST"
        const val CANCEL_LIST = "CANCEL_LIST"
        const val SELL = "SELL"
        const val MINT = "MINT"
        const val TRANSFER = "TRANSFER"
        const val BURN = "BURN"

        const val MAKE_BID = "MAKE_BID"
        const val GET_BID = "GET_BID"
        const val CANCEL_BID = "CANCEL_BID"

        const val MAKE_FLOOR_BID = "MAKE_FLOOR_BID"
        const val GET_FLOOR_BID = "GET_FLOOR_BID"
        const val CANCEL_FLOOR_BID = "CANCEL_FLOOR_BID"
    }

    fun isNftActivity() = when (this) {
        is DipDupMintActivity, is DipDupTransferActivity, is DipDupBurnActivity -> true
        else -> false
    }
}

interface WithOperationCounter {
    val operationCounter: Int
}

data class DipDupOrderListActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime,
    override val reverted: Boolean,
    override val operationCounter: Int,
    val hash: String,
    val orderId: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset
) : WithOperationCounter, DipDupActivity()

data class DipDupOrderCancelActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime?,
    override val reverted: Boolean,
    override val operationCounter: Int,
    val hash: String,
    val orderId: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset
) : WithOperationCounter, DipDupActivity()

data class DipDupOrderSellActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime?,
    override val reverted: Boolean,
    override val operationCounter: Int,
    val hash: String,
    val orderId: String,
    val source: TezosPlatform,
    val seller: String,
    val nft: Asset,
    val payment: Asset,
    val buyer: String,
) : WithOperationCounter, DipDupActivity()

data class DipDupTransferActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime?,
    override val reverted: Boolean,
    val transferId: String,
    val contract: String,
    val tokenId: BigInteger,
    val value: BigDecimal,
    val transactionId: String,
    val from: String,
    val owner: String
) : DipDupActivity()

data class DipDupMintActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime?,
    override val reverted: Boolean,
    val transferId: String,
    val contract: String,
    val tokenId: BigInteger,
    val value: BigDecimal,
    val transactionId: String,
    val owner: String
) : DipDupActivity()

data class DipDupBurnActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime?,
    override val reverted: Boolean,
    val transferId: String,
    val contract: String,
    val tokenId: BigInteger,
    val value: BigDecimal,
    val transactionId: String,
    val owner: String
) : DipDupActivity()

data class DipDupMakeBidActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime?,
    override val reverted: Boolean,
    val orderId: String,
    val hash: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset,
    val price: BigDecimal
) : DipDupActivity()

data class DipDupGetBidActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime?,
    override val reverted: Boolean,
    val orderId: String,
    val hash: String,
    val source: TezosPlatform,
    val seller: String,
    val nft: Asset,
    val payment: Asset,
    val buyer: String,
    val price: BigDecimal
) : DipDupActivity()

data class DipDupCancelBidActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime?,
    override val reverted: Boolean,
    val orderId: String,
    val hash: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset
) : DipDupActivity()

data class DipDupMakeFloorBidActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime?,
    override val reverted: Boolean,
    val hash: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset,
    val price: BigDecimal
) : DipDupActivity()

data class DipDupGetFloorBidActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime?,
    override val reverted: Boolean,
    val hash: String,
    val source: TezosPlatform,
    val seller: String,
    val nft: Asset,
    val payment: Asset,
    val buyer: String,
    val price: BigDecimal
) : DipDupActivity()

data class DipDupCancelFloorBidActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val dbUpdatedAt: OffsetDateTime?,
    override val reverted: Boolean,
    val hash: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset
) : DipDupActivity()
