package com.rarible.tzkt.client

import com.rarible.tzkt.models.TokenTransfer
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class ActivityClientTests : BaseClientTests() {

    val activityClient = ActivityClient(client)

    @Test
    fun `should return NFT activities with size, continuation, and sorting ASC`() = runBlocking<Unit> {
        mock("""
            [{
            	"id": 23818305,
            	"level": 889166,
            	"timestamp": "2020-03-31T15:12:51Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"to": {
            		"address": "tz1ZAwyfujwED4yUhQAtc1eqm4gW5u2Xiw77"
            	},
            	"amount": "100000000",
            	"transactionId": 23818302
            }, {
            	"id": 23820166,
            	"level": 889188,
            	"timestamp": "2020-03-31T15:34:51Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1ZAwyfujwED4yUhQAtc1eqm4gW5u2Xiw77"
            	},
            	"to": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"amount": "10000",
            	"transactionId": 23820160
            }, {
            	"id": 23859318,
            	"level": 890534,
            	"timestamp": "2020-04-01T14:11:25Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"to": {
            		"address": "tz1Mj7RzPmMAqDUNFBn5t5VbXmWW4cSUAdtT"
            	},
            	"amount": "1",
            	"transactionId": 23859311
            }, {
            	"id": 23893925,
            	"level": 891786,
            	"timestamp": "2020-04-02T11:16:05Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"to": {
            		"address": "tz1Mj7RzPmMAqDUNFBn5t5VbXmWW4cSUAdtT"
            	},
            	"amount": "1",
            	"transactionId": 23893920
            }, {
            	"id": 23894430,
            	"level": 891803,
            	"timestamp": "2020-04-02T11:33:05Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1Mj7RzPmMAqDUNFBn5t5VbXmWW4cSUAdtT"
            	},
            	"to": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"amount": "1",
            	"transactionId": 23894426
            }, {
            	"id": 23901043,
            	"level": 892031,
            	"timestamp": "2020-04-02T15:23:45Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"to": {
            		"address": "tz1Mj7RzPmMAqDUNFBn5t5VbXmWW4cSUAdtT"
            	},
            	"amount": "1",
            	"transactionId": 23901040
            }, {
            	"id": 23901238,
            	"level": 892038,
            	"timestamp": "2020-04-02T15:30:45Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1Mj7RzPmMAqDUNFBn5t5VbXmWW4cSUAdtT"
            	},
            	"to": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"amount": "2",
            	"transactionId": 23901228
            }, {
            	"id": 23954716,
            	"level": 767840,
            	"timestamp": "2020-01-06T03:46:32Z",
            	"token": {
            		"id": 2,
            		"contract": {
            			"alias": "StakerDAO",
            			"address": "KT1EctCuorV2NfVb1XTQgvzJ88MQtWP8cMMv"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2"
            	},
            	"to": {
            		"address": "tz1WAVpSaCFtLQKSJkrdVApCQC1TNK8iNxq9"
            	},
            	"amount": "1500000",
            	"originationId": 19866275
            }, {
            	"id": 23954717,
            	"level": 893539,
            	"timestamp": "2020-04-03T16:47:21Z",
            	"token": {
            		"id": 2,
            		"contract": {
            			"alias": "StakerDAO",
            			"address": "KT1EctCuorV2NfVb1XTQgvzJ88MQtWP8cMMv"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2"
            	},
            	"from": {
            		"address": "tz1WAVpSaCFtLQKSJkrdVApCQC1TNK8iNxq9"
            	},
            	"to": {
            		"address": "tz1e7XJSrFNmuJq9EAFh5QVLjaUtWapWoiG3"
            	},
            	"amount": "1",
            	"transactionId": 23954715
            }, {
            	"id": 23955219,
            	"level": 893557,
            	"timestamp": "2020-04-03T17:05:21Z",
            	"token": {
            		"id": 2,
            		"contract": {
            			"alias": "StakerDAO",
            			"address": "KT1EctCuorV2NfVb1XTQgvzJ88MQtWP8cMMv"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2"
            	},
            	"from": {
            		"address": "tz1e7XJSrFNmuJq9EAFh5QVLjaUtWapWoiG3"
            	},
            	"to": {
            		"alias": "Community.tez",
            		"address": "tz1T7QMkroV81qCCrwTzTfqbVEyE2f3w9ccg"
            	},
            	"amount": "1",
            	"transactionId": 23955215
            }]
        """.trimIndent())

        mock("""
            [{
            	"id": 24209728,
            	"level": 901489,
            	"timestamp": "2020-04-09T06:47:20Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"to": {
            		"address": "tz1fNwSagKoNLDM7YFDDRCKg6CosghKtLFp8"
            	},
            	"amount": "1",
            	"transactionId": 24209723
            }, {
            	"id": 24276908,
            	"level": 903632,
            	"timestamp": "2020-04-10T18:47:02Z",
            	"token": {
            		"id": 2,
            		"contract": {
            			"alias": "StakerDAO",
            			"address": "KT1EctCuorV2NfVb1XTQgvzJ88MQtWP8cMMv"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2"
            	},
            	"from": {
            		"address": "tz1WAVpSaCFtLQKSJkrdVApCQC1TNK8iNxq9"
            	},
            	"to": {
            		"address": "tz1dcWXLS1UBeGc7EazGvoNE6D8YSzVkAsSa"
            	},
            	"amount": "1",
            	"transactionId": 24276901
            }, {
            	"id": 24504974,
            	"level": 910678,
            	"timestamp": "2020-04-15T16:42:51Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"to": {
            		"address": "tz1iBACSsRvkFa3611hQkXjrNkC5m7ZiQoY2"
            	},
            	"amount": "99",
            	"transactionId": 24504972
            }, {
            	"id": 24511046,
            	"level": 910863,
            	"timestamp": "2020-04-15T19:47:51Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1iBACSsRvkFa3611hQkXjrNkC5m7ZiQoY2"
            	},
            	"to": {
            		"address": "tz1UBTk5Jo18PAQcFWZhWS2fxt4S7Qbd5sYU"
            	},
            	"amount": "50",
            	"transactionId": 24511045
            }, {
            	"id": 24534343,
            	"level": 911682,
            	"timestamp": "2020-04-16T09:30:27Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1UBTk5Jo18PAQcFWZhWS2fxt4S7Qbd5sYU"
            	},
            	"to": {
            		"address": "tz1iBACSsRvkFa3611hQkXjrNkC5m7ZiQoY2"
            	},
            	"amount": "50",
            	"transactionId": 24534342
            }, {
            	"id": 24545827,
            	"level": 912083,
            	"timestamp": "2020-04-16T16:12:52Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"to": {
            		"address": "tz1VvWQ93JYFY1bw8QC6SrV2KdJCkDsRnVVu"
            	},
            	"amount": "35693999960",
            	"transactionId": 24545823
            }, {
            	"id": 24546945,
            	"level": 912122,
            	"timestamp": "2020-04-16T16:51:52Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1ZAwyfujwED4yUhQAtc1eqm4gW5u2Xiw77"
            	},
            	"to": {
            		"address": "tz1UBTk5Jo18PAQcFWZhWS2fxt4S7Qbd5sYU"
            	},
            	"amount": "99990000",
            	"transactionId": 24546944
            }, {
            	"id": 24576287,
            	"level": 913155,
            	"timestamp": "2020-04-17T10:08:09Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1VvWQ93JYFY1bw8QC6SrV2KdJCkDsRnVVu"
            	},
            	"to": {
            		"address": "tz1UBTk5Jo18PAQcFWZhWS2fxt4S7Qbd5sYU"
            	},
            	"amount": "705000000",
            	"transactionId": 24576285
            }, {
            	"id": 24592872,
            	"level": 913468,
            	"timestamp": "2020-04-17T15:23:41Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1VvWQ93JYFY1bw8QC6SrV2KdJCkDsRnVVu"
            	},
            	"to": {
            		"address": "tz1TunRhqnsrE9rrykDJJEEwFnfMf3Sz1mT5"
            	},
            	"amount": "34988999960",
            	"transactionId": 24592871
            }, {
            	"id": 24778005,
            	"level": 918998,
            	"timestamp": "2020-04-21T11:53:11Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1UBTk5Jo18PAQcFWZhWS2fxt4S7Qbd5sYU"
            	},
            	"to": {
            		"address": "tz1MpUMDnj5hYBHW6EmEbtiLC5BpaRxjo7H7"
            	},
            	"amount": "1",
            	"transactionId": 24778004
            }]
        """.trimIndent())

        val size = 10
        var continuation = 0L
        var activities = activityClient.activities(size, continuation)
        var prevId = 0
        activities.forEach {
            assert(it::class.java == TokenTransfer::class.java)
            assert(it.id!! > prevId)
            prevId = it.id!!
        }
        val lastId = activities.last().id!!.toLong()
        continuation = lastId
        activities = activityClient.activities(size, continuation)
        activities.forEach {
            assert(it::class.java == TokenTransfer::class.java)
            assert(it.id!! > prevId)
            prevId = it.id!!
        }
        assert(activities.first().id!!.toLong() > lastId)
    }

    @Test
    fun `should return NFT activities with size, continuation, and sorting DESC`() = runBlocking<Unit> {
        mock("""
            [{
            	"id": 24876439,
            	"level": 921975,
            	"timestamp": "2020-04-23T13:34:19Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1b6hmG6zk5mcbHPvtmuJ1Yorv6dhqjPS7A"
            	},
            	"to": {
            		"address": "tz1WHYp7tZPosRN71cGf8RSBBUFf6WfG7SbB"
            	},
            	"amount": "9",
            	"transactionId": 24876438
            }, {
            	"id": 24872046,
            	"level": 921860,
            	"timestamp": "2020-04-23T11:39:19Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1iBACSsRvkFa3611hQkXjrNkC5m7ZiQoY2"
            	},
            	"to": {
            		"address": "tz1b6hmG6zk5mcbHPvtmuJ1Yorv6dhqjPS7A"
            	},
            	"amount": "9",
            	"transactionId": 24872044
            }, {
            	"id": 24861670,
            	"level": 921655,
            	"timestamp": "2020-04-23T08:14:19Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1aKTCbAUuea2RV9kxqRVRg3HT7f1RKnp6a"
            	},
            	"to": {
            		"alias": "Atomex tzBTC",
            		"address": "KT1Ap287P1NzsnToSJdA4aqSNjPomRaHBZSr"
            	},
            	"amount": "290502",
            	"transactionId": 24861652
            }, {
            	"id": 24822478,
            	"level": 920602,
            	"timestamp": "2020-04-22T14:38:39Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"alias": "Atomex tzBTC",
            		"address": "KT1Ap287P1NzsnToSJdA4aqSNjPomRaHBZSr"
            	},
            	"to": {
            		"address": "tz1QB5LdNbZgfC2zxa26bMLARwDjv63M9gCg"
            	},
            	"amount": "347793",
            	"transactionId": 24822476
            }, {
            	"id": 24822182,
            	"level": 920599,
            	"timestamp": "2020-04-22T14:35:39Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1aKTCbAUuea2RV9kxqRVRg3HT7f1RKnp6a"
            	},
            	"to": {
            		"alias": "Atomex tzBTC",
            		"address": "KT1Ap287P1NzsnToSJdA4aqSNjPomRaHBZSr"
            	},
            	"amount": "347793",
            	"transactionId": 24822175
            }, {
            	"id": 24821432,
            	"level": 920572,
            	"timestamp": "2020-04-22T14:08:39Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1MpUMDnj5hYBHW6EmEbtiLC5BpaRxjo7H7"
            	},
            	"to": {
            		"address": "tz1aKTCbAUuea2RV9kxqRVRg3HT7f1RKnp6a"
            	},
            	"amount": "291737359",
            	"transactionId": 24821431
            }, {
            	"id": 24787702,
            	"level": 919350,
            	"timestamp": "2020-04-21T17:45:11Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1UBTk5Jo18PAQcFWZhWS2fxt4S7Qbd5sYU"
            	},
            	"to": {
            		"address": "tz1MpUMDnj5hYBHW6EmEbtiLC5BpaRxjo7H7"
            	},
            	"amount": "291737358",
            	"transactionId": 24787701
            }, {
            	"id": 24785817,
            	"level": 919283,
            	"timestamp": "2020-04-21T16:38:11Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1QB5LdNbZgfC2zxa26bMLARwDjv63M9gCg"
            	},
            	"to": {
            		"address": "tz1MpUMDnj5hYBHW6EmEbtiLC5BpaRxjo7H7"
            	},
            	"amount": "1",
            	"transactionId": 24785810
            }, {
            	"id": 24785700,
            	"level": 919279,
            	"timestamp": "2020-04-21T16:34:11Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1MpUMDnj5hYBHW6EmEbtiLC5BpaRxjo7H7"
            	},
            	"to": {
            		"address": "tz1QB5LdNbZgfC2zxa26bMLARwDjv63M9gCg"
            	},
            	"amount": "1",
            	"transactionId": 24785699
            }, {
            	"id": 24778005,
            	"level": 918998,
            	"timestamp": "2020-04-21T11:53:11Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1UBTk5Jo18PAQcFWZhWS2fxt4S7Qbd5sYU"
            	},
            	"to": {
            		"address": "tz1MpUMDnj5hYBHW6EmEbtiLC5BpaRxjo7H7"
            	},
            	"amount": "1",
            	"transactionId": 24778004
            }]
        """.trimIndent())

        mock("""
            [{
            	"id": 24592872,
            	"level": 913468,
            	"timestamp": "2020-04-17T15:23:41Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1VvWQ93JYFY1bw8QC6SrV2KdJCkDsRnVVu"
            	},
            	"to": {
            		"address": "tz1TunRhqnsrE9rrykDJJEEwFnfMf3Sz1mT5"
            	},
            	"amount": "34988999960",
            	"transactionId": 24592871
            }, {
            	"id": 24576287,
            	"level": 913155,
            	"timestamp": "2020-04-17T10:08:09Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1VvWQ93JYFY1bw8QC6SrV2KdJCkDsRnVVu"
            	},
            	"to": {
            		"address": "tz1UBTk5Jo18PAQcFWZhWS2fxt4S7Qbd5sYU"
            	},
            	"amount": "705000000",
            	"transactionId": 24576285
            }, {
            	"id": 24546945,
            	"level": 912122,
            	"timestamp": "2020-04-16T16:51:52Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1ZAwyfujwED4yUhQAtc1eqm4gW5u2Xiw77"
            	},
            	"to": {
            		"address": "tz1UBTk5Jo18PAQcFWZhWS2fxt4S7Qbd5sYU"
            	},
            	"amount": "99990000",
            	"transactionId": 24546944
            }, {
            	"id": 24545827,
            	"level": 912083,
            	"timestamp": "2020-04-16T16:12:52Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"to": {
            		"address": "tz1VvWQ93JYFY1bw8QC6SrV2KdJCkDsRnVVu"
            	},
            	"amount": "35693999960",
            	"transactionId": 24545823
            }, {
            	"id": 24534343,
            	"level": 911682,
            	"timestamp": "2020-04-16T09:30:27Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1UBTk5Jo18PAQcFWZhWS2fxt4S7Qbd5sYU"
            	},
            	"to": {
            		"address": "tz1iBACSsRvkFa3611hQkXjrNkC5m7ZiQoY2"
            	},
            	"amount": "50",
            	"transactionId": 24534342
            }, {
            	"id": 24511046,
            	"level": 910863,
            	"timestamp": "2020-04-15T19:47:51Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1iBACSsRvkFa3611hQkXjrNkC5m7ZiQoY2"
            	},
            	"to": {
            		"address": "tz1UBTk5Jo18PAQcFWZhWS2fxt4S7Qbd5sYU"
            	},
            	"amount": "50",
            	"transactionId": 24511045
            }, {
            	"id": 24504974,
            	"level": 910678,
            	"timestamp": "2020-04-15T16:42:51Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"to": {
            		"address": "tz1iBACSsRvkFa3611hQkXjrNkC5m7ZiQoY2"
            	},
            	"amount": "99",
            	"transactionId": 24504972
            }, {
            	"id": 24276908,
            	"level": 903632,
            	"timestamp": "2020-04-10T18:47:02Z",
            	"token": {
            		"id": 2,
            		"contract": {
            			"alias": "StakerDAO",
            			"address": "KT1EctCuorV2NfVb1XTQgvzJ88MQtWP8cMMv"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2"
            	},
            	"from": {
            		"address": "tz1WAVpSaCFtLQKSJkrdVApCQC1TNK8iNxq9"
            	},
            	"to": {
            		"address": "tz1dcWXLS1UBeGc7EazGvoNE6D8YSzVkAsSa"
            	},
            	"amount": "1",
            	"transactionId": 24276901
            }, {
            	"id": 24209728,
            	"level": 901489,
            	"timestamp": "2020-04-09T06:47:20Z",
            	"token": {
            		"id": 1,
            		"contract": {
            			"alias": "tzBTC",
            			"address": "KT1PWx2mnDueood7fEmfbBDKx1D9BAnnXitn"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2",
            		"metadata": {
            			"name": "tzBTC",
            			"symbol": "tzBTC",
            			"decimals": "8"
            		}
            	},
            	"from": {
            		"address": "tz1d75oB6T4zUMexzkr5WscGktZ1Nss1JrT7"
            	},
            	"to": {
            		"address": "tz1fNwSagKoNLDM7YFDDRCKg6CosghKtLFp8"
            	},
            	"amount": "1",
            	"transactionId": 24209723
            }, {
            	"id": 23955219,
            	"level": 893557,
            	"timestamp": "2020-04-03T17:05:21Z",
            	"token": {
            		"id": 2,
            		"contract": {
            			"alias": "StakerDAO",
            			"address": "KT1EctCuorV2NfVb1XTQgvzJ88MQtWP8cMMv"
            		},
            		"tokenId": "0",
            		"standard": "fa1.2"
            	},
            	"from": {
            		"address": "tz1e7XJSrFNmuJq9EAFh5QVLjaUtWapWoiG3"
            	},
            	"to": {
            		"alias": "Community.tez",
            		"address": "tz1T7QMkroV81qCCrwTzTfqbVEyE2f3w9ccg"
            	},
            	"amount": "1",
            	"transactionId": 23955215
            }]
        """.trimIndent())

        val size = 10
        var continuation = 24878056L
        var activities = activityClient.activities(size, continuation, false)
        var prevId = 24878056L
        activities.forEach {
            assert(it::class.java == TokenTransfer::class.java)
            assert(it.id!! < prevId)
            prevId = it.id!!.toLong()
        }
        val lastId = activities.last().id!!.toLong()
        continuation = lastId
        activities = activityClient.activities(size, continuation, false)
        prevId = lastId
        activities.forEach {
            assert(it::class.java == TokenTransfer::class.java)
            assert(it.id!! < prevId)
            prevId = it.id!!.toLong()
        }
        assert(activities.first().id!!.toLong() < lastId)
    }

}
