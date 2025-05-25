package com.example.themoviedbmovieplayer.movieplayer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ui.VideoPlayer

@Composable
fun MoviePlayerScreen(modifier: Modifier = Modifier) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    VideoPlayer(
        videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        modifier = modifier
    )
}


@SuppressLint("ContextCastToActivity")
@Composable
fun LockScreenOrientation(orientation: Int) {
    val activity = LocalContext.current as Activity

    DisposableEffect(Unit) {
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation

        onDispose {
            activity.requestedOrientation = originalOrientation
        }
    }
}
