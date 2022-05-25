package com.rarible.tzkt.model

import java.lang.Integer.min

data class Page<T>(
    val items: List<T>,
    val continuation: String?
) {
    companion object {
        fun <T> Get(items: List<T>, size: Int, last: (T) -> String): Page<T> {
            val continuation = when {
                items.size >= size -> last(items.last())
                else -> null
            }
            return Page(
                items = items.subList(0, min(size, items.size)),
                continuation = continuation
            )
        }
    }
}
