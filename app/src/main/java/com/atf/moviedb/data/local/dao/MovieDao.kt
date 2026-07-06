package com.atf.moviedb.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.atf.moviedb.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query(
        "SELECT * FROM movies WHERE genreId=:genreId"
    )
    fun getMovies(
        genreId: Int
    ): PagingSource<Int, MovieEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertMovies(
        movies: List<MovieEntity>
    )

    @Query(
        "DELETE FROM movies WHERE genreId=:genreId"
    )
    suspend fun clearMovies(
        genreId: Int
    )
}