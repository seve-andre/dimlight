package com.mitch.dimlight.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.dimlight.domain.repository.UserSettingsRepository
import com.mitch.dimlight.ui.util.Result
import com.mitch.dimlight.ui.util.asResult
import com.mitch.dimlight.util.DimlightLanguage
import com.mitch.dimlight.util.DimlightTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
) : ViewModel() {

    val uiState = combine(
        userSettingsRepository.getLanguage(),
        userSettingsRepository.getTheme(),
        ::Pair
    ).asResult()
        .map { result ->
            when (result) {
                Result.Loading -> HomeUiState.Loading
                is Result.Success -> {
                    val (language, theme) = result.data

                    HomeUiState.Success(
                        language = language,
                        theme = theme
                    )
                }

                is Result.Error -> HomeUiState.Error()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
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
