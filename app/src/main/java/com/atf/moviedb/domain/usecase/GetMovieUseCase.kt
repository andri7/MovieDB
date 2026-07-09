package com.atf.moviedb.domain.usecase

import com.atf.moviedb.domain.repository.MovieRepository

class GetMovieUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(genreId: Int) = repository.getMovies(genreId)
}