package com.atf.moviedb.core.cache

import com.atf.moviedb.data.local.dao.CacheInfoDao
import com.atf.moviedb.data.local.entity.CacheInfoEntity

class CacheManager(
    private val dao: CacheInfoDao
) {
    suspend fun shouldRefresh(
        key: String,
        expired: Long
    ): Boolean {

        val cache = dao.getCache(key) ?: return true

        return System.currentTimeMillis() -
                cache.updatedAt > expired
    }

    suspend fun update(
        key: String
    ) {
        dao.saveCache(
            CacheInfoEntity(
                key,
                System.currentTimeMillis()
            )
        )
    }
}