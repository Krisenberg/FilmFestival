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
import com.example.filmfestival.screens.News
import com.example.filmfestival.screens.NewsScreen
import com.example.filmfestival.screens.TrailerFullscreen
import com.example.filmfestival.screens.UserProfile

enum class NavigationRoutes {
    HOME_SCREEN,
    MOVIE_DETAILS,
    NEWS_DETAILS,
    MOVIE_SET,
    USER_PROFILE,
    USER_PROFILE_EDIT,
    USER_PHOTO_GALLERY,
    TRAILER_FULLSCREEN
}

@Composable
fun CreateNavigationGraph(
    navController: NavHostController,
    viewModel: MainViewModel
){
    val navHelper = NavigationHelper(navController)

    NavHost(navController = navController, startDestination = NavigationRoutes.HOME_SCREEN.name) {
        composable(NavigationRoutes.HOME_SCREEN.name) {
            HomeScreen(navHelper = navHelper, viewModel = viewModel)
        }
        composable(NavigationRoutes.MOVIE_SET.name) {
            MovieSet(navHelper = navHelper, viewModel = viewModel)
        }
        composable(
            route = "${NavigationRoutes.MOVIE_DETAILS.name}/{movieId}",
            arguments = listOf(navArgument(name = "movieId") { type = NavType.IntType})
        ){ args ->
            val movieId = args.arguments?.getInt("movieId") ?: 1
            MovieDetails(navHelper = navHelper, viewModel = viewModel, movieId = movieId)
        }
        composable(NavigationRoutes.USER_PROFILE.name) {
            UserProfile(navHelper = navHelper, viewModel = viewModel)
        }
        composable(NavigationRoutes.USER_PROFILE_EDIT.name) {
            EditUser(navHelper = navHelper, viewModel = viewModel)
        }
        composable(
            route = "${NavigationRoutes.TRAILER_FULLSCREEN.name}/{trailerId}/{movieId}/{playbackTime}",
            arguments = listOf(
                navArgument(name = "trailerId") { type = NavType.StringType},
                navArgument(name = "movieId") { type = NavType.IntType},
                navArgument(name = "playbackTime") { type = NavType.StringType}
            )
        ){ args ->
            val trailerId = args.arguments?.getString("trailerId")
            val movieId = args.arguments?.getInt("movieId")
            val playbackTime = args.arguments?.getString("playbackTime")!!.toFloat()

            TrailerFullscreen(navHelper = navHelper, trailerId = trailerId!!, movieId = movieId!!, playbackTime = playbackTime)
        }
        composable(
            route = "${NavigationRoutes.NEWS_DETAILS.name}/{imageRes}/{date}/{text}/{description}",
            arguments = listOf(
                navArgument(name = "imageRes"){ type = NavType.IntType},
                navArgument(name = "date"){ type = NavType.StringType},
                navArgument(name = "text"){ type = NavType.StringType},
                navArgument(name = "description"){ type = NavType.StringType}
            )
        ){args->
            val imageRes = args.arguments?.getInt("imageRes") ?: 1
            val date = args.arguments?.getString("date") ?: "Date"
            val text = args.arguments?.getString("text") ?: "Text"
            val description = args.arguments?.getString("description") ?: "Description"
            NewsScreen(navHelper = navHelper, viewModel = viewModel, imageRes = imageRes, date = date, text = text, description = description)
            //TODO - navgiation tutorial with arguments
            //TODO - passing username as a parameter
            //TODO - passing news as a parameter
        }
//        composable(
//            "${NavigationRoutes.USER_PROFILE_EDIT.name}/{username}",
//            arguments = listOf(navArgument("username") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val username = backStackEntry.arguments?.getString("username") ?: " "
//            EditUser(navHelper = navHelper, viewModel = viewModel, username = username)
//        }
    }
}