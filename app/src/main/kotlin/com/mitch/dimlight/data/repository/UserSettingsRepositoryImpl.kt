package com.mitch.dimlight.data.repository

import com.mitch.dimlight.data.local.LanguageLocalDataSource
import com.mitch.dimlight.data.local.datastore.user.preferences.UserPreferencesLocalDataSource
import com.mitch.dimlight.data.mapper.toAppLanguage
import com.mitch.dimlight.data.mapper.toDomainModel
import com.mitch.dimlight.data.mapper.toProtoModel
import com.mitch.dimlight.domain.model.DimlightLanguage
import com.mitch.dimlight.domain.model.DimlightTheme
import com.mitch.dimlight.domain.model.SettingsData
import com.mitch.dimlight.domain.repository.UserSettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

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
            val theme = it.first.toDomainModel()
            val language = it.second.toAppLanguage()

            SettingsData(
                theme = theme,
                language = language
            )
        }

    override suspend fun setTheme(theme: DimlightTheme) {
        userPreferencesLocalDataSource.setProtoTheme(theme.toProtoModel())
    }

    override fun setLanguage(language: DimlightLanguage) {
        languageLocalDataSource.setLocale(language.locale)
    }
}
