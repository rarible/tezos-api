package com.rarible.tzkt

import com.rarible.tzkt.api.TokensApi
import com.rarible.tzkt.models.Token
import org.junit.jupiter.api.Test

class TezosTzktClientTokensTests {

	@Test
	fun contextLoads() {
	}

	/**
	 * ACCOUNTS API TESTS
	 */
	@Test
	fun tokensGetNoFilter() {
		var tokensAPI = TokensApi();
		var tokens =	tokensAPI.tokensGetTokens(null,null,null,null,null,null,null,null,null,null, null, null,null);
		println(tokens)
		tokens.forEach { it::class.java == Token::class.java }
	}

}
