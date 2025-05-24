package com.example.themoviedbmovieplayer.moviedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.model.MovieItem

@Composable
fun MovieDetailScreen() {
    val movie = remember { mutableStateOf<MovieItem?>(null) }
    Scaffold(
        modifier = Modifier.fillMaxSize(), floatingActionButton = {
            if (movie.value?.mediaType?.equals("tv") == true || movie.value?.mediaType == "movie") {
                PlayButton(onClick = {

                })
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CoilImage(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                imageUrl = movie.value?.posterPath.toString()
            )
            Text(
                text = movie.value?.title ?: movie.value?.name ?: "",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = movie.value?.overview ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Release Date: ${movie.value?.releaseDate ?: movie.value?.firstAirDate}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Rating: ${movie.value?.voteAverage ?: 0.0}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Popularity: ${movie.value?.popularity ?: 0.0}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Vote Count: ${movie.value?.voteCount ?: 0}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Original Language: ${movie.value?.originalLanguage ?: ""}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

}

@Composable
fun PlayButton(
    modifier: Modifier = Modifier, onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF1E88E5), Color(0xFF42A5F5))
                )
            )
            .clickable(onClick = onClick)
            .shadow(8.dp, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "Play",
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun CoilImage(modifier: Modifier, imageUrl: String) {
    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(context).data(imageUrl).crossfade(true).build(),
        contentDescription = "Loaded Image",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}