package com.rarible.dipdup.client.core.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = DipDupActivity.TYPE)
@JsonSubTypes(
    JsonSubTypes.Type(value = DipDupOrderListActivity::class, name = DipDupActivity.LIST),
    JsonSubTypes.Type(value = DipDupOrderCancelActivity::class, name = DipDupActivity.CANCEL),
    JsonSubTypes.Type(value = DipDupOrderSellActivity::class, name = DipDupActivity.SELL),
    JsonSubTypes.Type(value = DipDupMintActivity::class, name = DipDupActivity.MINT),
    JsonSubTypes.Type(value = DipDupTransferActivity::class, name = DipDupActivity.TRANSFER),
    JsonSubTypes.Type(value = DipDupBurnActivity::class, name = DipDupActivity.BURN)
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
        const val MINT = "MINT"
        const val TRANSFER = "TRANSFER"
        const val BURN = "BURN"
    }
}

interface WithOperationCounter {
    val operationCounter: Int
}

data class DipDupOrderListActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val reverted: Boolean,
    override val operationCounter: Int,
    val hash: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset
) : WithOperationCounter, DipDupActivity()

data class DipDupOrderCancelActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val reverted: Boolean,
    override val operationCounter: Int,
    val hash: String,
    val source: TezosPlatform,
    val maker: String,
    val make: Asset,
    val take: Asset,
) : WithOperationCounter, DipDupActivity()

data class DipDupOrderSellActivity(
    override val id: String,
    override val date: OffsetDateTime,
    override val reverted: Boolean,
    override val operationCounter: Int,
    val hash: String,
    val source: TezosPlatform,
    val seller: String,
    val nft: Asset,
    val payment: Asset,
    val buyer: String,
) : WithOperationCounter, DipDupActivity()

data class DipDupTransferActivity(
    override val id: String,
    override val date: OffsetDateTime,
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
    override val reverted: Boolean,
    val transferId: String,
    val contract: String,
    val tokenId: BigInteger,
    val value: BigDecimal,
    val transactionId: String,
    val owner: String
) : DipDupActivity()
