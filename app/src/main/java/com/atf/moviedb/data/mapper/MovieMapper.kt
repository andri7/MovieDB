package com.atf.moviedb.data.mapper

import com.atf.moviedb.data.local.entity.MovieEntity
import com.atf.moviedb.data.remote.dto.MovieDto
import com.atf.moviedb.domain.model.Movie


fun MovieDto.toEntity(genreId: Int) = MovieEntity(
    id = id,
    genreId = genreId,
    title = title,
    overview = overview,
    poster = poster,
    releaseDate = releaseDate,
    rating = rating
)

fun MovieEntity.toDomain() = Movie(
    id = id,
    title = title,
    overview = overview,
    poster = poster,
    releaseDate = releaseDate,
    rating = rating
)