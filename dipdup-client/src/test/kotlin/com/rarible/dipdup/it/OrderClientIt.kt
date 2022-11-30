package com.rarible.dipdup.it

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.OrderClient
import com.rarible.dipdup.client.core.model.Asset
import com.rarible.dipdup.client.core.model.TezosPlatform
import com.rarible.dipdup.client.model.DipDupContinuation
import com.rarible.dipdup.client.model.DipDupOrderSort
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import java.util.*
import java.util.stream.Stream

// this test will be disabled on jenkins
@DisabledOnOs(OS.LINUX)
class OrderClientIt {

    companion object {

        @JvmStatic
        fun allPlatforms() = Stream.of(
            Arguments.of(TezosPlatform.HEN),
            Arguments.of(TezosPlatform.OBJKT_V1),
            Arguments.of(TezosPlatform.OBJKT_V2),
            Arguments.of(TezosPlatform.FXHASH_V1),
            Arguments.of(TezosPlatform.FXHASH_V2),
            Arguments.of(TezosPlatform.RARIBLE_V1),
            Arguments.of(TezosPlatform.RARIBLE_V2)
        )
    }

    @Nested
    class Dev {
        val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://dev-tezos-indexer.rarible.org/v1/graphql").build() }
        val orderClient = OrderClient(client)

        @Test
        fun `should return currency of bid orders from dev`() = runBlocking<Unit> {
            val currencies = orderClient.getBidOrdersCurrenciesByItem("KT1Uke8qc4YTfP41dGuoGC8UsgRyCtyvKPLA", "1083")
            assertThat(currencies).hasSize(1)
        }

        @Test
        fun `should return bid orders from dev`() = runBlocking<Unit> {
            val page = orderClient.getOrdersByItem("KT1Uke8qc4YTfP41dGuoGC8UsgRyCtyvKPLA", "1083", null, "XTZ",
                emptyList(), listOf(TezosPlatform.RARIBLE_V2), true, 10, null)
            assertThat(page.orders).hasSize(1)
        }
    }

    @Nested
    class Testnet {
        val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://testnet-tezos-indexer.rarible.org/v1/graphql").build() }
        val orderClient = OrderClient(client)

        @Test
        fun `should return orders by item from testnet`() = runBlocking<Unit> {
            val page = orderClient.getOrdersByItem("KT1Uke8qc4YTfP41dGuoGC8UsgRyCtyvKPLA", "1027", null, "XTZ",
                emptyList(), listOf(TezosPlatform.RARIBLE_V2, TezosPlatform.OBJKT_V1, TezosPlatform.OBJKT_V2), false, 1, null)
            assertThat(page.orders).hasSize(1)
        }

        @Test
        fun `should return orders by maker from testnet`() = runBlocking<Unit> {
            val page = orderClient.getOrdersByMakers(listOf("tz1Mxsc66En4HsVHr6rppYZW82ZpLhpupToC"), listOf(),
                listOf(TezosPlatform.OBJKT_V1, TezosPlatform.OBJKT_V2), false, 1, null)
            assertThat(page.orders).hasSize(1)
        }
    }

    @Nested
    @Disabled
    class Prod {

        val client: ApolloClient = runBlocking { ApolloClient.Builder().serverUrl("https://tezos-indexer.rarible.org/v1/graphql").build() }
        val orderClient = OrderClient(client)

        @ParameterizedTest
        @MethodSource("allPlatforms")
        fun `should have orders`(platform: TezosPlatform) = runBlocking<Unit> {
            orderClient.getOrdersAll(listOf(), listOf(platform), DipDupOrderSort.LAST_UPDATE_DESC, false, 1, null).apply {
                assertThat(orders).hasSize(1)

                val order = orders.first()
                val make = order.make.assetType as Asset.MT
                val take = order.take.assetType as Asset.XTZ
                orderClient.getOrdersByItem(
                    make.contract,
                    make.tokenId.toString(),
                    null,
                    "XTZ",
                    listOf(),
                    listOf(platform),
                    false,
                    1,
                    null
                ).apply {
                    assertThat(orders).hasSize(1)
                }

                orderClient.getOrdersByMakers(listOf(order.maker), listOf(), listOf(platform), false, 1, null).apply {
                    assertThat(orders).hasSize(1)
                }
            }
        }

        @Test
        fun `should return currency for legacy order`() = runBlocking<Unit> {
            val page = orderClient.getSellOrdersCurrenciesByItem("KT18pVpRXKPY2c4U2yFEGSH3ZnhB2kL8kwXS", "46075")
            assertThat(page).hasSize(1)
        }

        @Test
        fun `should have for legacy order`() = runBlocking<Unit> {
            val order = orderClient.getOrderById("7bf9b36a-4aab-55f5-bd2f-b0387092f0ca")
            assertThat(order.fill).isEqualTo(BigDecimal("26.000000000000000000000000000000000000"))
        }

        @Test
        fun `should have collection asset type`() = runBlocking<Unit> {
            val order = orderClient.getOrderById("3d331b52-472c-5484-84a6-e58794df09b9")
            assertThat(order.take.assetType).isEqualTo(
                Asset.COLLECTION(
                    Asset.COLLECTION_NAME,
                    "KT1Uke8qc4YTfP41dGuoGC8UsgRyCtyvKPLA"
                )
            )
        }

        @Test
        fun `iterate all order without duplication`() = runBlocking<Unit> {
            var continuation: String? = null
            var count = 0
            do {
                val orders = orderClient.getOrdersAll(
                    listOf(),
                    listOf(TezosPlatform.HEN),
                    DipDupOrderSort.LAST_UPDATE_ASC,
                    false,
                    1000, continuation
                )
                continuation = orders.continuation
                count += orders.orders.size
                DipDupContinuation.parse(continuation)?.let {
                    assertThat(UUID.fromString(orders.orders.first().id)).isNotEqualTo(it.id)
                }
                println("continuation: ${continuation}")

            } while (continuation != null)
            println("Count: ${count}")
        }

        @Test
        fun `should return legacy data`() = runBlocking<Unit> {
            val page = orderClient.getOrdersByIds(listOf("0c09883b-ecd1-5c3b-a56c-5da346dd111a"))
            assertThat(page).hasSize(1)
            assertThat(page.first().legacyData).isNotNull

            val order = orderClient.getOrderById("0c09883b-ecd1-5c3b-a56c-5da346dd111a")
            assertThat(order.legacyData).isNotNull
        }
    }
}
