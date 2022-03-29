package com.rarible.dipdup.client.starter

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.ActivityClient
import com.rarible.dipdup.client.OrderClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

@EnableConfigurationProperties(DipDupClientProperties::class)
class DipDupClientAutoConfiguration(
    private val properties: DipDupClientProperties
) {

    @Bean
    @ConditionalOnMissingBean(ApolloClient::class)
    fun apolloClient(): ApolloClient {
        return ApolloClient.Builder().serverUrl(properties.host).build()
    }

    @Bean
    @ConditionalOnMissingBean(OrderClient::class)
    fun orderClient(client: ApolloClient): OrderClient {
        return OrderClient(client)
    }

    @Bean
    @ConditionalOnMissingBean(ActivityClient::class)
    fun activityClient(client: ApolloClient): ActivityClient {
        return ActivityClient(client)
    }

}
