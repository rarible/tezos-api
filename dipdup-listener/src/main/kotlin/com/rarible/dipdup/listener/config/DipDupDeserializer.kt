package com.rarible.dipdup.listener.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.rarible.core.kafka.json.JsonDeserializer
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.core.model.DipDupCollection
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.client.core.util.DateTimeDeserializer
import com.rarible.dipdup.listener.model.DipDupItemEvent
import com.rarible.dipdup.listener.model.DipDupItemMetaEvent
import com.rarible.dipdup.listener.model.DipDupOwnershipEvent
import org.apache.kafka.common.header.Headers
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

sealed class DipDupDeserializer : JsonDeserializer() {

    private val logger = LoggerFactory.getLogger(javaClass)

    abstract val classValue: KClass<*>

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
                "Unable to deserialize data into class {}:\n{}\ncause: {}",
                classValue.simpleName,
                data?.let { String(it) },
                e.message
            )
        }
    }

    class OrderJsonSerializer : DipDupDeserializer() {
        override val classValue = DipDupOrder::class
    }

    class ActivityJsonSerializer : DipDupDeserializer() {
        override val classValue = DipDupActivity::class
    }

    class CollectionJsonSerializer : DipDupDeserializer() {
        override val classValue = DipDupCollection::class
    }

    class ItemEventJsonSerializer : DipDupDeserializer() {
        override val classValue = DipDupItemEvent::class
    }

    class ItemMetaEventJsonSerializer : DipDupDeserializer() {
        override val classValue = DipDupItemMetaEvent::class
    }

    class OwnershipEventJsonSerializer : DipDupDeserializer() {
        override val classValue = DipDupOwnershipEvent::class
    }
}
