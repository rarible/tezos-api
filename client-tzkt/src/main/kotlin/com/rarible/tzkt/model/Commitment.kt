/**
 * TzKT API
 *
 * # Introduction  TzKT Explorer provides free REST API and WebSocket API for accessing detailed Tezos blockchain data and helps developers build more services and applications on top of Tezos. TzKT is an open-source project, so you can easily clone and build it and use it as a self-hosted service to avoid any risks of depending on third-party services.  TzKT API is available for the following Tezos networks with the following base URLs:  - Mainnet: `https://api.tzkt.io/` or `https://api.mainnet.tzkt.io/` ([view docs](https://api.tzkt.io))  - Granadanet: `https://api.granadanet.tzkt.io/` ([view docs](https://api.granadanet.tzkt.io))     - Hangzhou2net: `https://api.hangzhou2net.tzkt.io/` ([view docs](https://api.hangzhou2net.tzkt.io))  We also provide a staging environment for testing newest features and pre-updating client applications before deploying to production:  - Mainnet staging: `https://staging.api.tzkt.io/` or `https://staging.api.mainnet.tzkt.io/` ([view docs](https://staging.api.tzkt.io))  Feel free to contact us if you have any questions or feature requests. Your feedback really helps us make TzKT better!  - Discord: https://discord.gg/aG8XKuwsQd - Telegram: https://t.me/baking_bad_chat - Slack: https://tezos-dev.slack.com/archives/CV5NX7F2L - Twitter: https://twitter.com/TezosBakingBad - Email: hello@baking-bad.org  And don't forget to star TzKT project [on GitHub](https://github.com/baking-bad/tzkt) ;)  # Terms of Use  TzKT API is free for everyone and for both commercial and non-commercial usage.  If your application or service uses the TzKT API in any forms: directly on frontend or indirectly on backend, you should mention that fact on your website or application by placing the label **\"Powered by TzKT API\"** with a direct link to [tzkt.io](https://tzkt.io).   # Rate Limits  There will be no rate limits as long as our servers can handle the load without additional infrastructure costs. However, any apparent abuse will be prevented by setting targeted rate limits.  Check out [Tezos Explorer API Best Practices](https://baking-bad.org/blog/tag/TzKT/) and in particular [how to optimize requests count](https://baking-bad.org/blog/2020/07/29/tezos-explorer-api-tzkt-how-often-to-make-requests/).  --- 
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
 * @param address Blinded address of the account
 * @param balance Account balance to be activated
 * @param activated Flag showing whether the account has been activated or not.
 * @param activationLevel Level of the block at which the account has been activated. `null` if the account is not activated yet.
 * @param activationTime Datetime of the block at which the account has been activated (ISO 8601, e.g. `2020-02-20T02:40:57Z`). `null` if the account is not activated yet.
 * @param activatedAccount Info about activated account. `null` if the account is not activated yet.
 */

data class Commitment (

    /* Blinded address of the account */
    @Json(name = "address")
    val address: kotlin.String? = null,

    /* Account balance to be activated */
    @Json(name = "balance")
    val balance: kotlin.Long? = null,

    /* Flag showing whether the account has been activated or not. */
    @Json(name = "activated")
    val activated: kotlin.Boolean? = null,

    /* Level of the block at which the account has been activated. `null` if the account is not activated yet. */
    @Json(name = "activationLevel")
    val activationLevel: kotlin.Int? = null,

    /* Datetime of the block at which the account has been activated (ISO 8601, e.g. `2020-02-20T02:40:57Z`). `null` if the account is not activated yet. */
    @Json(name = "activationTime")
    val activationTime: java.time.OffsetDateTime? = null,

    /* Info about activated account. `null` if the account is not activated yet. */
    @Json(name = "activatedAccount")
    val activatedAccount: Alias? = null

)

