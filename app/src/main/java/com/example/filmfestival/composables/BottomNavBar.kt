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
import com.example.filmfestival.utils.NavigationHelperInterface
import com.example.filmfestival.utils.NavigationRoutes
import com.posthog.PostHog
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

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

        val isTestVariant = remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit) {
            isTestVariant.value = PostHog.getFeatureFlag("android-navbar-color-experiment") == "test"
        }

        val iconColor = if (isTestVariant.value) Color.Blue else MaterialTheme.colorScheme.onBackground

        navIcons.forEach { icon ->
            NavigationBarItem(
                selected = currentRoute == icon.route.name,
                onClick = {
                    PostHog.capture("navigation_bar_clicked")
                    navHelper.navigateBottomBar(icon.route)
                },
                icon = { Icon (
                    imageVector = if (currentRoute == icon.route.name) { icon.iconFilled } else icon.iconOutlined,
                    contentDescription = icon.label,
                    tint = iconColor
                )},
                label = { Text(
                    text = icon.label,
                    fontSize = 12.sp,
                    color = iconColor
                )},
            )
        }
    }
}