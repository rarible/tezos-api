
package com.rarible.tzkt.model

class TypedTokenActivity (
    val type: ActivityType,
    val tokenActivity: TokenActivity
): TokenActivity(
    tokenActivity.id,
    tokenActivity.level,
    tokenActivity.timestamp,
    tokenActivity.token,
    tokenActivity.from,
    tokenActivity.to,
    tokenActivity.amount,
    tokenActivity.transactionId,
    tokenActivity.transactionHash,
    tokenActivity.originationId,
    tokenActivity.migrationId
) {
    fun addHash(hash: String?): TypedTokenActivity {
        return TypedTokenActivity(
            type = this.type,
            tokenActivity = TokenActivity(
                this.tokenActivity.id,
                this.tokenActivity.level,
                this.tokenActivity.timestamp,
                this.tokenActivity.token,
                this.tokenActivity.from,
                this.tokenActivity.to,
                this.tokenActivity.amount,
                this.tokenActivity.transactionId,
                hash,
                this.tokenActivity.originationId,
                this.tokenActivity.migrationId
            )
        )
    }
}

