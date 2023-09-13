package com.mitch.dimlight.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mitch.dimlight.domain.model.FlashlightDimLevel
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
    onTurnOnFlashlight: (FlashlightDimLevel) -> Unit,
    onTurnOffFlashlight: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onTurnOnFlashlight(FlashlightDimLevel.Max) }) {
            Text(text = "Turn on")
        }

        Button(onClick = onTurnOffFlashlight) {
            Text(text = "Turn off")
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
