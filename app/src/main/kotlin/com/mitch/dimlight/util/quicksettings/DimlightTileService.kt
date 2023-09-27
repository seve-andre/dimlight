package com.mitch.dimlight.util.quicksettings

import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.datastore.preferences.core.edit
import com.mitch.dimlight.R
import com.mitch.dimlight.domain.model.BrightnessFixedLevel
import com.mitch.dimlight.domain.usecase.flashlight.FlashlightUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DimlightTileService : TileService() {

    companion object {
        fun getComponentName(context: Context): ComponentName {
            return ComponentName(context.applicationContext, DimlightTileService::class.java)
        }

        fun getIcon(context: Context): Icon {
            return Icon.createWithResource(context, R.drawable.flashlight_icon)
        }
    }

    @Inject
    lateinit var flashlightUseCases: FlashlightUseCases

    private var coroutineScope: CoroutineScope? = null
    private var listeningJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope?.cancel()
    }

    // Called when the tile is added to the Quick Settings by the user.
    // Note that this won't be called when the tile was added by
    // [StatusBarManager.requestAddTileService()].
    override fun onTileAdded() {
        super.onTileAdded()
        coroutineScope?.launch {
            quickSettingsDataStore.edit { it[TILE_ADDED] = true }
        }
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        coroutineScope?.launch {
            quickSettingsDataStore.edit { it[TILE_ADDED] = false }
        }
    }

    // Called when the tile should start listening to some state change that it needs to react to.
    // Typically, this is invoked when the app calls [TileService.requestListeningState].
    override fun onStartListening() {
        super.onStartListening()
        listeningJob = coroutineScope?.launch {
            quickSettingsDataStore.data
                .map { prefs -> prefs[TILE_ACTIVE] ?: false }
                .collect { active -> updateTileAppearance(active) }
        }
    }

    override fun onStopListening() {
        super.onStopListening()
        listeningJob?.cancel()
    }

    override fun onClick() {
        super.onClick()
        coroutineScope?.launch {
            quickSettingsDataStore.edit { prefs ->
                val newState = !(prefs[TILE_ACTIVE] ?: true)
                updateTileAndPerformAction(newState)
            }
        }
    }

    private fun updateTileAppearance(shouldBeActivated: Boolean) {
        val tile = this.qsTile
        tile.label = getString(R.string.app_name)
        tile.icon = getIcon(this)
        tile.state = if (shouldBeActivated) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        tile.stateDescription =
            if (shouldBeActivated) getString(R.string.active) else getString(R.string.inactive)

        // The state updates won't be reflected until we call updateTile.
        tile.updateTile()
    }

    private fun performTileAction(shouldBeActivated: Boolean) {
        if (shouldBeActivated) {
            flashlightUseCases.turnOnFlashlight(BrightnessFixedLevel.Max.value)
        } else {
            flashlightUseCases.turnOffFlashlight()
        }
    }

    private fun updateTileAndPerformAction(shouldBeActivated: Boolean) {
        updateTileAppearance(shouldBeActivated)
        performTileAction(shouldBeActivated)
    }
}
