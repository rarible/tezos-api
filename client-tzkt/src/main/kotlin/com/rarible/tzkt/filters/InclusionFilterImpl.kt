package com.rarible.tzkt.filters

import com.squareup.moshi.Json

class InclusionFilterImpl: InclusionFilter {
    /* **In list** (any of) filter mode. \\ Specify a comma-separated list of addresses to get items where the specified field is equal to one of the specified values.  Example: `?sender.in=tz1WnfXMPaNTB,tz1SiPXX4MYGNJND`. */
    @Json(name = "in")
    override var `in`: List<String>? = null

    /* **Not in list** (none of) filter mode. \\ Specify a comma-separated list of addresses to get items where the specified field is not equal to all the specified values.  Example: `?sender.ni=tz1WnfXMPaNTB,tz1SiPXX4MYGNJND`. */
    @Json(name = "ni")
    override val ni: List<String>? = null

    override fun getFilter(): String{
        return if(!`in`.isNullOrEmpty()){
            ".in"
        } else if (!ni.isNullOrEmpty()) {
            ".ni"
        } else {
            ""
        }
    }

    override fun getFilterValue(): String{
        var value = ""
        if(!`in`.isNullOrEmpty()){
            `in`!!.forEach {  value = "$value,$it"}
        } else if (!ni.isNullOrEmpty()) {
            ni!!.forEach {  value = "$value,$it"}
        } else {
            ""
        }
        return value
    }
}