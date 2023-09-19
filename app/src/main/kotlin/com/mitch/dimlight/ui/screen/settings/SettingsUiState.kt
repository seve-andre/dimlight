package com.mitch.dimlight.ui.screen.settings

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data object Success : SettingsUiState
    data object Error : SettingsUiState
}
