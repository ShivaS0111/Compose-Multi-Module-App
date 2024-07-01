package com.invia.data.datasource.network.datasource

import com.invia.domain.datasource.network.ApiService
import com.invia.domain.common.Result
import com.invia.domain.datasource.network.datasource.MoviesDataSource
import com.invia.domain.model.ShowsResponseItem
import javax.inject.Inject

class MoviesDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    MoviesDataSource {

    override suspend fun getTvShows(): Result<List<ShowsResponseItem>> =
        getResult { apiService.getTvShows() }

}