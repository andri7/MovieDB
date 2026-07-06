package com.atf.moviedb.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.atf.moviedb.data.local.dao.GenreDao
import com.atf.moviedb.data.local.dao.MovieDao
import com.atf.moviedb.data.local.dao.ReviewDao
import com.atf.moviedb.data.local.entity.GenreEntity
import com.atf.moviedb.data.local.entity.MovieEntity
import com.atf.moviedb.data.local.entity.ReviewEntity

@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        ReviewEntity::class
    ],

    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    abstract fun reviewDao(): ReviewDao
}