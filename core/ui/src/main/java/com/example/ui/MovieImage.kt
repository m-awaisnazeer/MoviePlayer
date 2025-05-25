package com.example.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MovieImage(modifier: Modifier, posterPath: String) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${posterPath}"

    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(context).data(imageUrl).crossfade(true).build(),
        contentDescription = "Loaded Image",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}