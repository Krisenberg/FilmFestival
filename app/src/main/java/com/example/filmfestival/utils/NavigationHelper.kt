package com.example.filmfestival.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

open class NavigationHelper (
    private val navController: NavController
) : NavigationHelperInterface{

    override fun getCurrentRoute(): String? {
        val navBackStackEntry = navController.currentBackStackEntry
        return navBackStackEntry?.destination?.route
    }

    override fun navigate(route: NavigationRoutes) {
        navController.navigate(route.name) {
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun navigateWithId(route: NavigationRoutes, id: Int) {
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

    fun navigateToNewsDetails(route: NavigationRoutes, newsId: String){
        navController.navigate("${route.name}/${newsId}"){
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun navigateBottomBar(route: NavigationRoutes) {
        navController.navigate(route.name) {
            val backStackEntry = navController.previousBackStackEntry?.destination?.route
            if (backStackEntry == route.name) {
                goBack()
            } else {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    override fun goBack() {
        navController.previousBackStackEntry?.savedStateHandle
        navController.popBackStack()
    }
}