package com.atf.moviedb.presentation.genre

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.atf.moviedb.core.state.UiEvent
import com.atf.moviedb.presentation.component.AppStateHandler
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GenreScreen(
    modifier: Modifier = Modifier,
    viewModel: GenreViewModel = koinViewModel(),
    onClick: (Int) -> Unit
) {

    val context = LocalContext.current
    val state by viewModel.genres.collectAsState()

    LaunchedEffect(Unit) {

        viewModel.event.collect { event ->

            when (event) {

                UiEvent.Unauthorized -> {
                    Toast.makeText(
                        context,
                        "Unauthorized",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is UiEvent.Message -> {
                    Toast.makeText(
                        context,
                        "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    AppStateHandler(
        state = state,

        loading = {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        },
        empty = {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No genre found"
                )
            }

        },
        error = { message ->

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message
                )
            }

        },
        success = { genres ->

            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {

                items(
                    genres
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
    )
}