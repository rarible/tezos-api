package com.rarible.dipdup.client.core.model

enum class TezosPlatform(val value: String) {
    HEN("Hen"),
    OBJKT("Objkt"),
    OBJKT_V2("Objkt_v2"),
    RARIBLE("Rarible");

    companion object {
        fun get(value: String) = values().firstOrNull { it.value.equals(value) } ?: throw RuntimeException("Platform $value is unknown")
    }
}
