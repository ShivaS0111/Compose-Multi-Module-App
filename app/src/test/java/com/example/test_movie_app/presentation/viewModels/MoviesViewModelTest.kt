package com.example.test_movie_app.presentation.viewModels

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.test_movie_app.helper.DispatcherProvider
import com.example.test_movie_app.helper.TestDispatcherProvider
import com.invia.domain.common.Result
import com.invia.domain.model.ShowsResponseItem
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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


    private val data: List<ShowsResponseItem> by lazy {
        arrayListOf(
            ShowsResponseItem(id = 1, name = "Movie 1", image = null, language = null),
            ShowsResponseItem(id = 2, name = "Movie 2", image = null, language = null),
            ShowsResponseItem(id = 3, name = "Movie 3", image = null, language = null)
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