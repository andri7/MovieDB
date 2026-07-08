package com.atf.moviedb.core.di

import androidx.room.Room
import com.atf.moviedb.core.database.MovieDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java,
            "movie.db"
        ).build()
    }

    single {
        get<MovieDatabase>().movieDao()
    }

    single {
        get<MovieDatabase>().genreDao()
    }

    single {
        get<MovieDatabase>().reviewDao()
    }

    single {
        get<MovieDatabase>()
            .cacheInfoDao()
    }
}