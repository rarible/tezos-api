package com.rarible.tzkt

import com.rarible.tzkt.api.ContractsApi
import com.rarible.tzkt.filters.EqualityFilterImpl
import com.rarible.tzkt.model.parameters.ContractKindParameter
import org.junit.jupiter.api.Test

class TzktClientCollectionsTests {

    @Test
    fun contextLoads() {
    }

    /**
     * COLLECTIONS API TESTS
     */
    @Test
    fun contractsGetAllCollections() {
        var contractsAPI = ContractsApi();

        var contractKindParameter = ContractKindParameter()

        var kindEqualityFilter = EqualityFilterImpl()

        kindEqualityFilter.eq = "asset"

        contractKindParameter.equalityFilterImpl = kindEqualityFilter

        var tokens = contractsAPI.contractsGet(
            contractKindParameter,
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
        tokens.forEach{
            assert(it.kind == "asset")
        }
    }

    @Test
    fun contractsGetCollectionById() {
        var contractsAPI = ContractsApi();
        var address = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var collection = contractsAPI.contractsGetByAddress(address)
        assert(collection.address == address)
    }
}
