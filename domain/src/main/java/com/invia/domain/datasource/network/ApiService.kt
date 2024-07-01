package com.invia.domain.datasource.network

import com.invia.domain.model.ShowsResponseItem
import retrofit2.Response

interface ApiService {
    suspend fun getTvShows():Response<List<ShowsResponseItem>>
}