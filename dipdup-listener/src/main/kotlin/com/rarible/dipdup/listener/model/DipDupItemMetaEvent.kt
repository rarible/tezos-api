package com.rarible.dipdup.listener.model

import java.util.*

data class DipDupItemMetaEvent(
    val id: UUID,
    val itemId: String,
    val type: String
)
