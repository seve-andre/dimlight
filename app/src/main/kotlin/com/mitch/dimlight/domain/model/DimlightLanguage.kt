package com.mitch.dimlight.domain.model

import androidx.annotation.DrawableRes
import com.mitch.dimlight.R
import java.util.Locale

enum class DimlightLanguage(
    val locale: Locale,
    @DrawableRes val flagId: Int
) {
    ENGLISH(locale = Locale.ENGLISH, flagId = R.drawable.english_flag),
    ITALIAN(locale = Locale.ITALIAN, flagId = R.drawable.italian_flag);

    companion object {
        fun fromLocale(locale: Locale): DimlightLanguage {
            return values().find { it.locale == locale } ?: default()
        }

        fun default(): DimlightLanguage {
            return ENGLISH
        }
    }
}
