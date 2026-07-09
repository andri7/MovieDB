package com.atf.moviedb.core.image

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import java.io.File

object ImageLoaderFactory {
    fun create(context: Context): ImageLoader {

        return ImageLoader.Builder(context).memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }.diskCache {
            DiskCache.Builder()
                .directory(
                    File(context.cacheDir, "movie_image_cache")
                )
                .maxSizeBytes(100L * 1024 * 1024)
                .build()
        }
            .crossfade(true)
            .build()
    }
}