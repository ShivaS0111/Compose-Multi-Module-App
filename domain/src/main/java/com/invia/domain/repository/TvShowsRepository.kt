package com.invia.domain.repository

import com.invia.domain.common.Result
import com.invia.domain.datasource.database.entities.Movie
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {
    val data: Flow<List<Movie>>
    suspend fun getTvShows(): Flow<Result<List<Movie>>>
}