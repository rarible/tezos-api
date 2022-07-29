package com.rarible.tzkt.model

import java.time.Instant

data class TimestampIdContinuation(
    val date: Instant,
    val id: String
) : Continuation<TimestampIdContinuation>  {

    override fun toString(): String {
        return "${date.toEpochMilli()}_${id}"
    }

    companion object {
        fun parse(str: String?): TimestampIdContinuation? {
            val pair = Continuation.splitBy(str, "_") ?: return null
            val timestamp = pair.first
            val id = pair.second
            return TimestampIdContinuation(Instant.ofEpochMilli(timestamp.toLong()), id)
        }
    }
}
