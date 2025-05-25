package com.example.searchmovie


sealed class SearchMovieUiState {
    data class Error(val message: String) : SearchMovieUiState()
    data object Loading : SearchMovieUiState()
    data object Initial : SearchMovieUiState()
    data class Success(val data: List<com.example.domain.model.Movie>) : SearchMovieUiState()
    data object NoResultFound : SearchMovieUiState()
}
