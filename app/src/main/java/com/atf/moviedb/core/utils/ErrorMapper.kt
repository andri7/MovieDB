package com.atf.moviedb.core.utils

import io.ktor.client.network.sockets.SocketTimeoutException
import java.net.UnknownHostException

object ErrorMapper {

    fun getMessage(
        throwable: Throwable
    ): String {

        return when(
            throwable
        ){

            is UnknownHostException ->
                "No internet connection"

            is SocketTimeoutException ->
                "Connection timeout"

            else ->
                throwable.message
                    ?: "Something went wrong"
        }
    }
}