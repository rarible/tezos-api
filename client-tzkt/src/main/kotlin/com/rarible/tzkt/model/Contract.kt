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

import com.rarible.tzkt.model.Account
import com.rarible.tzkt.model.AccountMetadata

import com.squareup.moshi.Json

/**
 * 
 *
 * @param type Type of the account, `contract` - smart contract programmable account
 * @param address Public key hash of the contract
 * @param kind Kind of the contract (`delegator_contract` or `smart_contract`), where `delegator_contract` - manager.tz smart contract for delegation purpose only
 * @param tzips List of implemented standards (TZIPs)
 * @param alias Name of the project behind the contract or contract description
 * @param balance Contract balance (micro tez)
 * @param creator Information about the account, which has deployed the contract to the blockchain
 * @param manager Information about the account, which was marked as a manager when contract was deployed to the blockchain
 * @param `delegate` Information about the current delegate of the contract. `null` if it's not delegated
 * @param delegationLevel Block height of latest delegation. `null` if it's not delegated
 * @param delegationTime Block datetime of latest delegation (ISO 8601, e.g. `2020-02-20T02:40:57Z`). `null` if it's not delegated
 * @param numContracts Number of contracts, created (originated) and/or managed by the contract
 * @param activeTokensCount Number of account tokens with non-zero balances
 * @param tokenBalancesCount Number of tokens the account ever had
 * @param tokenTransfersCount Number of token transfers from/to the account
 * @param numDelegations Number of delegation operations of the contract
 * @param numOriginations Number of origination (deployment / contract creation) operations, related the contract
 * @param numTransactions Number of transaction (transfer) operations, related to the contract
 * @param numReveals Number of reveal (is used to reveal the public key associated with an account) operations of the contract
 * @param numMigrations Number of migration (result of the context (database) migration during a protocol update) operations related to the contract (synthetic type)
 * @param firstActivity Block height of the contract creation
 * @param firstActivityTime Block datetime of the contract creation (ISO 8601, e.g. `2020-02-20T02:40:57Z`)
 * @param lastActivity Height of the block in which the account state was changed last time
 * @param lastActivityTime Datetime of the block in which the account state was changed last time (ISO 8601, e.g. `2020-02-20T02:40:57Z`)
 * @param storage Contract storage value. Omitted by default. Use `?includeStorage=true` to include it in response.
 * @param typeHash 32-bit hash of the contract parameter and storage types. This field can be used for searching similar contracts (which have the same interface).
 * @param codeHash 32-bit hash of the contract code. This field can be used for searching same contracts (which have the same script).
 * @param metadata Metadata of the contract (alias, logo, website, contacts, etc)
 */

data class Contract (

    /* Type of the account, `contract` - smart contract programmable account */
    @Json(name = "type")
    override val type: String,

    /* Public key hash of the contract */
    @Json(name = "address")
    val address: kotlin.String? = null,

    /* Kind of the contract (`delegator_contract` or `smart_contract`), where `delegator_contract` - manager.tz smart contract for delegation purpose only */
    @Json(name = "kind")
    val kind: kotlin.String? = null,

    /* List of implemented standards (TZIPs) */
    @Json(name = "tzips")
    val tzips: kotlin.collections.List<kotlin.String>? = null,

    /* Name of the project behind the contract or contract description */
    @Json(name = "alias")
    val alias: kotlin.String? = null,

    /* Contract balance (micro tez) */
    @Json(name = "balance")
    val balance: kotlin.Long? = null,

    /* Information about the account, which has deployed the contract to the blockchain */
    @Json(name = "creator")
    val creator: CreatorInfo? = null,

    /* Information about the account, which was marked as a manager when contract was deployed to the blockchain */
    @Json(name = "manager")
    val manager: ManagerInfo? = null,

    /* Information about the current delegate of the contract. `null` if it's not delegated */
    @Json(name = "delegate")
    val `delegate`: DelegateInfo? = null,

    /* Block height of latest delegation. `null` if it's not delegated */
    @Json(name = "delegationLevel")
    val delegationLevel: kotlin.Int? = null,

    /* Block datetime of latest delegation (ISO 8601, e.g. `2020-02-20T02:40:57Z`). `null` if it's not delegated */
    @Json(name = "delegationTime")
    val delegationTime: java.time.OffsetDateTime? = null,

    /* Number of contracts, created (originated) and/or managed by the contract */
    @Json(name = "numContracts")
    val numContracts: kotlin.Int? = null,

    /* Number of account tokens with non-zero balances */
    @Json(name = "activeTokensCount")
    val activeTokensCount: kotlin.Int? = null,

    /* Number of tokens the account ever had */
    @Json(name = "tokenBalancesCount")
    val tokenBalancesCount: kotlin.Int? = null,

    /* Number of token transfers from/to the account */
    @Json(name = "tokenTransfersCount")
    val tokenTransfersCount: kotlin.Int? = null,

    /* Number of delegation operations of the contract */
    @Json(name = "numDelegations")
    val numDelegations: kotlin.Int? = null,

    /* Number of origination (deployment / contract creation) operations, related the contract */
    @Json(name = "numOriginations")
    val numOriginations: kotlin.Int? = null,

    /* Number of transaction (transfer) operations, related to the contract */
    @Json(name = "numTransactions")
    val numTransactions: kotlin.Int? = null,

    /* Number of reveal (is used to reveal the public key associated with an account) operations of the contract */
    @Json(name = "numReveals")
    val numReveals: kotlin.Int? = null,

    /* Number of migration (result of the context (database) migration during a protocol update) operations related to the contract (synthetic type) */
    @Json(name = "numMigrations")
    val numMigrations: kotlin.Int? = null,

    /* Block height of the contract creation */
    @Json(name = "firstActivity")
    val firstActivity: kotlin.Int? = null,

    /* Block datetime of the contract creation (ISO 8601, e.g. `2020-02-20T02:40:57Z`) */
    @Json(name = "firstActivityTime")
    val firstActivityTime: java.time.OffsetDateTime? = null,

    /* Height of the block in which the account state was changed last time */
    @Json(name = "lastActivity")
    val lastActivity: kotlin.Int? = null,

    /* Datetime of the block in which the account state was changed last time (ISO 8601, e.g. `2020-02-20T02:40:57Z`) */
    @Json(name = "lastActivityTime")
    val lastActivityTime: java.time.OffsetDateTime? = null,

    /* Contract storage value. Omitted by default. Use `?includeStorage=true` to include it in response. */
    @Json(name = "storage")
    val storage: kotlin.Any? = null,

    /* 32-bit hash of the contract parameter and storage types. This field can be used for searching similar contracts (which have the same interface). */
    @Json(name = "typeHash")
    val typeHash: kotlin.Int? = null,

    /* 32-bit hash of the contract code. This field can be used for searching same contracts (which have the same script). */
    @Json(name = "codeHash")
    val codeHash: kotlin.Int? = null,

    /* Metadata of the contract (alias, logo, website, contacts, etc) */
    @Json(name = "metadata")
    val metadata: AccountMetadata? = null

) : Account

