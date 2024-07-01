package com.example.test_movie_app.di

import com.invia.data.useCases.GetMoviesUseCaseImpl
import com.invia.domain.repository.TvShowsRepository
import com.invia.domain.useCases.GetMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(repository: TvShowsRepository): GetMoviesUseCase =
        GetMoviesUseCaseImpl(repository)

}