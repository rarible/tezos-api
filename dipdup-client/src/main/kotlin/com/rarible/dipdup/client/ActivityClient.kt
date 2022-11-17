package com.rarible.dipdup.client

import com.rarible.dipdup.client.core.model.DipDupActivity
import com.rarible.dipdup.client.model.DipDupActivitiesPage
import com.rarible.dipdup.client.model.DipDupActivityContinuation
import com.rarible.dipdup.client.model.DipDupSyncSort
import com.rarible.dipdup.client.model.DipDupSyncType
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Integer.min

class ActivityClient(
    val tokenActivityClient: TokenActivityClient,
    val orderActivityClient: OrderActivityClient
) {

    suspend fun getActivitiesSync(
        type: DipDupSyncType?,
        sort: DipDupSyncSort? = DipDupSyncSort.DB_UPDATE_DESC,
        limit: Int,
        continuation: String? = null,
    ) : DipDupActivitiesPage {
        val sortInternal = sort ?: DipDupSyncSort.DB_UPDATE_DESC
        val activities = when (type) {
            DipDupSyncType.NFT -> tokenActivityClient.getActivitiesSync(limit, continuation, sortInternal).activities
            DipDupSyncType.ORDER -> orderActivityClient.getActivitiesSync(limit, continuation, sortInternal).activities
            DipDupSyncType.AUCTION -> emptyList()
            else -> getAllActivitiesSync(sortInternal, limit, continuation)
        }
        return syncPage(activities, sortInternal, limit)
    }

    suspend fun getAllActivitiesSync(
        sort: DipDupSyncSort,
        limit: Int,
        continuation: String?
    ) = coroutineScope {
        val tokens = async { tokenActivityClient.getActivitiesSync(limit, continuation, sort) }
        val orders = async { orderActivityClient.getActivitiesSync(limit, continuation, sort) }
        tokens.await().activities + orders.await().activities
    }


    private fun syncPage(activities: List<DipDupActivity>, sort: DipDupSyncSort, limit: Int): DipDupActivitiesPage {
        var sorted = activities.sortedWith <DipDupActivity> (object : Comparator <DipDupActivity> {
            override fun compare (p0: DipDupActivity, p1: DipDupActivity) : Int {
                val dateCompare = p0.dbUpdatedAt!!.compareTo(p1.dbUpdatedAt!!)
                val sign = when (dateCompare) {
                    0 -> {
                        when {
                            p0.isNftActivity() && !p1.isNftActivity() -> 1
                            !p0.isNftActivity() && p1.isNftActivity() -> -1
                            else -> p0.id.compareTo(p1.id)
                        }
                    }
                    else -> dateCompare
                }
                return when (sort) {
                    DipDupSyncSort.DB_UPDATE_ASC -> sign
                    else -> -1 * sign
                }
            }
        }).subList(0, min(activities.size, limit))

        val nextContinuation = when (sorted.size) {
            limit -> sorted[limit - 1].let {
                DipDupActivityContinuation(it.dbUpdatedAt!!, it.id).toString()
            }
            else -> null
        }
        return DipDupActivitiesPage(
            activities = sorted,
            continuation = nextContinuation
        )
    }

}
