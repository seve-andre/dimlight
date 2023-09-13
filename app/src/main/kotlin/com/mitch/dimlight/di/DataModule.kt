package com.mitch.dimlight.di

import com.mitch.dimlight.data.repository.UserSettingsRepositoryImpl
import com.mitch.dimlight.domain.repository.UserSettingsRepository
import com.mitch.dimlight.util.network.ConnectivityManagerNetworkMonitor
import com.mitch.dimlight.util.network.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor

    @Binds
    @Singleton
    abstract fun bindsUserSettingsRepository(
        userSettingsRepositoryImpl: UserSettingsRepositoryImpl
    ): UserSettingsRepository
}
