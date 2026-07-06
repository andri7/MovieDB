package com.atf.moviedb.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.atf.moviedb.domain.usecase.GetMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        MovieState(loading = true)
    )

    val state = _state.asStateFlow()

    fun getMovies(genreId: Int) =
        getMovieUseCase(
            genreId
        ).cachedIn(
            viewModelScope
        )
}