package com.example.filmfestival.utils

import androidx.navigation.NavController
import com.example.filmfestival.utils.NavigationHelper
import com.example.filmfestival.utils.NavigationHelperInterface
import com.example.filmfestival.utils.NavigationRoutes

class FakeNavigationHelper : NavigationHelperInterface {
    override fun navigateWithId(route: NavigationRoutes, id: Int){

    }

    override fun goBack() {

    }

    override fun navigate(route: NavigationRoutes) {

    }

    override fun navigateBottomBar(route: NavigationRoutes) {

    }

    override fun getCurrentRoute(): String? {
        return null
    }
}