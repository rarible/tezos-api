package com.rarible.tzkt.filters

import com.squareup.moshi.Json

interface NullFilter {
    @Json(name = "null")
    val `null`: Boolean?

    fun getFilter(): String

    fun getFilterValue(): String
}