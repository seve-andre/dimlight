package com.mitch.dimlight.util.flashlight

import kotlinx.coroutines.flow.Flow

interface FlashlightHelper {
    val hasPermission: Flow<Boolean>
    val maxLevel: Int
    fun turnOn(level: Int)
    fun turnOff()
}
