package com.rarible.tzkt.utils

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun <A, B> Iterable<A>.mapAsync(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}

suspend fun <A, B> Iterable<A>.flatMapAsync(f: suspend (A) -> Iterable<B>): List<B> = coroutineScope {
    mapAsync(f).flatten()
}
