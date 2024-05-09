package com.example.test_movie_app.Presentation.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_movie_app.Presentation.viewModels.stateHolders.MovieStateHolder
import com.invia.data.useCases.GetMoviesUseCaseImpl
import com.invia.domain.common.Result
import com.invia.domain.datasource.ShowsResponse
import com.invia.domain.useCases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(val useCase: GetMoviesUseCaseImpl) : ViewModel() {
    //private var _response= MutableLiveData<List<ShowsResponseItem>>()
    private var _response = mutableStateOf(MovieStateHolder())
    val response: State<MovieStateHolder>
        get() = _response

    init {
        getAllTvShows()
    }

    private fun getAllTvShows() {
        viewModelScope.launch {
            /*  useCase().run {
                if(this.isSuccessful){
                    _response.value= this.body()!!
                }else{
                    Log.d("tag", "getAllTvShows Error: ${this.code()}")
                }
            }*/
            useCase().collect {
                when (it) {
                    is Result.Loading -> {
                        _response.value = MovieStateHolder(isLoading = true)
                    }

                    is Result.Success<*> -> {
                        if ( it.data is ShowsResponse) _response.value = MovieStateHolder(data = it.data as ShowsResponse)
                    }

                    is Result.Error -> {
                        _response.value = MovieStateHolder(error = it.message.toString())
                    }
                }

            }

        }
    }
}