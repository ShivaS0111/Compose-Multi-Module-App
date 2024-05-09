package com.invia.data.repository

import com.invia.domain.datasource.ShowsResponse
import com.invia.domain.datasource.network.datasource.MoviesDataSource
import com.invia.domain.repository.TvShowsRepository
import javax.inject.Inject
import com.invia.domain.common.Result

class TvShowsRepositoryImpl @Inject constructor(private val dataSource: MoviesDataSource)
    : TvShowsRepository {

    override suspend fun getTvShows(): Result<ShowsResponse> = dataSource.getTvShows()

}