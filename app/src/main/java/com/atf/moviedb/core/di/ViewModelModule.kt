package com.atf.moviedb.core.di

import com.atf.moviedb.presentation.detail.DetailViewModel
import com.atf.moviedb.presentation.genre.GenreViewModel
import com.atf.moviedb.presentation.movie.MovieViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GenreViewModel(getGenreUseCase = get()) }

    viewModel { MovieViewModel(get()) }

    viewModel { DetailViewModel(get(), get(), get()) }
}