package com.atf.moviedb.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.atf.moviedb.data.local.entity.ReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Query("""SELECT *FROM reviews WHERE movieId=:movieId""")
    fun getReviews(movieId: Int): PagingSource<Int, ReviewEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<ReviewEntity>)

    @Query("DELETE FROM reviews WHERE movieId=:movieId")
    suspend fun clearReviews(movieId: Int)
}