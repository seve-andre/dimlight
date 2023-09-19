package com.mitch.dimlight.ui.screen.home

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Error(
        val error: String? = null
    ) : HomeUiState

    data object Success : HomeUiState
}
