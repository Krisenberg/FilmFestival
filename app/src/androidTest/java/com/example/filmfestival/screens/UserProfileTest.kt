package com.example.filmfestival.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.performTouchInput
import androidx.test.espresso.action.ViewActions.swipeLeft
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Show
import com.example.filmfestival.repos.FakeMainViewModel
import com.example.filmfestival.repos.FakeNavigationHelper
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class UserProfileTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeViewModel: FakeMainViewModel
    private lateinit var fakeNavHelper: FakeNavigationHelper
//    private lateinit var tickets: List<Triple<Movie, LocalDateTime, Show>>

    @Before
    fun setUp() {

        fakeViewModel = FakeMainViewModel()
        fakeNavHelper = FakeNavigationHelper()
//        val movie = Movie(1, "Test Movie", "poster.jpg", "photo.jpg", 2024, 160, "Genre", "Description")
//        val show = Show(1, 1, "2023-06-01T12:00:00")
//        val ticket = Triple(movie, LocalDateTime.parse(show.dateTime), show)
//        tickets = listOf(ticket)
    }

    @Test
    fun testRemoveShowFromTickets() = runBlocking {

//        fakeViewModel.setUsersTickets(tickets)

        composeTestRule.setContent {
            UserProfile(
                navHelper = fakeNavHelper,
                viewModel = fakeViewModel
            )
        }

        composeTestRule.onNodeWithText("Movie 1").performGesture {
            swipeLeft()
        }

        assertTrue(fakeViewModel.removeUsersTicketCalled)
    }
}