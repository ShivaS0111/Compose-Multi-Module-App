package com.invia.domain.useCases

import com.invia.domain.common.Result
import com.invia.domain.model.ShowsResponseItem
import kotlinx.coroutines.flow.Flow

interface GetMoviesUseCase {
    suspend operator fun invoke(): Flow<Result<List<ShowsResponseItem>>>
}