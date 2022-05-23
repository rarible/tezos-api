package com.rarible.dipdup.client.model

import com.rarible.dipdup.client.core.model.DipDupActivity

data class DipDupActivitiesPage(
    val activities: List<DipDupActivity> = emptyList(),
    val continuation: String? = null
)
