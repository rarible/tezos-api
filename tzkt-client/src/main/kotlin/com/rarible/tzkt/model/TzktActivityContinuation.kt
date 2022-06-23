package com.rarible.tzkt.model

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

data class TzktActivityContinuation(
    val date: OffsetDateTime,
    val id: Long?
) {

    override fun toString(): String {
        return "${date.toEpochSecond() * 1_000}_${id}"
    }

    companion object {
        fun parse(value: String?): TzktActivityContinuation? {
            return value?.let {
                val (sortField, idStr) = value.split('_')
                TzktActivityContinuation(
                    date = Instant.ofEpochMilli(sortField.toLong()).atOffset(ZoneOffset.UTC),
                    id = idStr?.toLongOrNull()
                )
            }
        }
        fun isValid(value: String): Boolean {
            val raw = value.split('_')
            return raw.size == 2 && raw.last().toLongOrNull()?.let { true } ?: false
        }
    }

}
