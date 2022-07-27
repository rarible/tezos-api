package com.rarible.tzkt.utils

object Tezos {

    const val BURN_ADDRESS = "tz1burnburnburnburnburnburnburjAYjjX"
    const val NULL_ADDRESS = "tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU"

    val NULL_ADDRESSES = listOf(null, BURN_ADDRESS, NULL_ADDRESS)
    val NULL_ADDRESSES_STRING = NULL_ADDRESSES.joinToString(",")
}
