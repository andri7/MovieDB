package com.atf.moviedb.presentation

import com.atf.moviedb.MainDispatcherRule
import com.atf.moviedb.domain.model.Genre
import com.atf.moviedb.domain.usecase.GetGenreUseCase
import com.atf.moviedb.presentation.genre.GenreViewModel
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.*

class GenreViewModelTest {

    @get:Rule
    val rule =
        MainDispatcherRule()

    private val useCase =
        mockk<GetGenreUseCase>()

    @Test
    fun `genre loaded success`() =
        runTest {


            every {
                useCase()
            } returns flowOf(
                listOf(
                    Genre(
                        1,
                        "Action"
                    )
                )
            )

            val viewModel =
                GenreViewModel(
                    useCase
                )

            assertEquals(
                "Action",
                viewModel.state.value
                    .genres[0]
                    .name
            )
        }
}