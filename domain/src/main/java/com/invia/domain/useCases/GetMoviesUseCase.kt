package com.invia.domain.useCases

import com.invia.domain.common.Result
import com.invia.domain.datasource.ShowsResponse
import com.invia.domain.model.ShowsResponseItem
import kotlinx.coroutines.flow.Flow

interface GetMoviesUseCase {
    operator fun invoke(): Flow<Result<ShowsResponse>>
}