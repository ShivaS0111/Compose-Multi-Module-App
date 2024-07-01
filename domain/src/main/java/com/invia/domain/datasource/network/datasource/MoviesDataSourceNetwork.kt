package com.invia.domain.datasource.network.datasource

import com.invia.domain.common.Result
import com.invia.domain.datasource.database.entities.Movie
import com.invia.domain.datasource.network.datasource.BaseNetworkDataSource

interface MoviesDataSourceNetwork : BaseNetworkDataSource {
    suspend fun getTvShows(): Result<List<Movie>>
}