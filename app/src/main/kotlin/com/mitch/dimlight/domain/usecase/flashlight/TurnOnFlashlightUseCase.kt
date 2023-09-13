package com.mitch.dimlight.domain.usecase.flashlight

import com.mitch.dimlight.domain.model.FlashlightDimLevel
import com.mitch.dimlight.util.flashlight.FlashlightHelper

class TurnOnFlashlightUseCase(
    private val flashlightHelper: FlashlightHelper
) {

    operator fun invoke(level: FlashlightDimLevel) {
        val adjustedRange = level.convertToFlashlightRange(1..flashlightHelper.maxLevel)
        flashlightHelper.turnOn(adjustedRange)
    }
}
