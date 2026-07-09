package com.atf.moviedb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache_info")
data class CacheInfoEntity(
    @PrimaryKey
    val key: String,
    val updatedAt: Long
)