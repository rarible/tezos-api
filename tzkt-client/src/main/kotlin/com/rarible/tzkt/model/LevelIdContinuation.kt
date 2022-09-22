package com.rarible.tzkt.model

data class LevelIdContinuation(
    val level: Int,
    val id: Long
) {

    override fun toString(): String {
        return "${level}_${id}"
    }

    companion object {
        fun parse(value: String?): LevelIdContinuation? {
            return value?.let {
                val parsedContinuation = value.split("_")
                LevelIdContinuation(parsedContinuation[0].toInt(), parsedContinuation[1].toLong())
            }
        }
    }
}
