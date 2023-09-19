package com.mitch.dimlight.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mitch.dimlight.domain.model.BrightnessFixedLevel
import com.mitch.dimlight.ui.screens.home.components.BrightnessSlider
import com.mitch.dimlight.ui.screens.home.components.BrightnessTextFraction
import com.mitch.dimlight.ui.screens.home.components.FlashlightBrightnessControls
import com.mitch.dimlight.ui.screens.home.components.FlashlightImage
import com.mitch.dimlight.ui.screens.home.components.FlashlightPowerButton
import com.mitch.dimlight.ui.theme.custom.padding
import com.mitch.dimlight.util.ToastController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        onTurnOnFlashlight = viewModel::turnOnFlashlight,
        onTurnOffFlashlight = viewModel::turnOffFlashlight
    )
}

@Composable
fun HomeScreen(
    onTurnOnFlashlight: (Int) -> Unit,
    onTurnOffFlashlight: () -> Unit,
    modifier: Modifier = Modifier
) {
    var brightnessLevel by rememberSaveable { mutableIntStateOf(0) }
    val isOn by remember { derivedStateOf { brightnessLevel != 0 } }

    val context = LocalContext.current

    Column(modifier = modifier.fillMaxSize()) {
        // 1) flashlight image with slider
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                FlashlightImage(brightnessLevel)
                BrightnessSlider(
                    brightnessLevel = brightnessLevel,
                    onBrightnessChange = {
                        brightnessLevel = it
                        if (brightnessLevel == 0) {
                            onTurnOffFlashlight()
                        } else {
                            onTurnOnFlashlight(brightnessLevel)
                        }
                    }
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
                    verticalArrangement = Arrangement.spacedBy(padding.small),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BrightnessTextFraction(brightnessLevel)
                    FlashlightPowerButton(
                        onClick = {
                            brightnessLevel = if (isOn) {
                                onTurnOffFlashlight()
                                0
                            } else {
                                onTurnOnFlashlight(BrightnessFixedLevel.Max.value)
                                BrightnessFixedLevel.Max.value
                            }
                        },
                        isOn = isOn
                    )
                }
            }

            // 3) brightness controls
            FlashlightBrightnessControls(
                onControlEmit = {
                    onTurnOnFlashlight(it)
                    brightnessLevel = it

                    ToastController.show(
                        context,
                        "Flashlight brightness set at $it/${BrightnessFixedLevel.Max.value}"
                    )
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
