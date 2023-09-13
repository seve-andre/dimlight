package com.mitch.dimlight.util.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import androidx.core.content.getSystemService
import javax.inject.Inject
import timber.log.Timber

class CameraManagerFlashlightHelper @Inject constructor(
    private val context: Context
) : FlashlightHelper {
    override val hasPermission: Boolean
        get() = false

    override fun turnOn(level: Int) {
        val camera = context.getSystemService<CameraManager>() as CameraManager
        val cameraId = camera.cameraIdList[0] ?: ""
        Timber.d("cameraId is $cameraId")

        val characteristics = camera.getCameraCharacteristics(cameraId)

        val maxLevel = characteristics[CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL]
        val defaultLevel = characteristics[CameraCharacteristics.FLASH_INFO_STRENGTH_DEFAULT_LEVEL]
        Timber.d("max level is $maxLevel and default level is $defaultLevel")

        // camera?.turnOnTorchWithStrengthLevel("", 1)
    }
}
