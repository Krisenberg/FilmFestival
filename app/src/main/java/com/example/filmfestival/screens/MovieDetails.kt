package com.example.filmfestival.screens

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import com.example.filmfestival.composables.YouTubePlayer
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.dto.MovieAllData
import com.example.filmfestival.utils.NavigationHelper
import com.example.filmfestival.utils.NavigationRoutes
import com.example.filmfestival.utils.Sound
import com.example.filmfestival.composables.BottomNavBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetails(
    navHelper: NavigationHelper,
    viewModel: MainViewModel,
    movieId: Int
){
    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper) }
    ) { paddingValues ->
        val scope = rememberCoroutineScope()
        val movieData = remember { mutableStateOf<MovieAllData?>(null) }
        val isOnWatchlist = remember { mutableStateOf<Boolean?>(null) }

        val usersMovieTickets = viewModel
            .getUsersMovieTickets(1, movieId)
            .collectAsStateWithLifecycle(initialValue = emptyList())

        val state = rememberLazyListState()
        LaunchedEffect(scope) {
            movieData.value = viewModel.getMovieAllData(movieId)
            isOnWatchlist.value = viewModel.checkIfMovieIsOnUsersWatchlist(1, movieId)
        }

        movieData.value?.let { data ->
            val configuration = LocalConfiguration.current

            val screenHeight = configuration.screenHeightDp.dp
            val screenWidth = configuration.screenWidthDp.dp

            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                LazyColumn(
                    state = state,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = paddingValues.calculateBottomPadding())
                ) {
                    item {
                        AsyncImage(
                            modifier = Modifier
                                .size(
                                    width = screenWidth,
                                    height = ((0.55f) * screenHeight)
                                )
                                .padding(1.dp),
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
                                color = MaterialTheme.colorScheme.onBackground,
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
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 10.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.background
                                )
                        ){
                            YouTubePlayer(
                                youtubeVideoId = trailer.trailer,
                                lifecycleOwner = LocalLifecycleOwner.current,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                isFullscreen = false,
                                playbackTime = 0f,
                                onUpdatePlaybackTime = {},
                                onEnterFullscreen = {
                                    navHelper.navigateToTrailerFullscreen (
                                        route = NavigationRoutes.TRAILER_FULLSCREEN,
                                        trailerId = trailer.trailer,
                                        playbackTime = it.second
                                    )
                                },
                                onExitFullscreen = {}
                            )
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
                                        ((listOfPairs.count() - 1)
                                            .div(3) + 1) * 80.dp
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
                                                    viewModel.playSound(Sound.CLICK_PLINK)
                                                } else {
                                                    scope.launch {
                                                        viewModel.addUsersTicket(1, show.showId)
                                                    }
                                                    viewModel.playSound(Sound.CLICK_DRIP)
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
                                                fontSize = 16.sp,
                                                color = if (usersMovieTickets.value.contains(show))
                                                    MaterialTheme.colorScheme.onPrimaryContainer
                                                else MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        }
                                    }
                                }
                            }
                        }
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
                    },
                    colors = if (makeTopBarTransparent) TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.25f)) else TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}