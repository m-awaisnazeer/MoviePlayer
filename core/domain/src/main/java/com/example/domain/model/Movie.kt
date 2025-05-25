package com.example.domain.model

data class Movie(
    val backdropPath: String,
    val mediaType: String,
    val name: String?,
    val originalLanguage: String,
    val originalName: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String?,
    val voteAverage: Double,
    val voteCount: Int
)