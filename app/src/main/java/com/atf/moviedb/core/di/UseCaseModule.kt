package com.atf.moviedb.core.di

import com.atf.moviedb.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {

    factory {
        GetGenreUseCase(get())
    }

    factory {
        GetMovieUseCase(get())
    }

    factory {
        GetMovieDetailUseCase(get())
    }

    factory {
        GetReviewUseCase(get())
    }

    factory {
        GetTrailerUseCase(get())
    }
}