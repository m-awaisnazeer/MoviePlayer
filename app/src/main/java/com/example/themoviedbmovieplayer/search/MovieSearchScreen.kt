package com.example.themoviedbmovieplayer.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.data.model.MovieItem
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
            .filterNotNull()
            .groupBy { it.mediaType.capitalize(Locale.getDefault()) }
            .flatMap { (mediaType, items) ->
                listOf(MovieListItem.Header(mediaType)) +
                        items.map { MovieListItem.Movie(it) }
            }
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
                            items(sectionedItems) { item ->
                                when (item) {
                                    is MovieListItem.Header -> Text(
                                        text = item.title.toString(),
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.LightGray)
                                            .padding(8.dp)
                                    )
                                    is MovieListItem.Movie -> Text(
                                        text = item.data.name?:item.data.title.toString(),
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).clickable {
                                            onMovieClick(item.data)
                                        }
                                    )
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

sealed class MovieListItem {
    data class Header(val title: String?) : MovieListItem()
    data class Movie(val data: MovieItem) : MovieListItem()
}