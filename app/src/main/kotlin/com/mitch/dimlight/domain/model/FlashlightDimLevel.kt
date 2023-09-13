package com.mitch.dimlight.domain.model

import com.mitch.dimlight.util.extension.convertFromRangeToAnother

sealed class FlashlightDimLevel(val value: Int) {
    data object Min : FlashlightDimLevel(1)
    data object Half : FlashlightDimLevel(50)
    data object Max : FlashlightDimLevel(100)

    fun convertToFlashlightRange(flashlightRange: IntRange): Int {
        val min = Min.value
        val max = Max.value

        return this.value.convertFromRangeToAnother(min..max, flashlightRange)
    }
}
