package com.example.themoviedbmovieplayer.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.data.model.MovieItem
import com.example.ui.MovieImage
import com.example.ui.R
import java.util.Locale

@Composable
fun MovieSearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    onMovieClick: (MovieItem) -> Unit
) {
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val movies = searchViewModel.moviesPagingData.collectAsLazyPagingItems()

    val sectionedItems = remember(movies.itemSnapshotList.items) {
        movies.itemSnapshotList.items
            .groupBy { it.mediaType.capitalize(Locale.getDefault()) }
    }

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextField(
                value = searchQuery.value,
                onValueChange = {
                    searchQuery.value = it
                    searchViewModel.searchMovies(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search Movies") }
            )

            when (val state = movies.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator()
                }
                is LoadState.NotLoading -> {
                    if (movies.itemCount == 0) {
                        Text("No movies found")
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            sectionedItems.forEach { (title, movies) ->
                                item {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.LightGray)
                                            .padding(8.dp)
                                    )
                                    LazyRow(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)

                                    ) {
                                        items(movies) { movie ->
                                            MovieItem(
                                                modifier = Modifier
                                                    .padding(8.dp)
                                                    .width(120.dp)
                                                    .height(170.dp)
                                                    .clickable {
                                                        onMovieClick(movie)
                                                    }, movie
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                is LoadState.Error -> {
                    Text("Error: ${state.error.message}")
                }

            }
        }
    }
}

@Composable
fun MovieItem(modifier: Modifier = Modifier, movie: MovieItem) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column {
            if (movie.posterPath != null) {
                MovieImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp), movie.posterPath.toString()
                )

            } else Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                painter = painterResource(R.drawable.baseline_image_24),
                contentDescription = "Image"
            )
            Text(modifier = Modifier.padding(6.dp), text = movie.title ?: movie.name ?: "")
        }
    }

}