package com.atf.moviedb.core.utils

import com.atf.moviedb.core.error.AppError
import com.atf.moviedb.core.error.ErrorType
import com.atf.moviedb.core.network.ApiException
import io.ktor.client.plugins.*
import io.ktor.serialization.*
import java.io.IOException
import java.net.SocketTimeoutException

object ErrorMapper {

    fun map(throwable: Throwable): AppError {
        return when (throwable) {

            is ClientRequestException -> {
                when (throwable.response.status.value) {
                    401 -> AppError(
                        ErrorType.UNAUTHORIZED,
                        "Session expired",
                        throwable
                    )
                    else -> AppError(
                        ErrorType.UNKNOWN,
                        throwable.message ?: "Client error",
                        throwable
                    )
                }
            }

            is ServerResponseException -> {
                AppError(
                    ErrorType.SERVER_ERROR,
                    "Server problem",
                    throwable
                )
            }

            is HttpRequestTimeoutException,
            is SocketTimeoutException -> {
                AppError(
                    ErrorType.TIMEOUT,
                    "Connection timeout",
                    throwable
                )
            }

            is IOException -> {
                AppError(
                    ErrorType.NO_CONNECTION,
                    "No internet connection",
                    throwable
                )
            }

            is JsonConvertException -> {
                AppError(
                    ErrorType.SERIALIZATION,
                    "Invalid response format",
                    throwable
                )
            }

            is ApiException -> {
                when(throwable.code){

                    401 -> AppError(
                        ErrorType.UNAUTHORIZED,
                        throwable.message,
                        throwable
                    )

                    else -> AppError(
                        ErrorType.SERVER_ERROR,
                        throwable.message,
                        throwable
                    )
                }
            }

            else -> {
                AppError(
                    ErrorType.UNKNOWN,
                    throwable.message ?: "Unknown error",
                    throwable
                )
            }
        }
    }
}