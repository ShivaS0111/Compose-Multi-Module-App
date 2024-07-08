package com.invia.domain.datasource.database.datasource

import com.invia.domain.datasource.database.dao.NotesDAO
import com.invia.domain.datasource.database.entities.Note
import com.invia.domain.datasource.database.entities.relations.NoteWithLabels
import kotlinx.coroutines.flow.Flow

interface NotesDataSource {

    var dao:NotesDAO
    suspend fun insert(note: Note):Long

    suspend fun insert(notes: List<Note>):List<Long>

     fun getAllNotes(): Flow<List<NoteWithLabels>>

     fun getNoteById(id: Long): Flow<List<NoteWithLabels>>

     fun getNotesBySearch(term: String): Flow<List<NoteWithLabels>>

     fun getNotesBySearchTerm(term: String): Flow<List<NoteWithLabels>>
}