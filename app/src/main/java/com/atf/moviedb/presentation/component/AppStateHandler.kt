package com.atf.moviedb.presentation.component

import androidx.compose.runtime.*
import com.atf.moviedb.core.state.AppState

@Composable
fun <T> AppStateHandler(
    state: AppState<T>,
    loading: @Composable () -> Unit,
    empty: @Composable () -> Unit,
    error: @Composable (String) -> Unit,
    success: @Composable (T) -> Unit
) {

    when (state) {

        AppState.Loading -> {
            loading()
        }

        is AppState.Empty -> {
            empty()
        }

        is AppState.Error -> {
            error(state.error.message)
        }

        is AppState.Success -> {
            success(state.data)
        }

        is AppState.Offline -> {
            state.data?.let { success(it) }
        }

        is AppState.Refreshing -> {
            success(state.data)
        }

        AppState.Idle -> Unit
    }
}