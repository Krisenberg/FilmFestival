package com.example.filmfestival.screens

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.filmfestival.FakeMainViewModel
import com.example.filmfestival.utils.FakeNavigationHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EditAvatarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeViewModel: FakeMainViewModel
    private lateinit var fakeNavHelper: FakeNavigationHelper

    @Before
    fun setUp() {

        fakeViewModel = FakeMainViewModel()
        fakeNavHelper = FakeNavigationHelper()

        composeTestRule.setContent {
            EditAvatar(
                navHelper = fakeNavHelper,
                viewModel = fakeViewModel
            )
        }
    }

    @Test
    fun testProfilePhotoChanged() = runBlocking {

        Log.d("EditAvatarTest", "Test changing profile photo started")

        val newAvatarUrl = "https://fwcdn.pl/ppo/60/35/1546035/451218.2.jpg"

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Photo of actor with ID 2").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Do you want to set this image as a profile picture?").assertIsDisplayed()

        composeTestRule.onNodeWithText("Yes").performClick()

        composeTestRule.waitForIdle()

        assert(fakeViewModel.getAvatar(1).first() == newAvatarUrl)
    }
}