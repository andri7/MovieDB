package com.atf.moviedb.presentation.genre

import com.atf.moviedb.domain.model.Genre

data class GenreState(
    val loading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val error: String? = null
)