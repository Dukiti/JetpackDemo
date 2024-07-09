package com.dungnm.example.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.InstrumentationRegistry
import com.dungnm.example.compose.constants.Tags
import com.dungnm.example.compose.ui.activity.CommonViewModel
import com.dungnm.example.compose.ui.activity.home.HomeScreen
import com.dungnm.example.compose.ui.theme.MainAppTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            MainAppTheme(currentTheme = Tags.THEME_LIGHT) {
                HomeScreen().Screen(viewModel = CommonViewModel())
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.onNodeWithText(
            context.getString(R.string.label_login)
        ).performClick()

        composeTestRule.onNodeWithText("Remember Me").assertIsDisplayed()
    }
}