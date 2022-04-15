package com.rarible.dipdup.listener.config

import com.rarible.core.kafka.RaribleKafkaConsumer
import com.rarible.core.kafka.json.JsonDeserializer
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.core.model.DipDupOrder

import java.util.*

class DipDupEventsConsumerFactory(
    private val brokerReplicaSet: String,
    host: String,
    environment: String,
    username: String?,
    password: String?
) {

    private val clientIdPrefix = "$environment.$host.${UUID.randomUUID()}"

    private val properties = if (username != null && password != null) mapOf(
        "security.protocol" to "SASL_PLAINTEXT",
        "sasl.mechanism" to "PLAIN",
        "sasl.jaas.config" to "org.apache.kafka.common.security.plain.PlainLoginModule required " +
                "username=\"$username\" password=\"$password\";"
    ) else emptyMap()

    fun createOrderConsumer(consumerGroup: String): RaribleKafkaConsumer<DipDupOrder> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-order-consumer",
            valueDeserializerClass = JsonDeserializer::class.java,
            valueClass = DipDupOrder::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = DipDupTopicProvider.ORDER,
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createActivityConsumer(consumerGroup: String): RaribleKafkaConsumer<DipDupActivity> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-activity-consumer",
            valueDeserializerClass = JsonDeserializer::class.java,
            valueClass = DipDupActivity::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = DipDupTopicProvider.ACTIVITY,
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

}
