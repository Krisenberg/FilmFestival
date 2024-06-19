package com.example.filmfestival.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.R
import com.example.filmfestival.api.News
import com.example.filmfestival.api.NewsPreview
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.composables.ShowProgressIndicator
import com.example.filmfestival.utils.NavigationHelper

@Composable
fun NewsScreen(
    navHelper: NavigationHelper,
    viewModel: MainViewModel,
    newsId: String
) {
    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper) },
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    androidx.compose.material3.IconButton(
                        onClick = { navHelper.goBack() },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back arrow",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) {
        paddingValues ->

        val scope = rememberCoroutineScope()
        val newsDetails = remember { mutableStateOf<News?>(null) }

        LaunchedEffect(scope) {
            newsDetails.value = viewModel.getFilmFestivalNewsDetailsById(newsId)
        }

        newsDetails.value?.let { details ->
            val state = rememberLazyListState()
            LazyColumn(
                state = state,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .aspectRatio((16f) / (9f))
                            .clip(shape = RoundedCornerShape(16.dp)),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(details.imageUrl)
                            .build(),
                        contentDescription = "Image of the news ${details.title}",
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    Text(
                        text = details.title,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(0.85f)
                    )
                }

                item {
                    Divider(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        thickness = 0.67.dp,
                        modifier = Modifier
                            .padding(
                                start = 24.dp,
                                end = 24.dp
                            )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        text = details.description,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(0.85f)
                    )
                }
            }
        } ?: run {
            ShowProgressIndicator()
        }
    }
}