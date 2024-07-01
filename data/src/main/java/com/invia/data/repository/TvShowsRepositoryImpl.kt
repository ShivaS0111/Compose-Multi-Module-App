package com.invia.data.repository

import com.invia.domain.datasource.network.datasource.MoviesDataSource
import com.invia.domain.repository.TvShowsRepository
import javax.inject.Inject
import com.invia.domain.common.Result
import com.invia.domain.common.Result.Loading
import com.invia.domain.model.ShowsResponseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowsRepositoryImpl @Inject constructor(private val dataSource: MoviesDataSource) :
    TvShowsRepository {

    override suspend fun getTvShows() = flow {
        emit(Loading())
        try {
            val resp = dataSource.getTvShows()
            emit(resp)
        } catch (e: Exception) {
            println("===>" + e.message.toString())
            emit(Result.Error("========>"))
        }
    }

}


class MockTvShowsRepository @Inject constructor(private val mockData: List<ShowsResponseItem>) :
    TvShowsRepository {
    override suspend fun getTvShows() = flow {
        emit(Loading())
        kotlinx.coroutines.delay(2000)
        try {
            val result = Result.Success(mockData)
            emit(result)
        } catch (e: Exception) {
            println("===>" + e.message.toString())
            emit(Result.Error("========>"))
        }
    }

}