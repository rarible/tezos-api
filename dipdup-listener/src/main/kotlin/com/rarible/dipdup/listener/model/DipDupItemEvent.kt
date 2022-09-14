package com.rarible.dipdup.listener.model

import com.rarible.dipdup.client.core.model.DipDupItem

data class DipDupItemEvent(
        val type: Type,
        val eventId: String?,
        val itemId: String?,
        val item: DipDupItem?
) {

    enum class Type {
        UPDATE,
        DELETE
    }
}
