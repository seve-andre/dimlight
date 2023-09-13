package com.mitch.dimlight.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.dimlight.util.DimlightTheme
import org.junit.Rule
import org.junit.Test

class ThemePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun getString(@StringRes id: Int) = composeTestRule.activity.resources.getString(id)

    @Test
    fun showsAllThemeOptionsAreDisplayed() {
        composeTestRule.setContent {
            ThemePickerDialog(
                selectedTheme = DimlightTheme.default(),
                onDismiss = { },
                onConfirm = { }
            )
        }

        // all themes are displayed
        DimlightTheme.values().forEach { theme ->
            composeTestRule.onNodeWithText(getString(theme.translationId)).assertIsDisplayed()
        }

        // default theme is selected
        composeTestRule
            .onNodeWithText(getString(DimlightTheme.default().translationId))
            .assertIsSelected()
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        composeTestRule.setContent {
            ThemePickerDialog(
                selectedTheme = DimlightTheme.default(),
                onDismiss = { },
                onConfirm = { }
            )
        }

        val newOption = DimlightTheme.values().filter { it != DimlightTheme.default() }[0]

        // click on new one
        composeTestRule
            .onNodeWithText(getString(newOption.translationId))
            .performClick()

        // old one is not selected
        composeTestRule
            .onNodeWithText(getString(DimlightTheme.default().translationId))
            .assertIsNotSelected()

        // but new one is
        composeTestRule
            .onNodeWithText(getString(newOption.translationId))
            .assertIsSelected()
    }
}
