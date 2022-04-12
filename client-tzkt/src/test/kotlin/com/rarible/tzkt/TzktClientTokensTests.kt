package com.rarible.tzkt

import com.rarible.tzkt.api.TokensApi
import com.rarible.tzkt.filters.EqualityFilterImpl
import com.rarible.tzkt.filters.OffsetFilterImpl
import com.rarible.tzkt.model.parameters.AccountParameter
import com.rarible.tzkt.model.parameters.NatParameter
import com.rarible.tzkt.model.parameters.OffsetParameter
import com.rarible.tzkt.models.Token
import org.junit.jupiter.api.Test

class TzktClientTokensTests {

    @Test
    fun contextLoads() {
    }

    /**
     * TOKEN API TESTS
     */
    @Test
    fun tokensGetAll() {
        var tokensAPI = TokensApi();
        var tokens =
            tokensAPI.tokensGetTokens(null, null, null, null, null, null, null, null, null, null, null, null, null);
        println(tokens)
        tokens.forEach { it::class.java == Token::class.java }
    }

    @Test
    fun tokensGetAllLimitAndOffset() {
        var tokensAPI = TokensApi();
        var limit = 10
        var offsetParameter = OffsetParameter()
        var offsetFilter = OffsetFilterImpl()
        offsetFilter.pg = 0
        offsetParameter.offsetFilterImpl = offsetFilter
        var tokens1 = tokensAPI.tokensGetTokens(
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
            offsetParameter,
            limit,
            null
        );
        tokens1.forEach { it::class.java == Token::class.java }
        assert(tokens1.first().id == 1)
        offsetFilter.pg = 1
        var tokens2 = tokensAPI.tokensGetTokens(
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
            offsetParameter,
            limit,
            null
        );
        tokens2.forEach { it::class.java == Token::class.java }
        assert(tokens2.first().id == 11)
    }

    @Test
    fun tokensGetById() {
        var tokensAPI = TokensApi();
        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "631268"

        var accountParameter = AccountParameter()
        var tokenIdParameter = NatParameter()

        var accountEqualityFilter = EqualityFilterImpl()
        var tokenIdEqualityFilter = EqualityFilterImpl()

        accountEqualityFilter.eq = contract
        tokenIdEqualityFilter.eq = tokenId

        accountParameter.equalityFilterImpl = accountEqualityFilter
        tokenIdParameter.equalityFilterImpl = tokenIdEqualityFilter

        var tokens = tokensAPI.tokensGetTokens(
            null,
            accountParameter,
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
            null
        );
        assert(tokens.size == 1)
        val token = tokens.first()
        assert(token.tokenId == tokenId)
        assert(token.contract?.address == contract)
        assert(token.standard == "fa2")
        assert(token.firstLevel == 2027158)
    }

}
