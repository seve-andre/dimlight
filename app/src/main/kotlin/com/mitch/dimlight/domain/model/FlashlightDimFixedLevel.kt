package com.mitch.dimlight.domain.model

import com.mitch.dimlight.util.extension.convertFromRangeToAnother

sealed class FlashlightDimFixedLevel(val value: Int) {
    data object Min : FlashlightDimFixedLevel(1)
    data object Half : FlashlightDimFixedLevel(50)
    data object Max : FlashlightDimFixedLevel(100)

    fun convertToFlashlightRange(flashlightRange: IntRange): Int {
        val min = Min.value
        val max = Max.value

        return this.value.convertFromRangeToAnother(min..max, flashlightRange)
    }
}
