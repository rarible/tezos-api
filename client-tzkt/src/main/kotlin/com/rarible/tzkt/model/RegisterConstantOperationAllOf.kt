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
import com.rarible.tzkt.models.OperationError
import com.rarible.tzkt.models.QuoteShort

import com.squareup.moshi.Json

/**
 * 
 *
 * @param type Type of the operation, `register_constant` - is used to register a global constant - Micheline expression that can be reused by multiple smart contracts
 * @param id Unique ID of the operation, stored in the TzKT indexer database
 * @param level The height of the block from the genesis block, in which the operation was included
 * @param timestamp Datetime of the block, in which the operation was included (ISO 8601, e.g. `2020-02-20T02:40:57Z`)
 * @param block Hash of the block, in which the operation was included
 * @param hash Hash of the operation
 * @param sender Information about the account who has sent the operation
 * @param counter An account nonce which is used to prevent operation replay
 * @param gasLimit A cap on the amount of gas a given operation can consume
 * @param gasUsed Amount of gas, consumed by the operation
 * @param storageLimit A cap on the amount of storage a given operation can consume
 * @param storageUsed Amount of storage, consumed by the operation
 * @param bakerFee Fee to the baker, produced block, in which the operation was included (micro tez)
 * @param storageFee The amount of funds burned from the sender account for used the blockchain storage (micro tez)
 * @param status Operation status (`applied` - an operation applied by the node and successfully added to the blockchain, `failed` - an operation which failed with some particular error (not enough balance, gas limit, etc), `backtracked` - an operation which was successful but reverted due to one of the following operations in the same operation group was failed, `skipped` - all operations after the failed one in an operation group)
 * @param address Global address of the registered constant (null if the operation failed)
 * @param `value` Constant value. Note: you can configure code format by setting `micheline` query parameter (`0 | 2` - raw micheline, `1 | 3` - raw micheline string).
 * @param errors List of errors provided by the node, injected the operation to the blockchain. `null` if there is no errors
 * @param quote Injected historical quote at the time of operation
 */

data class RegisterConstantOperationAllOf (

    /* Type of the operation, `register_constant` - is used to register a global constant - Micheline expression that can be reused by multiple smart contracts */
    @Json(name = "type")
    val type: kotlin.String? = null,

    /* Unique ID of the operation, stored in the TzKT indexer database */
    @Json(name = "id")
    val id: kotlin.Int? = null,

    /* The height of the block from the genesis block, in which the operation was included */
    @Json(name = "level")
    val level: kotlin.Int? = null,

    /* Datetime of the block, in which the operation was included (ISO 8601, e.g. `2020-02-20T02:40:57Z`) */
    @Json(name = "timestamp")
    val timestamp: java.time.OffsetDateTime? = null,

    /* Hash of the block, in which the operation was included */
    @Json(name = "block")
    val block: kotlin.String? = null,

    /* Hash of the operation */
    @Json(name = "hash")
    val hash: kotlin.String? = null,

    /* Information about the account who has sent the operation */
    @Json(name = "sender")
    val sender: Alias? = null,

    /* An account nonce which is used to prevent operation replay */
    @Json(name = "counter")
    val counter: kotlin.Int? = null,

    /* A cap on the amount of gas a given operation can consume */
    @Json(name = "gasLimit")
    val gasLimit: kotlin.Int? = null,

    /* Amount of gas, consumed by the operation */
    @Json(name = "gasUsed")
    val gasUsed: kotlin.Int? = null,

    /* A cap on the amount of storage a given operation can consume */
    @Json(name = "storageLimit")
    val storageLimit: kotlin.Int? = null,

    /* Amount of storage, consumed by the operation */
    @Json(name = "storageUsed")
    val storageUsed: kotlin.Int? = null,

    /* Fee to the baker, produced block, in which the operation was included (micro tez) */
    @Json(name = "bakerFee")
    val bakerFee: kotlin.Long? = null,

    /* The amount of funds burned from the sender account for used the blockchain storage (micro tez) */
    @Json(name = "storageFee")
    val storageFee: kotlin.Long? = null,

    /* Operation status (`applied` - an operation applied by the node and successfully added to the blockchain, `failed` - an operation which failed with some particular error (not enough balance, gas limit, etc), `backtracked` - an operation which was successful but reverted due to one of the following operations in the same operation group was failed, `skipped` - all operations after the failed one in an operation group) */
    @Json(name = "status")
    val status: kotlin.String? = null,

    /* Global address of the registered constant (null if the operation failed) */
    @Json(name = "address")
    val address: kotlin.String? = null,

    /* Constant value. Note: you can configure code format by setting `micheline` query parameter (`0 | 2` - raw micheline, `1 | 3` - raw micheline string). */
    @Json(name = "value")
    val `value`: kotlin.Any? = null,

    /* List of errors provided by the node, injected the operation to the blockchain. `null` if there is no errors */
    @Json(name = "errors")
    val errors: kotlin.collections.List<OperationError>? = null,

    /* Injected historical quote at the time of operation */
    @Json(name = "quote")
    val quote: QuoteShort? = null

)

