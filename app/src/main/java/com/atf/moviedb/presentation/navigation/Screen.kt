package com.atf.moviedb.presentation.navigation

sealed class Screen(
    val route:String
){

    data object Genre:
        Screen("genre")


    data object Movie:
        Screen(
            "movie/{genreId}"
        ){

        fun createRoute(
            id:Int
        ) =
            "movie/$id"
    }

    data object Detail:
        Screen("detail/{movieId}") {

        fun createRoute(
            id:Int
        ) = "detail/$id"
    }
}