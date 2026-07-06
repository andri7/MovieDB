package com.atf.moviedb.domain.usecase

import com.atf.moviedb.domain.repository.MovieRepository

class GetGenreUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getGenres()
}