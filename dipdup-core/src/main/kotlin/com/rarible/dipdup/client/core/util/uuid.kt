package com.rarible.dipdup.client.core.util

import java.util.regex.Pattern

fun isValidUUID(str: String?): Boolean {
    return if (str == null) {
        false
    } else UUID_REGEX_PATTERN.matcher(str).matches()
}

private val UUID_REGEX_PATTERN: Pattern =
    Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$")
