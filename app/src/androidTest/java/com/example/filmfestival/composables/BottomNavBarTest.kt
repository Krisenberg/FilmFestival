package com.example.filmfestival.composables

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.filmfestival.utils.FakeNavigationHelper
import com.example.filmfestival.utils.NavigationRoutes
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BottomNavBarTest{

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeNavHelper: FakeNavigationHelper

    @Before
    fun setUp() {
        fakeNavHelper = FakeNavigationHelper()

        composeTestRule.setContent {
            BottomNavBar(navHelper = fakeNavHelper)
        }

    }

    @Test
    fun testNavigateToHome() {
        composeTestRule.onNodeWithText("Home").performClick()
        assertEquals(NavigationRoutes.HOME_SCREEN, fakeNavHelper.lastRoute.value)
    }

    @Test
    fun testNavigateToMovies() {
        composeTestRule.onNodeWithText("Movies").performClick()
        assertEquals(NavigationRoutes.MOVIE_SET, fakeNavHelper.lastRoute.value)
    }

    @Test
    fun testNavigateToProfile() {
        composeTestRule.onNodeWithText("Profile").performClick()
        assertEquals(NavigationRoutes.USER_PROFILE, fakeNavHelper.lastRoute.value)
    }
}