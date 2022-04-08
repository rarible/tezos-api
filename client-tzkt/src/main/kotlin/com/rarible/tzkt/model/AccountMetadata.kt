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


import com.squareup.moshi.Json

/**
 * 
 *
 * @param kind 
 * @param alias 
 * @param description 
 * @param site 
 * @param support 
 * @param email 
 * @param twitter 
 * @param telegram 
 * @param discord 
 * @param reddit 
 * @param slack 
 * @param github 
 * @param gitlab 
 * @param instagram 
 * @param facebook 
 * @param medium 
 */

data class AccountMetadata (

    @Json(name = "kind")
    val kind: kotlin.String? = null,

    @Json(name = "alias")
    val alias: kotlin.String? = null,

    @Json(name = "description")
    val description: kotlin.String? = null,

    @Json(name = "site")
    val site: kotlin.String? = null,

    @Json(name = "support")
    val support: kotlin.String? = null,

    @Json(name = "email")
    val email: kotlin.String? = null,

    @Json(name = "twitter")
    val twitter: kotlin.String? = null,

    @Json(name = "telegram")
    val telegram: kotlin.String? = null,

    @Json(name = "discord")
    val discord: kotlin.String? = null,

    @Json(name = "reddit")
    val reddit: kotlin.String? = null,

    @Json(name = "slack")
    val slack: kotlin.String? = null,

    @Json(name = "github")
    val github: kotlin.String? = null,

    @Json(name = "gitlab")
    val gitlab: kotlin.String? = null,

    @Json(name = "instagram")
    val instagram: kotlin.String? = null,

    @Json(name = "facebook")
    val facebook: kotlin.String? = null,

    @Json(name = "medium")
    val medium: kotlin.String? = null

)

