package com.atf.moviedb.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val genres: List<GenreDto>
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)