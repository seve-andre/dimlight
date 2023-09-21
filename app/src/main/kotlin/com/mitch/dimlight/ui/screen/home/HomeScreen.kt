package com.mitch.dimlight.ui.screen.home

import android.service.quicksettings.TileService
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.dimlight.R
import com.mitch.dimlight.data.local.datastore.flashlight.BRIGHTNESS_VALUE
import com.mitch.dimlight.data.local.datastore.flashlight.flashlightDataStore
import com.mitch.dimlight.domain.model.BrightnessFixedLevel
import com.mitch.dimlight.ui.screen.home.components.BrightnessSlider
import com.mitch.dimlight.ui.screen.home.components.BrightnessTextFraction
import com.mitch.dimlight.ui.screen.home.components.FlashlightBrightnessControls
import com.mitch.dimlight.ui.screen.home.components.FlashlightPowerButton
import com.mitch.dimlight.ui.theme.custom.padding
import com.mitch.dimlight.util.quicksettings.DimlightTileService
import com.mitch.dimlight.util.quicksettings.TILE_ACTIVE
import com.mitch.dimlight.util.quicksettings.quickSettingsDataStore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val isFlashlightOn by viewModel.isFlashlightOn.collectAsStateWithLifecycle()

    HomeScreen(
        isFlashlightOn = isFlashlightOn,
        onTurnOnFlashlight = viewModel::turnOnFlashlight,
        onTurnOffFlashlight = viewModel::turnOffFlashlight
    )
}

@Composable
fun HomeScreen(
    isFlashlightOn: Boolean,
    onTurnOnFlashlight: (Int) -> Unit,
    onTurnOffFlashlight: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val brightnessLevelFlow =
        remember { context.flashlightDataStore.data.map { it[BRIGHTNESS_VALUE] ?: 0 } }

    val brightnessLevel by brightnessLevelFlow.collectAsStateWithLifecycle(initialValue = 0)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(padding.small)
    ) {
        // 1) flashlight image with slider
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(R.drawable.flashlight),
                    contentDescription = null
                )
                BrightnessSlider(
                    brightnessLevel = brightnessLevel,
                    onBrightnessChange = { brightness ->
                        coroutineScope.launch {
                            context.flashlightDataStore.edit { it[BRIGHTNESS_VALUE] = brightness }
                        }

                        if (brightness == 0) {
                            onTurnOffFlashlight()
                        } else {
                            onTurnOnFlashlight(brightness)
                        }
                    },
                    onValueChangeFinished = {
                        coroutineScope.launch {
                            context.quickSettingsDataStore.edit { prefs ->
                                prefs[TILE_ACTIVE] = isFlashlightOn
                            }
                        }
                        TileService.requestListeningState(
                            context,
                            DimlightTileService.getComponentName(context)
                        )
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }

        // 2) brightness text and power button
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(padding.large)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(padding.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BrightnessTextFraction(brightnessLevel)
                    FlashlightPowerButton(
                        onClick = {
                            if (isFlashlightOn) {
                                onTurnOffFlashlight()
                                coroutineScope.launch {
                                    context.flashlightDataStore.edit { it[BRIGHTNESS_VALUE] = 0 }
                                }
                            } else {
                                onTurnOnFlashlight(BrightnessFixedLevel.Max.value)
                                coroutineScope.launch {
                                    context.flashlightDataStore.edit {
                                        it[BRIGHTNESS_VALUE] = BrightnessFixedLevel.Max.value
                                    }
                                }
                            }

                            coroutineScope.launch {
                                context.quickSettingsDataStore.edit { prefs ->
                                    prefs[TILE_ACTIVE] = !isFlashlightOn
                                }
                            }
                            TileService.requestListeningState(
                                context,
                                DimlightTileService.getComponentName(context)
                            )
                        },
                        isOn = isFlashlightOn
                    )
                }
            }

            // 3) brightness controls
            FlashlightBrightnessControls(
                onControlEmit = { brightness ->
                    onTurnOnFlashlight(brightness)
                    coroutineScope.launch {
                        context.flashlightDataStore.edit { it[BRIGHTNESS_VALUE] = brightness }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
//    HomeScreen()
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
//    HomeScreen()
}
