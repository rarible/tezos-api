package com.rarible.dipdup.listener.model

import com.rarible.dipdup.client.core.model.DipDupCollection
import java.util.UUID

data class DipDupCollectionEvent(
    val id: UUID,
    val eventId: String,
    val collection: DipDupCollection,
)
