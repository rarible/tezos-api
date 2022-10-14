package com.rarible.dipdup.listener.config

class DipDupTopicProvider {

    companion object {

        fun getActivityTopic(environment: String): String =
            "protocol.$environment.tezos.indexer.activity"

        fun getCollectionTopic(environment: String): String =
            "protocol.$environment.tezos.indexer.collection"

        fun getOrderTopic(environment: String): String =
            "protocol.$environment.tezos.indexer.order"

        fun getItemTopic(environment: String): String =
            "protocol.$environment.tezos.indexer.item"

        fun getItemMetaTopic(environment: String): String =
            "protocol.$environment.tezos.indexer.item.meta"

        fun getOwnershipTopic(environment: String): String =
            "protocol.$environment.tezos.indexer.ownership"

    }

}
