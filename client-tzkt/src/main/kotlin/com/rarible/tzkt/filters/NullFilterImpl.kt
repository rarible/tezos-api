package com.rarible.tzkt.filters

import com.squareup.moshi.Json

class NullFilterImpl: NullFilter {
    @Json(name = "null")
    override val `null`: Boolean? = null

    override fun getFilter(): String {
        return if(`null` != null){
           ".null"
        } else {
            ""
        }
    }

    override fun getFilterValue(): String {
        return if(`null` != null && `null` == true){
            "true"
        } else if(`null` != null && `null` == false) {
            "false"
        } else {
            ""
        }
    }

}