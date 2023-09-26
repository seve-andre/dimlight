package com.mitch.dimlight.util.flashlight

interface FlashlightHelper {
    val maxLevel: Int
    fun turnOn(level: Int)
    fun turnOff()
}
