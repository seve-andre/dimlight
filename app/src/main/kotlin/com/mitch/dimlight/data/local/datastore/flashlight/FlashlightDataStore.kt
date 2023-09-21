package com.mitch.dimlight.data.local.datastore.flashlight

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

internal val Context.flashlightDataStore by preferencesDataStore(name = "flashlight")

/** Whether the tile has been added to the Quick Settings. */
internal val BRIGHTNESS_VALUE = intPreferencesKey("flashlight_value")
