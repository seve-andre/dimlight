package com.mitch.dimlight.domain.model

sealed class FlashlightDimFixedLevel(val value: Int) {
    data object Min : FlashlightDimFixedLevel(1)
    data object Half : FlashlightDimFixedLevel(50)
    data object Max : FlashlightDimFixedLevel(100)
}
