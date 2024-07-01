package com.invia.data.repository

import com.invia.domain.datasource.MoviesDataSource
import com.invia.domain.repository.TvShowsRepository
import javax.inject.Inject
import com.invia.domain.common.Result
import com.invia.domain.common.Result.Loading
import com.invia.domain.datasource.database.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowsRepositoryImpl @Inject constructor(
    private val dataSource: MoviesDataSource
) : TvShowsRepository {

    override val data: Flow<List<Movie>> = dataSource.data

    override suspend fun getTvShows() = dataSource.getTvShows()

}


class MockTvShowsRepository @Inject constructor(private val mockData: List<Movie>) :
    TvShowsRepository {

    override val data: Flow<List<Movie>> = flow { emit(mockData) }

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