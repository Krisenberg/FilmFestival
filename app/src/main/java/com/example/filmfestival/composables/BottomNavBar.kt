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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import com.example.filmfestival.utils.NavigationHelper
import com.example.filmfestival.utils.NavigationHelperInterface
import com.example.filmfestival.utils.NavigationRoutes

sealed class BottomNavIcon(val route: NavigationRoutes, val iconOutlined: ImageVector,
                           val iconFilled: ImageVector, val label: String){
    data object Home : BottomNavIcon(NavigationRoutes.HOME_SCREEN, Icons.Outlined.Home,
        Icons.Filled.Home, "Home")
    data object Movies : BottomNavIcon(NavigationRoutes.MOVIE_SET, Icons.Outlined.Movie,
        Icons.Filled.Movie, "Movies")
    data object Profile : BottomNavIcon(NavigationRoutes.USER_PROFILE, Icons.Outlined.PermIdentity,
        Icons.Filled.PermIdentity, "Profile")
}

@Composable
fun BottomNavBar(
    navHelper: NavigationHelperInterface,
){
    NavigationBar (
        containerColor = Color.Transparent
    ){
        val currentRoute = navHelper.getCurrentRoute()
        val navIcons = listOf(BottomNavIcon.Movies, BottomNavIcon.Home, BottomNavIcon.Profile)

        navIcons.forEach { icon ->
            NavigationBarItem(
                selected = currentRoute == icon.route.name,
                onClick = {
                    navHelper.navigateBottomBar(icon.route)
                },
                icon = { Icon (
                    imageVector = if (currentRoute == icon.route.name) { icon.iconFilled } else icon.iconOutlined,
                    contentDescription = icon.label,
                    tint = MaterialTheme.colorScheme.onBackground
                )},
                label = { Text(
                    text = icon.label,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )},
            )
        }
    }
}