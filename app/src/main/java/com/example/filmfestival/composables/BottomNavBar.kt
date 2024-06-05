package com.example.filmfestival.composables

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.filmfestival.utils.NavigationHelper
import com.example.filmfestival.utils.NavigationRoutes

sealed class BottomNavItem(val route: NavigationRoutes, val iconOutlined: ImageVector,
                           val iconFilled: ImageVector, val label: String){
    data object Home : BottomNavItem(NavigationRoutes.HOME_SCREEN, Icons.Outlined.Home,
        Icons.Filled.Home, "Home")
    data object Movies : BottomNavItem(NavigationRoutes.MOVIE_SET, Icons.Outlined.Movie,
        Icons.Filled.Movie, "Movies")
    data object Profile : BottomNavItem(NavigationRoutes.USER_PROFILE, Icons.Outlined.PermIdentity,
        Icons.Filled.PermIdentity, "Profile")
}

@Composable
fun BottomNavBar(
    navHelper: NavigationHelper,
){
    NavigationBar (
        containerColor = Color.Transparent
    ){
        val currentRoute = navHelper.getCurrentRoute()

        val navIcons = listOf(BottomNavItem.Movies, BottomNavItem.Home, BottomNavItem.Profile)
        navIcons.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route.name,
                onClick = {
                    navHelper.navigateBottomBar(item.route)
                },
                icon = { Icon (
                    imageVector = if (currentRoute == item.route.name) { item.iconFilled } else item.iconOutlined,
                    contentDescription = item.label,
                    tint = MaterialTheme.colorScheme.onBackground
                )},
                label = { Text(
                    text = item.label,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )},
            )
        }
    }
}