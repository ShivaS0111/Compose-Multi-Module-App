package com.example.test_movie_app.presentation.viewModels

import app.cash.turbine.test
import com.example.test_movie_app.helper.DispatcherProvider
import com.example.test_movie_app.helper.TestDispatcherProvider
import com.invia.domain.common.Result
import com.invia.domain.datasource.database.entities.Movie
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.doReturn
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltAndroidTest
class MoviesViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider

    @Inject
    lateinit var viewModel: MoviesViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }


    private val data: List<Movie> by lazy {
        arrayListOf(
            Movie(id = 1, name = "Movie 1", image = null, language = null),
            Movie(id = 2, name = "Movie 2", image = null, language = null),
            Movie(id = 3, name = "Movie 3", image = null, language = null)
        )
    }
    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() =runTest {
        runTest {
            doReturn(
                flow {
                    println("========flow=========")
                    emit(Result.Success(data))
                }
            ).`when` (viewModel.useCase())
            viewModel.useCase().test {
                assertEquals( data.size, awaitItem().data?.size)
            }
        }
    }

    @Test
    fun test() {
        assertEquals(1, 1)
    }

}