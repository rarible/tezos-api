package com.rarible.dipdup.listener.config

import com.rarible.core.kafka.RaribleKafkaConsumer
import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.core.model.DipDupOrder
import com.rarible.dipdup.listener.model.DipDupItemMetaEvent
import com.rarible.dipdup.listener.model.DipDupCollectionEvent
import com.rarible.dipdup.listener.model.DipDupItemEvent
import com.rarible.dipdup.listener.model.DipDupOwnershipEvent
import org.apache.kafka.clients.consumer.OffsetResetStrategy

import java.util.*

class DipDupEventsConsumerFactory(
    private val brokerReplicaSet: String,
    val host: String,
    val environment: String
) {

    private val clientIdPrefix = "$environment.$host.${UUID.randomUUID()}"

    private val properties = emptyMap<String, String>()

    fun createOrderConsumer(consumerGroup: String): RaribleKafkaConsumer<DipDupOrder> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos.consumer.order",
            valueDeserializerClass = DipDupDeserializer.OrderJsonSerializer::class.java,
            valueClass = DipDupOrder::class.java,
            consumerGroup = consumerGroup,
            offsetResetStrategy = OffsetResetStrategy.EARLIEST,
            defaultTopic = DipDupTopicProvider.getOrderTopic(environment),
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createActivityConsumer(consumerGroup: String): RaribleKafkaConsumer<DipDupActivity> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos.consumer.activity",
            valueDeserializerClass = DipDupDeserializer.ActivityJsonSerializer::class.java,
            valueClass = DipDupActivity::class.java,
            consumerGroup = consumerGroup,
            offsetResetStrategy = OffsetResetStrategy.EARLIEST,
            defaultTopic = DipDupTopicProvider.getActivityTopic(environment),
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createCollectionConsumer(consumerGroup: String): RaribleKafkaConsumer<DipDupCollectionEvent> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos.consumer.collection",
            valueDeserializerClass = DipDupDeserializer.CollectionJsonSerializer::class.java,
            valueClass = DipDupCollectionEvent::class.java,
            consumerGroup = consumerGroup,
            offsetResetStrategy = OffsetResetStrategy.EARLIEST,
            defaultTopic = DipDupTopicProvider.getCollectionTopic(environment),
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createItemConsumer(consumerGroup: String): RaribleKafkaConsumer<DipDupItemEvent> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos.consumer.item",
            valueDeserializerClass = DipDupDeserializer.ItemEventJsonSerializer::class.java,
            valueClass = DipDupItemEvent::class.java,
            consumerGroup = consumerGroup,
            offsetResetStrategy = OffsetResetStrategy.EARLIEST,
            defaultTopic = DipDupTopicProvider.getItemTopic(environment),
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createItemMetaConsumer(consumerGroup: String): RaribleKafkaConsumer<DipDupItemMetaEvent> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos.consumer.item.meta",
            valueDeserializerClass = DipDupDeserializer.ItemMetaEventJsonSerializer::class.java,
            valueClass = DipDupItemMetaEvent::class.java,
            consumerGroup = consumerGroup,
            offsetResetStrategy = OffsetResetStrategy.EARLIEST,
            defaultTopic = DipDupTopicProvider.getItemMetaTopic(environment),
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

    fun createOwnershipConsumer(consumerGroup: String): RaribleKafkaConsumer<DipDupOwnershipEvent> {
        return RaribleKafkaConsumer(
            clientId = "$clientIdPrefix.tezos.consumer.ownership",
            valueDeserializerClass = DipDupDeserializer.OwnershipEventJsonSerializer::class.java,
            valueClass = DipDupOwnershipEvent::class.java,
            consumerGroup = consumerGroup,
            offsetResetStrategy = OffsetResetStrategy.EARLIEST,
            defaultTopic = DipDupTopicProvider.getOwnershipTopic(environment),
            bootstrapServers = brokerReplicaSet,
            properties = properties
        )
    }

}
