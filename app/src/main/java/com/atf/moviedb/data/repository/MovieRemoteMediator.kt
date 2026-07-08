package com.atf.moviedb.data.repository

import androidx.paging.*
import androidx.room.withTransaction
import com.atf.moviedb.core.cache.CacheManager
import com.atf.moviedb.core.database.MovieDatabase
import com.atf.moviedb.data.local.entity.MovieEntity
import com.atf.moviedb.data.mapper.toEntity
import com.atf.moviedb.data.remote.MovieApi

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val genreId: Int,
    private val api: MovieApi,
    private val db: MovieDatabase,
    private val cacheManager: CacheManager
) : RemoteMediator<Int, MovieEntity>() {

    private val movieDao =
        db.movieDao()

    override suspend fun initialize()
            : InitializeAction {

        val refresh =
            cacheManager.shouldRefresh(
                key = "MOVIE_$genreId",
                expired = 30 * 60 * 1000L
            )

        return if (refresh)
            InitializeAction.LAUNCH_INITIAL_REFRESH
        else
            InitializeAction.SKIP_INITIAL_REFRESH
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {

        return try {

            val page = when (loadType) {

                LoadType.REFRESH -> 1

                LoadType.APPEND -> {

                    val itemCount =
                        state.pages.sumOf {
                            it.data.size
                        }

                    (itemCount / state.config.pageSize) + 1
                }

                LoadType.PREPEND -> {

                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
            }

            val response =
                api.getMovies(
                    genreId = genreId,
                    page = page
                )

            val movies =
                response.results.map {

                    it.toEntity(
                        genreId
                    )
                }

            db.withTransaction {

                if (loadType == LoadType.REFRESH) {

                    movieDao.clearMovies(genreId)

                }

                movieDao.insertMovies(
                    movies
                )

                cacheManager.update(
                    "MOVIE_$genreId"
                )
            }

            MediatorResult.Success(
                endOfPaginationReached =
                    response.results.isEmpty()
            )

        } catch (e: Exception) {

            MediatorResult.Error(e)

        }
    }
}