package com.rarible.tzkt.filters

import com.squareup.moshi.Json

class EqualityFilterImpl: EqualityFilter {
    /* **Equal** filter mode (optional, i.e. `param.eq=123` is the same as `param=123`). \\ Specify an account type to get items where the specified field is equal to the specified value.  Example: `?type=delegate`. */
    @Json(name = "eq")
    override var eq: String? = null

    /* **Not equal** filter mode. \\ Specify an account type to get items where the specified field is not equal to the specified value.  Example: `?type.ne=contract`. */
    @Json(name = "ne")
    override var ne: String? = null

    override fun getFilter(): String {
        return if(!eq.isNullOrEmpty()){
            ".eq"
        } else if(!ne.isNullOrEmpty()){
            ".ne"
        } else {
            ""
        }
    }

    override fun getFilterValue(): String {
        return if (!eq.isNullOrEmpty()) {
            eq.toString()
        } else if (!ne.isNullOrEmpty()) {
            ne.toString()
        } else {
            ""
        }
    }

    fun setNE(value: String){
        ne = value
        eq = null
    }

    fun setEQ(value: String){
        eq = value
        ne = null
    }
}