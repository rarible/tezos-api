package com.rarible.dipdup.listener.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.rarible.dipdup.client.core.model.DipDupOwnership

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = DipDupOwnershipEvent.TYPE)
@JsonSubTypes(
    JsonSubTypes.Type(value = DipDupUpdateOwnershipEvent::class, name = DipDupOwnershipEvent.UPDATE),
    JsonSubTypes.Type(value = DipDupDeleteOwnershipEvent::class, name = DipDupOwnershipEvent.DELETE)
)
sealed class DipDupOwnershipEvent {

    abstract val eventId: String
    abstract val ownershipId: String

    companion object {
        const val TYPE = "type"
        const val UPDATE = "UPDATE"
        const val DELETE = "DELETE"
    }
}

data class DipDupUpdateOwnershipEvent(
    override val eventId: String,
    override val ownershipId: String,
    val ownership: DipDupOwnership
) : DipDupOwnershipEvent()

data class DipDupDeleteOwnershipEvent(
    override val eventId: String,
    override val ownershipId: String
) : DipDupOwnershipEvent()
