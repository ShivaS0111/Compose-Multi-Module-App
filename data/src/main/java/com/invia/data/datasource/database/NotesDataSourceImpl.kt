package com.invia.data.datasource.database

import com.invia.domain.datasource.database.dao.NotesDAO
import com.invia.domain.datasource.database.datasource.NotesDataSource
import com.invia.domain.datasource.database.entities.Note
import javax.inject.Inject

class NotesDataSourceImpl @Inject constructor(override var dao: NotesDAO) : NotesDataSource {
    override suspend fun insert(note: Note) = dao.insert(note)

    override suspend fun insert(notes: List<Note>) = dao.insert(notes)

    override  fun getAllNotes() = dao.getAllNotes()

    override  fun getNoteById(id: Long) = dao.getNoteById(id)

    override  fun getNotesBySearch(term: String) = dao.getNotesBySearch(term)

    override  fun getNotesBySearchTerm(term: String) = dao.getNotesBySearchTerm(term)

}