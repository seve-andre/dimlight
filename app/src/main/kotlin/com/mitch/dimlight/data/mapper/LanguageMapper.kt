package com.mitch.dimlight.data.mapper

import com.mitch.dimlight.util.DimlightLanguage
import java.util.Locale

fun Locale.toAppLanguage(): DimlightLanguage {
    return DimlightLanguage.fromLocale(this)
}
