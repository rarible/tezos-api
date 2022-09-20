package com.rarible.dipdup.client

import com.apollographql.apollo3.ApolloClient
import com.rarible.dipdup.client.core.model.DipDupCollection
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class CollectionClientFt : BaseClientFt() {

    // For dev testing
//    val local: ApolloClient =
//        runBlocking { ApolloClient.Builder().serverUrl("https://dev-tezos-indexer.rarible.org/v1/graphql").build() }
//    val collectionClient = CollectionClient(local)

   val collectionClient = CollectionClient(client)

    @Test
    fun `should return collection`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "collection_with_meta": [
                  {
                    "__typename": "collection",
                    "owner": "tz2WFEmgnWqFY6FWBMFgrS4MW4c3pJRS8nzU",
                    "metadata": {
                      "name": "Test Taquito FA2 token Factory",
                      "source": {
                        "tools": [
                          "FA2 Token Factory"
                        ],
                        "location": "https://www.github.com/claudebarde"
                      },
                      "license": {
                        "name": "MIT"
                      },
                      "interfaces": [
                        "TZIP-012"
                      ],
                      "description": "This is a test to retrieve tokens metadata when they are located in the storage of the contract in the big map %token_metadata"
                    },
                    "contract": "KT1Pz65ssbPF7Zv9Dh7ggqUkgAYNSuJ9iia7",
                    "db_updated_at": "2022-09-12T16:08:47.783243+00:00"
                  }
                ]
              }
            }"""
        )

        val collection = collectionClient.getCollectionById("KT1Pz65ssbPF7Zv9Dh7ggqUkgAYNSuJ9iia7")
        assertThat(collection).isEqualTo(
            DipDupCollection(
                id = "KT1Pz65ssbPF7Zv9Dh7ggqUkgAYNSuJ9iia7",
                owner = "tz2WFEmgnWqFY6FWBMFgrS4MW4c3pJRS8nzU",
                updated = OffsetDateTime.parse("2022-09-12T16:08:47.783243Z").toInstant(),
                name = "Test Taquito FA2 token Factory",
                symbol = null,
                standard = null,
                minters = emptyList()
            )
        )
    }

    @Test
    fun `should return collections all`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "collection_with_meta": [
                  {
                    "__typename": "collection",
                    "owner": "tz1VLnrVYrMtLHRUfLV594uvzSthZ5w7wXqE",
                    "metadata": null,
                    "contract": "KT1TfDvhubeqKqY361ARAw42H2bjL2ZE1kJY",
                    "db_updated_at": "2022-09-19T07:42:00.607048+00:00"
                  },
                  {
                    "__typename": "collection",
                    "owner": "tz1VLnrVYrMtLHRUfLV594uvzSthZ5w7wXqE",
                    "metadata": null,
                    "contract": "KT1R9eGJniFyPmCtwFqv5K7U49pciVUD2vPH",
                    "db_updated_at": "2022-09-19T07:31:00.559048+00:00"
                  }
                ]
              }
            }"""
        )

        val collections = collectionClient.getCollectionsAll(limit = 2, continuation = null, sortAsc = false)
        assertThat(collections.items).hasSize(2)
        assertThat(collections.continuation).isEqualTo("1663572660559_KT1R9eGJniFyPmCtwFqv5K7U49pciVUD2vPH")
    }

    @Test
    fun `should return collections desc`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "collection_with_meta": [
                  {
                    "__typename": "collection",
                    "owner": "tz1VSUr8wwNhLAzempoch5d6hLRiTh8Cjcjb",
                    "metadata": null,
                    "contract": "KT1D1QnovAbgP4L4G1qcUC2KNMhgjCVgvV7R",
                    "db_updated_at": "2022-09-19T10:08:00.140312+00:00"
                  },
                  {
                    "__typename": "collection",
                    "owner": "KT1CZdyP9eccRSazfKrcmKz596dStkSJnBXA",
                    "metadata": null,
                    "contract": "KT1KvFrpqm2mdbZ6dcTXCkqtoAfR7hq8r1VD",
                    "db_updated_at": "2022-09-19T10:02:00.127594+00:00"
                  }
                ]
              }
            }"""
        )

        val collections = collectionClient.getCollectionsAll(limit = 2, continuation = "1663572660559_KT1R9eGJniFyPmCtwFqv5K7U49pciVUD2vPH", sortAsc = false)
        assertThat(collections.items).hasSize(2)
        assertThat(collections.continuation).isEqualTo("1663581720127_KT1KvFrpqm2mdbZ6dcTXCkqtoAfR7hq8r1VD")
    }

    @Test
    fun `should return collections asc`() = runBlocking<Unit> {
        mock(
            """{
              "data": {
                "collection_with_meta": [
                  {
                    "__typename": "collection",
                    "owner": "tz298EP2cQLjj2dtQgMtHeEXQEv4oZZNtjse",
                    "metadata": null,
                    "contract": "KT1HrsMChopx1dbDWquaozpWg5pdad9bLtZt",
                    "db_updated_at": "2022-09-09T14:42:00.365595+00:00"
                  },
                  {
                    "__typename": "collection",
                    "owner": "tz29dzvzrTa8eqGS2BVnsFUh67n8DGkXAd6t",
                    "metadata": null,
                    "contract": "KT1RL42TjespD4myAqomNdDaWBvgS1PokEZ9",
                    "db_updated_at": "2022-09-09T14:42:00.407533+00:00"
                  }
                ]
              }
            }"""
        )

        val collections = collectionClient.getCollectionsAll(limit = 2, continuation = "1663572660559_KT1R9eGJniFyPmCtwFqv5K7U49pciVUD2vPH", sortAsc = true)
        assertThat(collections.items).hasSize(2)
        assertThat(collections.continuation).isEqualTo("1662734520407_KT1RL42TjespD4myAqomNdDaWBvgS1PokEZ9")
    }
}