package com.example.filmfestival.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.R
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.Show
import com.example.filmfestival.models.dto.MovieAllData
import com.example.filmfestival.utils.NavigationHelper
import com.example.filmfestival.utils.NavigationRoutes
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun UserProfile(
    navHelper: NavigationHelper,
    viewModel: MainViewModel,
    username: String = "Jan Kowalski"
){
    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper) }
    ){ paddingValues ->
        var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

        val movies = remember { mutableStateOf<List<Movie>?>(null) }
        val scope = rememberCoroutineScope()
        val usersTickets = viewModel
            .getUsersTickets(1)
            .collectAsStateWithLifecycle(initialValue = emptyList<Pair<Movie, LocalDateTime>>())

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
//                navController = navController,
                modifier = Modifier
                    .padding(10.dp),
                username = username
            )
            Spacer(modifier = Modifier.height(4.dp))
            ProfileSection(
                username = username
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
//            ) {
//                selectedTabIndex = it
//            }
            Spacer(modifier = Modifier.height(15.dp))
            when(selectedTabIndex) {
                0 -> { movies.value?.let { data ->
                    if (data.isNotEmpty()) {
                        Watchlist(
                            watchlist = data,
                            navHelper = navHelper,
                            modifier = Modifier.fillMaxWidth()
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
                        // Show a loading indicator or placeholder while movieData is null
                        Text(text = "Fetching the data from database...")
                    }
                }
                1 -> Tickets(
                    tickets = usersTickets.value,
//                    tickets = listOf(
//                        Ticket("Dune: Part Two", "22.07.2024", "17:30", painterResource(id = R.drawable.dune)),
//                        Ticket("Avatar", "29.07.2024", "7:30", painterResource(id = R.drawable.avatar)),
//                        Ticket("Dune: Part Two", "22.07.2024", "17:30", painterResource(id = R.drawable.dune)),
//                        Ticket("Avatar", "29.07.2024", "7:30", painterResource(id = R.drawable.avatar))
//                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun TopBar(
    navHelper: NavigationHelper,
    modifier: Modifier = Modifier,
    username : String
) {
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
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
            )
        }
        Button(
            onClick = {
                navHelper.navigateWithStr(NavigationRoutes.USER_PROFILE_EDIT, username)
//                navController.navigate("USER_PROFILE_EDIT/${username}", )
//                {
//                    navController.graph.startDestinationRoute?.let { route ->
//                        popUpTo(route) {
//                            saveState = true
//                        }
//                    }
//                    launchSingleTop = true
//                    restoreState = true
//                }
            },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_pencil),
                contentDescription = "Edit",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}



@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    username: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundImage(
            image = painterResource(id = R.drawable.timothe),
            modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = username,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .fillMaxSize()
                .align(Alignment.Center)
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
    navHelper: NavigationHelper,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(watchlist.size) { index ->
            MovieRow(movie = watchlist[index], navHelper = navHelper)
            if (index < watchlist.size - 1) {
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, navHelper: NavigationHelper) {
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
                .size(110.dp)
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
    tickets: List<Pair<Movie, LocalDateTime>>,
    modifier: Modifier = Modifier
) {
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
fun TicketRow(ticket: Pair<Movie, LocalDateTime>) {
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
                color = Color.Gray,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = "Time: ${ticket.second.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        AsyncImage(
            model = ticket.first.moviePoster,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(110.dp)
                .clip(MaterialTheme.shapes.medium)
        )
    }
}

//data class Ticket(
//    val movieTitle: String,
//    val date: String,
//    val time: String,
//    val poster: Painter
//)