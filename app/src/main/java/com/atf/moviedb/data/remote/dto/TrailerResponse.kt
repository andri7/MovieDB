package com.atf.moviedb.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TrailerResponse(
    val results: List<TrailerDto>
)

@Serializable
data class TrailerDto(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String
)