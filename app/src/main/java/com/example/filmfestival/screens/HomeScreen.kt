package com.example.filmfestival.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.R
import com.example.filmfestival.composables.BottomNavBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
//import androidx.constraintlayout.compose.Wrap
import androidx.navigation.NavController
import com.example.filmfestival.ui.theme.LightBackground
import com.example.filmfestival.ui.theme.WhiteText
import com.example.filmfestival.utils.NavigationHelper

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navHelper: NavigationHelper,
    viewModel: MainViewModel
){
    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper) }
    ){
        paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App logo",
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                        top = 36.dp,
                        end = 24.dp,
                        bottom = 36.dp
                    )
                    .clip(RoundedCornerShape(16.dp))
            )
//        Spacer(
//            modifier = Modifier
//                .height(24.dp)
//                .fillMaxWidth()
//        )
            Text(
                text = "News",
                fontSize = 36.sp,
                color = WhiteText
            )
            Divider(
                color = LightBackground,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                        end = 24.dp
                    )
            )
            val currentPage by remember { mutableIntStateOf(0) }
            val pagerState = rememberPagerState(
                initialPage = currentPage,
                initialPageOffsetFraction = 0f
            ) {
                newsList.size
            }
            HorizontalPager(state = pagerState) { index ->
                NewsItem(newsList[index])
                Spacer(modifier = Modifier.width(16.dp))
            }
//            LazyRow(
//                modifier = Modifier
//                    .padding(paddingValues)
//            )
//            {
//                items(newsList) { news ->
//                    NewsItem(news)
//                    Spacer(modifier = Modifier.width(16.dp))
//                }
//            }
        }

    }
}

@Composable
fun NewsItem(news: News) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 5.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = news.imageRes),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .width(350.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = news.date,
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 24.dp) // Add padding for date
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .height(500.dp)
                .width(350.dp)
                .padding(horizontal = 24.dp)
        ) {
//            Column (
//                verticalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxSize()
//            ){
                Text(
                    text = news.text,
                    color = WhiteText,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                )
//            }
        }
    }
}

data class News(
    val imageRes: Int,
    val date: String,
    val text: String,
    val description: String
)

val newsList = listOf(
    News(
        imageRes = R.drawable.news1,
        date = "May 5, 2024",
        text = "Want to take part in discussion related to the movie? Sign up!",
        description = "We're inviting you to share your thoughts, insights, and opinions on the film “Dune: Part Two”. It's the perfect opportunity to connect with fellow film buffs and explore different perspectives!\n" +
                "\n" +
                "To participate, simply sign up on our website and reserve your spot.\n" +
                "\n" +
                "See you at the discussion! \uD83C\uDFA5✨"
    ),
    News(
        imageRes = R.drawable.news2,
        date = "May 6, 2024",
        text = "Almost booked out! Only last tickets left",
        description = ""
    ),
    News(
        imageRes = R.drawable.news3,
        date = "May 7, 2024",
        text = "New movie released! Zone of Interest joins SFFF this year",
        description = ""
    )
)