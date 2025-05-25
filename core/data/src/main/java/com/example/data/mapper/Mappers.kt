package com.example.data.mapper

import com.example.data.model.MovieItem
import com.example.domain.model.Movie

fun MovieItem.toDomainMovie() = Movie(
    backdropPath = backdropPath.toString(),
    mediaType = mediaType.toString(),
    name = name,
    originalLanguage = originalLanguage.toString(),
    originalName = originalName.toString(),
    originalTitle = originalName.toString(),
    overview = overview.toString(),
    popularity = popularity ?: 0.0,
    posterPath = posterPath,
    releaseDate = releaseDate.toString(),
    title = title,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0
)