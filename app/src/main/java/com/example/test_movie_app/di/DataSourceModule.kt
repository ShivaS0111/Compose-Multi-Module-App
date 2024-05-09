package com.example.test_movie_app.di

import com.invia.data.datasource.network.ApiServiceImpl
import com.invia.data.datasource.network.datasource.MoviesDataSourceImpl
import com.invia.domain.datasource.network.ApiService
import com.invia.domain.datasource.network.datasource.MoviesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideMoviesDataSource(apiService: ApiService): MoviesDataSource =
        MoviesDataSourceImpl(apiService)

}