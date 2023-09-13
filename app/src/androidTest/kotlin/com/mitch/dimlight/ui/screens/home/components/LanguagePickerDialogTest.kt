package com.mitch.dimlight.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.dimlight.util.DimlightLanguage
import org.junit.Rule
import org.junit.Test

class LanguagePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showsAllLanguageOptionsAreDisplayed() {
        composeTestRule.setContent {
            LanguagePickerDialog(
                selectedLanguage = DimlightLanguage.default(),
                onDismiss = { },
                onConfirm = { }
            )
        }

        // all languages and their flags are displayed
        DimlightLanguage.values().forEach { language ->
            composeTestRule.onNodeWithText(language.locale.displayLanguage).assertIsDisplayed()

            composeTestRule
                .onNodeWithTag(
                    testTag = language.flagId.toString(),
                    useUnmergedTree = true
                )
                .assertIsDisplayed()
        }

        // default language is selected
        composeTestRule
            .onNodeWithText(DimlightLanguage.default().locale.displayLanguage)
            .assertIsSelected()
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        composeTestRule.setContent {
            LanguagePickerDialog(
                selectedLanguage = DimlightLanguage.default(),
                onDismiss = { },
                onConfirm = { }
            )
        }

        val newOption = DimlightLanguage.values().filter { it != DimlightLanguage.default() }[0]

        // click on new one
        composeTestRule
            .onNodeWithText(newOption.locale.displayLanguage)
            .performClick()

        // old one is not selected
        composeTestRule
            .onNodeWithText(DimlightLanguage.default().locale.displayLanguage)
            .assertIsNotSelected()

        // but new one is
        composeTestRule
            .onNodeWithText(newOption.locale.displayLanguage)
            .assertIsSelected()
    }
}
