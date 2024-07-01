package com.invia.data.datasource.network.datasource

import com.invia.domain.datasource.network.ApiService
import com.invia.domain.common.Result
import com.invia.domain.datasource.database.entities.Movie
import com.invia.domain.datasource.network.datasource.MoviesDataSourceNetwork
import javax.inject.Inject

class MoviesDataSourceNetworkImpl @Inject constructor(private val apiService: ApiService) :
    MoviesDataSourceNetwork {

    override suspend fun getTvShows(): Result<List<Movie>> = getResult { apiService.getTvShows() }

}