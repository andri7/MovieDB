package com.atf.moviedb.presentation.genre

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GenreScreen(
    modifier: Modifier = Modifier,
    viewModel: GenreViewModel = koinViewModel(),
    onClick: (Int) -> Unit
) {

    val state by viewModel.state.collectAsState()

    when {

        state.loading -> {
            CircularProgressIndicator()
        }

        state.error != null -> {
            Text(
                text = state.error
                    ?: ""
            )
        }

        else -> {

            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {

                items(
                    state.genres
                ) { genre ->

                    Text(
                        text = genre.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onClick(
                                    genre.id
                                )
                            }
                            .padding(16.dp)
                    )

                }
            }
        }
    }
}