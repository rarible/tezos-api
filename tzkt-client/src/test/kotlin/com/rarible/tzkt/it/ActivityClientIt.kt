package com.rarible.tzkt.it

import com.rarible.tzkt.client.TokenActivityClient
import com.rarible.tzkt.model.ActivityType
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import preparedClient

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
class ActivityClientIt {

    val client = preparedClient("https://api.tzkt.io")
    val activityClient = TokenActivityClient(client)

    @Test
    fun `should activities acs`() = runBlocking<Unit> {
        val activities = activityClient.getActivitiesAll(
            listOf(ActivityType.MINT, ActivityType.BURN, ActivityType.TRANSFER), 10, null, true
        )
        assertThat(activities.items).hasSize(10)
    }

    @Test
    fun `should return burn activities`() = runBlocking<Unit> {
        val activities = activityClient.getActivitiesAll(
            listOf(ActivityType.BURN), 10, null, true
        )
        assertThat(activities.items).hasSize(10)
        activities.items.forEach { assertThat(it.type).isEqualTo(ActivityType.BURN) }
    }

    @Test
    fun `should return transfer activities`() = runBlocking<Unit> {
        val activities = activityClient.getActivitiesAll(
            listOf(ActivityType.TRANSFER), 10, null, true
        )
        assertThat(activities.items).hasSize(10)
        activities.items.forEach { assertThat(it.type).isEqualTo(ActivityType.TRANSFER) }
    }

}
