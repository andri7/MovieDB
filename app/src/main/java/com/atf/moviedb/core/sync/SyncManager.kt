package com.atf.moviedb.core.sync

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SyncManager {

    private val mutex = Mutex()

    suspend fun <T> sync(
        block: suspend () -> T
    ): T {
        return mutex.withLock {
            block()
        }
    }
}