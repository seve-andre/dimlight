package com.mitch.dimlight.data.mapper

import com.mitch.dimlight.util.DimlightLanguage
import java.util.Locale

fun Locale.toAppLanguage(): DimlightLanguage {
    // removes country code and variants if present
    val localeLanguageOnly = Locale.forLanguageTag(this.language)

    return DimlightLanguage.fromLocale(localeLanguageOnly)
}
