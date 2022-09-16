package com.rarible.dipdup.client.core.model

import java.util.UUID

data class DipDupCollection(
    val id: UUID,
    val eventId: String,
    val collection: Collection,
    val type: EventType
) {

    data class Collection(
        val id: String,
        val owner: String,
        val name: String,
        val minters: List<String>,
        val standard: String,
        val symbol: String?
    )
}
