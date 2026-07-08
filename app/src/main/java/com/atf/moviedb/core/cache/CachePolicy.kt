package com.atf.moviedb.core.cache

object CachePolicy {

    private const val MOVIE_CACHE_TIME =
        30 * 60 * 1000L

    fun shouldRefresh(
        lastUpdate:Long?
    ):Boolean {

        if(lastUpdate == null)
            return true

        return System.currentTimeMillis() - lastUpdate >
                MOVIE_CACHE_TIME
    }
}