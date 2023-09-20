package com.mitch.dimlight.domain.model

sealed class BrightnessFixedLevel(val value: Int) {
    data object Min : BrightnessFixedLevel(FlashlightUtils.brightnessActiveRange.first)
    data object Half : BrightnessFixedLevel(FlashlightUtils.brightnessActiveRange.average().toInt())
    data object Max : BrightnessFixedLevel(FlashlightUtils.brightnessActiveRange.last)
}

object FlashlightUtils {

    private val brightnessRange = 0..100
    val brightnessActiveRange = brightnessRange.first + 1..brightnessRange.last
}
