package com.example.filmfestival

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.filmfestival.utils.NavigationRoutes
import com.example.filmfestival.screens.HomeScreen
import com.example.filmfestival.screens.MovieSet
import com.example.filmfestival.utils.CreateNavigationGraph
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

//@Composable
//fun App() {
//    val navController = rememberNavController()
//    val viewModel = hiltViewModel<MainViewModel>()
////    NavHost(navController = navController, startDestination = NavigationRoutes.HOME_SCREEN.name) {
////        composable(NavigationRoutes.HOME_SCREEN.name) {
////            HomeScreen(navController = navController)
////        }
////        composable(NavigationRoutes.MOVIE_SET.name) {
////            MovieSet(navController = navController)
////        }
////        composable(NavigationRoutes.USER_PROFILE.name) {
////            MovieSet(navController = navController)
////        }
////    }
//}

@HiltAndroidApp
class MyApp: Application()
