package com.rarible.dipdup.client

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ActivityClientFt : BaseClientFt() {

    val activityClient = ActivityClient(client)

    @Test
    fun `should return activities`() = runBlocking<Unit> {
        mock("""
            {
                "data": {
                    "marketplace_activity": [
                        {
                            "amount": 10.000000000000000000000000000000000000,
                            "contract": "KT1Q8JB2bdphCHhEBKc1PMsjArLPcAezGBVK",
                            "id": 2,
                            "internal_order_id": "1000000",
                            "maker": "tz1XHhjLXQuG9rf9n7o1VbgegMkiggy1oktu",
                            "network": "mainnet",
                            "operation_hash": "opVpgjZYgKLYFw5nXKkBcE8EMJMNTXhUNjWCKVsUY5643BgwRxX",
                            "operation_level": 2105745,
                            "operation_timestamp": "2022-02-10T13:01:54+00:00",
                            "platform": "Objkt_v2",
                            "sell_price": 0.010000000000000000000000000000000000,
                            "token_id": "2",
                            "type": "cancel"
                        },
                        {
                            "amount": 10.000000000000000000000000000000000000,
                            "contract": "KT1Q8JB2bdphCHhEBKc1PMsjArLPcAezGBVK",
                            "id": 3,
                            "internal_order_id": "1000001",
                            "maker": "tz1XHhjLXQuG9rf9n7o1VbgegMkiggy1oktu",
                            "network": "mainnet",
                            "operation_hash": "opVpgjZYgKLYFw5nXKkBcE8EMJMNTXhUNjWCKVsUY5643BgwRxX",
                            "operation_level": 2105745,
                            "operation_timestamp": "2022-02-10T13:01:54+00:00",
                            "platform": "Objkt_v2",
                            "sell_price": 0.020000000000000000000000000000000000,
                            "token_id": "2",
                            "type": "list"
                        }
                    ]
                }
            }""")

        val activities = activityClient.getActivities(10, "1")
        assertThat(activities).hasSize(2)
    }
}
