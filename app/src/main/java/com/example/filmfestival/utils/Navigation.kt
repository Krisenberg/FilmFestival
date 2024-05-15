package com.example.filmfestival.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.screens.EditUser
import com.example.filmfestival.screens.HomeScreen
import com.example.filmfestival.screens.MovieDetails
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
        composable(
            route = "${NavigationRoutes.MOVIE_DETAILS.name}/{movieId}",
            arguments = listOf(navArgument(name = "movieId") { type = NavType.IntType})
        ){ args ->
            val movieId = args.arguments?.getInt("movieId") ?: 1
            MovieDetails(navController = navController, viewModel = viewModel, movieId = movieId)
        }
        composable(NavigationRoutes.USER_PROFILE.name) {
            UserProfile(navController = navController, viewModel = viewModel)
        }
        composable(NavigationRoutes.NEWS_DETAILS.name){
            //TODO - navgiation tutorial with arguments
            //TODO - passing username as a parameter
            //TODO - passing news as a parameter
        }
        composable(
            "${NavigationRoutes.USER_PROFILE_EDIT.name}/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: " "
            EditUser(navController = navController, viewModel = viewModel, username = username)
        }
    }
}