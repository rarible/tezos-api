package com.rarible.dipdup.client.core.util

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object MetaUtils {
    fun mapper() : ObjectMapper {
        return jacksonObjectMapper().configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
    }
}
