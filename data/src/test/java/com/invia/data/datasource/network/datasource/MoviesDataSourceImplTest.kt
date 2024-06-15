package com.invia.data.datasource.network.datasource

import com.invia.domain.datasource.ShowsResponse
import com.invia.domain.datasource.network.ApiService
import com.invia.domain.common.Result
import com.invia.domain.datasource.network.datasource.MoviesDataSource
import javax.inject.Inject

class MoviesDataSourceImplTest @Inject constructor(private val apiService: ApiService)
    : MoviesDataSource {

    override suspend fun getTvShows(): Result<ShowsResponse> = getResult { apiService.getTvShows() }

}