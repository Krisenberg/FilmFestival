package com.example.filmfestival.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.screens.HomeScreen
import com.example.filmfestival.screens.MovieSet
import com.example.filmfestival.screens.UserProfile

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
    viewModel: MainViewModel
){
    NavHost(navController = navController, startDestination = NavigationRoutes.HOME_SCREEN.name) {
        composable(NavigationRoutes.HOME_SCREEN.name) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavigationRoutes.MOVIE_SET.name) {
            MovieSet(navController = navController, viewModel = viewModel)
        }
        composable(NavigationRoutes.USER_PROFILE.name) {
            UserProfile(navController = navController, viewModel = viewModel)
        }
    }
}