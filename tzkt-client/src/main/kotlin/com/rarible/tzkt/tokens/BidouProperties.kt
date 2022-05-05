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
            return if (map.keys.containsAll(
                    listOf(
                        "rgb",
                        "creater",
                        "token_id",
                        "token_name",
                        "creater_name",
                        "token_description"
                    )
                )
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
