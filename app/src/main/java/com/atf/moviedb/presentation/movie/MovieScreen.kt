package com.atf.moviedb.presentation.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import coil.compose.AsyncImage
import com.atf.moviedb.core.utils.Constant
import org.koin.compose.viewmodel.koinViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.atf.moviedb.core.utils.ImageUrl
import com.atf.moviedb.presentation.component.MovieImage
import com.atf.moviedb.presentation.component.PagingStateHandler

@Composable
fun MovieScreen(
    genreId:Int,
    modifier:Modifier = Modifier,
    viewModel:MovieViewModel = koinViewModel(),
    onClick:(Int)->Unit
){

    LaunchedEffect(
        genreId
    ){
        viewModel.setGenre(
            genreId
        )
    }

    val movies =
        viewModel.movies
            .collectAsLazyPagingItems()

    PagingStateHandler(

        items = movies,

        loading = {

            CircularProgressIndicator()

        },

        empty = {

            Text(
                "Movie not found"
            )

        },

        error = {

            Button(
                onClick = {
                    movies.retry()
                }
            ){
                Text(
                    "Retry"
                )
            }

        }

    ){

        LazyColumn(
            modifier =
                modifier.fillMaxSize()
        ){

            items(
                movies.itemCount
            ){ index ->


                val movie =
                    movies[index]
                        ?: return@items


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clickable {
                            onClick(
                                movie.id
                            )
                        }
                ){

                    Row(
                        modifier =
                            Modifier.padding(12.dp)
                    ){


                        MovieImage(
                            poster =
                                ImageUrl.poster(
                                    movie.poster
                                ),
                            modifier =
                                Modifier
                                    .width(100.dp)
                                    .height(150.dp)
                        )


                        Column(
                            modifier =
                                Modifier.padding(
                                    start = 12.dp
                                )
                        ){

                            Text(
                                movie.title,
                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium
                            )


                            Spacer(
                                Modifier.height(8.dp)
                            )


                            Text(
                                movie.releaseDate ?: "-"
                            )


                            Spacer(
                                Modifier.height(8.dp)
                            )


                            Text(
                                "⭐ ${movie.rating}"
                            )
                        }
                    }
                }
            }

            item {

                if(
                    movies.loadState.append
                            is LoadState.Loading
                ){
                    CircularProgressIndicator()
                }

            }
        }
    }
}