package com.invia.domain.datasource.network.datasource

import com.invia.domain.common.Result
import com.invia.domain.model.ShowsResponseItem

interface MoviesDataSource :BaseNetworkDataSource {
    suspend fun getTvShows(): Result<List<ShowsResponseItem>>
}