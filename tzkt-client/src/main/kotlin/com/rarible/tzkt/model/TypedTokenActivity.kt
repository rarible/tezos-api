
package com.rarible.tzkt.model

class TypedTokenActivity (
    val type: ActivityType,
    tokenActivity: TokenActivity
): TokenActivity(
    tokenActivity.id,
    tokenActivity.level,
    tokenActivity.timestamp,
    tokenActivity.token,
    tokenActivity.from,
    tokenActivity.to,
    tokenActivity.amount,
    tokenActivity.transactionId,
    tokenActivity.originationId,
    tokenActivity.migrationId
)

