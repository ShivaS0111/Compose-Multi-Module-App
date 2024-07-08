package com.example.test_movie_app

import com.example.test_movie_app.presentation.viewModels.MoviesViewModel
import com.example.test_movie_app.presentation.viewModels.stateHolders.StateHolder
import com.invia.domain.common.Result
import com.invia.domain.datasource.database.entities.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MvUnitTest {

    @get:Rule(order = 1)
    var instantTaskExecutorRule = CoroutineDispatcherRule()

    private lateinit var viewModel: MoviesViewModel

    private  val data: List<Movie> by lazy {
        arrayListOf(
            Movie(id = 1, name = "Movie 1", image = null, language = null),
            Movie(id = 2, name = "Movie 2", image = null, language = null),
            Movie(id = 3, name = "Movie 3", image = null, language = null)
        )
    }
    @Before
    fun setup() {
        //MockitoAnnotations.openMocks(this)
        viewModel = MoviesViewModel(mockk())
    }

    @Test
    fun testAssert() {

        assert(true)
    }


    @Test
    fun `getAllTvShows emits loading and success states`() = runTest{
        // Mock repository response
        coEvery { viewModel.useCaseInvoke()} returns  flow {
            emit(Result.Success(data))
        }

        // Trigger the ViewModel method
        viewModel.getAllTvShows()

        coVerify (atLeast = 1){
            viewModel.useCaseInvoke()
        }

        // Verify that the response LiveData is in the loading state
        Assert.assertTrue(viewModel.response.value.isLoading)
        Assert.assertEquals(viewModel.response.value, StateHolder(isLoading = true))

        println(viewModel.response.value.data)
        // Verify that the LiveData contains the correct data after the repository call
        Assert.assertEquals(StateHolder(data = data), viewModel.response.value.data)
        verify(exactly = 0) {
            viewModel.response.value
        }
    }

    @Test
    fun `getAllTvShows emits loading and error states`() = runTest {
        val errorMessage = "Network error"

        // Mock repository response
        coEvery { viewModel.useCase.invoke() } returns flow {
            emit(Result.Error(errorMessage))
        }

        // Trigger the ViewModel method
        viewModel.getAllTvShows()

        // Verify that the response LiveData is in the loading state
        Assert.assertTrue(viewModel.response.value.isLoading)

        // Verify that the LiveData contains the correct error message after the repository call
        Assert.assertEquals(StateHolder(error = errorMessage), viewModel.response.value)
    }

}
