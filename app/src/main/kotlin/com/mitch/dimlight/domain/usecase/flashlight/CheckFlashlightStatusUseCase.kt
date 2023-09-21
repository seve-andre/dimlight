package com.mitch.dimlight.domain.usecase.flashlight

import com.mitch.dimlight.util.flashlight.FlashlightHelper
import kotlinx.coroutines.flow.Flow

class CheckFlashlightStatusUseCase(
    private val flashlightHelper: FlashlightHelper
) {

    operator fun invoke(): Flow<Boolean> {
        return flashlightHelper.isOn
    }
}
