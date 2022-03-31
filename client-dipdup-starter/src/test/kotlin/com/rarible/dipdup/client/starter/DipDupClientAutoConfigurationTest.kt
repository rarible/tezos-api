package com.rarible.dipdup.client.starter

import com.rarible.dipdup.client.ActivityClient
import com.rarible.dipdup.client.OrderClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@SpringBootConfiguration
@EnableAutoConfiguration
class DipDupClientAutoConfigurationTest {

    @Autowired
    private lateinit var orderClient: OrderClient

    @Autowired
    private lateinit var activityClient: ActivityClient

    @Test
    fun `all clients should be inizialised`() {
        assertThat(orderClient).isNotNull
        assertThat(activityClient).isNotNull
    }
}
