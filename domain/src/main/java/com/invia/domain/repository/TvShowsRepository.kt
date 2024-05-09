package com.invia.domain.repository

import com.invia.domain.datasource.ShowsResponse
import com.invia.domain.common.Result

interface TvShowsRepository {
    suspend fun getTvShows(): Result<ShowsResponse>
}