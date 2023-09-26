package com.mitch.dimlight.di

import android.content.Context
import android.hardware.camera2.CameraManager
import android.service.quicksettings.TileService
import androidx.core.content.getSystemService
import androidx.datastore.preferences.core.edit
import com.mitch.dimlight.data.local.datastore.flashlight.BRIGHTNESS_VALUE
import com.mitch.dimlight.data.local.datastore.flashlight.flashlightDataStore
import com.mitch.dimlight.domain.model.FlashlightUtils
import com.mitch.dimlight.util.convert
import com.mitch.dimlight.util.exception.CameraServiceNotFoundException
import com.mitch.dimlight.util.quicksettings.DimlightTileService
import com.mitch.dimlight.util.quicksettings.TILE_ACTIVE
import com.mitch.dimlight.util.quicksettings.quickSettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Module
@InstallIn(SingletonComponent::class)
object CameraModule {

    @Provides
    @Singleton
    fun providesCameraManagerService(
        @ApplicationContext context: Context
    ): CameraManager {
        val cameraManager = context.getSystemService<CameraManager>()

        val detectChangesCallback = object : CameraManager.TorchCallback() {
            val scope = CoroutineScope(SupervisorJob())
            override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
                if (enabled) {
                    cameraManager?.getTorchStrengthLevel(cameraId)?.let {
                        onTorchStrengthLevelChanged(
                            cameraId,
                            it
                        )
                    }
                } else {
                    scope.launch {
                        context.flashlightDataStore.edit { it[BRIGHTNESS_VALUE] = 0 }
                    }
                }

                scope.launch {
                    context.quickSettingsDataStore.edit { it[TILE_ACTIVE] = enabled }
                }
                TileService.requestListeningState(
                    context,
                    DimlightTileService.getComponentName(context)
                )
            }

            @Suppress("MagicNumber")
            override fun onTorchStrengthLevelChanged(cameraId: String, newStrengthLevel: Int) {
                val brightnessActiveMin = FlashlightUtils.brightnessActiveRange.first
                val adjustedRangeLevel = convert(newStrengthLevel)
                    .fromRange(brightnessActiveMin..45)
                    .toRange(FlashlightUtils.brightnessActiveRange)

                scope.launch {
                    context.flashlightDataStore.edit { it[BRIGHTNESS_VALUE] = adjustedRangeLevel }
                }
            }
        }

        cameraManager?.registerTorchCallback(detectChangesCallback, null)
        return cameraManager ?: throw CameraServiceNotFoundException()
    }
}
