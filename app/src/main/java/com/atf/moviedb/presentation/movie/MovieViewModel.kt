package com.atf.moviedb.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.atf.moviedb.domain.repository.MovieRepository
import com.atf.moviedb.domain.usecase.GetMovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _genreId = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies =
        _genreId
            .filter {
                it != 0
            }
            .flatMapLatest { id ->
                repository
                    .getMovies(id)
            }
            .cachedIn(viewModelScope)

    fun setGenre(id: Int){
        _genreId.value = id
    }
}