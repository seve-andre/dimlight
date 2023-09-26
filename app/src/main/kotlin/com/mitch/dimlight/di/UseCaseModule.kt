package com.mitch.dimlight.di

import android.content.Context
import com.mitch.dimlight.domain.usecase.flashlight.FlashlightUseCases
import com.mitch.dimlight.domain.usecase.flashlight.GetBrightnessLevelUseCase
import com.mitch.dimlight.domain.usecase.flashlight.TurnOffFlashlightUseCase
import com.mitch.dimlight.domain.usecase.flashlight.TurnOnFlashlightUseCase
import com.mitch.dimlight.util.flashlight.FlashlightHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun providesFlashlightUseCases(
        @ApplicationContext context: Context,
        flashlightHelper: FlashlightHelper
    ): FlashlightUseCases {
        return FlashlightUseCases(
            getBrightnessLevel = GetBrightnessLevelUseCase(context),
            turnOnFlashlight = TurnOnFlashlightUseCase(flashlightHelper),
            turnOffFlashlight = TurnOffFlashlightUseCase(flashlightHelper)
        )
    }
}
