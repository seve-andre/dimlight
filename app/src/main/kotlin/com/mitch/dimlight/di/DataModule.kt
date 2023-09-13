package com.mitch.dimlight.di

import com.mitch.dimlight.data.repository.UserSettingsRepositoryImpl
import com.mitch.dimlight.domain.repository.UserSettingsRepository
import com.mitch.dimlight.util.flashlight.CameraManagerFlashlightHelper
import com.mitch.dimlight.util.flashlight.FlashlightHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsUserSettingsRepository(
        userSettingsRepositoryImpl: UserSettingsRepositoryImpl
    ): UserSettingsRepository

    @Binds
    abstract fun bindsFlashlightHelper(
        cameraManagerFlashlightHelper: CameraManagerFlashlightHelper
    ): FlashlightHelper
}
