package com.mitch.dimlight.ui.util

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mitch.dimlight.navigation.NavGraphs
import com.mitch.dimlight.navigation.appCurrentDestinationAsState
import com.mitch.dimlight.navigation.appDestination
import com.mitch.dimlight.navigation.destinations.Destination
import com.mitch.dimlight.navigation.startAppDestination
import com.mitch.dimlight.util.SnackbarController
import com.mitch.dimlight.util.network.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberDimlightState(
    networkMonitor: NetworkMonitor,
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): DimlightState {
    return remember(navController, snackbarHostState, coroutineScope, networkMonitor) {
        DimlightState(navController, snackbarHostState, coroutineScope, networkMonitor)
    }
}

// Controls app state. Stable -> if any of the values is changed, the Composables are recomposed
@Stable
class DimlightState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
    val snackbarController: SnackbarController = SnackbarController(
        snackbarHostState,
        coroutineScope
    )
) {
    /**
     * App's current [Destination] if set, otherwise starting destination.
     *
     * Starting destination: search for `@RootNavGraph(start = true)`
     */
    val currentDestination: Destination
        @Composable get() = navController.appCurrentDestinationAsState().value
            ?: NavGraphs.root.startAppDestination

    /**
     * App's previous destination if set, otherwise null
     */
    val prevDestination: Destination?
        @Composable get() = navController.previousBackStackEntry?.appDestination()

    /**
     * Manages app connectivity status
     */
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun goBack() {
        navController.navigateUp()
    }
}
