package com.mitch.dimlight.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.dimlight.domain.model.DimlightLanguage
import com.mitch.dimlight.domain.model.DimlightTheme
import com.mitch.dimlight.domain.repository.UserSettingsRepository
import com.mitch.dimlight.ui.util.Result
import com.mitch.dimlight.ui.util.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
) : ViewModel() {

    val uiState = userSettingsRepository.settingsData.asResult()
        .map { result ->
            when (result) {
                Result.Loading -> SettingsUiState.Loading
                is Result.Success -> SettingsUiState.Success(result.data)
                is Result.Error -> SettingsUiState.Error
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsUiState.Loading
        )

    fun updateTheme(theme: DimlightTheme) {
        viewModelScope.launch {
            userSettingsRepository.setTheme(theme)
        }
    }

    fun updateLanguage(language: DimlightLanguage) {
        viewModelScope.launch {
            userSettingsRepository.setLanguage(language)
        }
    }
}
