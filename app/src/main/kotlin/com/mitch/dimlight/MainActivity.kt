package com.mitch.dimlight

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mitch.dimlight.navigation.NavGraphs
import com.mitch.dimlight.navigation.destinations.SettingsRouteDestination
import com.mitch.dimlight.ui.theme.DimlightMaterialTheme
import com.mitch.dimlight.ui.theme.custom.LocalPadding
import com.mitch.dimlight.ui.theme.custom.padding
import com.mitch.dimlight.ui.util.rememberDimlightState
import com.mitch.dimlight.util.DimlightTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Settings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        /* Must be called before super.onCreate()
         *
         * Splashscreen look in res/values/themes.xml
         */
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        setContent {
            val isThemeDark = shouldUseDarkTheme(uiState)

            CompositionLocalProvider(LocalPadding provides padding) {
                DimlightMaterialTheme(
                    isThemeDark = isThemeDark
                ) {
                    val appState = rememberDimlightState()

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = stringResource(R.string.app_name))
                                },
                                actions = {
                                    TooltipBox(
                                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                                        tooltip = {
                                            PlainTooltip {
                                                Text("Show settings")
                                            }
                                        },
                                        state = rememberTooltipState()
                                    ) {
                                        IconButton(
                                            onClick = {
                                                appState.navController.navigate(SettingsRouteDestination)
                                            }
                                        ) {
                                            Icon(
                                                imageVector = EvaIcons.Outline.Settings,
                                                contentDescription = "Show settings"
                                            )
                                        }
                                    }
                                }
                            )
                        },
                        snackbarHost = { SnackbarHost(appState.snackbarHostState) }
                    ) { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            DestinationsNavHost(
                                navGraph = NavGraphs.root,
                                navController = appState.navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> when (uiState.theme) {
        DimlightTheme.DARK -> true
        DimlightTheme.LIGHT -> false
        DimlightTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    }
}
