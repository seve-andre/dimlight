package com.mitch.dimlight.util.flashlight

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CameraManagerFlashlightHelper @Inject constructor(
    private val cameraManager: CameraManager
) : FlashlightHelper {
    private val cameraId = cameraManager.cameraIdList[0]
        ?: throw CameraAccessException(CameraAccessException.CAMERA_ERROR)
    private val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)

    override val isOn: Flow<Boolean> = flow {
        emit(cameraManager.getTorchStrengthLevel(cameraId) > 0)
    }

    override val maxLevel: Int
        get() {
            return cameraCharacteristics[CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL]
                ?: throw CameraAccessException(CameraAccessException.CAMERA_ERROR)
        }

    override fun turnOn(level: Int) {
        require(level in 1..maxLevel)
        cameraManager.turnOnTorchWithStrengthLevel(cameraId, level)
    }

    override fun turnOff() {
        cameraManager.setTorchMode(cameraId, false)
    }
}
