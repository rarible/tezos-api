package com.rarible.tzkt

import com.rarible.tzkt.api.AccountsApi
import com.rarible.tzkt.filters.AnyAllFilterImpl
import com.rarible.tzkt.filters.EqualityFilterImpl
import com.rarible.tzkt.models.Account
import com.rarible.tzkt.models.AccountTypeParameter
import org.junit.jupiter.api.Test
class TezosTzktClientAccountTests {

	@Test
	fun contextLoads() {
	}

	/**
	 * ACCOUNTS API TESTS
	 */
	@Test
	fun getAccountsWithoutFilters() {
		var accountsApi = AccountsApi();
		var accounts =	accountsApi.accountsGet(null,null,null,null,null,null,null,null,null,null);
		accounts.forEach { it::class.java == Account::class.java }
	}

	@Test
	fun getAccountsWithAllFilters() {
		var accountsApi = AccountsApi();
		var equalityFilterImpl = EqualityFilterImpl()
		equalityFilterImpl.setEQ("user")
		var accountTypeFilter = AccountTypeParameter()
		accountTypeFilter.equalityFilterImpl = equalityFilterImpl
		var accounts =	accountsApi.accountsGet(accountTypeFilter,null,null,null,null,null,null,null,null,null);
		println(accounts)
	}

}
