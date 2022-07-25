package com.rarible.tzkt.model

import java.time.Instant

data class TimestampItemIdContinuation(
    val date: Instant,
    val id: String
) : Continuation<TimestampItemIdContinuation>  {

    override fun toString(): String {
        return "${date.toEpochMilli()}_${id}"
    }

    companion object {
        fun parse(str: String?): TimestampItemIdContinuation? {
            val pair = Continuation.splitBy(str, "_") ?: return null
            val timestamp = pair.first
            val id = pair.second
            return TimestampItemIdContinuation(Instant.ofEpochMilli(timestamp.toLong()), id)
        }
    }
}
