package com.example.filmfestival.screens

import android.content.res.Resources.Theme
import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.dto.MovieAllData
import com.example.filmfestival.ui.theme.FilmFestivalTheme
import com.example.filmfestival.ui.theme.WhiteText
import com.example.filmfestival.utils.NavigationHelper
import com.example.filmfestival.utils.fadingEdge
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MovieDetails(
    navHelper: NavigationHelper,
    viewModel: MainViewModel,
    movieId: Int
){
    Scaffold(
//        topBar = { TopAppBar(
//            title = {},
//            navigationIcon = {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                    contentDescription = "Back arrow",
//                    tint = Color.White
//                )
//            },
//            actions = {
//                OutlinedButton(onClick = {}) {
//                    Row {
//                        Icons.Filled.Add
//                        Text(text = "Watchlist")
//                    }
//                }
//            }
//        )},
        bottomBar = { BottomNavBar(navHelper = navHelper) }
    ) { paddingValues ->
        val scope = rememberCoroutineScope()
        val actors = remember { mutableStateOf(listOf<Actor>()) }
        val movieData = remember { mutableStateOf<MovieAllData?>(null) }

        LaunchedEffect(scope) {
            actors.value = viewModel.getActors()
            movieData.value = viewModel.getMovieAllData(movieId)
        }

        movieData.value?.let { data ->
            val configuration = LocalConfiguration.current

            val screenHeight = configuration.screenHeightDp.dp
            val screenWidth = configuration.screenWidthDp.dp

            Box(
                modifier = Modifier
                    .fillMaxSize()
//                    .offset(y = -paddingValues.calculateTopPadding())
            ) {
                val state = rememberLazyListState()
                LazyColumn(
                    state = state,
                    modifier = Modifier
                        .fillMaxSize()
//                        .offset(y = (-50).dp)
                        .padding(bottom = paddingValues.calculateBottomPadding())
                ) {
                    item {
//                    Text(
//                        text = "Movie ${data.movie.title}, year: ${data.movie.year}, dur.: ${data.movie.duration}",
//                        fontSize = 12.sp,
//                        color = Color.White
//                    )
                        AsyncImage(
                            modifier = Modifier
                                .size(
                                    width = screenWidth,
                                    height = ((0.55f) * screenHeight)
                                )
//                            .fillMaxHeight(2f/3f)
//                            .fillMaxWidth()
                                .padding(1.dp),
//                            .clip(RoundedCornerShape(4.dp)),
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(data.movie.moviePhoto)
                                .build(),
                            contentDescription = "Photo from ${data.movie.title}",
                            contentScale = ContentScale.Crop
                        )
                    }

                    item {
                        Box (
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ){
                            Text(
                                text = data.movie.title.uppercase(),
                                fontSize = 40.sp,
                                color = WhiteText,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    item {
                        Box (
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(8.dp)
                        ){
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                fun formatMovieDuration(minutes: Int): String {
                                    val hours = minutes.div(60)
                                    val rest = minutes.mod(60)
                                    return "${hours}h ${rest}m"
                                }

                                val dataToDisplay = listOf(
                                    data.movie.year.toString(),
                                    formatMovieDuration(data.movie.duration),
                                    data.movie.genre
                                )
                                dataToDisplay.forEach {
                                    AssistChip(
                                        onClick = {},
                                        label = {
                                            Text(
                                                text = it,
                                                fontSize = 20.sp,
                                                color = WhiteText,
                                                modifier = Modifier.padding(4.dp)
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Box(modifier = Modifier.fillMaxWidth()){
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth(0.95f)
                                    .align(Alignment.Center),
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(color = MaterialTheme.colorScheme.surface)
                                .clip(RoundedCornerShape(12.dp))
                        ){
                            Text(
                                text = data.movie.description,
                                fontSize = 20.sp,
                                color = WhiteText,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }

                    items(count = 10) {
//                    Text(
//                        text = "Movie ${data.movie.title}, year: ${data.movie.year}, dur.: ${data.movie.duration}",
//                        fontSize = 12.sp,
//                        color = Color.White
//                    )
                        AsyncImage(
                            modifier = Modifier
                                .size(
                                    width = screenWidth,
                                    height = ((0.55f) * screenHeight)
                                )
//                            .fillMaxHeight(2f/3f)
//                            .fillMaxWidth()
                                .padding(1.dp),
//                            .clip(RoundedCornerShape(4.dp)),
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(data.movie.moviePhoto)
                                .build(),
                            contentDescription = "Photo from ${data.movie.title}",
                            contentScale = ContentScale.Crop
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
                val showTitleOnTopBar by remember {
                    derivedStateOf {
                        state.firstVisibleItemIndex >= 3
                    }
                }
                val makeTopBarTransparent by remember {
                    derivedStateOf {
                        state.firstVisibleItemIndex == 0
                    }
                }
                val topBarTitle = remember { mutableStateOf("") }
                LaunchedEffect(showTitleOnTopBar) {
                    Log.d("SCROLL_STATE", "show: $showTitleOnTopBar")
//                    if (firstItemVisible >= 4)
//                        topBarTitle.value = data.movie.title
//                    else
//                        topBarTitle.value = ""
                }
                TopAppBar(
                    title = {
                        AnimatedVisibility(
                            visible = showTitleOnTopBar,
                            exit = slideOutVertically() + fadeOut(),
                            enter = slideInVertically() + fadeIn()
                        ){
                            Text(
                                text = data.movie.title,
                                color = Color.White
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navHelper.goBack() },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back arrow",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        var isSelected by remember { mutableStateOf(false) }
//                        var color by remember { mutableStateOf(Color.Transparent.copy(alpha = 0.5f)) }
//                        val primaryColor = MaterialTheme.colorScheme.primary

//                        val color = if (isPressed) Color.Blue else Color.Yellow
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Color(red = 255, blue = 255, green = 255, alpha = 150)),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add to watchlist",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(end = 4.dp)
//                                    .offset(x = (-4).dp)
                            )
                            Text(
                                text = "Watchlist",
                                color = Color.White
                            )
                        }
//                        Button(
//                            onClick = {
//                                isSelected = !isSelected
//
//                                scope.launch {
//                                    delay(150)
//                                    isSelected = !isSelected
//                                }
//                            },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent.copy(alpha = 0.5f)
////                                containerColor = color
//                            ),
//                            modifier = Modifier
//                                .padding(end = 8.dp)
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.Add,
//                                contentDescription = "Add to watchlist",
//                                tint = Color.White,
//                                modifier = Modifier
//                                    .padding(end = 4.dp)
////                                    .offset(x = (-4).dp)
//                            )
//                            Text(
//                                text = "Watchlist",
//                                color = Color.White
//                            )
//                        }
                    },
                    colors = if (makeTopBarTransparent) TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent.copy(alpha = 0.15f)) else TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        } ?: run {
            // Show a loading indicator or placeholder while movieData is null
            Text(text = "Waiting...")
        }
    }
}
