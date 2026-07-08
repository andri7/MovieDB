package com.atf.moviedb.core.di

import com.atf.moviedb.core.cache.CacheManager
import com.atf.moviedb.core.image.ImageLoaderFactory
import com.atf.moviedb.core.sync.SyncManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val appModule = module {
    single {
        SyncManager()
    }
}

val cacheManager = module {
    single {
        CacheManager(get())
    }
}

val imageCache = module {
    single {
        ImageLoaderFactory.create(androidContext())
    }
}

val appModules = listOf(
    appModule,
    cacheManager,
    imageCache,
    networkModule,
    databaseModule,
    repositoryModule,
    useCaseModule,
    viewModelModule,
)