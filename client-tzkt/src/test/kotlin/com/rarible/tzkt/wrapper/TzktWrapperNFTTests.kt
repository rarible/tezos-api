package com.rarible.tzkt.wrapper

import com.rarible.tzkt.models.Token
import org.junit.jupiter.api.Test

class TzktWrapperNFTTests {

    @Test
    fun contextLoads() {
    }

    /**
     * TOKEN API TESTS
     */
    @Test
    fun tokensGetAll() {
        val size = 10
        var continuation = 0L
        var tokens = getAllNFTs(size, continuation)
        tokens.forEach { it::class.java == Token::class.java }

        var prevId = 0
        tokens.forEach{
            assert(it.id!! >= prevId)
            prevId = it.id!!
        }
        prevId = 0
        val lastId = tokens.last().id!!.toLong()
        continuation = lastId
        tokens = getAllNFTs(size, continuation)
        tokens.forEach{
            assert(it.id!! >= prevId)
            prevId = it.id!!
        }
        assert(tokens.first().id!!.toLong() > lastId)
    }

    @Test
    fun tokensGetById() {
        var contract = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        var tokenId = "631268"
        val token = getNFTById(contract, tokenId)
        assert(token.size == 1)
        assert(token.first().contract!!.address == contract)
        assert(token.first().tokenId == tokenId)
    }

}
