package com.invia.data.repository

import com.invia.domain.common.Result
import com.invia.domain.datasource.database.datasource.MoviesDataSourceLocal
import com.invia.domain.datasource.database.entities.Movie
import com.invia.domain.datasource.network.datasource.MoviesDataSourceNetwork
import com.invia.domain.repository.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private var networkDatasource: MoviesDataSourceNetwork,
    private var localDataSource: MoviesDataSourceLocal
) : TvShowsRepository {
    override val data: Flow<List<Movie>>
        get() = localDataSource.getAllMovies()

    override suspend fun getTvShows(): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading())

        println("==>step1")
        networkDatasource.getTvShows().let {
            println("==>step1 success")
            (it as Result.Success).data?.let { data ->
                println("==>insert: ${data.size}")
                localDataSource.insert(data)
            }
            println("==>step1 complete")
        }

        println("==>steps completed")
    }
}