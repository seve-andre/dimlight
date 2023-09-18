package com.mitch.dimlight.domain.model

sealed class BrightnessFixedLevel(val value: Int) {
    data object Min : BrightnessFixedLevel(value = FlashlightUtils.brightnessActiveRange.first)
    data object Half : BrightnessFixedLevel(value = FlashlightUtils.brightnessActiveRange.average().toInt())
    data object Max : BrightnessFixedLevel(value = FlashlightUtils.brightnessActiveRange.last)
}

object FlashlightUtils {

    private val brightnessRange = 0..100
    val brightnessActiveRange = brightnessRange.first + 1..brightnessRange.last

    val brightnessDrawableRange = 0..5
}
