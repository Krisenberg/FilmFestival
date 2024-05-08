package com.example.filmfestival.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.navigation.NavController
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.R
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.utils.NavigationRoutes


@Composable
fun UserProfile(
    navController: NavController,
    viewModel: MainViewModel,
    username: String = "Jan Kowalski"
){
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ){ paddingValues ->
        var selectedTabIndex by remember {
            mutableStateOf(0)
        }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            TopBar(
                navController = navController,
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
                )
            ) {
                selectedTabIndex = it
            }
            Spacer(modifier = Modifier.height(15.dp))
            when(selectedTabIndex) {
                0 -> Watchlist(
                    watchlist = listOf(
                        Pair(painterResource(id = R.drawable.dune), "Dune: Part Two"),
                        Pair(painterResource(id = R.drawable.avatar), "Avatar"),
                        Pair(painterResource(id = R.drawable.dune), "Dune: Part Two"),
                        Pair(painterResource(id = R.drawable.avatar), "Avatar")
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                1 -> Tickets(
                    tickets = listOf(
                        Ticket("Dune: Part Two", "22.07.2024", "17:30", painterResource(id = R.drawable.dune)),
                        Ticket("Avatar", "29.07.2024", "7:30", painterResource(id = R.drawable.avatar)),
                        Ticket("Dune: Part Two", "22.07.2024", "17:30", painterResource(id = R.drawable.dune)),
                        Ticket("Avatar", "29.07.2024", "7:30", painterResource(id = R.drawable.avatar))
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun TopBar(
    navController: NavController,
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
            onClick = {
                navController.previousBackStackEntry?.savedStateHandle
                navController.popBackStack()
            },
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
                navController.navigate("USER_PROFILE_EDIT/${username}", ) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
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
    onTabSelected: (selectedIndex : Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
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
                    onTabSelected(index)
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
    watchlist: List<Pair<Painter, String>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(watchlist.size) { index ->
            MovieRow(movie = watchlist[index])
            if (index < watchlist.size - 1) {
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

@Composable
fun MovieRow(movie: Pair<Painter, String>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = movie.first,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(110.dp)
                .clip(MaterialTheme.shapes.medium)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = movie.second,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun Tickets(
    tickets: List<Ticket>,
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
fun TicketRow(ticket: Ticket) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = ticket.movieTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 3.dp)
                    .padding(start = 20.dp)
                    .width(150.dp)
            )
            Text(
                text = ticket.date,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = ticket.time,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = ticket.poster,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(110.dp)
                .clip(MaterialTheme.shapes.medium)
        )
    }
}

data class Ticket(
    val movieTitle: String,
    val date: String,
    val time: String,
    val poster: Painter
)