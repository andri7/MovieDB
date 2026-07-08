package com.atf.moviedb.core.network

import android.util.Log
import com.atf.moviedb.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.accept
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {

    fun create(): HttpClient {
        return HttpClient(Android) {

            expectSuccess = true

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 30_000
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        if (BuildConfig.DEBUG) {
                            Log.d("MOVIEDB_API", message)
                        }
                    }
                }

                level = if (BuildConfig.DEBUG) {
                    LogLevel.ALL
                } else {
                    LogLevel.NONE
                }
            }

            HttpResponseValidator {

                handleResponseExceptionWithRequest { exception, _ ->

                    val clientException =
                        exception as? ClientRequestException

                    when (
                        clientException?.response?.status
                    ) {

                        HttpStatusCode.Unauthorized -> {
                            throw ApiException(
                                message = "Unauthorized",
                                code = 401
                            )
                        }

                        else -> throw exception
                    }
                }
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
    }
}