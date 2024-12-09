package com.example.gitclone.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    companion object {
        fun <T> success(data: T): Result<T> = Success(data)
        fun failure(exception: Exception): Result<Nothing> = Failure(exception)
        fun loading(): Result<Nothing> = Loading
    }
}