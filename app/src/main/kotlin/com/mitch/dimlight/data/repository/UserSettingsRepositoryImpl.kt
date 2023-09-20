package com.mitch.dimlight.data.repository

import com.mitch.dimlight.data.local.LanguageLocalDataSource
import com.mitch.dimlight.data.local.datastore.user.preferences.UserPreferencesLocalDataSource
import com.mitch.dimlight.data.mapper.toAppLanguage
import com.mitch.dimlight.data.mapper.toLocal
import com.mitch.dimlight.data.mapper.toProto
import com.mitch.dimlight.domain.model.SettingsData
import com.mitch.dimlight.domain.repository.UserSettingsRepository
import com.mitch.dimlight.util.DimlightLanguage
import com.mitch.dimlight.util.DimlightTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsRepositoryImpl @Inject constructor(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource,
    private val languageLocalDataSource: LanguageLocalDataSource
) : UserSettingsRepository {

    override val settingsData: Flow<SettingsData>
        get() = combine(
            userPreferencesLocalDataSource.getProtoTheme(),
            languageLocalDataSource.getLocale(),
            ::Pair
        ).map {
            val theme = it.first.toLocal()
            val language = it.second.toAppLanguage()

            SettingsData(
                theme = theme,
                language = language
            )
        }

    override suspend fun setTheme(theme: DimlightTheme) {
        userPreferencesLocalDataSource.setProtoTheme(theme.toProto())
    }

    override fun setLanguage(language: DimlightLanguage) {
        languageLocalDataSource.setLocale(language.locale)
    }
}
