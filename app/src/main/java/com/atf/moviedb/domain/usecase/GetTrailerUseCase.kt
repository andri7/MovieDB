package com.atf.moviedb.domain.usecase

import com.atf.moviedb.domain.repository.MovieRepository


class GetTrailerUseCase(
    private val repository: MovieRepository
){

    suspend operator fun invoke(
        movieId:Int
    )= repository.getTrailer(movieId)

}