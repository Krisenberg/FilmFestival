package com.example.filmfestival.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.MovieAllData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun UserProfile(
    navController: NavController,
    viewModel: MainViewModel
){
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ){ paddingValues ->
//        val actors by viewModel.actors.observe()
//
        val scope = rememberCoroutineScope()
        val actors = remember { mutableStateOf(listOf<Actor>()) }
        val movieData = remember { mutableStateOf<MovieAllData?>(null) }
        LaunchedEffect(scope) {
            actors.value = viewModel.getActors()
            movieData.value = viewModel.getMovieAllData(1)
        }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            actors.value.forEach{ actor ->
                Row (
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ){
                    AsyncImage(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(1.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(actor.photo)
                            .build(),
                        contentDescription = "Image of ${actor.name}",
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = actor.name,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
            movieData.value?.let { dune ->
                Text(
                    text = dune.movie.title,
                    fontSize = 12.sp,
                    color = Color.White
                )
                dune.rolesWithActors.forEach { roleWithActor ->
                    Text(
                        text = "Actor ${roleWithActor.actor.name} played ${roleWithActor.role.starring}",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            } ?: run {
                // Show a loading indicator or placeholder while movieData is null
                Text(text = "Waiting...")
            }
//            val dune = movieData.value!!
//            Text(
//                text = dune.movie.title,
//                fontSize = 12.sp,
//                color = Color.White
//            )
//            dune.shows.forEach{ show ->
//                Text(
//                    text = "Show ${show.dateTime}",
//                    fontSize = 12.sp,
//                    color = Color.White
//                )
//            }
//            dune.roles.forEach{ role ->
//                Text(
//                    text = "Actor $role.",
//                    fontSize = 12.sp,
//                    color = Color.White
//                )
//            }
//            Text(
//                text = "TEST",
//                fontSize = 60.sp
//            )
        }
    }
}