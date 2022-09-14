package com.rarible.dipdup.listener.model

import com.rarible.dipdup.client.core.model.DipDupOwnership

data class DipDupOwnershipEvent(
        val type: Type,
        val eventId: String?,
        val ownershipId: String?,
        val ownership: DipDupOwnership
) {

    enum class Type {
        UPDATE,
        DELETE
    }
}
