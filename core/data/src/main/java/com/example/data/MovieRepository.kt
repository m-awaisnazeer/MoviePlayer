package com.example.data

import com.example.data.model.MovieDTO
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun searchMovies(query: String, page: Int): Flow<Result<MovieDTO>>
}