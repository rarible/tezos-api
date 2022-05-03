package com.rarible.tzkt.tokens

import com.rarible.tzkt.client.BaseClientTests
import com.rarible.tzkt.client.BigMapKeyClient
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BidouTests : BaseClientTests() {

    val bigMapKeyClient = BigMapKeyClient(client)

    val `8x8` = "KT1MxDwChiDwd6WBVs24g1NjERUoK622ZEFp"
    //val `24x24` = "KT1TR1ErEQPTdtaJ7hbvKTJSa1tsGnHGZTpf"

    @Test
    fun `should correctly fetch and parse 8Bidou data`() = runBlocking<Unit> {
        mock("""
            {
            	"id": 19575683,
            	"active": true,
            	"hash": "expruDGbwWN7Jqay8SnvBhQQxvbYXtxjNnUXYwm9sExfKo42XqtubG",
            	"key": "69",
            	"value": {
            		"rgb": "989898916d91b37db3000000ce62ced925d9e947e9fd01fd6b956b979b97ffccbcffccbc000000b89286d729d7e74ae779b879679767ffccbcffccbcffccbcffccbcca67cad32bd341bf4177bb77ffccbc000000ffccbc000000ad53adc86ac859d759ffccbcffccbcffccbcffccbcffccbcaa86aaa955a917e917e6ff00ffccbcffccbcffccbcffccbc837d83a989a93af63a13eb13ffccbc423430423430ffccbc8ba68b7f7f7f00ff0039f839ffccbcffccbcffccbc6ec46e59a75989a989",
            		"creater": "tz2QhmKtUWRyArfaqfBedvVdidgKpCcckMXV",
            		"token_id": "69",
            		"token_name": "f09f928038c9aec9a8c8b6d684ca8ad5bcd3bcf09f9280",
            		"creater_name": "67757275677572756879656e61",
            		"token_description": "f09fa498e29aa1efb88f"
            	},
            	"firstLevel": 2139139,
            	"lastLevel": 2139139,
            	"updates": 1
            }
        """.trimIndent())

        val handler = BidouHandler(bigMapKeyClient, `8x8`)
        val data = handler.getData("69")
        assertThat(data).isNotNull
        assertThat(data!!.rgb).isEqualTo("989898916d91b37db3000000ce62ced925d9e947e9fd01fd6b956b979b97ffccbcffccbc000000b89286d729d7e74ae779b879679767ffccbcffccbcffccbcffccbcca67cad32bd341bf4177bb77ffccbc000000ffccbc000000ad53adc86ac859d759ffccbcffccbcffccbcffccbcffccbcaa86aaa955a917e917e6ff00ffccbcffccbcffccbcffccbc837d83a989a93af63a13eb13ffccbc423430423430ffccbc8ba68b7f7f7f00ff0039f839ffccbcffccbcffccbc6ec46e59a75989a989")
        assertThat(data!!.creator).isEqualTo("tz2QhmKtUWRyArfaqfBedvVdidgKpCcckMXV")
        assertThat(data!!.tokenId).isEqualTo("69")
        assertThat(data!!.tokenName).isEqualTo("f09f928038c9aec9a8c8b6d684ca8ad5bcd3bcf09f9280")
        assertThat(data!!.creatorName).isEqualTo("67757275677572756879656e61")
        assertThat(data!!.tokenDescription).isEqualTo("f09fa498e29aa1efb88f")
    }
}
