package com.rarible.dipdup.listener.config

import com.rarible.core.kafka.RaribleKafkaConsumer
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.core.model.DipDupCollection
import com.rarible.dipdup.client.core.model.DipDupOrder

import java.util.*

class DipDupEventsConsumerFactory(
    private val network: String,
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
            valueDeserializerClass = DipDupDeserializer.OrderJsonSerializer::class.java,
            valueClass = DipDupOrder::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = "${DipDupTopicProvider.ORDER}_$network",
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createActivityConsumer(consumerGroup: String): RaribleKafkaConsumer<DipDupActivity> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-activity-consumer",
            valueDeserializerClass = DipDupDeserializer.ActivityJsonSerializer::class.java,
            valueClass = DipDupActivity::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = "${DipDupTopicProvider.ACTIVITY}_$network",
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createCollectionConsumer(consumerGroup: String): RaribleKafkaConsumer<DipDupCollection> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos-collection-consumer",
            valueDeserializerClass = DipDupDeserializer.ActivityJsonSerializer::class.java,
            valueClass = DipDupCollection::class.java,
            consumerGroup = consumerGroup,
            defaultTopic = "${DipDupTopicProvider.COLLECTION}_$network",
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

}
