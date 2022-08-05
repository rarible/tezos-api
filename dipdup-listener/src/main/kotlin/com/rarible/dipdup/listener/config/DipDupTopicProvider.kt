package com.rarible.dipdup.listener.config

class DipDupTopicProvider {

    companion object {
        const val ITEM = "item_topic"
        const val COLLECTION = "collection_topic"
        const val ORDER = "order_topic"
        const val ACTIVITY = "activity_topic"
        const val OWNERSHIP = "ownership_topic"

        fun getActivityTopic(environment: String): String =
            "protocol.$environment.tezos.indexer.activity"

        fun getCollectionTopic(environment: String): String =
            "protocol.$environment.tezos.indexer.collection"

        fun getOrderTopic(environment: String): String =
            "protocol.$environment.tezos.indexer.order"

    }

}
