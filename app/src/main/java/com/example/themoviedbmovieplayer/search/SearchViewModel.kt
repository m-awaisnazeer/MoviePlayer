package com.example.themoviedbmovieplayer.search

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.data.MovieRepository
import com.example.data.model.MovieItem
import com.example.data.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    fun searchMovies(searchedQuery: String) {
        this.searchedQuery.value = searchedQuery
    }

    private val searchedQuery = MutableStateFlow<String?>(null)

    val moviesPagingData: Flow<PagingData<MovieItem>> =
        searchedQuery
            .filterNotNull()
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        enablePlaceholders = false,
                        prefetchDistance = 2
                    ),
                    pagingSourceFactory = { MoviePagingSource(repository, query) }
                ).flow
            }
            .cachedIn(viewModelScope)
}