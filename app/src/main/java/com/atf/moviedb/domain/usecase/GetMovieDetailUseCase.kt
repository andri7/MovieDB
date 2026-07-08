package com.atf.moviedb.domain.usecase

import com.atf.moviedb.domain.repository.MovieRepository

class GetMovieDetailUseCase(
    private val repository: MovieRepository
){

    suspend operator fun invoke(
        id:Int
    ) =
        repository.getMovieDetail(id)

}