
package com.rarible.tzkt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenBalanceShort (
    val id: Int,
    @JsonProperty("token.contract.address")
    val contract: String? = null,
    @JsonProperty("token.tokenId")
    val tokenId: String? = null,
    val balance: String? = null
) {
    fun itemId() = "${contract}:$tokenId"
}

