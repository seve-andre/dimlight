package com.mitch.dimlight.ui.screens.home

import com.mitch.dimlight.util.DimlightLanguage
import com.mitch.dimlight.util.DimlightTheme

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Error(
        val error: String? = null
    ) : HomeUiState

    data class Success(
        val language: DimlightLanguage,
        val theme: DimlightTheme
    ) : HomeUiState
}
