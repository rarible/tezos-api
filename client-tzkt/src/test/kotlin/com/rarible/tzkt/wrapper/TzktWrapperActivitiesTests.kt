package com.rarible.tzkt.wrapper

import com.rarible.tzkt.models.TokenTransfer
import org.junit.jupiter.api.Test

class TzktWrapperActivitiesTests {

    @Test
    fun contextLoads() {
    }

    /**
     * ACTIVITIES API TESTS
     */
    @Test
    fun activitiesGetAll() {
        val size = 10
        var continuation = 0L
        var tokens = getAllActivities(size, continuation);
        tokens.forEach { it::class.java == TokenTransfer::class.java }
        val lastId = tokens.last().id!!.toLong()
        continuation = lastId
        tokens = getAllActivities(size, continuation);
        tokens.forEach { it::class.java == TokenTransfer::class.java }
        assert(tokens.first().id!!.toLong() > lastId)
    }

}
