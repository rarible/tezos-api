package com.rarible.dipdup.listener.model

import java.util.*

data class DipDipItemMetaEvent(
    val id: UUID,
    val itemId: String,
    val type: String
)
