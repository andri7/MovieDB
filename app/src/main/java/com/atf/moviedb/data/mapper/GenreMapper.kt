package com.atf.moviedb.data.mapper

import com.atf.moviedb.data.local.entity.GenreEntity
import com.atf.moviedb.data.remote.dto.GenreDto
import com.atf.moviedb.domain.model.Genre

fun GenreDto.toEntity() = GenreEntity(
    id = id,
    name = name
)

fun GenreEntity.toDomain() = Genre(
    id = id,
    name = name
)