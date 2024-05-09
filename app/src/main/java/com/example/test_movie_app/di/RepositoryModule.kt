package com.example.test_movie_app.di

import com.invia.data.repository.TvShowsRepositoryImpl
import com.invia.domain.datasource.network.datasource.MoviesDataSource
import com.invia.domain.repository.TvShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMoviesRepository(dataSource: MoviesDataSource): TvShowsRepository =
        TvShowsRepositoryImpl(dataSource)

}