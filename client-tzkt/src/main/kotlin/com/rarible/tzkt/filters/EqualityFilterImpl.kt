package com.rarible.tzkt.filters

import com.squareup.moshi.Json

class EqualityFilterImpl: EqualityFilter {
    /* **Equal** filter mode (optional, i.e. `param.eq=123` is the same as `param=123`). \\ Specify an account type to get items where the specified field is equal to the specified value.  Example: `?type=delegate`. */
    @Json(name = "eq")
    override var eq: Any? = null

    /* **Not equal** filter mode. \\ Specify an account type to get items where the specified field is not equal to the specified value.  Example: `?type.ne=contract`. */
    @Json(name = "ne")
    override var ne: Any? = null

    override fun getFilter(): String {
        return if(eq != null){
            ".eq"
        } else if(ne != null){
            ".ne"
        } else {
            ""
        }
    }

    override fun getFilterValue(): String {
        return if (eq != null) {
            eq.toString()
        } else if (ne != null) {
            ne.toString()
        } else {
            ""
        }
    }
}