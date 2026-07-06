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

@Composable
fun MovieScreen(
    genreId: Int,
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = koinViewModel(),
    onClick: (Int) -> Unit
) {
//    val state by viewModel.state.collectAsState()

    val movies =
        viewModel.getMovies(
            genreId
        ).collectAsLazyPagingItems()

    LaunchedEffect(genreId) {
        viewModel.getMovies(genreId)
    }

    if(
        movies.itemCount == 0 &&
        movies.loadState.refresh
                !is LoadState.Loading
    ){

        Text(
            text = "Movie not found"
        )

        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(movies.itemCount) { index ->
            val movie =
                movies[index]
                    ?: return@items
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clickable {
                        onClick(movie.id)
                    }
            ) {

                Row(
                    modifier = Modifier.padding(12.dp)
                ) {

                    AsyncImage(
                        model = Constant.IMAGE_URL + movie.poster,
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(100.dp)
                            .height(150.dp)
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp)
                    ) {

                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(
                            Modifier.height(8.dp)
                        )

                        Text(
                            text = movie.releaseDate ?: "-"
                        )

                        Spacer(
                            Modifier.height(8.dp)
                        )

                        Text(
                            text = "⭐ ${movie.rating}"
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


            if(
                movies.loadState.append
                        is LoadState.Error
            ){

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
        }
    }
}