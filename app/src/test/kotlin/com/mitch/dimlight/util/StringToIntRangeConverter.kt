package com.mitch.dimlight.util

import org.junit.jupiter.params.converter.TypedArgumentConverter

class StringToIntRangeConverter :
    TypedArgumentConverter<String, IntRange>(String::class.java, IntRange::class.java) {

    override fun convert(source: String?): IntRange {
        val rangeValues = source?.split("..")
        val rangeStart = rangeValues?.get(0)?.toInt()
        val rangeEnd = rangeValues?.get(1)?.toInt()

        return if (rangeStart != null && rangeEnd != null) {
            IntRange(rangeStart, rangeEnd)
        } else {
            IntRange(0, 0)
        }
    }
}
