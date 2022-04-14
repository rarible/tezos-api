/**
 * TzKT API
 *
 * # Introduction  TzKT Explorer provides free REST API and WebSocket API for accessing detailed Tezos blockchain data and helps developers build more services and applications on top of Tezos. TzKT is an open-source project, so you can easily clone and build it and use it as a self-hosted service to avoid any risks of depending on third-party services.  TzKT API is available for the following Tezos networks with the following base URLs:  - Mainnet: `https://api.tzkt.io/` or `https://api.mainnet.tzkt.io/` ([view docs](https://api.tzkt.io))  - Granadanet: `https://api.granadanet.tzkt.io/` ([view docs](https://api.granadanet.tzkt.io))     - Hangzhou2net: `https://api.hangzhou2net.tzkt.io/` ([view docs](https://api.hangzhou2net.tzkt.io))  We also provide a staging environment for testing newest features and pre-updating client applications before deploying to production:  - Mainnet staging: `https://api.tzkt.io/` or `https://staging.api.mainnet.tzkt.io/` ([view docs](https://api.tzkt.io))  Feel free to contact us if you have any questions or feature requests. Your feedback really helps us make TzKT better!  - Discord: https://discord.gg/aG8XKuwsQd - Telegram: https://t.me/baking_bad_chat - Slack: https://tezos-dev.slack.com/archives/CV5NX7F2L - Twitter: https://twitter.com/TezosBakingBad - Email: hello@baking-bad.org  And don't forget to star TzKT project [on GitHub](https://github.com/baking-bad/tzkt) ;)  # Terms of Use  TzKT API is free for everyone and for both commercial and non-commercial usage.  If your application or service uses the TzKT API in any forms: directly on frontend or indirectly on backend, you should mention that fact on your website or application by placing the label **\"Powered by TzKT API\"** with a direct link to [tzkt.io](https://tzkt.io).   # Rate Limits  There will be no rate limits as long as our servers can handle the load without additional infrastructure costs. However, any apparent abuse will be prevented by setting targeted rate limits.  Check out [Tezos Explorer API Best Practices](https://baking-bad.org/blog/tag/TzKT/) and in particular [how to optimize requests count](https://baking-bad.org/blog/2020/07/29/tezos-explorer-api-tzkt-how-often-to-make-requests/).  --- 
 *
 * The version of the OpenAPI document: v1.7.0
 * Contact: hello@baking-bad.org
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.rarible.tzkt.model

import com.rarible.tzkt.model.Alias

import com.squareup.moshi.Json

/**
 * 
 *
 * @param id Internal TzKT id (not the same as `tokenId`).   **[sortable]**
 * @param contract Contract, created the token.
 * @param tokenId Token id, unique within the contract.   **[sortable]**
 * @param standard Token standard (`fa1.2` or `fa2`).
 * @param firstLevel Level of the block where the token was first seen.   **[sortable]**
 * @param firstTime Timestamp of the block where the token was first seen.
 * @param lastLevel Level of the block where the token was last seen.   **[sortable]**
 * @param lastTime Timestamp of the block where the token was last seen.
 * @param transfersCount Total number of transfers.   **[sortable]**
 * @param balancesCount Total number of holders ever seen.   **[sortable]**
 * @param holdersCount Total number of current holders.   **[sortable]**
 * @param totalMinted Total number of minted tokens (raw value, not divided by `decimals`).
 * @param totalBurned Total number of burned tokens (raw value, not divided by `decimals`).
 * @param totalSupply Total number of existing tokens (raw value, not divided by `decimals`).
 * @param metadata Token metadata.   **[sortable]**
 */

data class Token (

    /* Internal TzKT id (not the same as `tokenId`).   **[sortable]** */
    @Json(name = "id")
    val id: kotlin.Int? = null,

    /* Contract, created the token. */
    @Json(name = "contract")
    val contract: Alias? = null,

    /* Token id, unique within the contract.   **[sortable]** */
    @Json(name = "tokenId")
    val tokenId: kotlin.String? = null,

    /* Token standard (`fa1.2` or `fa2`). */
    @Json(name = "standard")
    val standard: kotlin.String? = null,

    /* Level of the block where the token was first seen.   **[sortable]** */
    @Json(name = "firstLevel")
    val firstLevel: kotlin.Int? = null,

    /* Timestamp of the block where the token was first seen. */
    @Json(name = "firstTime")
    val firstTime: java.time.OffsetDateTime? = null,

    /* Level of the block where the token was last seen.   **[sortable]** */
    @Json(name = "lastLevel")
    val lastLevel: kotlin.Int? = null,

    /* Timestamp of the block where the token was last seen. */
    @Json(name = "lastTime")
    val lastTime: java.time.OffsetDateTime? = null,

    /* Total number of transfers.   **[sortable]** */
    @Json(name = "transfersCount")
    val transfersCount: kotlin.Int? = null,

    /* Total number of holders ever seen.   **[sortable]** */
    @Json(name = "balancesCount")
    val balancesCount: kotlin.Int? = null,

    /* Total number of current holders.   **[sortable]** */
    @Json(name = "holdersCount")
    val holdersCount: kotlin.Int? = null,

    /* Total number of minted tokens (raw value, not divided by `decimals`). */
    @Json(name = "totalMinted")
    val totalMinted: kotlin.String? = null,

    /* Total number of burned tokens (raw value, not divided by `decimals`). */
    @Json(name = "totalBurned")
    val totalBurned: kotlin.String? = null,

    /* Total number of existing tokens (raw value, not divided by `decimals`). */
    @Json(name = "totalSupply")
    val totalSupply: kotlin.String? = null,

    /* Token metadata.   **[sortable]** */
    @Json(name = "metadata")
    val metadata: kotlin.Any? = null

)
