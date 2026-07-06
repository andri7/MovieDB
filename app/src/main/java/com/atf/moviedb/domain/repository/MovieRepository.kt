package com.atf.moviedb.domain.repository

import androidx.paging.PagingData
import com.atf.moviedb.domain.model.Genre
import com.atf.moviedb.domain.model.Movie
import com.atf.moviedb.domain.model.Review
import com.atf.moviedb.domain.model.Trailer
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getGenres(): Flow<List<Genre>>

    fun getMovies(genreId:Int): Flow<PagingData<Movie>>

    suspend fun getMovieDetail(movieId:Int): Movie

    fun getReviews(movieId:Int): Flow<PagingData<Review>>

    suspend fun getTrailer(movieId:Int): List<Trailer>

}