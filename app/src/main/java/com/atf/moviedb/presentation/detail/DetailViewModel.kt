package com.atf.moviedb.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.atf.moviedb.core.utils.ErrorMapper
import com.atf.moviedb.domain.usecase.GetMovieDetailUseCase
import com.atf.moviedb.domain.usecase.GetReviewUseCase
import com.atf.moviedb.domain.usecase.GetTrailerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val useCase: GetMovieDetailUseCase,
    private val reviewUseCase: GetReviewUseCase,
    private val trailerUseCase: GetTrailerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        DetailState(loading = true)
    )

    val state = _state.asStateFlow()

    fun getDetail(id: Int) {
        viewModelScope.launch {
            try {
                val result = useCase(id)
                _state.value = DetailState(
                    movie = result
                )
            } catch (e: Exception) {
                _state.value =  _state.value.copy(
                    loading = false,
                    error = ErrorMapper.getMessage(e)
                )
            }
        }

//        getReviews(id)
        getTrailer(id)
    }

    fun getReviews(movieId: Int)=
        reviewUseCase(
            movieId
        )
            .cachedIn(
                viewModelScope
            )

    private fun getTrailer(id: Int) {
        viewModelScope.launch {
            val result = trailerUseCase(id)

            _state.value =
                _state.value.copy(
                    trailers = result
                )
        }
    }
}