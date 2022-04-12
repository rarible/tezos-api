package com.rarible.tzkt.wrapper

import com.rarible.tzkt.api.TokensApi
import com.rarible.tzkt.filters.EqualityFilterImpl
import com.rarible.tzkt.model.parameters.AccountParameter
import com.rarible.tzkt.model.parameters.NatParameter
import com.rarible.tzkt.models.TokenBalance

fun getOwnershipById(contract: String, tokenId: String, owner: String): List<TokenBalance> {
    var tokensAPI = TokensApi();

    var accountParameter = AccountParameter()
    var contractParameter = AccountParameter()
    var tokenIdParameter = NatParameter()

    var accountEqualityFilter = EqualityFilterImpl()
    var contractEqualityFilter = EqualityFilterImpl()
    var tokenIdEqualityFilter = EqualityFilterImpl()

    accountEqualityFilter.eq = owner
    contractEqualityFilter.eq = contract
    tokenIdEqualityFilter.eq = tokenId

    accountParameter.equalityFilterImpl = accountEqualityFilter
    contractParameter.equalityFilterImpl = contractEqualityFilter
    tokenIdParameter.equalityFilterImpl = tokenIdEqualityFilter

    return tokensAPI.tokensGetTokenBalances(
        null,
        accountParameter,
        null,
        contractParameter,
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
        null,
        null,
        null
    );
}

fun getOwnershipsByNFT(contract: String, tokenId: String): List<TokenBalance> {
    var tokensAPI = TokensApi();

    var contractParameter = AccountParameter()
    var tokenIdParameter = NatParameter()

    var contractEqualityFilter = EqualityFilterImpl()
    var tokenIdEqualityFilter = EqualityFilterImpl()

    contractEqualityFilter.eq = contract
    tokenIdEqualityFilter.eq = tokenId

    contractParameter.equalityFilterImpl = contractEqualityFilter
    tokenIdParameter.equalityFilterImpl = tokenIdEqualityFilter

    return tokensAPI.tokensGetTokenBalances(
        null,
        null,
        null,
        contractParameter,
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
        null,
        null,
        null
    );
}

fun getOwnershipsByOwner(owner: String): List<TokenBalance> {
    var tokensAPI = TokensApi();

    var ownerParameter = AccountParameter()

    var ownerEqualityFilter = EqualityFilterImpl()

    ownerEqualityFilter.eq = owner

    ownerParameter.equalityFilterImpl = ownerEqualityFilter

    return tokensAPI.tokensGetTokenBalances(
        null,
        ownerParameter,
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
        null
    );
}