package com.rarible.tzkt.filters

import com.squareup.moshi.Json

interface InclusionFilter {
    /* **In list** (any of) filter mode. \\ Specify a comma-separated list of addresses to get items where the specified field is equal to one of the specified values.  Example: `?sender.in=tz1WnfXMPaNTB,tz1SiPXX4MYGNJND`. */
    @Json(name = "in")
    val `in`: List<String>?

    /* **Not in list** (none of) filter mode. \\ Specify a comma-separated list of addresses to get items where the specified field is not equal to all the specified values.  Example: `?sender.ni=tz1WnfXMPaNTB,tz1SiPXX4MYGNJND`. */
    @Json(name = "ni")
    val ni: List<String>?

    fun getFilter(): String

    fun getFilterValue(): String
}