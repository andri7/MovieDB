package com.atf.moviedb.core.di

import com.atf.moviedb.data.repository.MovieRepositoryImpl
import com.atf.moviedb.domain.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<MovieRepository> {
        MovieRepositoryImpl(
            api = get(),
            movieDao = get(),
            genreDao = get(),
            reviewDao = get(),
            db = get(),
            syncManager = get(),
            cacheManager = get()
        )
    }
}