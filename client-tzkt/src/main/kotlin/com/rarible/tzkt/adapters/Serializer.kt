package com.rarible.tzkt.adapters

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.rarible.tzkt.model.Account
import com.rarible.tzkt.model.AccountTypes
import com.rarible.tzkt.models.Contract
import com.rarible.tzkt.model.Delegate
import com.rarible.tzkt.models.Ghost
import com.rarible.tzkt.models.User

object Serializer {
    @JvmStatic
    val moshiBuilder: Moshi.Builder = Moshi.Builder()
        .add(OffsetDateTimeAdapter())
        .add(LocalDateTimeAdapter())
        .add(LocalDateAdapter())
        .add(UUIDAdapter())
        .add(ByteArrayAdapter())
        .add(URIAdapter())
        .add(KotlinJsonAdapterFactory())
        .add(BigDecimalAdapter())
        .add(BigIntegerAdapter())
        .add(
            PolymorphicJsonAdapterFactory.of(Account::class.java, "type")
                .withSubtype(User::class.java, AccountTypes.USER.value)
                .withSubtype(Contract::class.java, AccountTypes.CONTRACT.value)
                .withSubtype(Delegate::class.java, AccountTypes.DELEGATE.value)
                .withSubtype(Ghost::class.java, AccountTypes.GHOST.value)
        )

    @JvmStatic
    val moshi: Moshi by lazy {
        moshiBuilder.build()
    }
}
