package com.atf.moviedb.core.di

import com.atf.moviedb.core.network.KtorClient
import com.atf.moviedb.data.remote.MovieApi
import org.koin.dsl.module

val networkModule = module {

    single { KtorClient.client }

    single { MovieApi(get()) }
}