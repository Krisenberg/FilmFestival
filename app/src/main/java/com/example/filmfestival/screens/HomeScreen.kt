package com.example.filmfestival.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.R
import com.example.filmfestival.composables.BottomNavBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filmfestival.api.NewsPreview
import com.example.filmfestival.composables.ShowProgressIndicator
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
                ShowProgressIndicator()
            }
        }

    }
}

@Composable
fun NewsItem(
    news: NewsPreview,
    navHelper: NavigationHelper
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 5.dp)
            .fillMaxSize()
            .clickable(enabled = true) {
                navHelper.navigateToNewsDetails(
                    NavigationRoutes.NEWS_DETAILS,
                    news.id
                )
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio((16f) / (9f))
                .clip(shape = RoundedCornerShape(16.dp)),
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(news.imageUrl)
                .build(),
            contentDescription = "Image of the news ${news.title}",
            contentScale = ContentScale.Crop
        )
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
        }
    }
}