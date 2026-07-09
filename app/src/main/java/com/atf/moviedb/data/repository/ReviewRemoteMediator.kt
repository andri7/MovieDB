package com.atf.moviedb.data.repository

import androidx.paging.*
import androidx.room.withTransaction
import com.atf.moviedb.core.database.MovieDatabase
import com.atf.moviedb.data.local.entity.ReviewEntity
import com.atf.moviedb.data.mapper.toEntity
import com.atf.moviedb.data.remote.MovieApi

@OptIn(ExperimentalPagingApi::class)
class ReviewRemoteMediator(
    private val movieId: Int,
    private val api: MovieApi,
    private val db: MovieDatabase
) : RemoteMediator<Int, ReviewEntity>() {

    private var page = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReviewEntity>
    ): MediatorResult {

        return try {
            page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.APPEND -> page + 1
                LoadType.PREPEND ->
                    return MediatorResult.Success(true)
            }

            val response = api.getReviews(
                movieId,
                page
            )

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.reviewDao()
                        .clearReviews(movieId)
                }

                db.reviewDao()
                    .insertReviews(
                        response.results.map {
                            it.toEntity(movieId)
                        }
                    )
            }

            MediatorResult.Success(
                response.results.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}