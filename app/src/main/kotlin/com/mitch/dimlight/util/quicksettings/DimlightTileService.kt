package com.mitch.dimlight.util.quicksettings

import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.datastore.preferences.core.edit
import com.mitch.dimlight.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DimlightTileService : TileService() {

    companion object {
        fun getComponentName(context: Context): ComponentName {
            return ComponentName(context.applicationContext, DimlightTileService::class.java)
        }

        fun getIcon(context: Context): Icon {
            return Icon.createWithResource(context, R.drawable.flashlight_icon)
        }
    }

    // The coroutine scope that's available from onCreate to onDestroy.
    private var coroutineScope: CoroutineScope? = null

    // The job for observing the state change. Available from onStartListening to onStopListening.
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
            dataStore.edit { it[TILE_ADDED] = true }
        }
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        coroutineScope?.launch {
            dataStore.edit { it[TILE_ADDED] = false }
        }
    }

    // Called when the tile should start listening to some state change that it needs to react to.
    // Typically, this is invoked when the app calls [TileService.requestListeningState].
    override fun onStartListening() {
        super.onStartListening()
        listeningJob = coroutineScope?.launch {
            dataStore.data
                .map { prefs -> prefs[TILE_ACTIVE] ?: false }
                .collect { active -> updateTile(active) }
        }
    }

    override fun onStopListening() {
        super.onStopListening()
        listeningJob?.cancel()
    }

    override fun onClick() {
        super.onClick()
        coroutineScope?.launch {
            dataStore.edit { prefs ->
                val newState = !(prefs[TILE_ACTIVE] ?: true)
                prefs[TILE_ACTIVE] = newState
                updateTile(newState)
            }
        }
    }

    private fun updateTile(active: Boolean) {
        val tile = qsTile
        // Update the tile states.
        tile.label = getString(R.string.app_name)
        tile.icon = getIcon(this)
        tile.state = if (active) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        tile.stateDescription = if (active) "Active" else "Inactive"
        // The state updates won't be reflected until we call updateTile.
        tile.updateTile()
    }
}
