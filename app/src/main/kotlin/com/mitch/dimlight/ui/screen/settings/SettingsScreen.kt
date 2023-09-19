package com.mitch.dimlight.ui.screen.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mitch.dimlight.ui.screen.home.HomeViewModel
import com.mitch.dimlight.ui.screen.settings.components.LanguagePickerDialog
import com.mitch.dimlight.ui.screen.settings.components.ThemePickerDialog
import com.mitch.dimlight.util.DimlightLanguage
import com.mitch.dimlight.util.DimlightTheme
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    SettingsScreen()
}

@Composable
fun SettingsScreen() {
    val (activeDialog, setActiveDialog) = rememberSaveable {
        mutableStateOf<ActiveDialog>(
            ActiveDialog.None
        )
    }

    when (activeDialog) {
        ActiveDialog.None -> Unit

        ActiveDialog.Language -> {
            LanguagePickerDialog(
                selectedLanguage = DimlightLanguage.ENGLISH,
                onDismiss = { setActiveDialog(ActiveDialog.None) },
                onConfirm = { }
            )
        }

        ActiveDialog.Theme -> {
            ThemePickerDialog(
                selectedTheme = DimlightTheme.LIGHT,
                onDismiss = { setActiveDialog(ActiveDialog.None) },
                onConfirm = { }
            )
        }
    }
}

sealed interface ActiveDialog {
    data object None : ActiveDialog
    data object Language : ActiveDialog
    data object Theme : ActiveDialog
}
