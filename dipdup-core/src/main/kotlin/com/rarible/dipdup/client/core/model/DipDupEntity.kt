package com.rarible.dipdup.client.core.model

import java.time.Instant

interface DipDupEntity {
    val id: String
    val updated: Instant
}
