package com.mitch.dimlight.util

class RangeConverter private constructor() {
    companion object {
        fun <T> convert(number: T): ValueRangeConverter<T> where T : Number, T : Comparable<T> {
            return ValueRangeConverter(number)
        }
    }
}

class ValueRangeConverter<T>(private val number: T) where T : Number, T : Comparable<T> {

    fun <R> fromRange(initialRange: R): FromRangeConverter<T, R> where R : ClosedRange<T> {
        check(initialRange.contains(number))

        return FromRangeConverter(number, initialRange)
    }
}

class FromRangeConverter<T, R>(
    private val number: T,
    private val initialRange: R
) where T : Number, T : Comparable<T>, R : ClosedRange<T> {

    fun toRange(targetRange: R): T {
        val fromRangeStart = initialRange.start.toDouble()
        val fromRangeEnd = initialRange.endInclusive.toDouble()

        val toRangeStart = targetRange.start.toDouble()
        val toRangeEnd = targetRange.endInclusive.toDouble()

        val ratio = (number.toDouble() - fromRangeStart) / (fromRangeEnd - fromRangeStart)
        val result = toRangeStart + (ratio * (toRangeEnd - toRangeStart))

        @Suppress("UNCHECKED_CAST")
        return result as T
    }
}

fun <T> convert(number: T): ValueRangeConverter<T> where T : Number, T : Comparable<T> {
    return RangeConverter.convert(number)
}
