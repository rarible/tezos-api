package com.rarible.dipdup.client.starter

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

internal const val DIPDUP_CLIENT = "dipdup.client"

@ConstructorBinding
@ConfigurationProperties(DIPDUP_CLIENT)
data class DipDupClientProperties(
    val host: String
)
