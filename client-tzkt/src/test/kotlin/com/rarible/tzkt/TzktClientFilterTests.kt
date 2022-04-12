package com.rarible.tzkt

import com.rarible.tzkt.filters.AnyAllFilterImpl
import com.rarible.tzkt.filters.EqualityFilterImpl
import org.junit.jupiter.api.Test

class TzktClientFilterTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun equalityFilterTest(){
		var equalityFilterImplEQ = EqualityFilterImpl()
		equalityFilterImplEQ.eq = "user"
		assert(equalityFilterImplEQ.getFilter() == ".eq")
		assert(equalityFilterImplEQ.getFilterValue() == "user")

		var equalityFilterImplNE = EqualityFilterImpl()
		equalityFilterImplNE.ne = "contract"
		assert(equalityFilterImplNE.getFilter() == ".ne")
		assert(equalityFilterImplNE.getFilterValue() == "contract")
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
