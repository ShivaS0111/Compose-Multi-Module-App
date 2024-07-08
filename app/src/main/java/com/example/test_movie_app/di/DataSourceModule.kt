package com.example.test_movie_app.di

import com.invia.data.datasource.MoviesDataSourceImpl
import com.invia.data.datasource.database.MoviesDataSourceLocalImpl
import com.invia.data.datasource.database.NotesDataSourceImpl
import com.invia.data.datasource.network.datasource.MoviesDataSourceNetworkImpl
import com.invia.domain.datasource.database.dao.MovieDAO
import com.invia.domain.datasource.database.datasource.MoviesDataSourceLocal
import com.invia.domain.datasource.network.ApiService
import com.invia.domain.datasource.MoviesDataSource
import com.invia.domain.datasource.database.dao.NotesDAO
import com.invia.domain.datasource.database.datasource.NotesDataSource
import com.invia.domain.datasource.network.datasource.MoviesDataSourceNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideMoviesDataSource(
        network: MoviesDataSourceNetwork,
        local: MoviesDataSourceLocal
    ): MoviesDataSource =
        MoviesDataSourceImpl(network, local)

    @Provides
    fun provideMoviesDataSourceNetwork(apiService: ApiService): MoviesDataSourceNetwork =
        MoviesDataSourceNetworkImpl(apiService)

    @Provides
    fun provideMoviesDataSourceLocal(dao: MovieDAO): MoviesDataSourceLocal =
        MoviesDataSourceLocalImpl(dao)

    @Provides
    fun provideNotesDataSourceLocal(dao: NotesDAO): NotesDataSource =
        NotesDataSourceImpl(dao)

}