package com.invia.domain.datasource.network.datasource

import com.invia.domain.common.Result
import com.invia.domain.datasource.ShowsResponse

interface MoviesDataSource :BaseNetworkDataSource {
    suspend fun getTvShows(): Result<ShowsResponse>
}