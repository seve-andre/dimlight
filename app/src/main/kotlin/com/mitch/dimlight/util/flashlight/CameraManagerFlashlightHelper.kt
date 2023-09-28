package com.mitch.dimlight.util.flashlight

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import androidx.datastore.preferences.core.edit
import com.mitch.dimlight.data.local.datastore.flashlight.BRIGHTNESS_VALUE
import com.mitch.dimlight.data.local.datastore.flashlight.flashlightDataStore
import com.mitch.dimlight.domain.model.BrightnessFixedLevel
import com.mitch.dimlight.domain.model.FlashlightUtils
import com.mitch.dimlight.util.convert
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

class CameraManagerFlashlightHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cameraManager: CameraManager
) : FlashlightHelper {

    private val cameraId = cameraManager.cameraIdList[0]
        ?: throw CameraAccessException(CameraAccessException.CAMERA_ERROR)

    private val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)

    override val maxLevel: Int =
        cameraCharacteristics[CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL]
            ?: throw CameraAccessException(CameraAccessException.CAMERA_ERROR)

    init {
        cameraManager.registerTorchCallback(detectChangesCallback(), null)
    }

    private fun detectChangesCallback() =
        object : CameraManager.TorchCallback() {
            val scope = CoroutineScope(SupervisorJob())
            override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
                Timber.d("torch - onTorchModeChanged() - enabled: $enabled")

                if (enabled) {
                    onTorchStrengthLevelChanged(
                        cameraId,
                        cameraManager.getTorchStrengthLevel(cameraId)
                    )
                } else {
                    scope.launch {
                        context.flashlightDataStore.edit { it[BRIGHTNESS_VALUE] = 0 }
                    }
                }
            }

            override fun onTorchStrengthLevelChanged(cameraId: String, newStrengthLevel: Int) {
                val brightnessActiveMin = FlashlightUtils.brightnessActiveRange.first
                val adjustedRangeLevel = convert(newStrengthLevel)
                    .fromRange(brightnessActiveMin..maxLevel)
                    .toRange(FlashlightUtils.brightnessActiveRange)

                Timber.d("torch - onTorchStrengthLevelChanged - old: $newStrengthLevel, new: $adjustedRangeLevel")

                scope.launch {
                    context.flashlightDataStore.edit { it[BRIGHTNESS_VALUE] = adjustedRangeLevel }
                }
            }
        }

    override fun turnOn(level: Int) {
        val minLevel = BrightnessFixedLevel.Min.value
        require(level in minLevel..maxLevel)
        cameraManager.turnOnTorchWithStrengthLevel(cameraId, level)
    }

    override fun turnOff() {
        cameraManager.setTorchMode(cameraId, false)
    }
}
