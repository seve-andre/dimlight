package com.mitch.dimlight.domain.usecase.flashlight

import com.mitch.dimlight.util.extension.convertFromRangeToAnother
import com.mitch.dimlight.util.flashlight.FlashlightHelper

class TurnOnFlashlightUseCase(
    private val flashlightHelper: FlashlightHelper
) {

    operator fun invoke(level: Int) {
        val adjustedRangeLevel = level.convertFromRangeToAnother(1..100, 1..flashlightHelper.maxLevel)
        flashlightHelper.turnOn(adjustedRangeLevel)
    }
}
