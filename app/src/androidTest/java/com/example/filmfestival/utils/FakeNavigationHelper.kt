package com.example.filmfestival.utils

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.example.filmfestival.utils.NavigationHelper
import com.example.filmfestival.utils.NavigationHelperInterface
import com.example.filmfestival.utils.NavigationRoutes

class FakeNavigationHelper : NavigationHelperInterface {

    val lastRoute = mutableStateOf<NavigationRoutes?>(null)
    override fun navigateWithId(route: NavigationRoutes, id: Int){
        lastRoute.value = route
    }

    override fun goBack() {

    }

    override fun navigate(route: NavigationRoutes) {
        lastRoute.value = route
    }

    override fun navigateBottomBar(route: NavigationRoutes) {
        lastRoute.value = route
    }

    override fun getCurrentRoute(): String? {
        return lastRoute.value?.name
    }
}