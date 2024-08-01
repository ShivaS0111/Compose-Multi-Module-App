package com.example.test_movie_app.di

import com.invia.data.repository.LabelRepositoryImpl
import com.invia.data.repository.MoviesRepositoryImpl
import com.invia.data.repository.NotesRepositoryImpl
import com.invia.data.repository.TvShowsRepositoryImpl
import com.invia.domain.datasource.database.datasource.MoviesDataSourceLocal
import com.invia.domain.datasource.MoviesDataSource
import com.invia.domain.datasource.database.datasource.LabelDataSource
import com.invia.domain.datasource.database.datasource.NotesDataSource
import com.invia.domain.datasource.network.datasource.MoviesDataSourceNetwork
import com.invia.domain.repository.LabelRepository
import com.invia.domain.repository.NotesRepository
import com.invia.domain.repository.TvShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /*@Provides
    fun provideTvShowsRepository(dataSource: MoviesDataSource): TvShowsRepository =
        TvShowsRepositoryImpl(dataSource)*/

    @Provides
    fun provideMoviesRepository(
        network: MoviesDataSourceNetwork,
        local: MoviesDataSourceLocal
    ): TvShowsRepository = MoviesRepositoryImpl(network, local)

    @Provides
    fun provideNotesRepository(dataSource: NotesDataSource): NotesRepository =
        NotesRepositoryImpl(dataSource)
    @Provides
    fun provideLabelRepository(dataSource: LabelDataSource): LabelRepository =
        LabelRepositoryImpl(dataSource)

}