package com.example.filmfestival.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

class NavigationHelper (
    private val navController: NavController
){

    fun getCurrentRoute(): String? {
        val navBackStackEntry = navController.currentBackStackEntry
        return navBackStackEntry?.destination?.route
    }

    fun navigate(route: NavigationRoutes) {
        navController.navigate(route.name) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateWithId(route: NavigationRoutes, id: Int) {
        navController.navigate("${route.name}/${id}") {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToTrailerFullscreen(route: NavigationRoutes, trailerId: String, playbackTime: Float){
        navController.navigate("${route.name}/${trailerId}/${playbackTime}"){
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateWithNews(route: NavigationRoutes, imageRes: Int, date: String, text: String, description: String){
        navController.navigate("${route.name}/${imageRes}/${date}/${text}/${description}"){
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateBottomBar(route: NavigationRoutes) {
        navController.navigate(route.name) {
//            navController.popBackStack(
//                destinationId = navController.graph.findStartDestination().id,
//                inclusive = false
//            )
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun goBack() {
        navController.previousBackStackEntry?.savedStateHandle
        navController.popBackStack()
    }
}