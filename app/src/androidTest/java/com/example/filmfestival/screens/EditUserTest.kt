package com.example.filmfestival.screens

import android.util.Log
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performImeAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.filmfestival.FakeMainViewModel
import com.example.filmfestival.utils.FakeNavigationHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditUserTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeViewModel: FakeMainViewModel
    private lateinit var fakeNavHelper: FakeNavigationHelper

    @Before
    fun setUp() {

        fakeViewModel = FakeMainViewModel()
        fakeNavHelper = FakeNavigationHelper()

        composeTestRule.setContent {
            EditUser(
                navHelper = fakeNavHelper,
                viewModel = fakeViewModel
            )
        }
    }

    @Test
    fun testUsernameChanged() = runBlocking {

        Log.d("EditUserTest", "Test changing username started")

        val newUsername = "newUsername"

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("usernameTextField").performTextClearance()

        composeTestRule.onNodeWithTag("usernameTextField").performTextInput(newUsername)

        composeTestRule.onNodeWithTag("usernameTextField").performImeAction()

        composeTestRule.waitForIdle()

        assert(fakeViewModel.getUsername(1).first() == newUsername) { "Username was not updated correctly" }
    }
}