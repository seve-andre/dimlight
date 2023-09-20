package com.mitch.dimlight.ui.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import com.mitch.dimlight.R
import com.mitch.dimlight.domain.model.BrightnessFixedLevel
import com.mitch.dimlight.ui.theme.custom.padding

@Composable
fun FlashlightBrightnessControls(
    onControlEmit: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = padding.medium,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        ThreeCenteredChipsLayout(
            chip1 = {
                AssistChip(
                    onClick = { onControlEmit(BrightnessFixedLevel.Min.value) },
                    label = { Text(stringResource(R.string.min)) }
                )
            },
            chip2 = {
                AssistChip(
                    onClick = { onControlEmit(BrightnessFixedLevel.Half.value) },
                    label = { Text(stringResource(R.string.half)) }
                )
            },
            chip3 = {
                AssistChip(
                    onClick = { onControlEmit(BrightnessFixedLevel.Max.value) },
                    label = { Text(stringResource(R.string.max)) }
                )
            }
        )
    }
}

@Composable
private fun ThreeCenteredChipsLayout(
    chip1: @Composable () -> Unit,
    chip2: @Composable () -> Unit,
    chip3: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        modifier = modifier,
        content = {
            chip1()
            chip2()
            chip3()
        }
    ) { measurables, constraints ->
        val secondPlaceable = measurables[1].measure(constraints)
        val otherPlaceables = measurables
            .filterIndexed { index, _ -> index != 1 }
            .map { it.measure(constraints) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            secondPlaceable.placeRelative((constraints.maxWidth - secondPlaceable.width) / 2, 0)
            otherPlaceables.forEachIndexed { index, placeable ->
                if (index == 0) {
                    placeable.placeRelative(placeable.width, 0)
                } else {
                    placeable.placeRelative((constraints.maxWidth + placeable.width) / 2, 0)
                }
            }
        }
    }
}
