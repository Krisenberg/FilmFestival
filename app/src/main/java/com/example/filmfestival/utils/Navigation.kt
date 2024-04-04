package com.example.filmfestival.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.filmfestival.screens.HomeScreen
import com.example.filmfestival.screens.MovieSet

enum class NavigationRoutes {
    HOME_SCREEN,
    MOVIE_DETAILS,
    NEWS_DETAILS,
    MOVIE_SET,
    USER_PROFILE,
    USER_PROFILE_EDIT,
    USER_PHOTO_GALLERY
}

@Composable
fun CreateNavigationGraph(
    navController: NavHostController,
){
    NavHost(navController = navController, startDestination = NavigationRoutes.HOME_SCREEN.name) {
        composable(NavigationRoutes.HOME_SCREEN.name) {
            HomeScreen(navController = navController)
        }
        composable(NavigationRoutes.MOVIE_SET.name) {
            MovieSet(navController = navController)
        }
        composable(NavigationRoutes.USER_PROFILE.name) {
            MovieSet(navController = navController)
        }
    }
}