package com.mitch.dimlight.util.extension

fun Int.convertFromRangeToAnother(original: IntRange, target: IntRange): Int {
    val ratio = this.toFloat() / (original.last - original.first)
    return (ratio * (target.last - target.first)).toInt() + 1
}
