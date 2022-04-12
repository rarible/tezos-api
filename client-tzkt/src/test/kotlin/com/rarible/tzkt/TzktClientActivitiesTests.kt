package com.rarible.tzkt

import com.rarible.tzkt.api.TokensApi
import com.rarible.tzkt.filters.OffsetFilterImpl
import com.rarible.tzkt.filters.SortFilterImpl
import com.rarible.tzkt.model.parameters.OffsetParameter
import com.rarible.tzkt.model.parameters.SortParameter
import com.rarible.tzkt.models.TokenTransfer
import org.junit.jupiter.api.Test

class TzktClientActivitiesTests {

    @Test
    fun contextLoads() {
    }

    /**
     * ACTIVITIES API TESTS
     */
    @Test
    fun activitiesGetAll() {
        val tokensAPI = TokensApi();

        val limit = 10

        val offsetParameter = OffsetParameter()
        val sortParameter = SortParameter()

        val offsetFilter = OffsetFilterImpl()
        val sortFilter = SortFilterImpl()

        sortFilter.asc = "id"
        offsetFilter.cr = 0

        offsetParameter.offsetFilterImpl = offsetFilter
        sortParameter.sortFilterImpl = sortFilter

        var tokens =
            tokensAPI.tokensGetTokenTransfers(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                sortParameter,
                offsetParameter,
                limit,
                null
            );

        tokens.forEach { it::class.java == TokenTransfer::class.java; println(it.id) }
        val lastId = tokens.last().id!!.toLong()
        offsetFilter.cr = lastId
        tokens =
            tokensAPI.tokensGetTokenTransfers(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                sortParameter,
                offsetParameter,
                limit,
                null
            );
        tokens.forEach { it::class.java == TokenTransfer::class.java; println(it.id) }
        assert(tokens.first().id!!.toLong() > lastId)
    }

}
