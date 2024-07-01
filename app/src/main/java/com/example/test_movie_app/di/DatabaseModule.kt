package com.example.test_movie_app.di

import android.content.Context
import androidx.room.Room
import com.invia.domain.datasource.database.AppDatabase
import com.invia.domain.datasource.database.dao.LabelDAO
import com.invia.domain.datasource.database.dao.LabelledNotesDAO
import com.invia.domain.datasource.database.dao.MovieDAO
import com.invia.domain.datasource.database.dao.NotesDAO
import com.invia.domain.datasource.database.entities.LabelledNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideLocalDataBase( @ApplicationContext applicationContext: Context): AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "Notes"
    ).build()

    @Provides
    fun provideMoviesDAO( db:AppDatabase):MovieDAO = db.moviesDAO()
    @Provides
    fun provideNotesDAO( db:AppDatabase):NotesDAO = db.notesDAO()
    @Provides
    fun provideLabelDAO( db:AppDatabase):LabelDAO = db.labelsDAO()
    @Provides
    fun provideLabelledNotesDAO( db:AppDatabase):LabelledNotesDAO = db.labelledNotesDao()
}