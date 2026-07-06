package com.atf.moviedb.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atf.moviedb.domain.usecase.GetGenreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GenreViewModel(
    private val getGenreUseCase: GetGenreUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        GenreState(loading = true)
    )

    val state = _state.asStateFlow()

    init {
        getGenres()
    }

    private fun getGenres() {
        viewModelScope.launch {
            getGenreUseCase()
                .collect { genres ->
                    _state.value = GenreState(
                        loading = false,
                        genres = genres
                    )
                }
        }
    }
}