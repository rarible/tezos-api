package com.rarible.dipdup.client.model

import com.rarible.dipdup.client.core.util.isValidUUID
import com.rarible.dipdup.client.exception.WrongArgument
import java.math.BigDecimal
import java.util.UUID

data class DipDupCurrencyContinuation(
    val value: BigDecimal?,
    val id: UUID?
) {

    override fun toString(): String {
        return "${value}_${id}"
    }

    companion object {
        fun parse(value: String?): DipDupCurrencyContinuation? {
            return value?.let {
                val (sortField, idStr) = value.split('_')
                DipDupCurrencyContinuation(
                    value = BigDecimal(sortField),
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
    }

}
