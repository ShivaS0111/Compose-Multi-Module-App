package com.invia.data.repository

import com.invia.domain.common.Result
import com.invia.domain.datasource.database.datasource.NotesDataSource
import com.invia.domain.datasource.database.entities.Movie
import com.invia.domain.datasource.database.entities.Note
import com.invia.domain.datasource.database.entities.relations.NoteWithLabels
import com.invia.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(dataSource: NotesDataSource):NotesRepository {
    override val data = dataSource.getAllNotes()

    override suspend fun getNotes()= data

}