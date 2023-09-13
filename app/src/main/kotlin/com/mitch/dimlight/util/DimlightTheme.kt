package com.mitch.dimlight.util

import androidx.annotation.StringRes
import com.mitch.dimlight.R

enum class DimlightTheme(
    @StringRes val translationId: Int
) {
    FOLLOW_SYSTEM(R.string.system_default),
    LIGHT(R.string.light_theme),
    DARK(R.string.dark_theme);

    companion object {
        fun default(): DimlightTheme {
            return FOLLOW_SYSTEM
        }
    }
}
