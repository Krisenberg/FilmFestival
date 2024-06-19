package com.example.filmfestival.composables

import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier,
    isFullscreen: Boolean,
    playbackTime: Float,
    onUpdatePlaybackTime: (Float) -> Unit,
    onEnterFullscreen: (Pair<View, Float>) -> Unit,
    onExitFullscreen: () -> Unit
){
    val currentPlaybackTime = remember { mutableFloatStateOf(playbackTime) }

    AndroidView(
        modifier = modifier,
        factory = { context->
            YouTubePlayerView(context = context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                enableAutomaticInitialization = false

                val iFramePlayerOptions: IFramePlayerOptions = IFramePlayerOptions.Builder()
                    .controls(1)
                    .rel(0)
                    .fullscreen(1)
                    .build()

                initialize(object: AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        if (isFullscreen) {
                            youTubePlayer.loadVideo(youtubeVideoId, playbackTime)
                            post {
                                youTubePlayer.toggleFullscreen()
                            }
                        } else {
                            youTubePlayer.cueVideo(youtubeVideoId, 0f)
                        }
                        youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                            override fun onCurrentSecond(
                                youTubePlayer: YouTubePlayer,
                                second: Float
                            ) {
                                super.onCurrentSecond(youTubePlayer, second)
                                currentPlaybackTime.floatValue = second
                                onUpdatePlaybackTime(second)
                            }
                        })
                    }
                }, iFramePlayerOptions)

                addFullscreenListener(object: FullscreenListener {
                    override fun onEnterFullscreen(
                        fullscreenView: View,
                        exitFullscreen: () -> Unit
                    ) {
                        fullscreenView.layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        onEnterFullscreen(Pair(fullscreenView, currentPlaybackTime.floatValue))
                    }
                    override fun onExitFullscreen() {
                        onExitFullscreen()
                    }
                })
            }
        }
    )
}