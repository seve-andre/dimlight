package com.mitch.dimlight.util.flashlight

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.datastore.preferences.core.edit
import com.mitch.dimlight.domain.usecase.flashlight.FlashlightUseCases
import com.mitch.dimlight.util.quicksettings.TILE_ADDED
import com.mitch.dimlight.util.quicksettings.quickSettingsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlashlightService : Service() {

    @Inject
    lateinit var flashlightUseCases: FlashlightUseCases

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            flashlightUseCases
                .checkFlashlightStatus()
                .collect { isActive -> quickSettingsDataStore.edit { it[TILE_ADDED] = isActive } }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
