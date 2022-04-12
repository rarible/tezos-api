package com.rarible.tzkt.wrapper

import org.junit.jupiter.api.Test

class TzktWrapperCollectionsTests {

    @Test
    fun contextLoads() {
    }

    /**
     * COLLECTIONS API TESTS
     */
    @Test
    fun getAllCollections() {
        val size = 10
        var continuation = 0L
        var collections = getAllCollections(size, continuation)
        assert(collections.size == size)
        var prevId = 0
        collections.forEach{
            assert(it.kind == "asset")
            assert(it.firstActivity!! >= prevId)
            prevId = it.firstActivity!!
        }
        prevId = 0
        val lastId = collections.last().firstActivity!!.toLong()
        continuation = lastId
        collections = getAllCollections(size, continuation)
        collections.forEach{
            assert(it.kind == "asset")
            assert(it.firstActivity!! >= prevId)
            prevId = it.firstActivity!!
        }
        assert(collections.first().firstActivity!!.toLong() > lastId)
    }

    @Test
    fun getCollectionById() {
        var address = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var collection = getCollectionById(address)
        assert(collection.address == address)
        assert(collection.kind == "asset")
    }
}
