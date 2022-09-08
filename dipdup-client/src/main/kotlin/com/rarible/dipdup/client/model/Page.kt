package com.rarible.dipdup.client.model

import com.rarible.dipdup.client.core.model.DipDupEntity
import com.rarible.dipdup.client.core.model.TimestampIdContinuation

data class Page<T : DipDupEntity>(
    val items: List<T>,
    val continuation: String? = null
) {
    companion object {
        fun <T: DipDupEntity> of(items: List<T>, limit: Int) : Page<T> {
            val continuation = when {
                items.size < limit -> null
                else -> {
                    val last = items[limit - 1]
                    TimestampIdContinuation(last.updated, last.id).toString()
                }
            }
            return Page(items, continuation)
        }
    }
}
