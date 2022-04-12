package com.rarible.tzkt.wrapper

import com.rarible.tzkt.api.ContractsApi
import com.rarible.tzkt.filters.EqualityFilterImpl
import com.rarible.tzkt.filters.OffsetFilterImpl
import com.rarible.tzkt.filters.SortFilterImpl
import com.rarible.tzkt.model.parameters.ContractKindParameter
import com.rarible.tzkt.model.parameters.OffsetParameter
import com.rarible.tzkt.model.parameters.SortParameter
import com.rarible.tzkt.models.Contract

fun getAllCollections(size: Int?, continuation: Long?, sortAsc: Boolean = true): List<Contract> {
    var contractsAPI = ContractsApi();

    val offsetParameter = OffsetParameter()
    val sortParameter = SortParameter()
    val contractKindParameter = ContractKindParameter()

    val offsetFilter = OffsetFilterImpl()
    val sortFilter = SortFilterImpl()
    val kindEqualityFilter = EqualityFilterImpl()

    if (sortAsc)
        sortFilter.asc = "firstActivity"
    else
        sortFilter.desc = "firstActivity"

    offsetFilter.cr = continuation
    kindEqualityFilter.eq = "asset"

    offsetParameter.offsetFilterImpl = offsetFilter
    sortParameter.sortFilterImpl = sortFilter
    contractKindParameter.equalityFilterImpl = kindEqualityFilter

    return contractsAPI.contractsGet(
        contractKindParameter,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        sortParameter,
        offsetParameter,
        size,
        null
    )
}

fun getCollectionById(id: String): Contract {
    var contractsAPI = ContractsApi();
    return contractsAPI.contractsGetByAddress(id)
}