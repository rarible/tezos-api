package com.rarible.tzkt.wrapper

import org.junit.jupiter.api.Test

class TzktWrapperOwnershipsTests {

    @Test
    fun contextLoads() {
    }

    /**
     * OWNERSHIPS API TESTS
     */
    @Test
    fun getOwnershipByIdTest() {
        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "631268"
        var owner = "tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"
        var ownerships = getOwnershipById(contract, tokenId, owner)
        assert(ownerships.size == 1)
        var balance = ownerships.first()
        assert(balance.account?.address == owner)
        assert(balance.token?.tokenId == tokenId)
        assert(balance.token?.contract?.address == contract)
    }
}
