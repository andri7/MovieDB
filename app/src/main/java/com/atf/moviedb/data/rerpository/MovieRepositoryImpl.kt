package com.atf.moviedb.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.atf.moviedb.core.database.MovieDatabase
import com.atf.moviedb.data.local.dao.GenreDao
import com.atf.moviedb.data.local.dao.MovieDao
import com.atf.moviedb.data.local.dao.ReviewDao
import com.atf.moviedb.data.mapper.*
import com.atf.moviedb.data.remote.MovieApi
import com.atf.moviedb.data.rerpository.MovieRemoteMediator
import com.atf.moviedb.data.rerpository.ReviewRemoteMediator
import com.atf.moviedb.domain.model.*
import com.atf.moviedb.domain.repository.MovieRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieRepositoryImpl(
    private val api: MovieApi,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao,
    private val reviewDao: ReviewDao,
    private val db: MovieDatabase
) : MovieRepository {

    override fun getGenres(): Flow<List<Genre>> {
        refreshGenres()
        return genreDao.getGenres().map { list ->
            list.map { it.toDomain() }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun refreshGenres() {
        GlobalScope.launch {
            try {
                val response = api.getGenres()
                val genres = response.genres.map {
                    it.toEntity()
                }
                genreDao.insertGenres(genres)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(genreId: Int): Flow<PagingData<Movie>> {
//        refreshMovies(genreId)

        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator =
                MovieRemoteMediator(
                    genreId,
                    api,
                    db
                ),
            pagingSourceFactory = {
                movieDao.getMovies(
                    genreId
                )
            }

        ).flow.map { paging ->

            paging.map {
                it.toDomain()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun refreshMovies(genreId: Int) {
        GlobalScope.launch {
            try {
                val response = api.getMovies(genreId, 1)
                val movies = response.results.map {
                    it.toEntity(genreId)
                }
                movieDao.insertMovies(movies)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Movie {
        return api.getMovieDetail(movieId)
            .toEntity(0)
            .toDomain()
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getReviews(movieId: Int): Flow<PagingData<Review>> {
        refreshReviews(movieId)

        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator =
                ReviewRemoteMediator(
                    movieId,
                    api,
                    db
                ),
            pagingSourceFactory = {
                reviewDao.getReviews(
                    movieId
                )
            }
        ).flow.map { data ->

            data.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getTrailer(movieId: Int): List<Trailer> {
        return try {
            api.getTrailer(movieId)
                .results
                .filter {
                    it.site == "YouTube" &&
                            it.type == "Trailer"
                }
                .map {
                    it.toDomain()
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun refreshReviews(movieId: Int) {
        GlobalScope.launch {
            try {
                val response = api.getReviews(
                    id = movieId,
                    page = 1
                )

                val reviews = response.results.map {
                    it.toEntity(movieId)
                }

                reviewDao.insertReviews(reviews)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}