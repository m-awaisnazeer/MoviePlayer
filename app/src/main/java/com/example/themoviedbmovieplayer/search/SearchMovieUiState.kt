package com.example.themoviedbmovieplayer.search

import com.example.data.model.Movie


sealed class SearchMovieUiState {
    data class Error(val message: String) : SearchMovieUiState()
    data object Loading : SearchMovieUiState()
    data object Initial : SearchMovieUiState()
    data class Success(val data: List<Movie>) : SearchMovieUiState()
    data object NoResultFound : SearchMovieUiState()
}
