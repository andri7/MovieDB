package com.atf.moviedb.domain.usecase

import com.atf.moviedb.domain.repository.MovieRepository

class GetReviewUseCase(
    private val repository: MovieRepository
){
    operator fun invoke(
        movieId:Int
    ) = repository.getReviews(movieId)
}