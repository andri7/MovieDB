package com.atf.moviedb.data.rerpository

import androidx.paging.*
import androidx.room.withTransaction
import com.atf.moviedb.core.database.MovieDatabase
import com.atf.moviedb.data.mapper.toEntity
import com.atf.moviedb.data.remote.MovieApi

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val genreId: Int,
    private val api: MovieApi,
    private val db: MovieDatabase
) : RemoteMediator<Int, com.atf.moviedb.data.local.entity.MovieEntity>() {

    private var page = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, com.atf.moviedb.data.local.entity.MovieEntity>
    ): MediatorResult {

        return try {

            page =
                when(loadType){

                    LoadType.REFRESH -> 1

                    LoadType.APPEND -> page + 1

                    LoadType.PREPEND ->
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                }


            val response =
                api.getMovies(
                    genreId,
                    page
                )


            db.withTransaction {

                if(loadType == LoadType.REFRESH){
                    db.movieDao()
                        .clearMovies(genreId)
                }


                db.movieDao()
                    .insertMovies(
                        response.results.map {
                            it.toEntity(
                                genreId
                            )
                        }
                    )
            }


            MediatorResult.Success(
                endOfPaginationReached =
                    response.results.isEmpty()
            )


        } catch (e: Exception){

            MediatorResult.Error(e)

        }
    }
}