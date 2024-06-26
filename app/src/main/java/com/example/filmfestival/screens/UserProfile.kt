@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.filmfestival.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.filmfestival.MainViewModelInterface
import com.example.filmfestival.R
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.composables.ShowProgressIndicator
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Show
import com.example.filmfestival.utils.NavigationHelperInterface
import com.example.filmfestival.utils.NavigationRoutes
import com.posthog.PostHog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun UserProfile(
    navHelper: NavigationHelperInterface,
    viewModel: MainViewModelInterface
){
    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper) }
    ){ paddingValues ->
        var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

        val movies = remember { mutableStateOf<List<Movie>?>(null) }
        val scope = rememberCoroutineScope()
        val usersTickets = viewModel
            .getUsersTickets(1)
            .collectAsStateWithLifecycle(initialValue = emptyList())

        val username by viewModel.getUsername(1).collectAsStateWithLifecycle(initialValue = "Loading...")
        val avatarUrl by viewModel.getAvatar(1).collectAsStateWithLifecycle(initialValue = "")

        LaunchedEffect(scope) {
            movies.value = viewModel.getUserWatchlistMovies(1)
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            TopBar(
                navHelper = navHelper,
                modifier = Modifier
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            ProfileSection(
                username = username,
                avatarUrl = avatarUrl
            )
            Spacer(modifier = Modifier.height(50.dp))
            TabView(
                texts = listOf(
                    "Watchlist",
                    "Tickets"
                ),
                currentIndex = selectedTabIndex,
                updateTabIndex = { newIndex: Int -> selectedTabIndex = newIndex}
            )
            Spacer(modifier = Modifier.height(15.dp))
            when(selectedTabIndex) {
                0 -> { movies.value?.let { data ->
                    if (data.isNotEmpty()) {
                        Watchlist(
                            watchlist = data,
                            navHelper = navHelper,
                            modifier = Modifier.fillMaxWidth(),
                            viewModel = viewModel
                        )
                    } else {
                        Box( modifier = Modifier.fillMaxSize()){
                            Text (
                                text = "Your watchlist is empty",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .align(alignment = Alignment.Center)
                            )
                        }
                    }} ?: run {
                        ShowProgressIndicator()
                    }
                }
                1 -> if(usersTickets.value.isEmpty()) {
                    Box( modifier = Modifier.fillMaxSize()){
                        Text (
                            text = "You haven't signed up for any shows",
                            fontSize = 20.sp,
                            modifier = Modifier
                                .align(alignment = Alignment.Center)
                        )
                    }
                } else {
                    Tickets (
                    tickets = usersTickets.value,
                    viewModel,
                    modifier = Modifier.fillMaxWidth()
                )}
            }
        }
    }
}

@Composable
fun TopBar(
    navHelper: NavigationHelperInterface,
    modifier: Modifier = Modifier
) {
    val isTestVariant = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        isTestVariant.value = PostHog.getFeatureFlag("android-edit-user-clicked") == "test"
    }

    val iconSize = if (isTestVariant.value) 40.dp else 24.dp


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { navHelper.goBack() },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(iconSize)
            )
        }
        Button(
            onClick = {
                PostHog.capture("edit_user_clicked")
                navHelper.navigate(NavigationRoutes.USER_PROFILE_EDIT)
            },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_pencil),
                contentDescription = "Edit",
                tint = Color.White,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    username: String,
    avatarUrl: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundImage(
            avatarUrl = avatarUrl,
            modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = username,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RoundImage(
    avatarUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun TabView(
    modifier: Modifier = Modifier,
    texts: List<String>,
    currentIndex: Int,
    updateTabIndex: (selectedIndex : Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(currentIndex)
    }
    val inactiveColor = Color.LightGray
    TabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = Color.White,
        modifier = modifier
    ) {
        texts.forEachIndexed { index, item ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = Color.White,
                unselectedContentColor = inactiveColor,
                onClick = {
                    selectedTabIndex = index
                    updateTabIndex(index)
                }
            ) {
                Text(
                    text = item,
                    fontSize = 20.sp,
                    color = if (selectedTabIndex == index) Color.White else inactiveColor,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .width(90.dp)
                        .height(50.dp)
                )
            }
        }
    }
}

@Composable
fun Watchlist(
    watchlist: List<Movie>,
    navHelper: NavigationHelperInterface,
    modifier: Modifier = Modifier,
    viewModel: MainViewModelInterface
) {
    val coroutineScope = rememberCoroutineScope()
    val isTestVariant = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        isTestVariant.value = PostHog.getFeatureFlag("android-remove-from-watchlist") == "test"
    }
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = watchlist,
            key = { it.movieId }
        ) { movie ->
            if (isTestVariant.value) {
                SwipeToDeleteContainerTest(
                    item = movie,
                    onDelete = { movieToDelete ->
                        coroutineScope.launch {
                            viewModel.removeMovieFromUsersWatchlist(1, movieToDelete.movieId)
                        }
                        PostHog.capture("remove_from_watchlist")
                    }
                ) { movieItem ->
                    MovieRow(movie = movieItem, navHelper = navHelper)
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
            else {
                SwipeToDeleteContainer(
                    item = movie,
                    onDelete = { movieToDelete ->
                        coroutineScope.launch {
                            viewModel.removeMovieFromUsersWatchlist(1, movieToDelete.movieId)
                        }
                        PostHog.capture("remove_from_watchlist")
                    }
                ) { movieItem ->
                    MovieRow(movie = movieItem, navHelper = navHelper)
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, navHelper: NavigationHelperInterface) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            navHelper.navigateWithId(NavigationRoutes.MOVIE_DETAILS, movie.movieId)
        }
    ) {
        AsyncImage(
            model = movie.moviePoster,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
                .clip(MaterialTheme.shapes.medium)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = movie.title,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun Tickets(
    tickets: List<Triple<Movie, LocalDateTime, Show>>,
    viewModel: MainViewModelInterface,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = tickets,
            key = { it }
        ) { ticket ->
            SwipeToDeleteContainer(
                item = ticket,
                onDelete = { ticketToDelete ->
                    coroutineScope.launch {
                        viewModel.removeUsersTicket(1, ticketToDelete.third.showId)
                    }
                }
            ) { ticketItem ->
                TicketRow(ticket = ticketItem)
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(tickets.size) { index ->
            TicketRow(ticket = tickets[index])
            if (index < tickets.size - 1) {
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

@Composable
fun TicketRow(ticket: Triple<Movie, LocalDateTime, Show>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = ticket.first.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 3.dp)
                    .padding(start = 20.dp)
                    .width(150.dp)
            )
            Text(
                text = "Date: ${ticket.second.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = "Time: ${ticket.second.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        AsyncImage(
            model = ticket.first.moviePoster,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
                .clip(MaterialTheme.shapes.medium)
        )
    }
}

@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    Log.d("SwipeToDeleteContainer", "SwipeToDeleteContainer created")
    var isRemoved by remember { mutableStateOf(false) }
    val state = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToEnd || value == DismissValue.DismissedToStart) {
                isRemoved = true
                Log.d("SwipeToDeleteContainer", "Dismiss value changed: $value")
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            Log.d("SwipeToDeleteContainer", "Item removed")
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                DeleteBackground(
                    swipeProgress = {
                        // Calculate the swipe progress based on the dismiss state's progress
                        val fraction = if (state.dismissDirection == DismissDirection.EndToStart) {
                            state.progress
                        } else {
                            0f
                        }
                        fraction
                    }
                )


            },
            dismissContent = { content(item) },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}

@Composable
fun DeleteBackground(swipeProgress: () -> Float) {
    val progress = swipeProgress()
    val color = if (progress > 0f) Color.DarkGray else Color.Transparent
    val iconAlpha by animateFloatAsState(targetValue = if (progress > 0.1f) 1f else 0f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.alpha(iconAlpha)
        )
    }
}

@Composable
fun <T> SwipeToDeleteContainerTest(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )
            LaunchedEffect(key1 = isRemoved) {
                if(isRemoved) {
                    delay(animationDuration.toLong())
                    onDelete(item)
                }
            }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                DeleteBackgroundTest(swipeDismissState = state)
            },
            dismissContent = { content(item) },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}

@Composable
fun DeleteBackgroundTest(
    swipeDismissState: DismissState
) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.DarkGray
    } else Color.Transparent
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.White
                )
            }
}