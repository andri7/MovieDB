package com.atf.moviedb.presentation.component

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> PagingStateHandler(
    items: LazyPagingItems<T>,
    loading: @Composable () -> Unit,
    empty: @Composable () -> Unit,
    error: @Composable (String) -> Unit,
    content: @Composable () -> Unit
) {

    when (val state = items.loadState.refresh) {

        LoadState.Loading -> {
            loading()
        }

        is LoadState.Error -> {
            error(state.error.message ?: "Unknown error")
        }

        else -> {
            if (items.itemCount == 0) {
                empty()
            } else {
                content()
            }
        }
    }
}