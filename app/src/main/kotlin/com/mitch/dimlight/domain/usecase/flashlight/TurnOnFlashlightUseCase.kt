package com.mitch.dimlight.domain.usecase.flashlight

import com.mitch.dimlight.util.convert
import com.mitch.dimlight.util.flashlight.FlashlightHelper

class TurnOnFlashlightUseCase(
    private val flashlightHelper: FlashlightHelper
) {

    operator fun invoke(level: Int) {
        val adjustedRangeLevel = convert(level)
            .fromRange(1..100)
            .toRange(1..flashlightHelper.maxLevel)

        flashlightHelper.turnOn(adjustedRangeLevel)
    }
}
