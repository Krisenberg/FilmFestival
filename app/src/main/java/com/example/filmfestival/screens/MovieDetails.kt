package com.example.filmfestival.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.dto.MovieAllData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MovieDetails(
    navController: NavController,
    viewModel: MainViewModel,
    movieId: Int
){
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        val scope = rememberCoroutineScope()
        val actors = remember { mutableStateOf(listOf<Actor>()) }
        val movieData = remember { mutableStateOf<MovieAllData?>(null) }

        LaunchedEffect(scope) {
            actors.value = viewModel.getActors()
            movieData.value = viewModel.getMovieAllData(movieId)
        }

        movieData.value?.let { data ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    Text(
                        text = "Movie ${data.movie.title}, year: ${data.movie.year}, dur.: ${data.movie.duration}",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }

                items(data.rolesWithActors) {
                    Text(
                        text = "Actor ${it.actor.name}, played: ${it.role.starring}",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }

                items(data.awards) {
                    Text(
                        text = "Award ${it.name}, details: ${it.details}",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }

                items(data.trailers) {
                    Text(
                        text = "Trailer ${it.trailer}",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }

                items(data.shows) {
                    val inputDateString = it.dateTime
                    val test = LocalDateTime.parse(inputDateString, DateTimeFormatter.ISO_DATE_TIME)
                    val test2 = test.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"))
                    Text(
                        text = "Show $test2",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        } ?: run {
            // Show a loading indicator or placeholder while movieData is null
            Text(text = "Waiting...")
        }
    }
}

