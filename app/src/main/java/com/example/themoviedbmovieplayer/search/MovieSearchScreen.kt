package com.example.themoviedbmovieplayer.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun MovieSearchScreen(modifier: Modifier = Modifier, searchViewModel: SearchViewModel) {
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val movies = searchViewModel.moviesPagingData.collectAsLazyPagingItems()

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
                        LazyColumn(
                            modifier = Modifier.weight(1f).fillMaxWidth()
                        ) {
                            items(movies.itemCount) { index ->
                                val movie = movies[index]
                                Text(text = movie?.name ?: "Loading...")
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


val LazyPagingItems<*>?.isEmptyAndNotLoading: Boolean
    get() = this?.itemCount == 0 && this.loadState.refresh != LoadState.Loading

val LazyPagingItems<*>?.isNotEmptyAndNotLoading: Boolean
    get() = (this?.itemCount ?: 0) > 0 && this?.loadState?.refresh != LoadState.Loading
