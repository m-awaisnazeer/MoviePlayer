package com.example.themoviedbmovieplayer.moviedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.data.model.MovieItem
import com.example.ui.MovieImage
import com.example.ui.PlayButton

@Composable
fun MovieDetailScreen(movie: MovieItem, onPlayClick: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(), floatingActionButton = {
            if (movie.mediaType == "tv" || movie.mediaType == "movie") {
                PlayButton(onClick = onPlayClick)
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MovieImage(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                posterPath = movie.posterPath.toString()
            )
            Text(
                text = movie.title ?: movie.name ?: "",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Release Date: ${movie.releaseDate}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Rating: ${movie.voteAverage}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Popularity: ${movie.popularity}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Vote Count: ${movie.voteCount}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Original Language: ${movie.originalLanguage}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

}