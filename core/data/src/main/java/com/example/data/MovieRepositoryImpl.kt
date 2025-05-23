package com.example.data

import com.example.data.model.MovieDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDbApi: TheMovieDbApi
) : MovieRepository {

    override suspend fun searchMovies(query: String, page: Int): Flow<Result<MovieDTO>> {
        return apiCallService {
            movieDbApi.searchMovies(query = query, page = page)
        }
    }

    private fun <T> apiCallService(
        apiCall: suspend () -> Response<T>,
    ): Flow<Result<T>> = flow {
        emitAll(getResult(apiCall.invoke()))
    }

    private fun <T> getResult(
        response: Response<T>,
    ): Flow<Result<T>> {
        return flow {
            if (response.isSuccessful) {
                emit(Result.Success(data = response.body(), responseCode = response.code()))
            } else {
                emit(
                    Result.Failure(
                        message = response.run { message() },
                        errorCode = -111,
                    )
                )
            }
        }
    }

}

sealed class Result<out T> {
    data class Success<T>(var data: T?, var responseCode: Int) : Result<T>()
    data class Failure<T>(
        var message: String?,
        var errorCode: Int?,
    ) : Result<T>()
}
