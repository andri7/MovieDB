package com.atf.moviedb.presentation.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.atf.moviedb.core.utils.Constant
import org.koin.compose.viewmodel.koinViewModel
import androidx.core.net.toUri
import androidx.paging.compose.collectAsLazyPagingItems
import com.atf.moviedb.presentation.component.ErrorView
import com.atf.moviedb.presentation.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    onBack: () -> Unit,
    onTrailerClick: (String) -> Unit,
    viewModel: DetailViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val reviews =
        viewModel.getReviews(
            movieId
        )
            .collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.getDetail(movieId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        state.movie?.title ?: ""
                    )
                },
                navigationIcon = {

                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            null
                        )
                    }
                }
            )
        }
    ) { padding ->

        when {

            state.loading -> {
                CircularProgressIndicator()
            }

            state.movie != null -> {

                val movie = state.movie!!

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {

                    AsyncImage(
                        model = Constant.IMAGE_URL + movie.poster,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    )

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
//                        Text(
//                            text = movie.title,
//                            style = MaterialTheme.typography.headlineSmall
//                        )

                        Spacer(
                            Modifier.height(8.dp)
                        )

                        Text(
                            text = "⭐ ${movie.rating}"
                        )

                        Spacer(
                            Modifier.height(12.dp)
                        )


                        if (
                            state.trailers.isNotEmpty()
                        ) {

                            Button(
                                onClick = {
                                    val key =
                                        state.trailers.first().key

                                    onTrailerClick(
                                        key
                                    )

//                                    val intent = Intent(
//                                        Intent.ACTION_VIEW,
//                                        Uri.parse(
//                                            "https://www.youtube.com/watch?v=$key"
//                                        )
//                                    )
//
//                                    context.startActivity(intent)
                                }
                            ) {
                                Text(
                                    text = "▶ Watch Trailer"
                                )
                            }
                        }

                        Spacer(
                            Modifier.height(8.dp)
                        )

                        Text(
                            text = movie.genres.joinToString()
                        )

                        Spacer(
                            Modifier.height(16.dp)
                        )

                        Text(
                            text = "Sinopsis",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(
                            Modifier.height(8.dp)
                        )


                        Text(
                            text = movie.overview
                        )

                        Spacer(
                            Modifier.height(24.dp)
                        )

                        Text(
                            text = "User Reviews",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(
                            Modifier.height(12.dp)
                        )

                        repeat(
                            reviews.itemCount
                        ) { index ->

                            val review =
                                reviews[index]
                                    ?: return@repeat


                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
                            ) {

                                Column(
                                    Modifier.padding(12.dp)
                                ) {

                                    Text(
                                        "👤 ${review.author}"
                                    )


                                    Spacer(
                                        Modifier.height(8.dp)
                                    )


                                    Text(
                                        review.content
                                    )
                                }
                            }
                        }
                    }
                }
            }

            state.error != null -> {
                ErrorView(
                    message = state.error ?: "",
                    onRetry = {
                        viewModel.getDetail(
                            movieId
                        )
                    }
                )
            }
        }
    }
}