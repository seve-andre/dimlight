package com.mitch.dimlight.util.flashlight

interface FlashlightHelper {
    val hasPermission: Boolean
    fun turnOn(level: Int = 1)
}
