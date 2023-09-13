package com.mitch.dimlight.domain.usecase.flashlight

data class FlashlightUseCases(
    val turnOnFlashlight: TurnOnFlashlightUseCase,
    val turnOffFlashlight: TurnOffFlashlightUseCase
)
