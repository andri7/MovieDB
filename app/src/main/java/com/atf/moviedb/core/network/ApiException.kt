package com.atf.moviedb.core.network

class ApiException(override val message: String, val code: Int? = null) : Exception(message)