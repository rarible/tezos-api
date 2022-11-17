package com.rarible.dipdup.client.model

import com.rarible.dipdup.client.core.util.isValidUUID
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

data class DipDupActivityContinuation(
    val date: OffsetDateTime,
    val id: String
) {

    override fun toString(): String {
        return "${date.toEpochSecond() * 1_000}_${id}"
    }

    companion object {
        fun parse(value: String?): DipDupActivityContinuation? {
            return value?.let {
                val (sortField, idStr) = value.split('_')
                DipDupActivityContinuation(
                    date = Instant.ofEpochMilli(sortField.toLong()).atOffset(ZoneOffset.UTC),
                    id = idStr
                )
            }
        }

        fun isIdValidLong(value: String): Boolean {
            val raw = value.split('_')
            val parsed = raw.last().toLongOrNull()
            return parsed != null
        }

        fun isIdValidUUID(value: String): Boolean {
            val raw = value.split('_')
            return isValidUUID(raw.last())
        }
    }
}
