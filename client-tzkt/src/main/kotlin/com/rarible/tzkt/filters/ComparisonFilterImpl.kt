package com.rarible.tzkt.filters

import com.squareup.moshi.Json

class ComparisonFilterImpl: ComparisonFilter {

    /* **Greater than** filter mode. \\ Specify a datetime to get items where the specified field is greater than the specified value.  Example: `?timestamp.gt=2020-02-20T02:40:57Z`. */
    @Json(name = "gt")
    override var gt:String? = null

    /* **Greater or equal** filter mode. \\ Specify a datetime to get items where the specified field is greater than equal to the specified value.  Example: `?timestamp.ge=2020-02-20T02:40:57Z`. */
    @Json(name = "ge")
    override var ge:String? = null

    /* **Less than** filter mode. \\ Specify a datetime to get items where the specified field is less than the specified value.  Example: `?timestamp.lt=2020-02-20T02:40:57Z`. */
    @Json(name = "lt")
    override var lt:String? = null

    /* **Less or equal** filter mode. \\ Specify a datetime to get items where the specified field is less than or equal to the specified value.  Example: `?timestamp.le=2020-02-20T02:40:57Z`. */
    @Json(name = "le")
    override var le:String? = null

    override fun getFilter(): String {
        return if(!gt.isNullOrEmpty()){
            ".gt"
        } else if(!ge.isNullOrEmpty()){
            ".ge"
        } else if(!lt.isNullOrEmpty()){
            ".lt"
        } else if(!le.isNullOrEmpty()){
            ".le"
        } else {
            ""
        }
    }

    override fun getFilterValue(): String {
        return if(!gt.isNullOrEmpty()){
            gt!!
        } else if(!ge.isNullOrEmpty()){
            ge!!
        } else if(!lt.isNullOrEmpty()){
            lt!!
        } else if(!le.isNullOrEmpty()){
            le!!
        } else {
            ""
        }
    }


}