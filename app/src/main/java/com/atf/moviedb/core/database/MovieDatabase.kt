package com.atf.moviedb.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.atf.moviedb.data.local.dao.CacheInfoDao
import com.atf.moviedb.data.local.dao.GenreDao
import com.atf.moviedb.data.local.dao.MovieDao
import com.atf.moviedb.data.local.dao.ReviewDao
import com.atf.moviedb.data.local.entity.GenreEntity
import com.atf.moviedb.data.local.entity.MovieEntity
import com.atf.moviedb.data.local.entity.ReviewEntity
import com.atf.moviedb.data.local.entity.CacheInfoEntity

@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        ReviewEntity::class,
        CacheInfoEntity::class
    ],

    version = 2
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    abstract fun reviewDao(): ReviewDao

    abstract fun cacheInfoDao(): CacheInfoDao
}