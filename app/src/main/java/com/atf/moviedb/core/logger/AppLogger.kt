package com.atf.moviedb.core.logger


import android.util.Log
import com.atf.moviedb.BuildConfig

object AppLogger {

    private const val TAG = "MovieDB"

    fun debug(message:String) {
        if(BuildConfig.DEBUG) {
            Log.d(TAG,message)
        }
    }

    fun error(
        throwable:Throwable,
        message:String? = null
    ){
        if(BuildConfig.DEBUG){
            Log.e(
                TAG,
                message ?: throwable.message.orEmpty(),
                throwable
            )
        }
    }
}