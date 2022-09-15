package com.rarible.dipdup.listener.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.rarible.dipdup.client.core.model.DipDupItem

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = DipDupItemEvent.TYPE)
@JsonSubTypes(
    JsonSubTypes.Type(value = DipDupUpdateItemEvent::class, name = DipDupItemEvent.UPDATE),
    JsonSubTypes.Type(value = DipDupDeleteItemEvent::class, name = DipDupItemEvent.DELETE)
)
sealed class DipDupItemEvent {

    abstract val eventId: String
    abstract val itemId: String

    companion object {
        const val TYPE = "type"
        const val UPDATE = "UPDATE"
        const val DELETE = "DELETE"
    }
}

data class DipDupUpdateItemEvent(
    override val eventId: String,
    override val itemId: String,
    val item: DipDupItem
) : DipDupItemEvent()

data class DipDupDeleteItemEvent(
    override val eventId: String,
    override val itemId: String
) : DipDupItemEvent()
