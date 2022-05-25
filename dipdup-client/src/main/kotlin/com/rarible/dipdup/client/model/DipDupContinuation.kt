package com.rarible.dipdup.client.model

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID
import java.util.regex.Pattern

data class DipDupContinuation(
    val date: OffsetDateTime,
    val id: UUID?
) {

    override fun toString(): String {
        return "${date.toEpochSecond()}_${id}"
    }

    companion object {
        fun parse(value: String?): DipDupContinuation? {
            return value?.let {
                val (sortField, idStr) = value.split('_')
                DipDupContinuation(
                    date = Instant.ofEpochSecond(sortField.toLong()).atOffset(ZoneOffset.UTC),
                    id = idStr?.let {
                        if (isValidUUID(it))
                            UUID.fromString(it)
                        else null
                    }
                )
            }
        }

        fun isValid(value: String): Boolean {
            val raw = value.split('_')
            return raw.size == 2 && isValidUUID(raw.last())
        }

        fun isValidUUID(str: String?): Boolean {
            return if (str == null) {
                false
            } else UUID_REGEX_PATTERN.matcher(str).matches()
        }

        private val UUID_REGEX_PATTERN: Pattern =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$")
    }

}
