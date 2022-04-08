package com.rarible.tzkt.filters

import com.squareup.moshi.Json

class FieldEqualityFilterImpl: FieldEqualityFilter {
    /* **Equal to another field** filter mode. \\ Specify a field name to get items where the specified fields are equal.  Example: `?sender.eqx=target`. */
    @Json(name = "eqx")
    override var eqx: String? = null

    /* **Not equal to another field** filter mode. \\ Specify a field name to get items where the specified fields are not equal.  Example: `?sender.nex=initiator`. */
    @Json(name = "nex")
    override var nex: String?  = null

    override fun getFilter(): String {
        return if(!eqx.isNullOrEmpty()){
            ".eqx"
        } else if(!nex.isNullOrEmpty()){
            ".nex"
        } else {
            ""
        }
    }

    override fun getFilterValue(): String {
        return if (!eqx.isNullOrEmpty()) {
            eqx!!
        } else if (!nex.isNullOrEmpty()) {
            nex!!
        } else {
            ""
        }
    }
}