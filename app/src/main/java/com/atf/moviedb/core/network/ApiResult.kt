package com.atf.moviedb.core.network

import com.atf.moviedb.core.error.AppError

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val error: AppError) : ApiResult<Nothing>
}