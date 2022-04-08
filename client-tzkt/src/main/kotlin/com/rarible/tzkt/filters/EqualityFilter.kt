package com.rarible.tzkt.filters

import com.squareup.moshi.Json

interface EqualityFilter {
    /* **Equal** filter mode (optional, i.e. `param.eq=123` is the same as `param=123`). \\ Specify an account type to get items where the specified field is equal to the specified value.  Example: `?type=delegate`. */
    @Json(name = "eq")
    val eq: String?

    /* **Not equal** filter mode. \\ Specify an account type to get items where the specified field is not equal to the specified value.  Example: `?type.ne=contract`. */
    @Json(name = "ne")
    val ne: String?

    fun getFilter(): String

    fun getFilterValue(): String
}