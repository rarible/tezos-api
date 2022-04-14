package com.rarible.dipdup.client.core.model

enum class OrderStatus(val value: String) {
    ACTIVE("ACTIVE"),
    FILLED("FILLED"),
    CANCELLED("CANCELLED");

    companion object {
        fun get(value: String) = values().firstOrNull { it.value.equals(value) } ?: throw RuntimeException("OrderStatus $value is unknown")
    }
}
