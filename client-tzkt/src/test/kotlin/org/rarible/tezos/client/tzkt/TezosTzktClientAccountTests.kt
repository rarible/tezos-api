package com.rarible.tzkt

import com.rarible.tzkt.api.AccountsApi
import com.rarible.tzkt.filters.EqualityFilterImpl
import com.rarible.tzkt.filters.InclusionFilterImpl
import com.rarible.tzkt.models.Account
import com.rarible.tzkt.model.parameters.AccountTypeParameter
import com.rarible.tzkt.model.parameters.ContractKindParameter
import com.rarible.tzkt.models.Contract
import org.junit.jupiter.api.Test
class TezosTzktClientAccountTests {

	@Test
	fun contextLoads() {
	}

	/**
	 * ACCOUNTS API TESTS
	 */
	@Test
	fun accountsGetNoFilter() {
		var accountsApi = AccountsApi();
		var accounts =	accountsApi.accountsGet(null,null,null,null,null,null,null,null,null,null);
		accounts.forEach { it::class.java == Account::class.java }
	}

	@Test
	fun accountsGetTypeFiltered() {
		var accountsApi = AccountsApi();

		var accountTypeFilter = AccountTypeParameter()
		var equalityFilterImpl = EqualityFilterImpl()
		equalityFilterImpl.eq = "user"
		accountTypeFilter.equalityFilterImpl = equalityFilterImpl

		var accounts =	accountsApi.accountsGet(accountTypeFilter,null,null,null,null,null,null,null,null,null);
		accounts.forEach { it.type == "user" }
	}

	@Test
	fun accountsGetKindFiltered() {
		var accountsApi = AccountsApi();

		var contractKindParameter = ContractKindParameter()
		var equalityFilterImpl = EqualityFilterImpl()
		equalityFilterImpl.eq = "smart_contract"
		contractKindParameter.equalityFilterImpl = equalityFilterImpl

		var accountsEQ =	accountsApi.accountsGet(null,contractKindParameter,null,null,null,null,null,null,null,null);
		accountsEQ.forEach { (it as Contract).kind == "smart_contract" }

		var inclusionFilterImpl = InclusionFilterImpl()
		inclusionFilterImpl.`in` = listOf("smart_contract", "delegator_contract")

		contractKindParameter.equalityFilterImpl = null
		contractKindParameter.inclusionFilterImpl = inclusionFilterImpl

		var containsDelegator = false
		var containsSmartContract = false
		var accountsIN =	accountsApi.accountsGet(null,contractKindParameter,null,null,null,null,null,null,null,null);
		accountsIN.forEach {
			assert((it as Contract).kind == "smart_contract" || it.kind == "delegator_contract");
			if(it.kind == "smart_contract"){
				containsSmartContract = true
			};
			if(it.kind == "delegator_contract"){
				containsDelegator = true
			};
			println(it.kind)
		}
		assert(containsDelegator)
		assert(containsSmartContract)
	}

}
