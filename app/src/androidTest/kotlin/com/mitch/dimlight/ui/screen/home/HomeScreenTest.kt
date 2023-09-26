package com.mitch.dimlight.ui.screen.home

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertRangeInfoEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.mitch.dimlight.R
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun getString(@StringRes id: Int) = composeTestRule.activity.resources.getString(id)

    @Test
    fun showsOffState_whenBrightnessIsZero() {
        composeTestRule.setContent {
            HomeScreen(
                brightnessLevel = 0,
                onTurnOnFlashlight = { },
                onTurnOffFlashlight = { }
            )
        }

        val expectedValue = 0f
        val expectedRange = 0f..100f

        /*
        * 1) slider is at zero
        * 2) text is 0/maxLevel
        * */
        composeTestRule
            .onNodeWithContentDescription(getString(R.string.change_flashlight_brightness_level))
            .assertIsDisplayed()
            .assertRangeInfoEquals(
                ProgressBarRangeInfo(
                    current = expectedValue,
                    range = expectedRange
                )
            )

        composeTestRule
            .onNodeWithText("0")
            .assertIsDisplayed()
    }
}
