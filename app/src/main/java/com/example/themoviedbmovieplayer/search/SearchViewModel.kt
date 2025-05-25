package com.example.themoviedbmovieplayer.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.MovieRepository
import com.example.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: com.example.domain.repository.MovieRepository
) : ViewModel() {

    private val _state: MutableStateFlow<SearchMovieUiState> =
        MutableStateFlow(SearchMovieUiState.Initial)
    val state: StateFlow<SearchMovieUiState> = _state

    private var job = Job()
        get() {
            if (field.isCancelled) field = Job()
            return field
        }

    fun updateQuery(newText: String) {
        job.cancel()
        _state.value = SearchMovieUiState.Loading
        if (newText.isEmpty()) {
            _state.value = SearchMovieUiState.Initial
            return
        }
        val coroutineContext = job + Dispatchers.IO
        viewModelScope.launch(coroutineContext) {
            repository.searchMovies(newText, 1).collect { state ->
                when (state) {
                    is com.example.utils.Result.Failure<*> -> {
                        _state.value =
                            SearchMovieUiState.Error(state.message ?: "Some Error Occurred")
                    }

                    is com.example.utils.Result.Success<List<com.example.domain.model.Movie>> -> {
                        _state.value =
                            SearchMovieUiState.Success(state.data as List<com.example.domain.model.Movie>)
                    }
                }
            }
        }
    }
}