package com.atf.moviedb.core.network

import com.atf.moviedb.core.utils.Constant
import io.ktor.client.*
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {


    val client = HttpClient(Android) {


        install(ContentNegotiation) {

            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }


        defaultRequest {

            header(
                "Authorization",
                "Bearer ${Constant.TOKEN}"
            )
        }
    }
}