package com.invia.domain.repository

import com.invia.domain.common.Result
import com.invia.domain.model.ShowsResponseItem
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {
    suspend fun getTvShows(): Flow<Result<List<ShowsResponseItem>>>
}