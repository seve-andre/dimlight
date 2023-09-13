package com.mitch.dimlight.di

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.core.content.getSystemService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CameraModule {

    @Provides
    fun providesCameraManagerService(
        @ApplicationContext context: Context
    ): CameraManager {
        val cameraManager = context.getSystemService<CameraManager>()
        return cameraManager ?: throw Exception()
    }
}
