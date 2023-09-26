package com.mitch.dimlight.domain.usecase.flashlight

data class FlashlightUseCases(
    val getBrightnessLevel: GetBrightnessLevelUseCase,
    val turnOnFlashlight: TurnOnFlashlightUseCase,
    val turnOffFlashlight: TurnOffFlashlightUseCase
)
