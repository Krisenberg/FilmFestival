package com.example.filmfestival

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.filmfestival.utils.CreateNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val activity = (LocalContext.current as Activity)
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                val navController = rememberNavController()
                val viewModel = hiltViewModel<MainViewModel>()
                CreateNavigationGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}