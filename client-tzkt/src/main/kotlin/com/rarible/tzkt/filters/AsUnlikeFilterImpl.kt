package com.rarible.tzkt.filters

import com.squareup.moshi.Json

class AsUnlikeFilterImpl: AsUnlikeFilter {
    /* **Same as** filter mode. \\ Specify a string template to get items where the specified field matches the specified template. \\ This mode supports wildcard `*`. Use `\\*` as an escape symbol.  Example: `?parameter.as=*mid*` or `?parameter.as=*end`. */
    @Json(name = "as")
    override var `as`: String? = null

    /* **Unlike** filter mode. \\ Specify a string template to get items where the specified field doesn't match the specified template. This mode supports wildcard `*`. Use `\\*` as an escape symbol.  Example: `?parameter.un=*mid*` or `?parameter.un=*end`. */
    @Json(name = "un")
    override var un: String? = null

    override fun getFilter(): String {
        return if(!`as`.isNullOrEmpty()){
            ".as"
        } else if(!un.isNullOrEmpty()){
            ".un"
        } else {
            ""
        }
    }

    override fun getFilterValue(): String {
        return if (`as` != null) {
            `as`!!
        } else if ( un != null) {
            un!!
        } else {
            ""
        }
    }
}