package com.rarible.dipdup.client.model

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

data class DipDupActivityContinuation(
    val date: OffsetDateTime,
    val id: Int?
) {

    override fun toString(): String {
        return "${date.toEpochSecond() * 1_000}_${id.toString()}"
    }

    companion object {
        fun parse(value: String?): DipDupActivityContinuation? {
            return value?.let {
                val (sortField, idStr) = value.split('_')
                DipDupActivityContinuation(
                    date = Instant.ofEpochMilli(sortField.toLong()).atOffset(ZoneOffset.UTC),
                    id = idStr.toInt()
                )
            }
        }

        fun isValid(value: String): Boolean {
            val raw = value.split('_')
            val parsed = raw.last().toIntOrNull()
            return parsed != null
        }
    }

}
