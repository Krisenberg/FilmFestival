package com.example.filmfestival.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.R
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.dto.MovieAllData
import com.example.filmfestival.ui.theme.WhiteText
import com.example.filmfestival.utils.NavigationHelper
import com.example.filmfestival.utils.Sound
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@SuppressLint("SetJavaScriptEnabled")
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
//        val actors = remember { mutableStateOf(listOf<Actor>()) }
        val movieData = remember { mutableStateOf<MovieAllData?>(null) }
//        val userTicketsForMovie = remember { mutableStateOf<List< }
        val isOnWatchlist = remember { mutableStateOf<Boolean?>(null) }

        val usersMovieTickets = viewModel
            .getUsersMovieTickets(1, movieId)
            .collectAsStateWithLifecycle(initialValue = emptyList<Show>())

        LaunchedEffect(scope) {
//            actors.value = viewModel.getActors()
            movieData.value = viewModel.getMovieAllData(movieId)
            isOnWatchlist.value = viewModel.checkIfMovieIsOnUsersWatchlist(1, movieId)
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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = data.movie.title.uppercase(),
                                    fontSize = 40.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    overflow = TextOverflow.Ellipsis,
                                    style = TextStyle(lineHeight = 32.sp)
                                )
                            }
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
                                                modifier = Modifier.padding(4.dp)
                                            )
                                        },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)){
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
                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)){
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
                        Text(
                            text = "Actors",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    item {
                        LazyRow(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            items(data.rolesWithActors) { roleWithActor ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .wrapContentSize()
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(roleWithActor.actor.photo)
                                            .build(),
                                        contentDescription = "Photo of ${roleWithActor.actor.name}",
                                        modifier = Modifier
                                            .size(150.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = roleWithActor.actor.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(top = 4.dp),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = roleWithActor.role.starring,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)){
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
                        Text(
                            text = "Awards",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    item {
                        LazyRow(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            items(data.awards) { award ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .wrapContentSize()
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(R.drawable.oscars)
                                            .build(),
                                        contentDescription = "Award ${award.details}",
                                        modifier = Modifier
                                            .size(130.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = award.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Center,
                                        lineHeight = 16.sp,
                                        modifier = Modifier
                                            .padding(top = 1.dp)
                                            .width(130.dp),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = award.details,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Center,
                                        lineHeight = 14.sp,
                                        modifier = Modifier
                                            .padding(top = 1.dp)
                                            .width(130.dp),
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
//                    item {
//                        LazyRow(
//                            modifier = Modifier.padding(8.dp)
//                        ) {
//                            items(data.awards) {
//                                Text(
//                                    text = "Award ${it.name}, details: ${it.details}",
//                                    fontSize = 12.sp,
//                                    color = Color.White,
//                                    modifier = Modifier.padding(end = 8.dp)
//                                )
//                            }
//                        }
//                    }
//                    items(data.rolesWithActors) {
//                        Text(
//                            text = "Actor ${it.actor.name}, played: ${it.role.starring}",
//                            fontSize = 12.sp,
//                            color = Color.White
//                        )
//                    }
//
//                    items(data.awards) {
//                        Text(
//                            text = "Award ${it.name}, details: ${it.details}",
//                            fontSize = 12.sp,
//                            color = Color.White
//                        )
//                    }
                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)){
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
                        Text(
                            text = "Trailer",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    items(data.trailers) { trailer ->
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 10.dp),
                            color = MaterialTheme.colorScheme.background
                        ){
                            YouTubePlayer(youtubeVideoId = trailer.trailer, lifecycleOwner = LocalLifecycleOwner.current)
                        }
                    }

                    item {
                        val showsGroupedByDate = viewModel.groupShowsByDate(data.shows)
                        showsGroupedByDate.forEach { (localDate, listOfPairs) ->
                            Text(
                                text = "${localDate.dayOfWeek.name}, ${localDate.format(DateTimeFormatter.ofPattern("dd.MM"))}",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp, bottom = 4.dp)){
                                Divider(
                                    modifier = Modifier
                                        .fillMaxWidth(0.95f)
                                        .align(Alignment.Center),
                                    thickness = 2.dp,
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(
                                        data.shows
                                            .count()
                                            .div(3) * 80.dp
                                    )
                            ) {
                                LazyVerticalGrid(
                                    columns = GridCells.FixedSize(screenWidth * 0.3f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    this.items(listOfPairs){ (localTime, show) ->
                                        OutlinedButton(
                                            onClick = {
                                                  if (usersMovieTickets.value.contains(show)) {
                                                      scope.launch {
                                                          viewModel.removeUsersTicket(1, show.showId)
                                                      }
                                                  } else {
                                                      scope.launch {
                                                          viewModel.addUsersTicket(1, show.showId)
                                                      }
                                                  }
                                            },
                                            modifier = Modifier.padding(8.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                containerColor = if (usersMovieTickets.value.contains(show))
                                                    MaterialTheme.colorScheme.primaryContainer
                                                    else MaterialTheme.colorScheme.secondaryContainer
                                            )
                                        ) {
                                            Text(
                                                text = localTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                                                fontSize = 20.sp,
                                                color = if (usersMovieTickets.value.contains(show))
                                                    MaterialTheme.colorScheme.onPrimaryContainer
                                                    else MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        }
                                    }
                                }
                            }
                        }
//                        val inputDateString = it.dateTime
//                        val test = LocalDateTime.parse(inputDateString, DateTimeFormatter.ISO_DATE_TIME)
//                        val test2 = test.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"))
//                        Text(
//                            text = "Show $test2",
//                            fontSize = 12.sp,
//                            color = Color.White
//                        )
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
                                color = MaterialTheme.colorScheme.onBackground
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
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    actions = {
//                        var color by remember { mutableStateOf(Color.Transparent.copy(alpha = 0.5f)) }
//                        val primaryColor = MaterialTheme.colorScheme.primary

//                        val color = if (isPressed) Color.Blue else Color.Yellow
                        IconButton(
                            onClick = {
                                if (isOnWatchlist.value!!) {
                                    scope.launch {
                                        viewModel.removeMovieFromUsersWatchlist(1, movieId)
                                    }
                                    isOnWatchlist.value = false
                                    viewModel.playSound(Sound.CLICK_PLINK)
                                } else {
                                    scope.launch {
                                        viewModel.addMovieToUsersWatchlist(1, movieId)
                                    }
                                    isOnWatchlist.value = true
                                    viewModel.playSound(Sound.CLICK_DRIP)
                                }
                            },
                            enabled = (isOnWatchlist.value != null)
                        ){
                            if (isOnWatchlist.value != null && isOnWatchlist.value!!) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Remove from watchlist",
                                    tint = MaterialTheme.colorScheme.primaryContainer
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.FavoriteBorder,
                                    contentDescription = "Add to watchlist",
                                    tint = MaterialTheme.colorScheme.secondaryContainer
                                )
                            }
                        }
//                        Button(
//                            onClick = { scope.launch {
//                                viewModel.addMovieToUsersWatchlist(1,movieId)
//                            }},
//                            colors = ButtonDefaults.buttonColors(
////                                containerColor = Color(red = 255, blue = 255, green = 255, alpha = 150)
//                                containerColor = MaterialTheme.colorScheme.primaryContainer,
//                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
//                            ),
//                            modifier = Modifier.padding(end = 8.dp)
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.Add,
//                                contentDescription = "Add to watchlist",
////                                tint = Color.White,
//                                modifier = Modifier
//                                    .padding(end = 4.dp)
////                                    .offset(x = (-4).dp)
//                            )
//                            Text(
//                                text = "Watchlist",
////                                color = Color.White
//                            )
//                        }
                    },
                    colors = if (makeTopBarTransparent) TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.25f)) else TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        } ?: run {
            // Show a loading indicator or placeholder while movieData is null
            Text(text = "Waiting...")
        }
    }
}

@Composable
fun YouTubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
){
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        factory = { context->
            YouTubePlayerView(context = context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(youtubeVideoId, 0f)
                    }
                })
            }
        }
    )
}