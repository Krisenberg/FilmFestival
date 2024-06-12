package com.example.filmfestival.screens

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import com.example.filmfestival.FakeMainViewModel
import com.example.filmfestival.utils.FakeNavigationHelper
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserProfileTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeViewModel: FakeMainViewModel
    private lateinit var fakeNavHelper: FakeNavigationHelper

    @Before
    fun setUp() {

        fakeViewModel = FakeMainViewModel()
        fakeNavHelper = FakeNavigationHelper()

        composeTestRule.setContent {
            UserProfile(
                navHelper = fakeNavHelper,
                viewModel = fakeViewModel
            )
        }
    }

    @Test
    fun testRemoveShowFromWatchlist() = runBlocking {

        Log.d("UserProfileTest", "Test removing from watchlist started")

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Movie 4").performTouchInput {
            down(center)
            moveBy(Offset(x = -1000f, y = 0f))
            up()
        }

        composeTestRule.waitForIdle()

        assertTrue(fakeViewModel.removeMovieFromWatchlistCalled)
    }

    @Test
    fun testRemoveShowFromTickets() = runBlocking {

        Log.d("UserProfileTest", "Test removing from tickets started")

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Tickets").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithText("Movie 1")[0].performTouchInput {
            down(center)
            moveBy(Offset(x = -1000f, y = 0f))
            up()
        }

        composeTestRule.waitForIdle()

        assertTrue(fakeViewModel.removeUsersTicketCalled)
    }
}