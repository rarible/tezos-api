package com.rarible.tzkt.wrapper

import com.rarible.tzkt.api.TokensApi
import com.rarible.tzkt.filters.OffsetFilterImpl
import com.rarible.tzkt.filters.SortFilterImpl
import com.rarible.tzkt.model.parameters.OffsetParameter
import com.rarible.tzkt.model.parameters.SortParameter
import com.rarible.tzkt.models.TokenTransfer

fun getAllActivities(size: Int?, continuation: Long?, sortAsc: Boolean = true): List<TokenTransfer> {
    val tokensAPI = TokensApi();

    val offsetParameter = OffsetParameter()
    val sortParameter = SortParameter()

    val offsetFilter = OffsetFilterImpl()
    val sortFilter = SortFilterImpl()

    if (sortAsc)
        sortFilter.asc = "id"
    else
        sortFilter.desc = "id"

    offsetFilter.cr = continuation

    offsetParameter.offsetFilterImpl = offsetFilter
    sortParameter.sortFilterImpl = sortFilter

    return tokensAPI.tokensGetTokenTransfers(
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
        null,
        null,
        null,
        null,
        null,
        sortParameter,
        offsetParameter,
        size,
        null
    );
}