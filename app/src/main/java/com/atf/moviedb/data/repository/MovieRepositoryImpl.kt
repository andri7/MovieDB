package com.atf.moviedb.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.atf.moviedb.core.cache.CacheManager
import com.atf.moviedb.core.data.offlineFirstResource
import com.atf.moviedb.core.database.MovieDatabase
import com.atf.moviedb.core.state.AppState
import com.atf.moviedb.core.sync.SyncManager
import com.atf.moviedb.data.local.dao.GenreDao
import com.atf.moviedb.data.local.dao.MovieDao
import com.atf.moviedb.data.local.dao.ReviewDao
import com.atf.moviedb.data.mapper.*
import com.atf.moviedb.data.remote.MovieApi
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
    private val db: MovieDatabase,
    private val syncManager: SyncManager,
    private val cacheManager: CacheManager
) : MovieRepository {

    override fun getGenres(): Flow<AppState<List<Genre>>> {
       return offlineFirstResource(
           query = {
               genreDao.getGenres()
                   .map { list ->
                       list.map {
                           it.toDomain()
                       }
                   }
           },

           fetch = {
               syncManager.sync {
                   api.getGenres()
               }
           },

           saveFetchResult = {
               genreDao.insertGenres(
                   it.genres.map { genre ->
                       genre.toEntity()
                   }
               )
           },

           shouldFetch = {
               it.isEmpty()
           }
       )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(
        genreId:Int
    ):Flow<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),

            remoteMediator = MovieRemoteMediator(
                genreId = genreId,
                api = api,
                db = db,
                cacheManager = cacheManager
            ),

            pagingSourceFactory = {
                movieDao.getMovies(genreId)
            }

        ).flow.map { paging ->

            paging.map {
                it.toDomain()
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