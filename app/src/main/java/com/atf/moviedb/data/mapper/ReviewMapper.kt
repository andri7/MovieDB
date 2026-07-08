package com.atf.moviedb.data.mapper

import com.atf.moviedb.data.local.entity.ReviewEntity
import com.atf.moviedb.data.remote.dto.ReviewDto
import com.atf.moviedb.domain.model.Review

fun ReviewDto.toEntity(
    movieId: Int
) = ReviewEntity(
    id = id,
    movieId = movieId,
    author = author,
    content = content
)

fun ReviewEntity.toDomain() = Review(
    id = id,
    author = author,
    content = content
)