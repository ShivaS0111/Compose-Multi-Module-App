package com.invia.data.useCases

import com.invia.domain.repository.TvShowsRepository
import com.invia.domain.useCases.GetMoviesUseCase
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(private val repository: TvShowsRepository) :
    GetMoviesUseCase {
    override suspend operator fun invoke() = repository.getTvShows()
}