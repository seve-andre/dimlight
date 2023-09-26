package com.mitch.dimlight.domain.usecase.flashlight

import android.content.Context
import com.mitch.dimlight.data.local.datastore.flashlight.BRIGHTNESS_VALUE
import com.mitch.dimlight.data.local.datastore.flashlight.flashlightDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBrightnessLevelUseCase(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(): Flow<Int> {
        return context.flashlightDataStore.data.map { it[BRIGHTNESS_VALUE] ?: 0 }
    }
}
