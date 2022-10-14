package com.rarible.dipdup.client.model

data class Page<T>(
    val items: List<T>,
    val continuation: String? = null
) {
    companion object {
        fun <T> of(items: List<T>, limit: Int, handler: (T) -> Any) : Page<T> {
            val continuation = when {
                items.size < limit -> null
                else -> {
                    val last = items[limit - 1]
                    handler(last).toString()
                }
            }
            return Page(items, continuation)
        }
    }
}
