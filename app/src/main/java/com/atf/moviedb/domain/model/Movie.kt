package com.atf.moviedb.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String?,
    val releaseDate: String?,
    val rating: Double,
    val genres: List<String> = emptyList()
)