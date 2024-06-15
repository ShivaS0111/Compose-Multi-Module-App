import com.example.test_movie_app.Presentation.viewModels.MoviesViewModel
import com.example.test_movie_app.Presentation.viewModels.stateHolders.MovieStateHolder
import com.invia.data.useCases.GetMoviesUseCaseImpl
import com.invia.domain.datasource.ShowsResponse
import com.invia.domain.common.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.robolectric.annotation.Config
import javax.inject.Inject

//@HiltAndroidTest
//@Config(application = HiltTestApplication::class)
class MoviesViewModelTest {

    //@get:Rule
    //var hiltRule = HiltAndroidRule(this)

    /*@get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()*/

    //@Inject lateinit var useCase: GetMoviesUseCaseImpl

    //private lateinit var viewModel: MoviesViewModel

    @Before
    fun setUp() {
        //hiltRule.inject()
        //viewModel = MoviesViewModel(useCase)
    }

    @Test
    fun doTest(){

    }

    fun `getAllTvShows emits loading and success states`() = runTest {
        /*val showsResponse = mockk<ShowsResponse>()
        val successResult = Result.Success(showsResponse)

        coEvery { useCase() } returns flow {
            emit(Result.Loading())
            emit(successResult)
        }

        viewModel.getAllTvShows()

        Assert.assertEquals(MovieStateHolder(isLoading = true), viewModel.response.value)

        viewModel.getAllTvShows()

        Assert.assertEquals(MovieStateHolder(data = showsResponse), viewModel.response.value)
   */ }

    fun `getAllTvShows emits loading and error states`() = runTest {
       /* val errorMessage = "Network error"
        val errorResult = Result.Error<ShowsResponse>(errorMessage)

        coEvery { useCase() } returns flow {
            emit(Result.Loading())
            emit(errorResult)
        }

        viewModel.getAllTvShows()

        Assert.assertEquals(MovieStateHolder(isLoading = true), viewModel.response.value)

        viewModel.getAllTvShows()

        Assert.assertEquals(MovieStateHolder(error = errorMessage), viewModel.response.value)
   */ }
}
