package com.mitch.dimlight.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        AssistChip(
            onClick = { onControlEmit(BrightnessFixedLevel.Min.value) },
            label = { Text(text = "Min") }
        )

        AssistChip(
            onClick = { onControlEmit(BrightnessFixedLevel.Half.value) },
            label = { Text(text = "Half") }
        )

        AssistChip(
            onClick = { onControlEmit(BrightnessFixedLevel.Max.value) },
            label = { Text(text = "Max") }
        )
    }
}
