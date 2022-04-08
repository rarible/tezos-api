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

package com.rarible.tzkt.models

import com.rarible.tzkt.models.QuoteShort

import com.squareup.moshi.Json

/**
 * 
 *
 * @param index Cycle index starting from zero
 * @param firstLevel Level of the first block in the cycle
 * @param startTime Timestamp of the first block in the cycle
 * @param lastLevel Level of the last block in the cycle
 * @param endTime Timestamp of the last block in the cycle
 * @param snapshotIndex Index of the snapshot
 * @param snapshotLevel Height of the block where the snapshot was taken
 * @param randomSeed Randomly generated seed used by the network for things like baking rights distribution etc.
 * @param totalBakers Total number of all active in this cycle bakers
 * @param totalRolls Total number of rolls involved in baking rights distribution
 * @param totalStaking Total staking balance of all active in this cycle bakers
 * @param totalDelegators Total number of active bakers' delegators
 * @param totalDelegated Total balance delegated to active bakers
 * @param quote Injected historical quote at the end of the cycle
 */

data class Cycle (

    /* Cycle index starting from zero */
    @Json(name = "index")
    val index: kotlin.Int? = null,

    /* Level of the first block in the cycle */
    @Json(name = "firstLevel")
    val firstLevel: kotlin.Int? = null,

    /* Timestamp of the first block in the cycle */
    @Json(name = "startTime")
    val startTime: java.time.OffsetDateTime? = null,

    /* Level of the last block in the cycle */
    @Json(name = "lastLevel")
    val lastLevel: kotlin.Int? = null,

    /* Timestamp of the last block in the cycle */
    @Json(name = "endTime")
    val endTime: java.time.OffsetDateTime? = null,

    /* Index of the snapshot */
    @Json(name = "snapshotIndex")
    val snapshotIndex: kotlin.Int? = null,

    /* Height of the block where the snapshot was taken */
    @Json(name = "snapshotLevel")
    val snapshotLevel: kotlin.Int? = null,

    /* Randomly generated seed used by the network for things like baking rights distribution etc. */
    @Json(name = "randomSeed")
    val randomSeed: kotlin.String? = null,

    /* Total number of all active in this cycle bakers */
    @Json(name = "totalBakers")
    val totalBakers: kotlin.Int? = null,

    /* Total number of rolls involved in baking rights distribution */
    @Json(name = "totalRolls")
    val totalRolls: kotlin.Int? = null,

    /* Total staking balance of all active in this cycle bakers */
    @Json(name = "totalStaking")
    val totalStaking: kotlin.Long? = null,

    /* Total number of active bakers' delegators */
    @Json(name = "totalDelegators")
    val totalDelegators: kotlin.Int? = null,

    /* Total balance delegated to active bakers */
    @Json(name = "totalDelegated")
    val totalDelegated: kotlin.Long? = null,

    /* Injected historical quote at the end of the cycle */
    @Json(name = "quote")
    val quote: QuoteShort? = null

)

