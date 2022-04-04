package com.rarible.dipdup.listener.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

internal const val DIPDUP_LISTENER = "dipdup.listener"

@ConstructorBinding
@ConfigurationProperties(DIPDUP_LISTENER)
data class DipDupListenerProperties(
    val consumer: ConsumerProperties,
    val monitoringWorker: DaemonWorkerProperties = DaemonWorkerProperties()
)

data class ConsumerProperties(
    val brokerReplicaSet: String,
    val workers: Map<String, Int>,
    val orderTopic: String,
    val activityTopic: String
)

data class DaemonWorkerProperties(
    val pollingPeriod: Duration = Duration.ofSeconds(30),
    val errorDelay: Duration = Duration.ofSeconds(60),
    val consumerBatchSize: Int = 512
)
