package com.rarible.dipdup.listener.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.rarible.core.kafka.json.JsonDeserializer
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.core.model.DipDupCollection
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.client.core.util.DateTimeDeserializer
import com.rarible.dipdup.listener.model.DipDupItemEvent
import com.rarible.dipdup.listener.model.DipDupOwnershipEvent
import org.apache.kafka.common.header.Headers
import org.slf4j.LoggerFactory

sealed class DipDupDeserializer : JsonDeserializer() {

    private val logger = LoggerFactory.getLogger(javaClass)

    abstract val classValue: Any

    override fun createMapper(): ObjectMapper {
        val mapper = super.createMapper()
        mapper.registerModule(DateTimeDeserializer.getModule())
        return mapper
    }

    override fun deserialize(topic: String?, headers: Headers?, data: ByteArray?): Any {
        return try {
            super.deserialize(topic, headers, data)
        } catch (e: Exception) {
            logger.error(
                "Unable to deserialize data into class {}:\n{}",
                classValue.javaClass.simpleName,
                data?.let { String(it) })
        }
    }

    class OrderJsonSerializer : DipDupDeserializer() {
        override val classValue = DipDupOrder::class.java
    }

    class ActivityJsonSerializer : DipDupDeserializer() {
        override val classValue = DipDupActivity::class.java
    }

    class CollectionJsonSerializer : DipDupDeserializer() {
        override val classValue = DipDupCollection::class.java
    }

    class ItemEventJsonSerializer : DipDupDeserializer() {
        override val classValue = DipDupItemEvent::class.java
    }

    class OwnershipEventJsonSerializer : DipDupDeserializer() {
        override val classValue = DipDupOwnershipEvent::class.java
    }
}
