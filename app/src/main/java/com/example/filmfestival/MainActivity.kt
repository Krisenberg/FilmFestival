package com.example.filmfestival

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.filmfestival.ui.theme.FilmFestivalTheme
import com.example.filmfestival.utils.CreateNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmFestivalTheme {
                val navController = rememberNavController()
                val viewModel = hiltViewModel<MainViewModel>()
                CreateNavigationGraph(navController = navController, viewModel = viewModel)
//                viewModel.insertData()
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    App()
//                }
            }
        }
    }
}