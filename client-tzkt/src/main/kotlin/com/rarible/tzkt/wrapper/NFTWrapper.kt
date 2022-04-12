package com.rarible.tzkt.wrapper

import com.rarible.tzkt.api.TokensApi
import com.rarible.tzkt.filters.EqualityFilterImpl
import com.rarible.tzkt.filters.OffsetFilterImpl
import com.rarible.tzkt.filters.SortFilterImpl
import com.rarible.tzkt.model.parameters.AccountParameter
import com.rarible.tzkt.model.parameters.ContractKindParameter
import com.rarible.tzkt.model.parameters.NatParameter
import com.rarible.tzkt.model.parameters.OffsetParameter
import com.rarible.tzkt.model.parameters.SortParameter
import com.rarible.tzkt.model.parameters.TokenStandardParameter
import com.rarible.tzkt.models.Token

fun getAllNFTs(size: Int?, continuation: Long?, sortAsc: Boolean = true): List<Token> {
    var tokensAPI = TokensApi();

    val offsetParameter = OffsetParameter()
    val sortParameter = SortParameter()
    val contractKindParameter = ContractKindParameter()
    val tokenStandardParameter = TokenStandardParameter()

    val offsetFilter = OffsetFilterImpl()
    val sortFilter = SortFilterImpl()
    val kindEqualityFilter = EqualityFilterImpl()
    val tokenStandardFilter = EqualityFilterImpl()

    if (sortAsc)
        sortFilter.asc = "id"
    else
        sortFilter.desc = "id"

    offsetFilter.cr = continuation
    kindEqualityFilter.eq = "asset"
    tokenStandardFilter.eq = "fa2"

    offsetParameter.offsetFilterImpl = offsetFilter
    sortParameter.sortFilterImpl = sortFilter
    contractKindParameter.equalityFilterImpl = kindEqualityFilter
    tokenStandardParameter.equalityFilterImpl = tokenStandardFilter

    return tokensAPI.tokensGetTokens(
        null,
        null,
        null,
        tokenStandardParameter,
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

fun getNFTById(contract: String, tokenId: String): List<Token> {
    var tokensAPI = TokensApi();

    var accountParameter = AccountParameter()
    var tokenIdParameter = NatParameter()

    var accountEqualityFilter = EqualityFilterImpl()
    var tokenIdEqualityFilter = EqualityFilterImpl()

    accountEqualityFilter.eq = contract
    tokenIdEqualityFilter.eq = tokenId

    accountParameter.equalityFilterImpl = accountEqualityFilter
    tokenIdParameter.equalityFilterImpl = tokenIdEqualityFilter

    return tokensAPI.tokensGetTokens(
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
}