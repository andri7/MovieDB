package com.atf.moviedb.presentation.detail

import com.atf.moviedb.domain.model.Movie
import com.atf.moviedb.domain.model.Review
import com.atf.moviedb.domain.model.Trailer

data class DetailState(
    val loading: Boolean = false,
    val movie: Movie? = null,
    val trailers: List<Trailer> = emptyList(),
    val error: String? = null
)