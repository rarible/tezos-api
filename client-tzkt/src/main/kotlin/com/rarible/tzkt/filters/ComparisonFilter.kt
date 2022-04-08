package com.rarible.tzkt.filters

import com.squareup.moshi.Json

interface ComparisonFilter {

    /* **Greater than** filter mode. \\ Specify a datetime to get items where the specified field is greater than the specified value.  Example: `?timestamp.gt=2020-02-20T02:40:57Z`. */
    @Json(name = "gt")
    val gt:String?

    /* **Greater or equal** filter mode. \\ Specify a datetime to get items where the specified field is greater than equal to the specified value.  Example: `?timestamp.ge=2020-02-20T02:40:57Z`. */
    @Json(name = "ge")
    val ge:String?

    /* **Less than** filter mode. \\ Specify a datetime to get items where the specified field is less than the specified value.  Example: `?timestamp.lt=2020-02-20T02:40:57Z`. */
    @Json(name = "lt")
    val lt:String?

    /* **Less or equal** filter mode. \\ Specify a datetime to get items where the specified field is less than or equal to the specified value.  Example: `?timestamp.le=2020-02-20T02:40:57Z`. */
    @Json(name = "le")
    val le:String?

    fun getFilter(): String

    fun getFilterValue(): String
}