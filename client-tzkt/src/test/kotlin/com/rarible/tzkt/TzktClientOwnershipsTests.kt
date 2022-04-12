package com.rarible.tzkt

import com.rarible.tzkt.api.TokensApi
import com.rarible.tzkt.filters.EqualityFilterImpl
import com.rarible.tzkt.model.parameters.AccountParameter
import com.rarible.tzkt.model.parameters.NatParameter
import org.junit.jupiter.api.Test

class TzktClientOwnershipsTests {

    @Test
    fun contextLoads() {
    }

    /**
     * OWNERSHIPS API TESTS
     */
    @Test
    fun ownershipGetById() {
        var tokensAPI = TokensApi();
        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "631268"
        var user = "tz2L6ikhCEHz9rZnZWobd7jFSJ6KfkESSP88"

        var accountParameter = AccountParameter()
        var contractParameter = AccountParameter()
        var tokenIdParameter = NatParameter()

        var accountEqualityFilter = EqualityFilterImpl()
        var contractEqualityFilter = EqualityFilterImpl()
        var tokenIdEqualityFilter = EqualityFilterImpl()

        accountEqualityFilter.eq = user
        contractEqualityFilter.eq = contract
        tokenIdEqualityFilter.eq = tokenId

        accountParameter.equalityFilterImpl = accountEqualityFilter
        contractParameter.equalityFilterImpl = contractEqualityFilter
        tokenIdParameter.equalityFilterImpl = tokenIdEqualityFilter

        var tokens =
            tokensAPI.tokensGetTokenBalances(
                null,
                accountParameter,
                null,
                contractParameter,
                tokenIdParameter,
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
                null
            );
        println(tokens)
        assert(tokens.size == 1)
        var balance = tokens.first()
        assert(balance.account?.address == user)
        assert(balance.token?.tokenId == tokenId)
        assert(balance.token?.contract?.address == contract)
    }

}
