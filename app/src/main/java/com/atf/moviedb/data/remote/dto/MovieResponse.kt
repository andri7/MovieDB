package com.atf.moviedb.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieResponse(
    val results:List<MovieDto>
)



@Serializable
data class MovieDto(
    val id:Int,
    val title:String,
    val overview:String,
    @SerialName("poster_path")
    val poster:String?,
    @SerialName("release_date")
    val releaseDate:String?,
    @SerialName("vote_average")
    val rating:Double
)