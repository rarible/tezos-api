package com.rarible.tzkt

import com.rarible.tzkt.filters.AnyAllFilterImpl
import com.rarible.tzkt.filters.EqualityFilterImpl
import org.junit.jupiter.api.Test

class TezosTzktClientFilterTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun equalityFilterTest(){
		var equalityFilterImpl = EqualityFilterImpl()
		equalityFilterImpl.setEQ("user")
		assert(equalityFilterImpl.getFilter() == ".eq")
		assert(equalityFilterImpl.getFilterValue() == "user")
		assert(equalityFilterImpl.ne == null)
		equalityFilterImpl.setNE("contract")
		assert(equalityFilterImpl.getFilter() == ".ne")
		assert(equalityFilterImpl.getFilterValue() == "contract")
		assert(equalityFilterImpl.eq == null)
	}

	@Test
	fun anyAllFilterTest(){
		var anyAllFilter = AnyAllFilterImpl()
		anyAllFilter.setAllList(listOf("1","2"))
		assert(anyAllFilter.getFilter() == ".all")
		assert(anyAllFilter.getFilterValue() == "1,2")
		anyAllFilter.setAnyList(listOf("3","4"))
		assert(anyAllFilter.getFilter() == ".any")
		assert(anyAllFilter.getFilterValue() == "3,4")
	}

}
