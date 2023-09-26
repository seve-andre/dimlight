package com.mitch.dimlight.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.dimlight.R
import com.mitch.dimlight.domain.model.BrightnessFixedLevel
import com.mitch.dimlight.ui.screen.home.components.BrightnessSlider
import com.mitch.dimlight.ui.screen.home.components.BrightnessTextFraction
import com.mitch.dimlight.ui.screen.home.components.FlashlightBrightnessControls
import com.mitch.dimlight.ui.screen.home.components.FlashlightPowerButton
import com.mitch.dimlight.ui.theme.custom.padding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val brightnessLevel by viewModel.brightnessLevel.collectAsStateWithLifecycle()

    HomeScreen(
        brightnessLevel = brightnessLevel,
        onTurnOnFlashlight = viewModel::turnOnFlashlight,
        onTurnOffFlashlight = viewModel::turnOffFlashlight
    )
}

@Composable
fun HomeScreen(
    brightnessLevel: Int,
    onTurnOnFlashlight: (Int) -> Unit,
    onTurnOffFlashlight: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFlashlightOn by remember { derivedStateOf { brightnessLevel > 0 } }

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
                        if (brightness == 0) {
                            onTurnOffFlashlight()
                        } else {
                            onTurnOnFlashlight(brightness)
                        }
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
                            } else {
                                onTurnOnFlashlight(BrightnessFixedLevel.Max.value)
                            }
                        },
                        isOn = isFlashlightOn
                    )
                }
            }

            // 3) brightness controls
            FlashlightBrightnessControls(
                onControlEmit = { brightness ->
                    onTurnOnFlashlight(brightness)
                }
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    HomeScreen(
        brightnessLevel = 0,
        onTurnOnFlashlight = { },
        onTurnOffFlashlight = { }
    )
}
