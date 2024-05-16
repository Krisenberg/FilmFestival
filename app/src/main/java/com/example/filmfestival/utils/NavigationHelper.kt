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

    fun navigateWithStr(route: NavigationRoutes, arg: String) {
        navController.navigate("${route.name}/${arg}") {
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
            launchSingleTop = true
            restoreState = true
        }
    }

    fun goBack() {
        navController.previousBackStackEntry?.savedStateHandle
        navController.popBackStack()
    }
}