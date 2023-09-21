package com.mitch.dimlight.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.dimlight.domain.usecase.flashlight.FlashlightUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val flashlightUseCases: FlashlightUseCases
) : ViewModel() {

    val isFlashlightOn = flashlightUseCases
        .checkFlashlightStatus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun turnOnFlashlight(level: Int) {
        flashlightUseCases.turnOnFlashlight(level)
    }

    fun turnOffFlashlight() {
        flashlightUseCases.turnOffFlashlight()
    }
}

sealed interface FlashlightState {
    data object On : FlashlightState
    data object Off : FlashlightState
}
