package com.atf.moviedb.core.state

import com.atf.moviedb.core.error.AppError

sealed interface AppState<out T> {

    data object Idle : AppState<Nothing>

    data object Loading : AppState<Nothing>

    data class Success<T>(

        val data: T,

        val fromCache: Boolean = false,

        val lastUpdated: Long? = null

    ) : AppState<T>

    data class Refreshing<T>(

        val data: T

    ) : AppState<T>

    data class Empty(

        val message: String = "No Data"

    ) : AppState<Nothing>

    data class Offline<T>(

        val data: T? = null

    ) : AppState<T>

    data class Error(

        val error: AppError

    ) : AppState<Nothing>

}