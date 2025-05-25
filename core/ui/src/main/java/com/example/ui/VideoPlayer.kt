package com.example.ui

@Composable
fun VideoPlayer(modifier: Modifier = Modifier, videoUrl: String) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.Builder()
                .setUri(videoUrl)
                .setMimeType(MimeTypes.APPLICATION_MP4)
                .build()
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = true
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}
