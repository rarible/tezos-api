package com.rarible.tzkt.filters

import com.squareup.moshi.Json

class AnyAllFilterImpl: AnyAllFilter {
    /* **In list** (any of) filter mode. \\ Specify a comma-separated list of addresses to get items where the specified field is equal to one of the specified values.  Example: `?sender.in=tz1WnfXMPaNTB,tz1SiPXX4MYGNJND`. */
    @Json(name = "any")
    override var any: List<String>? = null

    /* **Not in list** (none of) filter mode. \\ Specify a comma-separated list of addresses to get items where the specified field is not equal to all the specified values.  Example: `?sender.ni=tz1WnfXMPaNTB,tz1SiPXX4MYGNJND`. */
    @Json(name = "all")
    override var all: List<String>? = null

    override fun getFilter(): String{
        return if(!any.isNullOrEmpty()){
            ".any"
        } else if (!all.isNullOrEmpty()) {
            ".all"
        } else {
            ""
        }
    }

    override fun getFilterValue(): String{
        var value = ""
        if(!any.isNullOrEmpty()){
           value =  any!!.joinToString(",")
        } else if (!all.isNullOrEmpty()) {
            value = all!!.joinToString(",")
        } else {
            ""
        }
        return value
    }

    fun setAnyList(value: List<String>){
        any = value
        all = null
    }

    fun setAllList(value: List<String>){
        all = value
        any = null
    }
}