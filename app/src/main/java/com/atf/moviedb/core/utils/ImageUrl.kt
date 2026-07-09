package com.atf.moviedb.core.utils

object ImageUrl {
    fun poster(path: String?): String? {
        return path?.let { Constant.IMAGE_URL + it }
    }
}