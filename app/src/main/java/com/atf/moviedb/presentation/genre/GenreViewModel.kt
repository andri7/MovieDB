package com.atf.moviedb.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atf.moviedb.core.error.ErrorType
import com.atf.moviedb.core.state.AppState
import com.atf.moviedb.core.state.UiEvent
import com.atf.moviedb.domain.model.Genre
import com.atf.moviedb.domain.usecase.GetGenreUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GenreViewModel(
    private val getGenreUseCase: GetGenreUseCase
) : ViewModel() {

    private val _genres =
        MutableStateFlow<AppState<List<Genre>>>(
            AppState.Idle
        )

    val genres = _genres.asStateFlow()

    private val _event = MutableSharedFlow<UiEvent>()

    val event = _event.asSharedFlow()

    init {
        loadGenres()
    }

    fun loadGenres() {

        viewModelScope.launch {
            getGenreUseCase()
                .collect { state ->

                    _genres.value = state

                    if (
                        state is AppState.Error &&
                        state.error.type ==
                        ErrorType.UNAUTHORIZED
                    ) {
                        _event.emit(
                            UiEvent.Unauthorized
                        )
                    }
                }
        }
    }
}