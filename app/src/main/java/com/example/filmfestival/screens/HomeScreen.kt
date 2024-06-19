package com.example.filmfestival.screens

import android.util.Log
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.constraintlayout.compose.Wrap
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filmfestival.api.NewsPreview
import com.example.filmfestival.models.dto.MovieAllData
import com.example.filmfestival.utils.NavigationHelper
import com.example.filmfestival.utils.NavigationRoutes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navHelper: NavigationHelper,
    viewModel: MainViewModel
){
    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper) }
    ){ paddingValues ->

        val scope = rememberCoroutineScope()
        val newsPreviews = remember { mutableStateOf<List<NewsPreview>?>(null) }

        LaunchedEffect(scope) {
            newsPreviews.value = viewModel.getFilmFestivalNewsPreviews()
        }

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
            Text(
                text = "News",
                fontSize = 36.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Divider(
                color = MaterialTheme.colorScheme.surfaceVariant,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                        end = 24.dp
                    )
            )
            newsPreviews.value?.let { newsList ->
                val currentPage by remember { mutableIntStateOf(0) }
                val pagerState = rememberPagerState(
                    initialPage = currentPage,
                    initialPageOffsetFraction = 0f
                ) {
                    newsList.size
                }
                HorizontalPager(state = pagerState) { index ->
                    NewsItem(newsList[index], navHelper)
                    Spacer(modifier = Modifier.width(16.dp))
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
}

@Composable
fun NewsItem(
    news: NewsPreview,
    navHelper: NavigationHelper
) {
//    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 5.dp)
            .fillMaxSize()
            .clickable(enabled = true){
                navHelper.navigateToNewsDetails(
                    NavigationRoutes.NEWS_DETAILS,
                    news.id
                )
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio((16f)/(9f))
                .clip(shape = RoundedCornerShape(16.dp)),
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(news.imageUrl)
                .build(),
            contentDescription = "Image of the news ${news.title}",
            contentScale = ContentScale.Crop
        )
//        Image(
//            painter = painterResource(id = news.imageRes),
//            contentDescription = null,
//            modifier = Modifier
//                .height(200.dp)
//                .width(350.dp)
//                .clip(shape = RoundedCornerShape(16.dp))
//                .clickable(enabled = true){
//                    navHelper.navigateWithNews(
//                        NavigationRoutes.NEWS_DETAILS,
//                        news.imageRes,
//                        news.date,
//                        news.text,
//                        news.description
//                    )
//                }
//        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = news.date,
            color = MaterialTheme.colorScheme.onBackground,
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
            Text(
                text = news.title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip,
                modifier = Modifier
            )
//            }
        }
    }
}

//data class News(
//    val imageRes: Int,
//    val date: String,
//    val text: String,
//    val description: String
//)
//val newsList = listOf(
//    News(
//        imageRes = R.drawable.news1,
//        date = "May 5, 2024",
//        text = "Want to take part in discussion related to the movie? Sign up!",
//        description = "We're inviting you to share your thoughts, insights, and opinions on the film “Dune: Part Two”. It's the perfect opportunity to connect with fellow film buffs and explore different perspectives! To participate, simply sign up on our website and reserve your spot. See you at the discussion! \uD83C\uDFA5✨"
//    ),
//    News(
//        imageRes = R.drawable.news2,
//        date = "May 6, 2024",
//        text = "Almost booked out! Only last tickets left.",
//        description = "Attention, movie buffs! Dune 2 is gearing up for its highly anticipated premiere, and tickets are flying off the shelves faster than a sandworm in a spice frenzy. Director Denis Villeneuve promises another visual feast, with epic battles and stellar performances. With just a few seats left, now's your chance to secure your spot and be among the first to experience the adventure. Don't miss out – grab your tickets now for the cinematic event of the year! \uD83C\uDF1F\uD83C\uDFA5"
//    ),
//    News(
//        imageRes = R.drawable.news3,
//        date = "May 8, 2024",
//        text = "New Movie Released! Zone of Interest Joins SFFF this Year.",
//        description = "Exciting news for film enthusiasts! \"Zone of Interest\" is the latest addition to this year's lineup at the SFFF and audiences are buzzing with anticipation. Directed by a visionary filmmaker, this gripping new release promises to captivate viewers with its unique storyline and compelling characters. As the festival draws near, make sure to mark your calendars and secure your tickets for a journey into the unknown \uD83D\uDE80\uD83C\uDF7F"
//    )
//)