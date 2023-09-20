package com.mitch.dimlight.ui.screen.home.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mitch.dimlight.R
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Power

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashlightPowerButton(
    onClick: () -> Unit,
    isOn: Boolean,
    modifier: Modifier = Modifier
) {
    val turnOnOffStringId = if (isOn) {
        R.string.turn_off_flashlight
    } else {
        R.string.turn_on_flashlight
    }

    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip {
                Text(stringResource(turnOnOffStringId))
            }
        },
        state = rememberTooltipState()
    ) {
        FilledIconButton(
            onClick = onClick,
            modifier = modifier
                .height(80.dp)
                .width(80.dp)
        ) {
            Icon(
                imageVector = EvaIcons.Outline.Power,
                contentDescription = stringResource(turnOnOffStringId)
            )
        }
    }
}
