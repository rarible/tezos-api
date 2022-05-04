package com.rarible.tzkt.tokens

data class BidouProperties(
    val rgb: String,
    val creator: String,
    val tokenId: String,
    val tokenName: String,
    val creatorName: String,
    val tokenDescription: String
) {
    companion object {
        fun fromMap(map: LinkedHashMap<String, String>): BidouProperties? {
            return if (
                map.containsKey("rgb") &&
                map.containsKey("creater") &&
                map.containsKey("token_id") &&
                map.containsKey("token_name") &&
                map.containsKey("creater_name") &&
                map.containsKey("token_description")
            ) {
                BidouProperties(
                    map["rgb"]!!,
                    map["creater"]!!,
                    map["token_id"]!!,
                    map["token_name"]!!,
                    map["creater_name"]!!,
                    map["token_description"]!!
                )
            } else {
                null
            }
        }
    }
}
