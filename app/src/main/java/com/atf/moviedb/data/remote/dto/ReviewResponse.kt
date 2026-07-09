package com.atf.moviedb.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponse(
    val results: List<ReviewDto>
)

@Serializable
data class ReviewDto(
    val id: String,
    val author: String,
    val content: String
)