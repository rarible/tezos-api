package com.rarible.dipdup.client

import com.rarible.dipdup.client.model.DipDupContinuation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

class DipDupActivityContinuationTest {

    companion object {
        @JvmStatic
        fun data() = Stream.of(
            Arguments.of("", false),
            Arguments.of("1586544422_24276908", false),
            Arguments.of("1586544422_109a80c1-6f3f-4fb7-972c-29a31447c9d6", true),
            Arguments.of("1586544422_c6832e31b067838008ee9c49edcf2401bbe40022f1f26c0926e421b489972aca5d81ddf831eb6bb4ec9ad576dc153ca731f2f6543c952c02f009ee41126e4bc0c1fa8868d6c4ff608b659d4aed6c81eb4d6ddb7818c29c25e50d292d5cb61c4283f65bc3a3d36762990a3e363cd9ac2e92f29cdd58f44c846ddb733da6d3d6f3", false),
        )
    }

    @ParameterizedTest
    @MethodSource("data")
    fun `should convert`(
        input: String,
        valid: Boolean
    ) {
        val continuation = DipDupContinuation.isValid(input)
        assertThat(continuation).isEqualTo(valid)
    }

    @Test
    fun `should parse all`() {
        val continuation = DipDupContinuation.parse("1586544422_109a80c1-6f3f-4fb7-972c-29a31447c9d6")
        assertThat(continuation!!.date).isNotNull
        assertThat(continuation!!.id).isEqualTo(UUID.fromString("109a80c1-6f3f-4fb7-972c-29a31447c9d6"))
    }

    // This test was used for legacy activity
    @Disabled
    @Test
    fun `should parse only date`() {
        val continuation = DipDupContinuation.parse("1586544422_24276908")
        assertThat(continuation!!.date).isNotNull
        assertThat(continuation!!.id).isNull()
    }

}
