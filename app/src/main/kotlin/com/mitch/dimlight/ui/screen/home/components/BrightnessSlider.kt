package com.mitch.dimlight.ui.screen.home.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.mitch.dimlight.R
import com.mitch.dimlight.ui.theme.custom.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrightnessSlider(
    brightnessLevel: Int,
    onBrightnessChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onValueChangeFinished: (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val sliderDescription = stringResource(R.string.change_flashlight_brightness_level)

    Slider(
        modifier = modifier
            .padding(padding.medium)
            .semantics {
                contentDescription = sliderDescription
            },
        value = brightnessLevel.toFloat(),
        onValueChange = {
            onBrightnessChange(it.toInt())
        },
        valueRange = 0f..100f,
        interactionSource = interactionSource,
        thumb = {
            SliderDefaults.Thumb(
                interactionSource = interactionSource,
                thumbSize = DpSize(40.dp, 40.dp)
            )
        },
        onValueChangeFinished = onValueChangeFinished
    )
}
