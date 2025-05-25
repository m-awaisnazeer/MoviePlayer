package com.example.data

import com.example.data.model.Movie
import com.example.data.model.MovieItem
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


