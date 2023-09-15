package com.mitch.dimlight.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mitch.dimlight.domain.model.FlashlightDimFixedLevel
import com.mitch.dimlight.ui.theme.custom.padding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Power

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
    var currentLevel by remember { mutableIntStateOf(0) }
    val isOn by remember { derivedStateOf { currentLevel != 0 } }

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(2f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding.medium)
            ) {
                Slider(
                    modifier = Modifier.semantics { contentDescription = "Localized Description" },
                    value = currentLevel.toFloat(),
                    onValueChange = { currentLevel = it.toInt() },
                    valueRange = 0f..100f,
                    onValueChangeFinished = {
                        if (currentLevel == 0) {
                            onTurnOffFlashlight()
                        } else {
                            onTurnOnFlashlight(currentLevel)
                        }
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f),
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$currentLevel",
                            fontSize = 18.sp
                        )
                        Text(
                            text = "/100",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    FilledIconButton(
                        onClick = {
                            currentLevel = if (isOn) {
                                onTurnOffFlashlight()
                                0
                            } else {
                                onTurnOnFlashlight(FlashlightDimFixedLevel.Max.value)
                                FlashlightDimFixedLevel.Max.value
                            }
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                    ) {
                        Icon(
                            imageVector = EvaIcons.Outline.Power,
                            contentDescription = "Turn flashlight ${if (isOn) "on" else "off"}"
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = padding.small,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                AssistChip(
                    onClick = {
                        onTurnOnFlashlight(FlashlightDimFixedLevel.Min.value)
                        currentLevel = FlashlightDimFixedLevel.Min.value
                    },
                    label = { Text(text = "Min") }
                )

                AssistChip(
                    onClick = {
                        onTurnOnFlashlight(FlashlightDimFixedLevel.Half.value)
                        currentLevel = FlashlightDimFixedLevel.Half.value
                    },
                    label = { Text(text = "Half") }
                )

                AssistChip(
                    onClick = {
                        onTurnOnFlashlight(FlashlightDimFixedLevel.Max.value)
                        currentLevel = FlashlightDimFixedLevel.Max.value
                    },
                    label = { Text(text = "Max") }
                )
            }
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
