package com.rarible.tzkt

import com.rarible.tzkt.api.TokensApi
import com.rarible.tzkt.filters.OffsetFilterImpl
import com.rarible.tzkt.model.parameters.OffsetParameter
import com.rarible.tzkt.models.Token
import org.junit.jupiter.api.Test

class TezosTzktClientTokensTests {

	@Test
	fun contextLoads() {
	}

	/**
	 * TOKEN API TESTS
	 */
	@Test
	fun tokensGetNoFilter() {
		var tokensAPI = TokensApi();
		var tokens =	tokensAPI.tokensGetTokens(null,null,null,null,null,null,null,null,null,null, null, null,null);
		println(tokens)
		tokens.forEach { it::class.java == Token::class.java }
	}

	@Test
	fun tokensGetLimitAndOffset() {
		var tokensAPI = TokensApi();
		var limit = 10
		var offsetParameter = OffsetParameter()
		var offsetFilter = OffsetFilterImpl()
		offsetFilter.pg = 0
		offsetParameter.offsetFilterImpl = offsetFilter
		var tokens1 =	tokensAPI.tokensGetTokens(null,null,null,null,null,null,null,null,null,null, offsetParameter, limit,null);
		tokens1.forEach { it::class.java == Token::class.java }
		assert(tokens1.first().id == 1)
		offsetFilter.pg = 1
		var tokens2 =	tokensAPI.tokensGetTokens(null,null,null,null,null,null,null,null,null,null, offsetParameter, limit,null);
		tokens2.forEach { it::class.java == Token::class.java }
		assert(tokens2.first().id == 11)
	}

	@Test
	fun tokensGetById() {}

	@Test
	fun tokensGetByContractAndTokenId() {}


}
