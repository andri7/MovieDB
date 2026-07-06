package com.atf.moviedb.data.mapper

import com.atf.moviedb.data.remote.dto.TrailerDto
import com.atf.moviedb.domain.model.Trailer

fun TrailerDto.toDomain() = Trailer(
    id = id,
    key = key,
    name = name,
    site = site,
    type = type
)