package com.mitch.dimlight.domain.repository

import com.mitch.dimlight.domain.model.DimlightLanguage
import com.mitch.dimlight.domain.model.DimlightTheme
import com.mitch.dimlight.domain.model.SettingsData
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    val settingsData: Flow<SettingsData>

    suspend fun setTheme(theme: DimlightTheme)
    fun setLanguage(language: DimlightLanguage)
}
