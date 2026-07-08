package com.atf.moviedb.domain

import app.cash.turbine.test
import com.atf.moviedb.domain.model.Genre
import com.atf.moviedb.domain.repository.MovieRepository
import com.atf.moviedb.domain.usecase.GetGenreUseCase
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

//class GetGenreUseCaseTest {
//
//    private val repository =
//        mockk<MovieRepository>()
//
//    private val useCase =
//        GetGenreUseCase(
//            repository
//        )
//
//    @Test
//    fun `get genre success`() =
//        runTest {
//
//            val dummy =
//                listOf(
//                    Genre(
//                        1,
//                        "Action"
//                    )
//                )
//
//            every {
//                repository.getGenres()
//            } returns flowOf(dummy)
//
//            useCase()
//                .test {
//
//                    val result =
//                        awaitItem()
//
//                    assertEquals(
//                        "Action",
//                        result[0].name
//                    )
//
//                    cancelAndIgnoreRemainingEvents()
//                }
//        }
//
//    @Test
//    fun `get genre empty`() =
//        runTest {
//
//            every {
//                repository.getGenres()
//            } returns flowOf(
//                emptyList()
//            )
//
//            useCase()
//                .test {
//
//                    val result =
//                        awaitItem()
//
//                    assertEquals(
//                        true,
//                        result.isEmpty()
//                    )
//
//                    cancelAndIgnoreRemainingEvents()
//                }
//        }
//}