package com.mitch.dimlight.ui.screen.settings

import com.mitch.dimlight.domain.model.SettingsData

sealed interface SettingsUiState {
    data object Loading : SettingsUiState

    data class Success(
        val settings: SettingsData
    ) : SettingsUiState

    data object Error : SettingsUiState
}
