package com.rarible.dipdup.client.model

import com.rarible.dipdup.client.core.util.isValidUUID
import com.rarible.dipdup.client.exception.WrongArgument
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID

data class DipDupContinuation(
    val date: OffsetDateTime,
    val id: UUID?
) {

    override fun toString(): String {
        return "${date.toEpochSecond() * 1_000}_${id}"
    }

    companion object {
        fun parse(value: String?): DipDupContinuation? {
            return value?.let {
                val (sortField, idStr) = value.split('_')
                DipDupContinuation(
                    date = Instant.ofEpochMilli(sortField.toLong()).atOffset(ZoneOffset.UTC),
                    id = idStr?.let {
                        if (isValidUUID(it))
                            UUID.fromString(it)
                        else {
                            throw WrongArgument("Illegal continuation: $value")
                        }
                    }
                )
            }
        }

        fun isValid(value: String): Boolean {
            val raw = value.split('_')
            return raw.size == 2 && isValidUUID(raw.last())
        }
    }

}
