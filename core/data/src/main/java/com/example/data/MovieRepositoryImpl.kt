package com.example.data

import com.example.data.model.MovieDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class MovieRepositoryImpl(
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
        try {
            emitAll(getResult(apiCall.invoke()))
        } catch (exception: Exception) {
            emitAll(getException(exception))
        }
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
                        errorCode = -1,
                    )
                )
            }
        }.catch { throwable ->
                emit(
                    Result.Failure(
                        message = throwable.message,
                        errorCode = -1,
                    )
                )
            }
    }

    private fun <T> getException(
        exception: Exception,
    ): Flow<Result<T>> {
        return flow {
            emit(
                Result.Failure(message = exception.message, errorCode = -1)
            )
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
