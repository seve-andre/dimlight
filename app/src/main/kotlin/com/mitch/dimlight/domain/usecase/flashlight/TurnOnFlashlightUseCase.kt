package com.mitch.dimlight.domain.usecase.flashlight

import com.mitch.dimlight.domain.model.FlashlightUtils
import com.mitch.dimlight.util.convert
import com.mitch.dimlight.util.flashlight.FlashlightHelper

class TurnOnFlashlightUseCase(
    private val flashlightHelper: FlashlightHelper
) {

    operator fun invoke(level: Int) {
        val brightnessActiveMin = FlashlightUtils.brightnessActiveRange.first

        val adjustedRangeLevel = convert(level)
            .fromRange(FlashlightUtils.brightnessActiveRange)
            .toRange(brightnessActiveMin..flashlightHelper.maxLevel)

        flashlightHelper.turnOn(adjustedRangeLevel)
    }
}
