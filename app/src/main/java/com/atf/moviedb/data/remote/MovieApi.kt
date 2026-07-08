package com.atf.moviedb.data.remote

import com.atf.moviedb.core.utils.Constant
import com.atf.moviedb.data.remote.dto.GenreResponse
import com.atf.moviedb.data.remote.dto.MovieDto
import com.atf.moviedb.data.remote.dto.MovieResponse
import com.atf.moviedb.data.remote.dto.ReviewResponse
import com.atf.moviedb.data.remote.dto.TrailerResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class MovieApi(

    private val client: HttpClient

) {

    fun token() {}

    suspend fun getGenres() =
        client.get(
            Constant.BASE_URL + "genre/movie/list"
        ) {
            parameter("api_key", Constant.TOKEN)

        }.body<GenreResponse>()

    suspend fun getMovies(
        genreId: Int,
        page: Int
    ) =

        client.get(
            Constant.BASE_URL + "discover/movie"
        ) {

            parameter("api_key", Constant.TOKEN)
            parameter("with_genres", genreId)
            parameter("page", page)

        }.body<MovieResponse>()


    suspend fun getMovieDetail(
        id: Int
    ) =

        client.get(Constant.BASE_URL + "movie/$id") {
            parameter("api_key", Constant.TOKEN)
        }.body<MovieDto>()

    suspend fun getReviews(
        id: Int,
        page: Int
    ) = client.get(Constant.BASE_URL + "movie/$id/reviews") {
        parameter("api_key", Constant.TOKEN)
        parameter("page", page)
    }.body<ReviewResponse>()

    suspend fun getTrailer(
        id: Int
    ) =
        client.get(Constant.BASE_URL + "movie/$id/videos") {
            parameter("api_key", Constant.TOKEN)
        }.body<TrailerResponse>()
}