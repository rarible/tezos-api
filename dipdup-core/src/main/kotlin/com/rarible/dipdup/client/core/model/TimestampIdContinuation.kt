package com.rarible.dipdup.client.core.model

import java.time.Instant

data class TimestampIdContinuation(
    val date: Instant,
    val id: String
) : Continuation<TimestampIdContinuation> {

    override fun toString(): String {
        return "${date.toEpochMilli()}_${id}"
    }

    companion object {
        fun parse(str: String?): TimestampIdContinuation {
            val pair = Continuation.splitBy(str, "_") ?: throw RuntimeException("Wrong continuation ${str}")
            val timestamp = pair.first
            val id = pair.second
            return TimestampIdContinuation(Instant.ofEpochMilli(timestamp.toLong()), id)
        }
    }
}
