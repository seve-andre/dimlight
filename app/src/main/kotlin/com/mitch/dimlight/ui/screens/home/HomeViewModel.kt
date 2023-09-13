package com.mitch.dimlight.ui.screens.home

import androidx.lifecycle.ViewModel
import com.mitch.dimlight.domain.model.FlashlightDimLevel
import com.mitch.dimlight.domain.usecase.flashlight.FlashlightUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val flashlightUseCases: FlashlightUseCases
) : ViewModel() {

    fun turnOnFlashlight(level: FlashlightDimLevel) {
        flashlightUseCases.turnOnFlashlight(level)
    }

    fun turnOffFlashlight() {
        flashlightUseCases.turnOffFlashlight()
    }
}
