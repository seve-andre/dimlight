package com.mitch.dimlight.domain.model

import com.mitch.dimlight.util.DimlightLanguage
import com.mitch.dimlight.util.DimlightTheme

data class SettingsData(
    val theme: DimlightTheme,
    val language: DimlightLanguage
)
