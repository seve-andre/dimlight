package com.mitch.dimlight.domain.usecase.flashlight

import com.mitch.dimlight.domain.model.FlashlightDimFixedLevel
import com.mitch.dimlight.util.convert
import com.mitch.dimlight.util.flashlight.FlashlightHelper

class TurnOnFlashlightUseCase(
    private val flashlightHelper: FlashlightHelper
) {

    operator fun invoke(level: Int) {
        val flashlightMin = FlashlightDimFixedLevel.Min.value
        val flashlightMax = FlashlightDimFixedLevel.Max.value

        val adjustedRangeLevel = convert(level)
            .fromRange(flashlightMin..flashlightMax)
            .toRange(1..flashlightHelper.maxLevel)

        flashlightHelper.turnOn(adjustedRangeLevel)
    }
}
