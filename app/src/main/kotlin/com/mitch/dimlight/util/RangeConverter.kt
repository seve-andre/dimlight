package com.mitch.dimlight.util

class RangeConverter private constructor() {
    companion object {
        fun <T> convert(number: T): ValueRangeConverter<T> where T : Number, T : Comparable<T> {
            return ValueRangeConverter(number)
        }
    }
}

class ValueRangeConverter<T>(private val number: T) where T : Number, T : Comparable<T> {

    fun <R> fromRange(initialRange: R): InitialRangeConverter<T, R> where R : ClosedRange<T> {
        check(initialRange.contains(number)) { "Number $number is not in initial range $initialRange" }

        return InitialRangeConverter(number, initialRange)
    }
}

class InitialRangeConverter<T, R>(
    private val number: T,
    private val initialRange: R
) where T : Number, T : Comparable<T>, R : ClosedRange<T> {

    fun toRange(targetRange: R): T {
        val fromRangeStart = initialRange.start.toDouble()
        val fromRangeEnd = initialRange.endInclusive.toDouble()

        val toRangeStart = targetRange.start.toDouble()
        val toRangeEnd = targetRange.endInclusive.toDouble()

        // 𝐺 = ((𝑆−𝑆𝑚𝑖𝑛)⋅(𝐺𝑚𝑎𝑥−𝐺𝑚𝑖𝑛)) / (𝑆𝑚𝑎𝑥−𝑆𝑚𝑖𝑛) + 𝐺𝑚𝑖𝑛
        val result = (((number.toDouble() - fromRangeStart) * (toRangeEnd - toRangeStart)) / (fromRangeEnd - fromRangeStart)) + toRangeStart

        @Suppress("UNCHECKED_CAST")
        return result as T
    }
}

fun <T> convert(number: T): ValueRangeConverter<T> where T : Number, T : Comparable<T> {
    return RangeConverter.convert(number)
}
