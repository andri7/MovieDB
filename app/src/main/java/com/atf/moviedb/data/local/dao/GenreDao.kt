package com.atf.moviedb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atf.moviedb.data.local.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Query(
        "SELECT * FROM genres"
    )
    fun getGenres()
            : Flow<List<GenreEntity>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertGenres(
        genres:List<GenreEntity>
    )

}