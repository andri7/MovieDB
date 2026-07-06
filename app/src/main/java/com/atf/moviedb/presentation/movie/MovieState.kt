package com.atf.moviedb.presentation.movie

import com.atf.moviedb.domain.model.Movie

data class MovieState(
    val loading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String? = null
)