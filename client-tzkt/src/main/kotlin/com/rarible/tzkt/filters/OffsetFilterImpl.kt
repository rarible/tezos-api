package com.rarible.tzkt.filters

import com.squareup.moshi.Json

class OffsetFilterImpl: OffsetFilter {
    /* **Elements** offset mode (optional, i.e. `offset.el=123` is the same as `offset=123`). \\ Skips specified number of elements.  Example: `?offset=100`. */
    @Json(name = "el")
    override val el: Int? = null

    /* **Page** offset mode. \\ Skips `page * limit` elements. This is a classic pagination.  Example: `?offset.pg=1`. */
    @Json(name = "pg")
    override val pg: Int? = null

    /* **Cursor** offset mode. \\ Skips all elements with the `cursor` before (including) the specified value. Cursor is a field used for sorting, e.g. `id`. Avoid using this offset mode with non-unique or non-sequential cursors such as `amount`, `balance`, etc.  Example: `?offset.cr=45837`. */
    @Json(name = "cr")
    override val cr: Long? = null
}