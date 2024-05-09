package com.invia.data.useCases

import com.invia.domain.common.Result
import com.invia.domain.datasource.ShowsResponse
import com.invia.domain.repository.TvShowsRepository
import com.invia.domain.useCases.GetMoviesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(private val repository: TvShowsRepository) :
    GetMoviesUseCase {
    override operator fun invoke() = flow {
        emit(Result.Loading())
        emit(Result.Error<ShowsResponse>("===>"))
        try {
            val resp =  repository.getTvShows()
            emit( resp )
        }catch (e:Exception){
            println("===>"+e.message.toString())
            emit(Result.Error<ShowsResponse>("========>"))
        }
    }
}