package com.example.filmfestival.utils

import android.content.ContentResolver
import android.content.Context
import androidx.media3.common.MediaItem
import android.net.Uri
import android.util.Log
import androidx.media3.exoplayer.ExoPlayer
import com.example.filmfestival.R

enum class Sound {
    CLICK_DRIP,
    CLICK_PLINK
}

class SoundManager (
    private val context: Context
){
    private val player = ExoPlayer.Builder(context).build()
    private val soundMap = mapOf(
        Pair(Sound.CLICK_DRIP, R.raw.click_drip),
        Pair(Sound.CLICK_PLINK, R.raw.click_plink)
    )

    fun playSound(sound: Sound) {
        val packageName = context.packageName
        val fileUri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(packageName)
            .path(soundMap[sound]!!.toString())
            .build()
        player.setMediaItem(MediaItem.fromUri(fileUri))
        player.prepare()
        player.play()
    }
}