package com.mitch.dimlight.util

class RangeConverter<T> where T : Number, T : Comparable<T> {
    private lateinit var numberToConvert: T
    private lateinit var fromRange: ClosedRange<T>
    private lateinit var toRange: ClosedRange<T>

    fun convert(number: T): RangeConverter<T> {
        this.numberToConvert = number
        return this
    }

    fun fromRange(range: ClosedRange<T>): RangeConverter<T> {
        check(range.contains(numberToConvert))

        this.fromRange = range
        return this
    }

    fun toRange(range: ClosedRange<T>): T {
        this.toRange = range
        return convertFromRangeToRange()
    }

    private fun convertFromRangeToRange(): T {
        val fromRangeStart = fromRange.start.toDouble()
        val fromRangeEnd = fromRange.endInclusive.toDouble()

        val toRangeStart = toRange.start.toDouble()
        val toRangeEnd = toRange.endInclusive.toDouble()

        val ratio = (numberToConvert.toDouble() - fromRangeStart) / (fromRangeEnd - fromRangeStart)
        val result = toRangeStart + (ratio * (toRangeEnd - toRangeStart))

        @Suppress("UNCHECKED_CAST")
        return result as T
    }
}

fun <T> convert(number: T): RangeConverter<T> where T : Number, T : Comparable<T> {
    return RangeConverter<T>().convert(number)
}
