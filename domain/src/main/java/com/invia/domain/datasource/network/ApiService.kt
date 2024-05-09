package com.invia.domain.datasource.network

import com.invia.domain.datasource.ShowsResponse
import retrofit2.Response

interface ApiService {
    suspend fun getTvShows():Response<ShowsResponse>
}