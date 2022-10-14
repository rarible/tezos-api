package com.rarible.tzkt.config

data class TzktSettings(
    val useTokensBatch: Boolean = false,
    val useOwnershipsBatch: Boolean = false,
    val useCollectionBatch: Boolean = false
)
