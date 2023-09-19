package com.mitch.dimlight.ui.screen.home.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Power

@Composable
fun FlashlightPowerButton(
    onClick: () -> Unit,
    isOn: Boolean,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier
            .height(80.dp)
            .width(80.dp)
    ) {
        Icon(
            imageVector = EvaIcons.Outline.Power,
            contentDescription = "Turn flashlight ${if (isOn) "on" else "off"}"
        )
    }
}
