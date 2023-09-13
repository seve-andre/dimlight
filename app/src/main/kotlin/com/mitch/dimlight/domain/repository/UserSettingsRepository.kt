package com.mitch.dimlight.domain.repository

import com.mitch.dimlight.util.DimlightLanguage
import com.mitch.dimlight.util.DimlightTheme
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    fun getTheme(): Flow<DimlightTheme>
    suspend fun setTheme(theme: DimlightTheme)

    fun getLanguage(): Flow<DimlightLanguage>
    fun setLanguage(language: DimlightLanguage)
}
