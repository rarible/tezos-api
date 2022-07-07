package com.rarible.tzkt.client

import com.rarible.tzkt.model.CollectionType
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class CollectionClientTests : BaseClientTests() {

    val collectionClient = CollectionClient(client)

    @Test
    fun `should return collection by address`() = runBlocking<Unit> {
        mock("""
            {
            	"type": "contract",
            	"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton",
            	"kind": "asset",
            	"tzips": ["fa2"],
            	"alias": "hic et nunc NFTs",
            	"balance": 0,
            	"creator": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 141,
            	"tokenBalancesCount": 170,
            	"tokenTransfersCount": 189,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 11065912,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1365143,
            	"firstActivityTime": "2021-03-01T01:59:41Z",
            	"lastActivity": 2274952,
            	"lastActivityTime": "2022-04-12T15:04:44Z",
            	"typeHash": 603828391,
            	"codeHash": 1973375561
            }
        """.trimIndent())
        mock("""[]""") // for meta

        val address = "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton"
        val collection = collectionClient.collection(address)
        assertThat(request().path).isEqualTo("/v1/contracts/KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton")

        assertThat(collection).isNotNull
        assertThat(collection.kind).isEqualTo("asset")
        assertThat(collection.address).isEqualTo(address)
    }

    @Test
    fun `should return collections sorted by ASC with a fixed size and continuation`() = runBlocking<Unit> {
        mock("""
            [{
            	"type": "contract",
            	"address": "KT1EctCuorV2NfVb1XTQgvzJ88MQtWP8cMMv",
            	"kind": "asset",
            	"tzips": ["fa1"],
            	"alias": "StakerDAO",
            	"balance": 0,
            	"creator": {
            		"address": "tz1UCSwQgi8JyFZ5H949rgBbrFV2XsKSNdzf"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 102,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 767840,
            	"firstActivityTime": "2020-01-06T03:46:32Z",
            	"lastActivity": 1323666,
            	"lastActivityTime": "2021-01-30T13:46:37Z",
            	"typeHash": 2125994749,
            	"codeHash": 1808981113
            }, {
            	"type": "contract",
            	"address": "KT1UUGjoCXxAxpZGNa4CpGuzmnEMjcKbV7T3",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1V2u3mLn2UWzpU91ADZXGq9z1Mg6cLzvC9"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 872102,
            	"firstActivityTime": "2020-03-19T14:32:16Z",
            	"lastActivity": 872102,
            	"lastActivityTime": "2020-03-19T14:32:16Z",
            	"typeHash": -1629719695,
            	"codeHash": -704477065
            }, {
            	"type": "contract",
            	"address": "KT1MPb3oRXNS5ko9gYEhUZjoEGDerpax1auZ",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1V2u3mLn2UWzpU91ADZXGq9z1Mg6cLzvC9"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 872111,
            	"firstActivityTime": "2020-03-19T14:41:16Z",
            	"lastActivity": 872111,
            	"lastActivityTime": "2020-03-19T14:41:16Z",
            	"typeHash": -1629719695,
            	"codeHash": -704477065
            }, {
            	"type": "contract",
            	"address": "KT1S4ne7rmQArkAeEzXqRYVhGU1dtDwPW1zY",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1bytfCuLdfGerT95yZKmswNQ9NK7awarYf"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 5,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 872128,
            	"firstActivityTime": "2020-03-19T14:58:16Z",
            	"lastActivity": 872367,
            	"lastActivityTime": "2020-03-19T19:05:32Z",
            	"typeHash": -1629719695,
            	"codeHash": -704477065
            }, {
            	"type": "contract",
            	"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"alias": "tzBTC",
            	"balance": 0,
            	"creator": {
            		"address": "tz1bytfCuLdfGerT95yZKmswNQ9NK7awarYf"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 1,
            	"tokenBalancesCount": 1,
            	"tokenTransfersCount": 3,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 183502,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 889027,
            	"firstActivityTime": "2020-03-31T12:53:51Z",
            	"lastActivity": 2274964,
            	"lastActivityTime": "2022-04-12T15:10:44Z",
            	"typeHash": -644342245,
            	"codeHash": -1120012485
            }, {
            	"type": "contract",
            	"address": "KT1LN4LPSqTMS7Sd2CJw4bbDGRkMv2t68Fy9",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"alias": "USDtz",
            	"balance": 1100000,
            	"creator": {
            		"alias": "USDtez",
            		"address": "tz1RaGb8tWxUh194btmAiXT9Tkk6pGBMZVL8"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 1,
            	"tokenBalancesCount": 2,
            	"tokenTransfersCount": 3,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 317922,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 938137,
            	"firstActivityTime": "2020-05-04T19:55:48Z",
            	"lastActivity": 2274964,
            	"lastActivityTime": "2022-04-12T15:10:44Z",
            	"typeHash": -2052509103,
            	"codeHash": -531708076
            }, {
            	"type": "contract",
            	"address": "KT1FftKSYijGge8JF5N5ytyPX4hWzJXwGNFv",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1ducpnTXQYh4wC8ZG36CZYFGAvUcbjSruP"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 948147,
            	"firstActivityTime": "2020-05-11T19:22:03Z",
            	"lastActivity": 948147,
            	"lastActivityTime": "2020-05-11T19:22:03Z",
            	"typeHash": 1399075562,
            	"codeHash": -295487895
            }, {
            	"type": "contract",
            	"address": "KT1HS4h6r1WnHVqsCbZELpC92y4ugrZRFhkT",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 2,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1003142,
            	"firstActivityTime": "2020-06-19T04:04:07Z",
            	"lastActivity": 1088971,
            	"lastActivityTime": "2020-08-18T02:04:14Z",
            	"typeHash": -2052509103,
            	"codeHash": -531708076
            }, {
            	"type": "contract",
            	"address": "KT1BePYYc7bnzMvGBp5E9gKfg2dT1L2U3HZg",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1euqMMX8dhf21M921UEq3f1EKy98FSYqTX"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 3,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1040989,
            	"firstActivityTime": "2020-07-15T13:53:33Z",
            	"lastActivity": 1063865,
            	"lastActivityTime": "2020-07-31T12:48:51Z",
            	"typeHash": 939185251,
            	"codeHash": -1161602143
            }, {
            	"type": "contract",
            	"address": "KT1AzrrdKcZQ7ApazLcya2VV83WaDrqbvSZr",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1QwEsCvVBs2owozESqSc3qEbUJLzBsY46P"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1048494,
            	"firstActivityTime": "2020-07-20T19:32:15Z",
            	"lastActivity": 1048494,
            	"lastActivityTime": "2020-07-20T19:32:15Z",
            	"typeHash": 2031571008,
            	"codeHash": -196911845
            }]
        """.trimIndent())

        mock("""
            [{
            	"type": "contract",
            	"address": "KT1Dvx2TMCec1sVm53H3wzfobeKsjoFhSwuC",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1QwEsCvVBs2owozESqSc3qEbUJLzBsY46P"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 7,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1048512,
            	"firstActivityTime": "2020-07-20T19:50:15Z",
            	"lastActivity": 1048545,
            	"lastActivityTime": "2020-07-20T20:23:15Z",
            	"typeHash": 2031571008,
            	"codeHash": -196911845
            }, {
            	"type": "contract",
            	"address": "KT1S5iPRQ612wcNm6mXDqDhTNegGFcvTV7vM",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"alias": "Aspen Digital",
            	"balance": 0,
            	"creator": {
            		"alias": "tZERO",
            		"address": "tz1TZERoLXqCytrJBmjdzNrAYie1TQ1c2cHN"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 195,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1068632,
            	"firstActivityTime": "2020-08-03T20:49:40Z",
            	"lastActivity": 1416235,
            	"lastActivityTime": "2021-04-05T20:11:13Z",
            	"typeHash": 2031571008,
            	"codeHash": -196911845
            }, {
            	"type": "contract",
            	"address": "KT1V2i7thU8h4ZFKA5FDux49ak2eiuY6AYBb",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1P7A3YFgeSsGgopKN9vUU86W3psgTMdtcJ"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 69,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1144891,
            	"firstActivityTime": "2020-09-26T08:18:44Z",
            	"lastActivity": 1154052,
            	"lastActivityTime": "2020-10-02T18:13:16Z",
            	"typeHash": -2052509103,
            	"codeHash": -531708076
            }, {
            	"type": "contract",
            	"address": "KT1CSYNJ6dFcnsV4QJ6HnBFtdif8LJGPQiDM",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"alias": "Werecoin",
            	"balance": 0,
            	"creator": {
            		"address": "tz1TQyYUuFbAroVARwhh5Fgqu2JE9HrvNi3a"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 76,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1159623,
            	"firstActivityTime": "2020-10-06T15:43:50Z",
            	"lastActivity": 1585217,
            	"lastActivityTime": "2021-08-03T13:25:38Z",
            	"typeHash": -873077331,
            	"codeHash": 1558863756
            }, {
            	"type": "contract",
            	"address": "KT1JKNrzC57FtUe3dmYXmm12ucmjDmzbkKrc",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"alias": "OroPocket XTZ/GOLD",
            	"balance": 0,
            	"creator": {
            		"address": "tz1Ts3m2dXTXB66XN7cg5ALiAvzZY6AxrFd9"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 2613,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1188864,
            	"firstActivityTime": "2020-10-27T02:22:39Z",
            	"lastActivity": 1576480,
            	"lastActivityTime": "2021-07-28T07:01:02Z",
            	"typeHash": 1632905779,
            	"codeHash": -289222764
            }, {
            	"type": "contract",
            	"address": "KT1Avd4SfQT7CezSiGYXFgHNKqSyWstYRz53",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"alias": "OroPocket XTZ/SILVER",
            	"balance": 0,
            	"creator": {
            		"address": "tz1Ts3m2dXTXB66XN7cg5ALiAvzZY6AxrFd9"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 901,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1188866,
            	"firstActivityTime": "2020-10-27T02:24:39Z",
            	"lastActivity": 1576481,
            	"lastActivityTime": "2021-07-28T07:02:02Z",
            	"typeHash": 1632905779,
            	"codeHash": -289222764
            }, {
            	"type": "contract",
            	"address": "KT1FaKvzjgVGZtiA7yyx97txY8J5cE5qpjQ1",
            	"kind": "asset",
            	"tzips": ["fa2"],
            	"balance": 0,
            	"creator": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 4,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1211782,
            	"firstActivityTime": "2020-11-12T02:20:22Z",
            	"lastActivity": 1213942,
            	"lastActivityTime": "2020-11-13T15:41:20Z",
            	"typeHash": -626470897,
            	"codeHash": -166556695
            }, {
            	"type": "contract",
            	"address": "KT1Ex8LrDbCrZuTgmWin8eEo7HFw74jAqTvz",
            	"kind": "asset",
            	"tzips": ["fa2"],
            	"balance": 0,
            	"creator": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 6,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1212167,
            	"firstActivityTime": "2020-11-12T08:46:02Z",
            	"lastActivity": 1321635,
            	"lastActivityTime": "2021-01-29T02:17:40Z",
            	"typeHash": -626470897,
            	"codeHash": -613779762
            }, {
            	"type": "contract",
            	"address": "KT1Tu6A2NHKwEjdHTTJBys8Pu8K9Eo87P2Vy",
            	"kind": "asset",
            	"tzips": ["fa2"],
            	"balance": 0,
            	"creator": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 1,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1218163,
            	"firstActivityTime": "2020-11-16T16:07:10Z",
            	"lastActivity": 1218168,
            	"lastActivityTime": "2020-11-16T16:12:10Z",
            	"typeHash": -626470897,
            	"codeHash": -613779762
            }, {
            	"type": "contract",
            	"address": "KT1CZGNkppGBiEQRXbs1JyRSz7jEDNNBQFo9",
            	"kind": "asset",
            	"tzips": ["fa2"],
            	"balance": 20,
            	"creator": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 52,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1218170,
            	"firstActivityTime": "2020-11-16T16:14:10Z",
            	"lastActivity": 1871691,
            	"lastActivityTime": "2021-11-17T16:28:02Z",
            	"typeHash": -626470897,
            	"codeHash": -613779762
            }]
        """.trimIndent())

        val size = 10
        var collections = collectionClient.collectionsAll(size, null)
        assertThat(request().path).isEqualTo("/v1/contracts?kind=asset&tzips.all=fa2&limit=10&sort.asc=firstActivity")
        assertThat(collections.items).hasSize(size)
        var prevId = 0
        collections.items.forEach{
            assertThat(it.kind).isEqualTo("asset")
            assertThat(it.firstActivity).isGreaterThan(prevId)
            prevId = it.firstActivity!!
        }
        prevId = 0
        val lastId = collections.items.last().firstActivity!!.toString()
        collections = collectionClient.collectionsAll(size, collections.continuation)
        assertThat(request().path).isEqualTo("/v1/contracts?kind=asset&tzips.all=fa2&limit=10&offset=10&sort.asc=firstActivity")
        collections.items.forEach{
            assertThat(it.kind).isEqualTo("asset")
            assertThat(it.firstActivity).isGreaterThan(prevId)
            prevId = it.firstActivity!!
        }
        assertThat(collections.items.first().firstActivity?.toLong()).isGreaterThan(lastId.toLong())
    }

    @Test
    fun `should return collections sorted by DESC with a fixed size and continuation`() = runBlocking<Unit> {
        mock("""
            [{
            	"type": "contract",
            	"address": "KT197cMAmydiH3QH7Xjqqrf8PgX7Xq5FyDat",
            	"kind": "asset",
            	"tzips": ["fa2"],
            	"balance": 0,
            	"creator": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 6,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1292607,
            	"firstActivityTime": "2021-01-08T01:50:42Z",
            	"lastActivity": 1322586,
            	"lastActivityTime": "2021-01-29T18:43:49Z",
            	"typeHash": -1153256293,
            	"codeHash": -1763848797
            }, {
            	"type": "contract",
            	"address": "KT1LNCYggtPrUQ8eHVE1EtYr1YZVxGLGxzfA",
            	"kind": "asset",
            	"tzips": ["fa2"],
            	"balance": 0,
            	"creator": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 1,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1292269,
            	"firstActivityTime": "2021-01-07T19:58:10Z",
            	"lastActivity": 1292275,
            	"lastActivityTime": "2021-01-07T20:04:10Z",
            	"typeHash": -822722271,
            	"codeHash": -1144090816
            }, {
            	"type": "contract",
            	"address": "KT1BvvmDsw1VodPssAfdDUY7V78u4qEQrCWD",
            	"kind": "asset",
            	"tzips": ["fa2"],
            	"balance": 0,
            	"creator": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 1,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1292248,
            	"firstActivityTime": "2021-01-07T19:36:30Z",
            	"lastActivity": 1292255,
            	"lastActivityTime": "2021-01-07T19:44:10Z",
            	"typeHash": -822722271,
            	"codeHash": -1144090816
            }, {
            	"type": "contract",
            	"address": "KT1UBKwp8V8byKMxXTLd1QS5YJcb9yyHXHfQ",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1MbKTiZQvHZuQCPCuhdkFvbP9rP88gu4iu"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 4717,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1289354,
            	"firstActivityTime": "2021-01-05T17:17:13Z",
            	"lastActivity": 1467191,
            	"lastActivityTime": "2021-05-11T16:03:14Z",
            	"typeHash": 644353582,
            	"codeHash": -1901673496
            }, {
            	"type": "contract",
            	"address": "KT1X8DhjBCqCPQ8Ssht8awJQxcmseC2W3bYW",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1MbKTiZQvHZuQCPCuhdkFvbP9rP88gu4iu"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 5073,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1289258,
            	"firstActivityTime": "2021-01-05T15:36:49Z",
            	"lastActivity": 1467189,
            	"lastActivityTime": "2021-05-11T16:00:34Z",
            	"typeHash": 644353582,
            	"codeHash": -1901673496
            }, {
            	"type": "contract",
            	"address": "KT1AFCfQN9UqNVVDuNpBZ5zVoGHuE15L9Npm",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1ehuSwRTVhdcB3Fj62bdsrCfXdLf9LvabH"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 501,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1288725,
            	"firstActivityTime": "2021-01-05T06:23:09Z",
            	"lastActivity": 1448149,
            	"lastActivityTime": "2021-04-28T06:37:32Z",
            	"typeHash": -1131283218,
            	"codeHash": 790255031
            }, {
            	"type": "contract",
            	"address": "KT1WSpk6nW3n6Pnfta58sshuDt3tVLMsmZ7S",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1ehuSwRTVhdcB3Fj62bdsrCfXdLf9LvabH"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1288712,
            	"firstActivityTime": "2021-01-05T06:10:09Z",
            	"lastActivity": 1288712,
            	"lastActivityTime": "2021-01-05T06:10:09Z",
            	"typeHash": -1131283218,
            	"codeHash": 790255031
            }, {
            	"type": "contract",
            	"address": "KT1ELokweLtxd77SZunyLkKwhjjASuzkWsXf",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1ehuSwRTVhdcB3Fj62bdsrCfXdLf9LvabH"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1288711,
            	"firstActivityTime": "2021-01-05T06:09:09Z",
            	"lastActivity": 1288711,
            	"lastActivityTime": "2021-01-05T06:09:09Z",
            	"typeHash": -1131283218,
            	"codeHash": 790255031
            }, {
            	"type": "contract",
            	"address": "KT1RKMnNCt7zkGv6QnzUmzyNSyrHkT6mE6uB",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1ehuSwRTVhdcB3Fj62bdsrCfXdLf9LvabH"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1288710,
            	"firstActivityTime": "2021-01-05T06:07:37Z",
            	"lastActivity": 1288710,
            	"lastActivityTime": "2021-01-05T06:07:37Z",
            	"typeHash": -1131283218,
            	"codeHash": 790255031
            }, {
            	"type": "contract",
            	"address": "KT1Kgf6MWTDakEWakDbmh1XouVjhoPFor9MF",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1ehuSwRTVhdcB3Fj62bdsrCfXdLf9LvabH"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1288709,
            	"firstActivityTime": "2021-01-05T06:06:37Z",
            	"lastActivity": 1288709,
            	"lastActivityTime": "2021-01-05T06:06:37Z",
            	"typeHash": -1131283218,
            	"codeHash": 790255031
            }]
        """.trimIndent())

        mock("""
            [{
            	"type": "contract",
            	"address": "KT1Kz8wwQ2mZ18rrAawXjJ3Mnp4kcXStfwyQ",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1ehuSwRTVhdcB3Fj62bdsrCfXdLf9LvabH"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1288708,
            	"firstActivityTime": "2021-01-05T06:05:37Z",
            	"lastActivity": 1288708,
            	"lastActivityTime": "2021-01-05T06:05:37Z",
            	"typeHash": -1131283218,
            	"codeHash": 790255031
            }, {
            	"type": "contract",
            	"address": "KT1HWGZqkxifb4TcoeGfYwsCD3rptJ65Lw9T",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1ehuSwRTVhdcB3Fj62bdsrCfXdLf9LvabH"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1288706,
            	"firstActivityTime": "2021-01-05T06:03:37Z",
            	"lastActivity": 1288706,
            	"lastActivityTime": "2021-01-05T06:03:37Z",
            	"typeHash": -1131283218,
            	"codeHash": 790255031
            }, {
            	"type": "contract",
            	"address": "KT1NxM3JJs37SAD5WfQgRARbzACowAtfJuwp",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1ehuSwRTVhdcB3Fj62bdsrCfXdLf9LvabH"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1288705,
            	"firstActivityTime": "2021-01-05T06:02:37Z",
            	"lastActivity": 1288705,
            	"lastActivityTime": "2021-01-05T06:02:37Z",
            	"typeHash": -1131283218,
            	"codeHash": 790255031
            }, {
            	"type": "contract",
            	"address": "KT1A5Podv4BeEsPmzSJ3NuydL9pWSgp4JZ5W",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1ehuSwRTVhdcB3Fj62bdsrCfXdLf9LvabH"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1288704,
            	"firstActivityTime": "2021-01-05T06:01:37Z",
            	"lastActivity": 1288704,
            	"lastActivityTime": "2021-01-05T06:01:37Z",
            	"typeHash": -1131283218,
            	"codeHash": 790255031
            }, {
            	"type": "contract",
            	"address": "KT1EZ5m49LXHdChxbVzq9NFcyGJp9bXFwVgk",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1ehuSwRTVhdcB3Fj62bdsrCfXdLf9LvabH"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1288703,
            	"firstActivityTime": "2021-01-05T06:00:37Z",
            	"lastActivity": 1288703,
            	"lastActivityTime": "2021-01-05T06:00:37Z",
            	"typeHash": -1131283218,
            	"codeHash": 790255031
            }, {
            	"type": "contract",
            	"address": "KT1RR7CHEYyyU2xTwzxMnqoa1D6iHyrFMHff",
            	"kind": "asset",
            	"tzips": ["fa2"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1hgc4AbJxpHA24sGCiQwUeiMAvNtLenVnH"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 1,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1288176,
            	"firstActivityTime": "2021-01-04T20:58:43Z",
            	"lastActivity": 1298223,
            	"lastActivityTime": "2021-01-12T03:59:01Z",
            	"typeHash": 438200635,
            	"codeHash": 652644101
            }, {
            	"type": "contract",
            	"address": "KT1UHj3FTZXmpaZSsCvA3RxVgabWZ4TG6f6K",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1MFdUjp818c96671eBzfCPnwwBh7eGaSj1"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 84,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1280625,
            	"firstActivityTime": "2020-12-30T11:20:16Z",
            	"lastActivity": 1315103,
            	"lastActivityTime": "2021-01-24T09:12:53Z",
            	"typeHash": -16015031,
            	"codeHash": 225181542
            }, {
            	"type": "contract",
            	"address": "KT1Sc7hwkxenUmYwgLUor94VxfjFXTAviBoV",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1MFdUjp818c96671eBzfCPnwwBh7eGaSj1"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 2,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1280285,
            	"firstActivityTime": "2020-12-30T05:33:28Z",
            	"lastActivity": 1280290,
            	"lastActivityTime": "2020-12-30T05:38:28Z",
            	"typeHash": -16015031,
            	"codeHash": 1457587264
            }, {
            	"type": "contract",
            	"address": "KT1VYsVfmobT7rsMVivvZ4J8i3bPiqz12NaH",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"alias": "wXTZ",
            	"balance": 810,
            	"creator": {
            		"address": "tz1T8G8vRZ1ggYhrQQYkF5dAM2acEdLmGaA8"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 5,
            	"tokenBalancesCount": 5,
            	"tokenTransfersCount": 36,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 33495,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1268684,
            	"firstActivityTime": "2020-12-22T02:21:04Z",
            	"lastActivity": 2275037,
            	"lastActivityTime": "2022-04-12T15:47:14Z",
            	"typeHash": 1364303341,
            	"codeHash": -356761656
            }, {
            	"type": "contract",
            	"address": "KT1KpkYFqPPrjoJFU532D8MeqMoqAmadRU5X",
            	"kind": "asset",
            	"tzips": ["fa12"],
            	"balance": 0,
            	"creator": {
            		"address": "tz1T8G8vRZ1ggYhrQQYkF5dAM2acEdLmGaA8"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 0,
            	"tokenBalancesCount": 0,
            	"tokenTransfersCount": 0,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 0,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1268678,
            	"firstActivityTime": "2020-12-22T02:15:04Z",
            	"lastActivity": 1268678,
            	"lastActivityTime": "2020-12-22T02:15:04Z",
            	"typeHash": 1364303341,
            	"codeHash": -356761656
            }]
        """.trimIndent())

        val size = 10
        var collections = collectionClient.collectionsAll(size, null, false)
        assertThat(request().path).isEqualTo("/v1/contracts?kind=asset&tzips.all=fa2&limit=10&sort.desc=firstActivity")
        assertThat(collections.items).hasSize(size)
        var prevId = Int.MAX_VALUE
        collections.items.forEach{
            assertThat(it.kind).isEqualTo("asset")
            assertThat(it.firstActivity!!).isLessThan(prevId)
            prevId = it.firstActivity!!
        }
        collections = collectionClient.collectionsAll(size, collections.continuation, false)
        assertThat(request().path).isEqualTo("/v1/contracts?kind=asset&tzips.all=fa2&limit=10&offset=10&sort.desc=firstActivity")
        collections.items.forEach{
            assertThat(it.kind).isEqualTo("asset")
            assertThat(it.firstActivity).isLessThan(prevId)
            prevId = it.firstActivity!!
        }
    }

    // This test is flaky in jenkins
    @Disabled
    @Test
    fun `should return collections by ids`() = runBlocking<Unit> {
        mock("""{
            "type": "contract",
            "address": "KT1QuofAgnsWffHzLA7D78rxytJruGHDe7XG",
            "kind": "smart_contract",
            "alias": "Vested funds 11",
            "balance": 597134936637,
            "manager": {
                "alias": "Null-address",
                "address": "tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU"
            },
            "delegate": {
                "alias": "Foundation Baker 1",
                "address": "tz3RDC3Jdn4j15J7bBHZd29EUee9gVB1CxD9",
                "active": true
            },
            "delegationLevel": 1,
            "delegationTime": "2018-06-30T17:39:57Z",
            "numContracts": 0,
            "activeTokensCount": 0,
            "tokenBalancesCount": 0,
            "tokenTransfersCount": 0,
            "numDelegations": 0,
            "numOriginations": 0,
            "numTransactions": 60,
            "numReveals": 0,
            "numMigrations": 2,
            "firstActivity": 1,
            "firstActivityTime": "2018-06-30T17:39:57Z",
            "lastActivity": 2219799,
            "lastActivityTime": "2022-03-23T08:16:54Z",
            "typeHash": 605223826,
            "codeHash": -2016262351
        }""")
        mock("""[]""") // for meta
        mock("""{
            "type": "contract",
            "address": "KT1CSKPf2jeLpMmrgKquN2bCjBTkAcAdRVDy",
            "kind": "smart_contract",
            "alias": "Vested funds 29",
            "balance": 597134936637,
            "manager": {
                "alias": "Null-address",
                "address": "tz1Ke2h7sDdakHJQh8WX4Z372du1KChsksyU"
            },
            "delegate": {
                "alias": "Foundation baker 2",
                "address": "tz3bvNMQ95vfAYtG8193ymshqjSvmxiCUuR5",
                "active": true
            },
            "delegationLevel": 1,
            "delegationTime": "2018-06-30T17:39:57Z",
            "numContracts": 0,
            "activeTokensCount": 0,
            "tokenBalancesCount": 0,
            "tokenTransfersCount": 0,
            "numDelegations": 0,
            "numOriginations": 0,
            "numTransactions": 69,
            "numReveals": 0,
            "numMigrations": 2,
            "firstActivity": 1,
            "firstActivityTime": "2018-06-30T17:39:57Z",
            "lastActivity": 2219803,
            "lastActivityTime": "2022-03-23T08:18:54Z",
            "typeHash": 605223826,
            "codeHash": -2016262351
        }""")
        mock("""[]""") // for meta
        val collections = collectionClient.collectionsByIds(listOf("KT1QuofAgnsWffHzLA7D78rxytJruGHDe7XG", "KT1CSKPf2jeLpMmrgKquN2bCjBTkAcAdRVDy"))
        assertThat(collections).hasSize(2)
    }

    @Test
    fun `should return collection by owner`() = runBlocking<Unit> {
        mock("""
            [{
            	"type": "contract",
            	"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton",
            	"kind": "asset",
            	"tzips": ["fa2"],
            	"alias": "hic et nunc NFTs",
            	"balance": 0,
            	"creator": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 141,
            	"tokenBalancesCount": 170,
            	"tokenTransfersCount": 189,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 11065912,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1365143,
            	"firstActivityTime": "2021-03-01T01:59:41Z",
            	"lastActivity": 2274952,
            	"lastActivityTime": "2022-04-12T15:04:44Z",
            	"typeHash": 603828391,
            	"codeHash": 1973375561
            }]
        """.trimIndent())

        val collection = collectionClient.collectionsByOwner("tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw", 1, null, false)
        assertThat(request().path).isEqualTo("/v1/contracts?kind=asset&tzips.all=fa2&limit=1&creator.eq=tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw&sort.desc=firstActivity")

        assertThat(collection.items).hasSize(1)
        assertThat(collection.continuation).isNotNull
    }

    @Test
    fun `should return collection by owner with continuation`() = runBlocking<Unit> {
        mock("""
            [{
            	"type": "contract",
            	"address": "KT1RJ6PbjHpwc3M5rw5s2Nbmefwbuwbdxton",
            	"kind": "asset",
            	"tzips": ["fa2"],
            	"alias": "hic et nunc NFTs",
            	"balance": 0,
            	"creator": {
            		"alias": "hicetnunc2000lab",
            		"address": "tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw"
            	},
            	"numContracts": 0,
            	"activeTokensCount": 141,
            	"tokenBalancesCount": 170,
            	"tokenTransfersCount": 189,
            	"numDelegations": 0,
            	"numOriginations": 1,
            	"numTransactions": 11065912,
            	"numReveals": 0,
            	"numMigrations": 0,
            	"firstActivity": 1365143,
            	"firstActivityTime": "2021-03-01T01:59:41Z",
            	"lastActivity": 2274952,
            	"lastActivityTime": "2022-04-12T15:04:44Z",
            	"typeHash": 603828391,
            	"codeHash": 1973375561
            }]
        """.trimIndent())

        val collection = collectionClient.collectionsByOwner("tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw", 1, "1", false)
        assertThat(request().path).isEqualTo("/v1/contracts?kind=asset&tzips.all=fa2&limit=1&creator.eq=tz1UBZUkXpKGhYsP5KtzDNqLLchwF4uHrGjw&offset=1&sort.desc=firstActivity")

        assertThat(collection.items).hasSize(1)
        assertThat(collection.continuation).isNotNull
    }

    @Test
    fun `should return NFT type for collection`() = runBlocking<Unit> {
        mock("""
            {
                "schema:object": {
                    "owner:address": "address",
                    "owner_candidate:?address": "address",
                    "paused:bool": "bool",
                    "royalties:big_map_flat:nat:list:object": {
                        "nat": [
                            {
                                "partAccount:address": "address",
                                "partValue:nat": "nat"
                            }
                        ]
                    },
                    "ledger:big_map_flat:nat:address": {
                        "nat": "address"
                    },
                    "operators:big_map:object:unit": [
                        {
                            "key:object": {
                                "address_0:address": "address",
                                "nat:nat": "nat",
                                "address_1:address": "address"
                            },
                            "value:unit": "unit"
                        }
                    ],
                    "token_metadata:big_map_flat:nat:object": {
                        "nat": {
                            "token_id:nat": "nat",
                            "token_info:map_flat:string:bytes": {
                                "string": "bytes"
                            }
                        }
                    },
                    "permits:big_map_flat:address:object": {
                        "address": {
                            "counter:nat": "nat",
                            "user_expiry:?nat": "nat",
                            "user_permits:map_flat:bytes:object": {
                                "bytes": {
                                    "expiry:?nat": "nat",
                                    "created_at:timestamp": "timestamp"
                                }
                            }
                        }
                    },
                    "operators_for_all:big_map:object:unit": [
                        {
                            "key:object": {
                                "address_0:address": "address",
                                "address_1:address": "address"
                            },
                            "value:unit": "unit"
                        }
                    ],
                    "default_expiry:nat": "nat",
                    "metadata:big_map_flat:string:bytes": {
                        "string": "bytes"
                    }
                }
            }
        """.trimIndent())

        val type = collectionClient.collectionType("KT1DtQV5qTnxdG49GbMRdKC8fg7bpvPLNcpm")
        assertThat(request().path).isEqualTo("/v1/contracts/KT1DtQV5qTnxdG49GbMRdKC8fg7bpvPLNcpm/storage/schema")

        assertThat(type).isEqualTo(CollectionType.NFT)
    }

    @Test
    fun `should return MT type for collection`() = runBlocking<Unit> {
        mock("""
            {
                "schema:object": {
                    "owner:address": "address",
                    "owner_candidate:?address": "address",
                    "paused:bool": "bool",
                    "royalties:big_map_flat:nat:list:object": {
                        "nat": [
                            {
                                "partAccount:address": "address",
                                "partValue:nat": "nat"
                            }
                        ]
                    },
                    "ledger:big_map:object:nat": [
                        {
                            "key:object": {
                                "nat:nat": "nat",
                                "address:address": "address"
                            },
                            "value:nat": "nat"
                        }
                    ],
                    "operator:big_map:object:unit": [
                        {
                            "key:object": {
                                "address_0:address": "address",
                                "nat:nat": "nat",
                                "address_1:address": "address"
                            },
                            "value:unit": "unit"
                        }
                    ],
                    "token_metadata:big_map_flat:nat:object": {
                        "nat": {
                            "token_id:nat": "nat",
                            "token_info:map_flat:string:bytes": {
                                "string": "bytes"
                            }
                        }
                    },
                    "permits:big_map_flat:address:object": {
                        "address": {
                            "counter:nat": "nat",
                            "user_expiry:?nat": "nat",
                            "user_permits:map_flat:bytes:object": {
                                "bytes": {
                                    "expiry:?nat": "nat",
                                    "created_at:timestamp": "timestamp"
                                }
                            }
                        }
                    },
                    "operator_for_all:big_map:object:unit": [
                        {
                            "key:object": {
                                "address_0:address": "address",
                                "address_1:address": "address"
                            },
                            "value:unit": "unit"
                        }
                    ],
                    "default_expiry:nat": "nat",
                    "metadata:big_map_flat:string:bytes": {
                        "string": "bytes"
                    }
                }
            }
        """.trimIndent())

        val type = collectionClient.collectionType("KT1Uke8qc4YTfP41dGuoGC8UsgRyCtyvKPLA")
        assertThat(request().path).isEqualTo("/v1/contracts/KT1Uke8qc4YTfP41dGuoGC8UsgRyCtyvKPLA/storage/schema")

        assertThat(type).isEqualTo(CollectionType.MT)
    }

    // This test is for testnet only
    @Disabled
    @Test
    fun `should return meta for collection`() = runBlocking<Unit> {
        val collection = collectionClient.collection("KT1UFkqihyjz1GhxM1hk78CjfcChsBbLGYMm")
        assertThat(collection.name).isEqualTo("123-000")
        assertThat(collection.symbol).isEqualTo("123")
    }
}
