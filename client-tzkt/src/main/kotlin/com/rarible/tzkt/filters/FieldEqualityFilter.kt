package com.rarible.tzkt.filters

import com.squareup.moshi.Json

interface FieldEqualityFilter {
    /* **Equal to another field** filter mode. \\ Specify a field name to get items where the specified fields are equal.  Example: `?sender.eqx=target`. */
    @Json(name = "eqx")
    val eqx: String?

    /* **Not equal to another field** filter mode. \\ Specify a field name to get items where the specified fields are not equal.  Example: `?sender.nex=initiator`. */
    @Json(name = "nex")
    val nex: String?

    fun getFilter(): String

    fun getFilterValue(): String
}