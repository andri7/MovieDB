package com.atf.moviedb.core.error

data class AppError(
    val type: ErrorType,
    val message:String,
    val throwable: Throwable? = null
)