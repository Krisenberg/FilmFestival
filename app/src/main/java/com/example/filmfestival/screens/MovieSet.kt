package com.example.filmfestival.screens

import android.icu.text.CaseMap.Upper
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filmfestival.MainViewModel
import com.example.filmfestival.composables.BottomNavBar
import com.example.filmfestival.models.Actor
import com.example.filmfestival.models.Movie
import com.example.filmfestival.models.dto.MovieAllData
import com.example.filmfestival.utils.NavigationHelper
import com.example.filmfestival.utils.NavigationRoutes
import com.example.filmfestival.utils.fadingEdge
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieSet(
    navHelper: NavigationHelper,
    viewModel: MainViewModel
){
    Scaffold(
        bottomBar = { BottomNavBar(navHelper = navHelper) }
    ) { paddingValues ->

        val scope = rememberCoroutineScope()
        val moviesIdPoster = remember { mutableStateOf<List<Triple<Int, String, String>>?>(null) }
//        val currentPage by rememberSaveable { mutableIntStateOf(0) }

        LaunchedEffect(scope) {
            moviesIdPoster.value = viewModel.getMoviesIdTitlePoster()
        }

        moviesIdPoster.value?.let { data ->
            val pagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f
            ) {
                data.size
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                HorizontalPager(
                    state = pagerState
                ) { index ->
                    val pageOffset =
                        (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
                    val matrix = remember {
                        ColorMatrix()
                    }
                    val imageSize by animateFloatAsState(
                        targetValue = if (pageOffset != 0.0f) 0.85f else 1f,
                        animationSpec = tween(durationMillis = 200),
                        label = "imageSizeFloat"
                    )
                    val topFade = Brush.verticalGradient(0f to Color.Transparent, 0.4f to Color.Red)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                scaleX = imageSize
                                scaleY = imageSize
                            }
                            .clickable(enabled = true) {
//                                navController.navigate(route = "${NavigationRoutes.MOVIE_DETAILS.name}/${data[index].first}")
                                navHelper.navigateWithId(
                                    NavigationRoutes.MOVIE_DETAILS,
                                    data[index].first
                                )
//                                navController.navigate("USER_PROFILE_EDIT/${username}", ) {
//                                    navController.graph.startDestinationRoute?.let { route ->
//                                        popUpTo(route) {
//                                            saveState = true
//                                        }
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }
                            },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(1.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .fadingEdge(topFade),
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(data[index].third)
                                .build(),
                            contentDescription = "Poster of ${data[index].second}",
                            colorFilter = ColorFilter.colorMatrix(matrix),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        } ?: run {
            // Show a loading indicator or placeholder while movieData is null
            Text(text = "Waiting...")
        }
    }
}