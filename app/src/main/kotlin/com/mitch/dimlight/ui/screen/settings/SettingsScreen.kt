package com.mitch.dimlight.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.dimlight.R
import com.mitch.dimlight.domain.model.SettingsData
import com.mitch.dimlight.ui.screen.settings.components.LanguagePickerDialog
import com.mitch.dimlight.ui.screen.settings.components.ThemePickerDialog
import com.mitch.dimlight.ui.util.components.loading.LoadingScreen
import com.mitch.dimlight.util.DimlightLanguage
import com.mitch.dimlight.util.DimlightTheme
import com.ramcosta.composedestinations.annotation.Destination
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowBack

@Destination
@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onChangeTheme = viewModel::updateTheme,
        onChangeLanguage = viewModel::updateLanguage
    )
}

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onBackClick: () -> Unit,
    onChangeTheme: (DimlightTheme) -> Unit,
    onChangeLanguage: (DimlightLanguage) -> Unit
) {
    when (uiState) {
        SettingsUiState.Loading -> LoadingScreen()
        is SettingsUiState.Success -> SettingsContent(
            settings = uiState.settings,
            onBackClick = onBackClick,
            onChangeTheme = onChangeTheme,
            onChangeLanguage = onChangeLanguage
        )

        SettingsUiState.Error -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    settings: SettingsData,
    onBackClick: () -> Unit,
    onChangeTheme: (DimlightTheme) -> Unit,
    onChangeLanguage: (DimlightLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    val (activeDialog, setActiveDialog) = remember {
        mutableStateOf<ActiveDialog>(
            ActiveDialog.None
        )
    }

    when (activeDialog) {
        ActiveDialog.None -> Unit

        ActiveDialog.Language -> {
            LanguagePickerDialog(
                selectedLanguage = settings.language,
                onDismiss = { setActiveDialog(ActiveDialog.None) },
                onConfirm = onChangeLanguage
            )
        }

        ActiveDialog.Theme -> {
            ThemePickerDialog(
                selectedTheme = settings.theme,
                onDismiss = { setActiveDialog(ActiveDialog.None) },
                onConfirm = onChangeTheme
            )
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = EvaIcons.Outline.ArrowBack,
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.settings))
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(text = "theme: ${settings.theme}")
            Text(text = "lang: ${settings.language}")
        }
    }
}

sealed interface ActiveDialog {
    data object None : ActiveDialog
    data object Language : ActiveDialog
    data object Theme : ActiveDialog
}
