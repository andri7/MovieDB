package com.atf.moviedb.data.local.dao

import androidx.room.*
import com.atf.moviedb.data.local.entity.CacheInfoEntity

@Dao
interface CacheInfoDao {

    @Query(
        "SELECT * FROM cache_info WHERE `key`=:key"
    )
    suspend fun getCache(
        key:String
    ):CacheInfoEntity?

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun saveCache(
        cache:CacheInfoEntity
    )

    @Query(
        "DELETE FROM cache_info WHERE `key`=:key"
    )
    suspend fun clear(
        key:String
    )
}