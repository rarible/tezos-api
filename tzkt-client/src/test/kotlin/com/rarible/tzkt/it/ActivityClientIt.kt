package com.rarible.tzkt.it

import com.rarible.tzkt.client.TokenActivityClient
import com.rarible.tzkt.model.ActivityType
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import org.springframework.web.reactive.function.client.WebClient

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
class ActivityClientIt {

    val client = WebClient.create("https://api.ithacanet.tzkt.io")
    val activityClient = TokenActivityClient(client)

    @Test
    fun `should activities acs`() = runBlocking<Unit> {
        val activities = activityClient.getActivitiesAll(
            listOf(ActivityType.MINT, ActivityType.BURN, ActivityType.TRANSFER), 10, null, true
        )
        assertThat(activities.items).hasSize(10)
    }

}
