package com.example.data

import com.example.data.model.MovieDTO
import com.example.utils.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun searchMovies(query: String, page: Int): Flow<Result<MovieDTO>>
}