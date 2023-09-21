package com.mitch.dimlight.di

import com.mitch.dimlight.domain.usecase.flashlight.CheckFlashlightStatusUseCase
import com.mitch.dimlight.domain.usecase.flashlight.FlashlightUseCases
import com.mitch.dimlight.domain.usecase.flashlight.TurnOffFlashlightUseCase
import com.mitch.dimlight.domain.usecase.flashlight.TurnOnFlashlightUseCase
import com.mitch.dimlight.util.flashlight.FlashlightHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun providesFlashlightUseCases(
        flashlightHelper: FlashlightHelper
    ): FlashlightUseCases {
        return FlashlightUseCases(
            turnOnFlashlight = TurnOnFlashlightUseCase(flashlightHelper),
            turnOffFlashlight = TurnOffFlashlightUseCase(flashlightHelper),
            checkFlashlightStatus = CheckFlashlightStatusUseCase(flashlightHelper)
        )
    }
}
