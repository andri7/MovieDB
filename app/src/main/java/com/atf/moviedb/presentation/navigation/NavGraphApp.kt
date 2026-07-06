package com.atf.moviedb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.atf.moviedb.presentation.detail.DetailScreen
import com.atf.moviedb.presentation.genre.GenreScreen
import com.atf.moviedb.presentation.movie.MovieScreen

@Composable
fun NavGraphApp(
    modifier: Modifier = Modifier
) {

    val navController =
        rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Genre.route
    ) {

        composable(
            Screen.Genre.route
        ) {

            GenreScreen(
                modifier = modifier,
                onClick = {
                    navController.navigate(
                        Screen.Movie.createRoute(it)
                    )
                }
            )
        }

        composable(
            Screen.Movie.route
        ) {

            val id =
                it.arguments
                    ?.getString("genreId")
                    ?.toInt()
                    ?: 0


            MovieScreen(
                genreId = id,
                modifier = modifier,
                onClick = { movieId ->
                    navController.navigate(
                        Screen.Detail.createRoute(movieId)
                    )
                }
            )
        }

        composable(
            Screen.Detail.route
        ) {

            val id =
                it.arguments
                    ?.getString("movieId")
                    ?.toInt()
                    ?: 0

            DetailScreen(
                movieId = id,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}