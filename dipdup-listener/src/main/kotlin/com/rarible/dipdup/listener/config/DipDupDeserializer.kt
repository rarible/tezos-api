package com.rarible.dipdup.listener.config

import com.rarible.core.kafka.json.JsonDeserializer
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.core.model.DipDupOrder
import org.apache.kafka.common.header.Headers
import org.slf4j.LoggerFactory


sealed class DipDupDeserializer : JsonDeserializer() {

    private val logger = LoggerFactory.getLogger(javaClass)

    abstract val classValue: Any

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

}
