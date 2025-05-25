package com.example.data.repository

import com.example.data.TheMovieDbApi
import com.example.data.mapper.toDomainMovie
import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import com.example.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDbApi: TheMovieDbApi
) : MovieRepository {

    override suspend fun searchMovies(query: String, page: Int): Flow<Result<List<Movie>>> = flow {
        try {
            val response = movieDbApi.searchMovies(query = query, page = page)
            if (response.isSuccessful) {
                response.body()?.movieItems?.let { movieItems ->
                    val movies: List<Movie> = movieItems.map { it.toDomainMovie() }
                    emit(Result.Success(movies, 200))
                }
            } else {
                emit(
                    Result.Failure(
                        message = response.run { message() },
                        errorCode = -111,
                    )
                )
            }

        } catch (e: IOException) {
            emit(
                Result.Failure(
                    message = e.message ?: "Network connection Error", errorCode = -100
                )
            )
        }
    }
}




