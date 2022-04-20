package com.rarible.tzkt.meta

import com.rarible.tzkt.model.Token
import com.rarible.tzkt.model.TokenAttribute
import com.rarible.tzkt.model.TokenMeta

class MetaService {

    fun meta(token: Token): TokenMeta {
        if (null != token.metadata) {
            return TokenMeta(
                name = token.metadata.get("name")?.toString() ?: TokenMeta.UNTITLED,
                description = token.metadata.get("description")?.toString(),
                image = image(token.metadata),
                attributes = attrs(token.metadata)
            )
        } else {
            return TokenMeta.EMPTY
        }
    }

    fun attrs(meta: Map<String, Any?>): List<TokenAttribute> {
        return when {
            meta.get("tags") is List<*> -> {
                (meta["tags"] as List<*>).map { TokenAttribute(key = it.toString()) }
            }
            else -> emptyList()
        }
    }

    fun image(meta: Map<String, Any?>): String? {
        return meta["displayUri"]?.toString()
    }

}
