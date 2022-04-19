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

package com.rarible.tzkt.model

/**
 * 
 *
 * @param type Type of the account, `delegate` - account, registered as a delegate (baker)
 * @param address Public key hash of the delegate (baker)
 * @param active Delegation status (`true` - active, `false` - deactivated)
 * @param alias Name of the baking service
 * @param publicKey Public key of the delegate (baker)
 * @param revealed Public key revelation status. Unrevealed account can't send manager operation (transaction, origination etc.)
 * @param balance Total balance of the delegate (baker), including spendable and frozen funds (micro tez)
 * @param frozenDeposits Amount of security deposit, currently locked for baked (produced) blocks and (or) given endorsements (micro tez)
 * @param frozenRewards Amount of currently frozen baking rewards (micro tez)
 * @param frozenFees Amount of currently frozen fees paid by operations inside blocks, baked (produced) by the delegate (micro tez)
 * @param counter An account nonce which is used to prevent operation replay
 * @param activationLevel Block height when delegate (baker) was registered as a baker last time
 * @param activationTime Block datetime when delegate (baker) was registered as a baker last time (ISO 8601, e.g. 2019-11-31)
 * @param deactivationLevel Block height when delegate (baker) was deactivated as a baker because of lack of funds or inactivity
 * @param deactivationTime Block datetime when delegate (baker) was deactivated as a baker because of lack of funds or inactivity (ISO 8601, e.g. 2019-11-31)
 * @param stakingBalance Sum of delegate (baker) balance and delegated funds minus frozen rewards (micro tez)
 * @param numContracts Number of contracts, created (originated) and/or managed by the delegate (baker)
 * @param activeTokensCount Number of account tokens with non-zero balances
 * @param tokenBalancesCount Number of tokens the account ever had
 * @param tokenTransfersCount Number of token transfers from/to the account
 * @param numDelegators Number of current delegators (accounts, delegated their funds) of the delegate (baker)
 * @param numBlocks Number of baked (validated) blocks all the time by the delegate (baker)
 * @param numEndorsements Number of given endorsements (approvals) by the delegate (baker)
 * @param numBallots Number of submitted by the delegate ballots during a voting period
 * @param numProposals Number of submitted (upvoted) by the delegate proposals during a proposal period
 * @param numActivations Number of account activation operations. Are used to activate accounts that were recommended allocations of tezos tokens for donations to the Tezos Foundation’s fundraiser
 * @param numDoubleBaking Number of double baking (baking two different blocks at the same height) evidence operations, included in blocks, baked (validated) by the delegate
 * @param numDoubleEndorsing Number of double endorsement (endorsing two different blocks at the same block height) evidence operations, included in blocks, baked (validated) by the delegate
 * @param numNonceRevelations Number of seed nonce revelation (are used by the blockchain to create randomness) operations provided by the delegate
 * @param numRevelationPenalties Number of operations for all time in which rewards were lost due to unrevealed seed nonces by the delegate (synthetic type)
 * @param numDelegations Number of all delegation related operations (new delegator, left delegator, registration as a baker), related to the delegate (baker) 
 * @param numOriginations Number of all origination (deployment / contract creation) operations, related to the delegate (baker)
 * @param numTransactions Number of all transaction (tez transfer) operations, related to the delegate (baker)
 * @param numReveals Number of reveal (is used to reveal the public key associated with an account) operations of the delegate (baker)
 * @param numRegisterConstants Number of register global constant operations of the delegate (baker)
 * @param numMigrations Number of migration (result of the context (database) migration during a protocol update) operations, related to the delegate (synthetic type) 
 * @param firstActivity Block height of the first operation, related to the delegate (baker)
 * @param firstActivityTime Block datetime of the first operation, related to the delegate (ISO 8601, e.g. `2020-02-20T02:40:57Z`)
 * @param lastActivity Height of the block in which the account state was changed last time
 * @param lastActivityTime Datetime of the block in which the account state was changed last time (ISO 8601, e.g. `2020-02-20T02:40:57Z`)
 * @param metadata Metadata of the delegate (alias, logo, website, contacts, etc)
 * @param software Last seen baker's software
 */

data class DelegateAllOf (

    /* Type of the account, `delegate` - account, registered as a delegate (baker) */
    val type: String? = null,

    /* Public key hash of the delegate (baker) */
    val address: String? = null,

    /* Delegation status (`true` - active, `false` - deactivated) */
    val active: Boolean? = null,

    /* Name of the baking service */
    val alias: String? = null,

    /* Public key of the delegate (baker) */
    val publicKey: String? = null,

    /* Public key revelation status. Unrevealed account can't send manager operation (transaction, origination etc.) */
    val revealed: Boolean? = null,

    /* Total balance of the delegate (baker), including spendable and frozen funds (micro tez) */
    val balance: Long? = null,

    /* Amount of security deposit, currently locked for baked (produced) blocks and (or) given endorsements (micro tez) */
    val frozenDeposits: Long? = null,

    /* Amount of currently frozen baking rewards (micro tez) */
    val frozenRewards: Long? = null,

    /* Amount of currently frozen fees paid by operations inside blocks, baked (produced) by the delegate (micro tez) */
    val frozenFees: Long? = null,

    /* An account nonce which is used to prevent operation replay */
    val counter: Int? = null,

    /* Block height when delegate (baker) was registered as a baker last time */
    val activationLevel: Int? = null,

    /* Block datetime when delegate (baker) was registered as a baker last time (ISO 8601, e.g. 2019-11-31) */
    val activationTime: java.time.OffsetDateTime? = null,

    /* Block height when delegate (baker) was deactivated as a baker because of lack of funds or inactivity */
    val deactivationLevel: Int? = null,

    /* Block datetime when delegate (baker) was deactivated as a baker because of lack of funds or inactivity (ISO 8601, e.g. 2019-11-31) */
    val deactivationTime: java.time.OffsetDateTime? = null,

    /* Sum of delegate (baker) balance and delegated funds minus frozen rewards (micro tez) */
    val stakingBalance: Long? = null,

    /* Number of contracts, created (originated) and/or managed by the delegate (baker) */
    val numContracts: Int? = null,

    /* Number of account tokens with non-zero balances */
    val activeTokensCount: Int? = null,

    /* Number of tokens the account ever had */
    val tokenBalancesCount: Int? = null,

    /* Number of token transfers from/to the account */
    val tokenTransfersCount: Int? = null,

    /* Number of current delegators (accounts, delegated their funds) of the delegate (baker) */
    val numDelegators: Int? = null,

    /* Number of baked (validated) blocks all the time by the delegate (baker) */
    val numBlocks: Int? = null,

    /* Number of given endorsements (approvals) by the delegate (baker) */
    val numEndorsements: Int? = null,

    /* Number of submitted by the delegate ballots during a voting period */
    val numBallots: Int? = null,

    /* Number of submitted (upvoted) by the delegate proposals during a proposal period */
    val numProposals: Int? = null,

    /* Number of account activation operations. Are used to activate accounts that were recommended allocations of tezos tokens for donations to the Tezos Foundation’s fundraiser */
    val numActivations: Int? = null,

    /* Number of double baking (baking two different blocks at the same height) evidence operations, included in blocks, baked (validated) by the delegate */
    val numDoubleBaking: Int? = null,

    /* Number of double endorsement (endorsing two different blocks at the same block height) evidence operations, included in blocks, baked (validated) by the delegate */
    val numDoubleEndorsing: Int? = null,

    /* Number of seed nonce revelation (are used by the blockchain to create randomness) operations provided by the delegate */
    val numNonceRevelations: Int? = null,

    /* Number of operations for all time in which rewards were lost due to unrevealed seed nonces by the delegate (synthetic type) */
    val numRevelationPenalties: Int? = null,

    /* Number of all delegation related operations (new delegator, left delegator, registration as a baker), related to the delegate (baker)  */
    val numDelegations: Int? = null,

    /* Number of all origination (deployment / contract creation) operations, related to the delegate (baker) */
    val numOriginations: Int? = null,

    /* Number of all transaction (tez transfer) operations, related to the delegate (baker) */
    val numTransactions: Int? = null,

    /* Number of reveal (is used to reveal the public key associated with an account) operations of the delegate (baker) */
    val numReveals: Int? = null,

    /* Number of register global constant operations of the delegate (baker) */
    val numRegisterConstants: Int? = null,

    /* Number of migration (result of the context (database) migration during a protocol update) operations, related to the delegate (synthetic type)  */
    val numMigrations: Int? = null,

    /* Block height of the first operation, related to the delegate (baker) */
    val firstActivity: Int? = null,

    /* Block datetime of the first operation, related to the delegate (ISO 8601, e.g. `2020-02-20T02:40:57Z`) */
    val firstActivityTime: java.time.OffsetDateTime? = null,

    /* Height of the block in which the account state was changed last time */
    val lastActivity: Int? = null,

    /* Datetime of the block in which the account state was changed last time (ISO 8601, e.g. `2020-02-20T02:40:57Z`) */
    val lastActivityTime: java.time.OffsetDateTime? = null,

    /* Metadata of the delegate (alias, logo, website, contacts, etc) */
    val metadata: AccountMetadata? = null,

    /* Last seen baker's software */
    val software: SoftwareAlias? = null

)

