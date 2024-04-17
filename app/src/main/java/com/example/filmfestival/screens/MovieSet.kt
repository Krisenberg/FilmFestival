package com.example.filmfestival.screens

import android.icu.text.CaseMap.Upper
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.remember
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
import com.example.filmfestival.models.Movie
import com.example.filmfestival.utils.fadingEdge
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieSet(
    navController: NavController,
    viewModel: MainViewModel
){
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ){ paddingValues ->
        val currentPage by remember { mutableIntStateOf(0) }
        val moviesFlow: Flow<List<Movie>> = viewModel.moviesOrderedByTitle
        val movies by moviesFlow.collectAsState(initial = emptyList())
//        val movies by viewModel.moviesOrderedByTitle.collectAsState(initial = emptyList())


        val pagerState = rememberPagerState(
            initialPage = currentPage,
            initialPageOffsetFraction = 0f
        ) {
            movies.size
        }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HorizontalPager(
                state = pagerState
            ) { index ->
                val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
                val matrix = remember {
                    ColorMatrix()
                }
                val imageSize by animateFloatAsState(
                    targetValue = if(pageOffset != 0.0f) 0.85f else 1f,
                    animationSpec = tween(durationMillis = 200),
                    label = "imageSizeFloat"
                )
                
//                LaunchedEffect(key1 = imageSize) {
//                    if(pageOffset != 0.0f) {
//                        Log.d("TAG", "0f")
//                        matrix.setToSaturation(0f)
//                    } else {
//                        Log.d("TAG", "1f")
//                        matrix.setToSaturation(1f)
//                    }
//                }
                val topFade = Brush.verticalGradient(0f to Color.Transparent, 0.4f to Color.Red)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = imageSize
                            scaleY = imageSize
                        },
                    contentAlignment = Alignment.TopCenter
                ){
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(1.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .fadingEdge(topFade),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(movies[index].posterUrl)
                            .build(),
                        contentDescription = "Poster of ${movies[index].title}",
                        colorFilter = ColorFilter.colorMatrix(matrix),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        modifier = Modifier.padding(top = 24.dp),
                        text = movies[index].title.uppercase(),
                        color = Color.White,
                        fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif-condensed"), weight = FontWeight.Medium)),
                        fontSize = 40.sp,
                        lineHeight = 30.sp
                    )
                }
            }
        }
        
//        Column (
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ){
//            val movies = viewModel.moviesOrderedByTitle.collectAsState(initial = emptyList())
//            val pagerState = rememberPagerState ()
//            Text(
//                text = "MOVIES HERE",
//                fontSize = 30.sp
//            )
//        }
    }
}