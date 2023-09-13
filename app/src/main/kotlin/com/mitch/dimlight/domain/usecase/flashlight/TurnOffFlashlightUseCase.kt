package com.mitch.dimlight.domain.usecase.flashlight

import com.mitch.dimlight.util.flashlight.FlashlightHelper

class TurnOffFlashlightUseCase(
    private val flashlightHelper: FlashlightHelper
) {

    operator fun invoke() {
        flashlightHelper.turnOff()
    }
}
