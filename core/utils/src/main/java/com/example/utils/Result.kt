package com.example.utils

sealed class Result<out T> {
    data class Success<T>(var data: T?, var responseCode: Int) : Result<T>()
    data class Failure<T>(
        var message: String?,
        var errorCode: Int?,
    ) : Result<T>()
}