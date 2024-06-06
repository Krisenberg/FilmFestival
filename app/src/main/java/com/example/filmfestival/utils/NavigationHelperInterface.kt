package com.example.filmfestival.utils

import androidx.navigation.NavGraph.Companion.findStartDestination

interface NavigationHelperInterface {
    fun navigateWithId(route: NavigationRoutes, id: Int)

    fun goBack()

    fun navigate(route: NavigationRoutes)

    fun navigateBottomBar(route: NavigationRoutes)

    fun getCurrentRoute(): String?
}