package com.mitch.dimlight.util

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class RangeConverterTest {

    @Test
    fun `when number is not in initial range, should throw Exception`() {
        assertFailure {
            convert(0).fromRange(1..10)
        }
    }

    @Test
    fun `when number is 0, converted number should be 0`() {
        val result = convert(0).fromRange(0..100).toRange(0..10)

        assertThat(result).isEqualTo(0)
    }

    @ParameterizedTest
    @CsvSource(
        "10, 100",
        "1, 1"
    )
    fun `test result is correct`(number: Int, expected: Int) {
        val result = convert(number).fromRange(1..10).toRange(1..100)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test result has correct type`() {
        val result1 = convert(0).fromRange(0..100).toRange(0..10)
        val result2 = convert(0f).fromRange(0f..100f).toRange(0f..10f)
        val result3 = convert(0.0).fromRange(0.0..100.0).toRange(0.0..10.0)
        val result4 = convert(0L).fromRange(0L..100L).toRange(0L..10L)

        assertThat(result1).isInstanceOf(Int::class)
        assertThat(result2).isInstanceOf(Float::class)
        assertThat(result3).isInstanceOf(Double::class)
        assertThat(result4).isInstanceOf(Long::class)
    }
}
