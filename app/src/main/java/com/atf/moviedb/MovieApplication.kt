package com.atf.moviedb

import android.app.Application
import com.atf.moviedb.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieApplication)
            modules(appModules)
        }
    }
}