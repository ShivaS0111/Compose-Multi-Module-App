import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.test_movie_app.CoroutineDispatcherRule
import com.example.test_movie_app.presentation.viewModels.MoviesViewModel
import com.example.test_movie_app.presentation.viewModels.stateHolders.MovieStateHolder
import com.invia.data.useCases.GetMoviesUseCaseImpl

import com.invia.domain.common.Result
import com.invia.domain.model.ShowsResponseItem
import com.invia.domain.repository.TvShowsRepository
import com.invia.domain.useCases.GetMoviesUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var repository: TvShowsRepository

    private val data: List<ShowsResponseItem> by lazy {
        arrayListOf(
            ShowsResponseItem(id = 1, name = "Movie 1", image = null, language = null),
            ShowsResponseItem(id = 2, name = "Movie 2", image = null, language = null),
            ShowsResponseItem(id = 3, name = "Movie 3", image = null, language = null)
        )
    }

    @Before
    fun setUp() {
        val useCase = GetMoviesUseCaseImpl(repository)
        viewModel = MoviesViewModel(useCase)
    }

    @Test
    fun `getAllTvShows emits loading and success states`() {
        // Mock repository response
        coEvery { viewModel.useCase.invoke() } returns  flow {
            emit(Result.Success(data))
        }

        // Trigger the ViewModel method
        viewModel.getAllTvShows()
        coVerify {
            viewModel.useCase.invoke()
        }

        // Verify that the response LiveData is in the loading state
        Assert.assertTrue(viewModel.response.value.isLoading)
        Assert.assertEquals(viewModel.response.value, MovieStateHolder(isLoading = true))

        println(viewModel.response.value.data)
        // Verify that the LiveData contains the correct data after the repository call
        Assert.assertEquals(MovieStateHolder(data = data), viewModel.response.value.data)
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
        Assert.assertEquals(MovieStateHolder(error = errorMessage), viewModel.response.value)
    }

}