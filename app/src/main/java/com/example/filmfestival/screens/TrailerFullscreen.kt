package com.example.filmfestival.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.drawable.GradientDrawable.Orientation
import android.util.Log
import android.view.View
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.filmfestival.composables.YouTubePlayer
import com.example.filmfestival.utils.NavigationHelper
import com.example.filmfestival.utils.NavigationRoutes

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun TrailerFullscreen(
    navHelper: NavigationHelper,
    trailerId: String,
    movieId: Int,
    playbackTime: Float
) {
//    val activity = (LocalContext.current as Activity)
//    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

//    lateinit var youTubePlayer: YouTubePlayer
//
//    var isFullscreen = false
//    val onBackPressedCallback = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            if (isFullscreen) {
//                // if the player is in fullscreen, exit fullscreen
//                youTubePlayer.toggleFullscreen()
//            } else {
//                finish()
//            }
//        }
//    }
    val activity = (LocalContext.current as Activity)
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    BackHandler {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        navHelper.navigateWithId(NavigationRoutes.MOVIE_DETAILS, movieId)
    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        val configuration = LocalConfiguration.current

        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp
        val fullscreenView = remember { mutableStateOf<View?>(null) }
        val currentPlaybackTimeSaver = run {
            val playbackTimeKey = "playbackTime"
            mapSaver(
                save = {
                    mapOf(playbackTimeKey to it)
                },
                restore = {
                    it[playbackTimeKey] as Float
                }
            )
        }
        val currentPlaybackTime = rememberSaveable(stateSaver = currentPlaybackTimeSaver) { mutableFloatStateOf(playbackTime) }

        if (fullscreenView.value == null) {
            Box(modifier = Modifier
                .padding(it)
                .height(screenHeight)
                .width(screenWidth)
            ){
                YouTubePlayer(
                    youtubeVideoId = trailerId,
                    lifecycleOwner = LocalLifecycleOwner.current,
                    modifier = Modifier.fillMaxSize(),
                    isFullscreen = true,
                    playbackTime = currentPlaybackTime.value,
                    onUpdatePlaybackTime = { second -> currentPlaybackTime.value = second },
                    onEnterFullscreen = {viewSecondPair ->
                        Log.d("DUPA", "Entering fullscreen: ${viewSecondPair.first}, ${viewSecondPair.second}")
                        fullscreenView.value = viewSecondPair.first
                        currentPlaybackTime.value = viewSecondPair.second
                    },
                    onExitFullscreen = {
//                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        navHelper.navigateWithId(NavigationRoutes.MOVIE_DETAILS, movieId)
                    }
                )
            }
        } else {
            Box(modifier = Modifier
                .padding(it)
                .height(screenHeight)
                .width(screenWidth)
            ){
                AndroidView(
                    factory = {
                        fullscreenView.value!!
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}