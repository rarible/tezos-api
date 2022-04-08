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

import com.rarible.tzkt.models.Alias

import com.squareup.moshi.Json

/**
 * 
 *
 * @param type Type of the right: - `baking` - right to bake (produce) a block; - `endorsing` - right to endorse (validate) a block.
 * @param cycle Cycle on which the right can be realized.
 * @param level Level at which a block must be baked or an endorsement must be sent.
 * @param timestamp Time (estimated, in case of future rights) when a block must be baked or an endorsement must be sent.
 * @param priority Priority (0 - ∞) with which baker can produce a block. If a baker with priority `0` doesn't produce a block within a given time interval, then the right goes to a baker with priority` 1`, etc. For `endorsing` rights this field is always `null`.
 * @param slots Number of slots (1 - 32) to be endorsed. For `baking` rights this field is always `null`.
 * @param baker Baker to which baking or endorsing right has been given.
 * @param status Status of the baking or endorsing right: - `future` - the right is not realized yet; - `realized` - the right was successfully realized; - `uncovered` - the right was not realized due to lack of bonds (for example, when a baker is overdelegated); - `missed` - the right was not realized for no apparent reason (usually due to issues with network or node).
 */

data class BakingRight (

    /* Type of the right: - `baking` - right to bake (produce) a block; - `endorsing` - right to endorse (validate) a block. */
    @Json(name = "type")
    val type: kotlin.String? = null,

    /* Cycle on which the right can be realized. */
    @Json(name = "cycle")
    val cycle: kotlin.Int? = null,

    /* Level at which a block must be baked or an endorsement must be sent. */
    @Json(name = "level")
    val level: kotlin.Int? = null,

    /* Time (estimated, in case of future rights) when a block must be baked or an endorsement must be sent. */
    @Json(name = "timestamp")
    val timestamp: java.time.OffsetDateTime? = null,

    /* Priority (0 - ∞) with which baker can produce a block. If a baker with priority `0` doesn't produce a block within a given time interval, then the right goes to a baker with priority` 1`, etc. For `endorsing` rights this field is always `null`. */
    @Json(name = "priority")
    val priority: kotlin.Int? = null,

    /* Number of slots (1 - 32) to be endorsed. For `baking` rights this field is always `null`. */
    @Json(name = "slots")
    val slots: kotlin.Int? = null,

    /* Baker to which baking or endorsing right has been given. */
    @Json(name = "baker")
    val baker: Alias? = null,

    /* Status of the baking or endorsing right: - `future` - the right is not realized yet; - `realized` - the right was successfully realized; - `uncovered` - the right was not realized due to lack of bonds (for example, when a baker is overdelegated); - `missed` - the right was not realized for no apparent reason (usually due to issues with network or node). */
    @Json(name = "status")
    val status: kotlin.String? = null

)

